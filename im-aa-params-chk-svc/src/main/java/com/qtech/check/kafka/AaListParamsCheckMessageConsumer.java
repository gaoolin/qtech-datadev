package com.qtech.check.kafka;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.qtech.check.algorithm.AaListParamsComparator;
import com.qtech.check.pojo.AaListParamsCheckResult;
import com.qtech.check.pojo.AaListParamsParsed;
import com.qtech.check.pojo.AaListParamsStdModel;
import com.qtech.check.pojo.AaListParamsStdModelInfo;
import com.qtech.check.service.IAaListParamsStdModelInfoService;
import com.qtech.check.service.IAaListParamsStdModelService;
import com.qtech.check.utils.RedisUtil;
import com.qtech.common.utils.DateUtils;
import org.apache.commons.lang3.tuple.Triple;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Component;

import java.text.SimpleDateFormat;
import java.util.List;
import java.util.Map;

import static com.qtech.check.constant.ComparisonConstants.*;

/**
 * author :  gaozhilin
 * email  :  gaoolin@gmail.com
 * date   :  2024/05/17 16:04:05
 * desc   :
 */

@Component
public class AaListParamsCheckMessageConsumer {
    private static final Logger logger = LoggerFactory.getLogger(AaListParamsCheckMessageConsumer.class);
    private final ObjectMapper objectMapper = new ObjectMapper()
            .configure(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS, false)
            .setDateFormat(new SimpleDateFormat("yyyy-MM-dd HH:mm:ss"));

    @Autowired
    private KafkaTemplate<String, Object> kafkaTemplate;
    @Autowired
    private IAaListParamsStdModelService aaListParamsStdModelService;
    @Autowired
    private IAaListParamsStdModelInfoService aaListParamsStdModelInfoService;
    @Autowired
    private RedisUtil redisUtil;
    @Autowired
    private RabbitTemplate rabbitTemplate;

    @KafkaListener(topics = "qtech_im_aa_list_parsed_topic", groupId = "aaList-do-check-group", containerFactory = "kafkaListenerContainerFactory")
    public void listenBatchMessages(List<ConsumerRecord<String, String>> records) throws JsonProcessingException {
        AaListParamsStdModel modelObj = null;
        AaListParamsCheckResult aaListParamsCheckResult = new AaListParamsCheckResult();
        aaListParamsCheckResult.setSource("aa-list");
        for (ConsumerRecord<String, String> record : records) {
            // 解析和处理消息
            // String key = record.key();
            String value = record.value();
            AaListParamsParsed actualObj = objectMapper.readValue(value, new TypeReference<AaListParamsParsed>() {
            });

            String prodType = actualObj.getProdType();
            AaListParamsStdModelInfo modelInfoObj = redisUtil.getAaListParamsStdModelInfo(REDIS_COMPARISON_MODEL_INFO_KEY_SUFFIX + prodType);
            if (modelInfoObj == null) {
                AaListParamsStdModelInfo stdModelInfoParam = new AaListParamsStdModelInfo();
                stdModelInfoParam.setProdType(actualObj.getProdType());
                modelInfoObj = aaListParamsStdModelInfoService.selectOneAaListParamsStdModelInfo(stdModelInfoParam);
                if (modelInfoObj == null) {
                    aaListParamsCheckResult.setSimId(actualObj.getSimId());
                    aaListParamsCheckResult.setProdType(actualObj.getProdType());
                    aaListParamsCheckResult.setChkDt(DateUtils.getNowDate());
                    aaListParamsCheckResult.setCode(1);
                    // 模版信息表中没有对应机型的模版信息（1.没有标准模版 2.模版明细存在，而模版信息丢失）
                    aaListParamsCheckResult.setDescription("Missing Template Information.");
                    kafkaTemplate.send("qtech_im_aa_list_checked_topic", objectMapper.writeValueAsString(aaListParamsCheckResult));
                    logger.warn(">>>>> Missing template info for prodType: {}. skip action.", actualObj.getProdType());

                    rabbitTemplate.convertAndSend("qtechImExchange", "eqReverseCtrlInfoQueue", objectMapper.writeValueAsString(aaListParamsCheckResult));
                    logger.info(">>>>> AA list Result sent to RabbitMQ!");
                    continue;
                }
                redisUtil.saveAaListParamsStdModelInfo(REDIS_COMPARISON_MODEL_INFO_KEY_SUFFIX + actualObj.getProdType(), modelInfoObj);
            }

            if (modelInfoObj.getStatus() == 0) {
                aaListParamsCheckResult.setSimId(actualObj.getSimId());
                aaListParamsCheckResult.setProdType(actualObj.getProdType());
                aaListParamsCheckResult.setChkDt(DateUtils.getNowDate());
                aaListParamsCheckResult.setCode(6);
                // 模版信息表中有对应机型的模版信息，但模版信息处于离线状态
                aaListParamsCheckResult.setDescription("Template Offline.");
                redisUtil.saveAaListParamsStdModelInfo(REDIS_COMPARISON_MODEL_INFO_KEY_SUFFIX + actualObj.getProdType(), modelInfoObj);
                kafkaTemplate.send("qtech_im_aa_list_checked_topic", objectMapper.writeValueAsString(aaListParamsCheckResult));
                logger.warn(">>>>> Missing template info for prodType: {}. skip action.", actualObj.getProdType());

                rabbitTemplate.convertAndSend("qtechImExchange", "eqReverseCtrlInfoQueue", objectMapper.writeValueAsString(aaListParamsCheckResult));
                logger.info(">>>>> AA list Result sent to RabbitMQ!");
                continue;
            }

            modelObj = redisUtil.getAaListParamsStdModel(REDIS_COMPARISON_MODEL_KEY_PREFIX + actualObj.getProdType());
            if (modelObj == null) {
                AaListParamsStdModel stdModelParam = new AaListParamsStdModel();
                stdModelParam.setProdType(actualObj.getProdType());
                modelObj = aaListParamsStdModelService.selectOneAaListParamsStdModel(stdModelParam);
                if (modelObj == null) {
                    aaListParamsCheckResult.setSimId(actualObj.getSimId());
                    aaListParamsCheckResult.setProdType(actualObj.getProdType());
                    aaListParamsCheckResult.setChkDt(DateUtils.getNowDate());
                    aaListParamsCheckResult.setCode(7);
                    // 模版信息表中有对应机型的信息，但是模版明细表中没有模版的明细。
                    aaListParamsCheckResult.setDescription("Missing Template Detail.");
                    kafkaTemplate.send("qtech_im_aa_list_checked_topic", objectMapper.writeValueAsString(aaListParamsCheckResult));
                    logger.warn(">>>>> Can not find standard template for prodType: {}. skip action.", actualObj.getProdType());

                    rabbitTemplate.convertAndSend("qtechImExchange", "eqReverseCtrlInfoQueue", objectMapper.writeValueAsString(aaListParamsCheckResult));
                    logger.info(">>>>> AA list Result sent to RabbitMQ!");
                    continue;
                }
                redisUtil.saveAaListParamsStdModel(REDIS_COMPARISON_MODEL_KEY_PREFIX + actualObj.getProdType(), modelObj);
            }

            Triple<Map<String, Map.Entry<Object, Object>>, Map<String, Object>, Map<String, Object>> result =
                    AaListParamsComparator.compareObjectsWithStandardAndActual(modelObj, actualObj, PROPERTIES_TO_COMPARE, PROPERTIES_TO_COMPUTE);

            Map<String, Map.Entry<Object, Object>> inconsistentProperties = result.getLeft();
            Map<String, Object> emptyInActual = result.getMiddle();
            Map<String, Object> emptyInStandard = result.getRight();

            // inconsistentProperties、emptyInActual内部数据才是异常信息。emptyInStandard非异常信息仅提示即可
            // inconsistentProperties为实际值和模版值不一致
            // emptyInActual为实际为空，但模版不为空
            // emptyInStandard为标准为空，但实际不为空
            // 输出不一致的属性
            // 输出空在实际参数对象的属性
            // 输出空在标准参数对象的属性

            aaListParamsCheckResult.setSimId(actualObj.getSimId());
            aaListParamsCheckResult.setProdType(actualObj.getProdType());
            aaListParamsCheckResult.setChkDt(DateUtils.getNowDate());
            StringBuilder description = new StringBuilder();

            if (inconsistentProperties.isEmpty() && emptyInActual.isEmpty()) {
                // 单独判断emptyInStandard的情况
                if (!emptyInStandard.isEmpty()) {
                    aaListParamsCheckResult.setCode(4);

                    emptyInStandard.forEach((prop, val) -> {
                        description.append(prop).append("+").append(";");
                    });
                    aaListParamsCheckResult.setDescription(description.toString());
                } else {
                    aaListParamsCheckResult.setCode(0);
                    aaListParamsCheckResult.setDescription("Ok.");
                }
            } else if (inconsistentProperties.isEmpty() && emptyInStandard.isEmpty()) {
                // 单独判断emptyInActual的情况, 前面已经判断过emptyInActual为空的情况，仅为emptyInActual不为空的情况
                aaListParamsCheckResult.setCode(2);
                emptyInActual.forEach((prop, val) -> {
                    description.append(prop).append("-").append(";");
                });
                aaListParamsCheckResult.setDescription(description.toString());
            } else if (emptyInActual.isEmpty() && emptyInStandard.isEmpty()) {
                // 单独判断inconsistentProperties的情况
                aaListParamsCheckResult.setCode(3);
                inconsistentProperties.forEach((prop, map) -> {
                    if (map != null) {
                        Object modelVal = map.getKey();
                        Object actualVal = map.getValue();
                        description.append(prop).append(":").append(actualVal).append("!=").append(modelVal).append(";");
                    }
                });
                aaListParamsCheckResult.setDescription(description.toString());
            } else {
                aaListParamsCheckResult.setCode(5);
                if (!emptyInActual.isEmpty()) {
                    emptyInActual.forEach((prop, val) -> {
                        description.append(prop).append("-").append(";");
                    });
                }
                if (!emptyInStandard.isEmpty()) {
                    emptyInStandard.forEach((prop, val) -> {
                        description.append(prop).append("+").append(";");
                    });
                }
                if (!inconsistentProperties.isEmpty()) {
                    inconsistentProperties.forEach((prop, map) -> {
                        if (map != null) {
                            Object modelVal = map.getKey();
                            Object actualVal = map.getValue();
                            description.append(prop).append(":").append(actualVal).append("!=").append(modelVal).append(";");
                        }
                    });
                }
                aaListParamsCheckResult.setDescription(description.toString());
            }
            // } else {
            //     StringBuilder description = new StringBuilder();
            //     if (!inconsistentProperties.isEmpty()) {
            //         inconsistentProperties.forEach((prop, map) -> {
            //             if (map != null) {
            //                 Object modelVal = map.getKey();
            //                 Object actualVal = map.getValue();
            //                 description.append(prop).append(":").append(actualVal).append("!=").append(modelVal).append(";");
            //             }
            //         });
            //     }
            //
            //     if (!emptyInActual.isEmpty()) {
            //         emptyInActual.forEach((prop, val) -> {
            //             description.append(prop).append("-").append(";");
            //         });
            //     }
            //
            //     if (!emptyInStandard.isEmpty()) {
            //         emptyInStandard.forEach((prop, val) -> {
            //             description.append(prop).append("+").append(";");
            //         });
            //     }
            //
            //     aaListParamsCheckResult.setSimId(actualObj.getSimId());
            //     aaListParamsCheckResult.setProdType(actualObj.getProdType());
            //     aaListParamsCheckResult.setChkDt(DateUtils.getNowDate());
            //     aaListParamsCheckResult.setCode(1);
            //     aaListParamsCheckResult.setDescription(description.toString());
            // }

            String jsonString = objectMapper.writeValueAsString(aaListParamsCheckResult);

            kafkaTemplate.send("qtech_im_aa_list_checked_topic", jsonString);
            logger.info(">>>>> Check message complete!");

            // 发送结果到 RabbitMQ
            rabbitTemplate.convertAndSend("qtechImExchange", "eqReverseCtrlInfoQueue", jsonString);
            logger.info(">>>>> AA list Result sent to RabbitMQ!");
        }
    }
}