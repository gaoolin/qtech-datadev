package com.qtech.check.kafka;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.TypeReference;
import com.qtech.check.algorithm.AaListParamsComparator;
import com.qtech.check.pojo.AaListParams;
import com.qtech.check.pojo.AaListParamsCheckResult;
import com.qtech.check.pojo.AaListParamsStdModel;
import com.qtech.check.service.IAaListParamsStdModelService;
import com.qtech.check.utils.RedisUtil;
import com.qtech.common.utils.DateUtils;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.tuple.Triple;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.config.ConcurrentKafkaListenerContainerFactory;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Map;

import static com.qtech.check.constant.ComparisonConstants.*;

/**
 * author :  gaozhilin
 * email  :  gaoolin@gmail.com
 * date   :  2024/05/17 16:04:05
 * desc   :
 */

@Slf4j
@Component
public class AaListParamsCheckMessageConsumer {

    @Autowired
    private ConcurrentKafkaListenerContainerFactory<String, String> kafkaListenerContainerFactory;

    @Autowired
    private KafkaTemplate<String, Object> kafkaTemplate;

    @Autowired
    private IAaListParamsStdModelService aaListParamsStdModelService;

    @Autowired
    private RedisUtil redisUtil;

    @KafkaListener(topics = "qtech_im_aa_list_parsed_topic", groupId = "aaList-do-check-group", containerFactory = "kafkaListenerContainerFactory")
    public void listenBatchMessages(List<ConsumerRecord<String, String>> records) {
        AaListParamsStdModel modelObj = null;
        AaListParamsCheckResult aaListParamsCheckResult = new AaListParamsCheckResult();
        for (ConsumerRecord<String, String> record : records) {
            // 解析和处理消息
            // String key = record.key();
            String value = record.value();
            AaListParams actualObj = JSON.parseObject(value, new TypeReference<AaListParams>() {
            }.getType());
            modelObj = redisUtil.getMessage(REDIS_COMPARISON_MODEL_KEY_PREFIX + actualObj.getProdType());
            if (modelObj == null) {
                AaListParamsStdModel stdModelParam = new AaListParamsStdModel();
                stdModelParam.setProdType(actualObj.getProdType());
                modelObj = aaListParamsStdModelService.selectOneAaListParamsStdModel(stdModelParam);
                if (modelObj == null) {
                    aaListParamsCheckResult.setSimId(actualObj.getSimId());
                    aaListParamsCheckResult.setProdType(actualObj.getProdType());
                    aaListParamsCheckResult.setCheckDt(DateUtils.getNowDate());
                    aaListParamsCheckResult.setStatusCode(2);
                    aaListParamsCheckResult.setDescription("Missing Template.");
                    kafkaTemplate.send("qtech_im_aa_list_checked_topic", JSON.toJSONString(aaListParamsCheckResult));
                    log.warn(">>>>> Can not find standard template for prodType: {}. skip action.", actualObj.getProdType());
                    continue;
                }
                redisUtil.saveMessage(REDIS_COMPARISON_MODEL_KEY_PREFIX + actualObj.getProdType(), modelObj);
            }

            Triple<Map<String, Map.Entry<Object, Object>>, Map<String, Object>, Map<String, Object>> result =
                    AaListParamsComparator.compareObjectsWithStandardAndActual(modelObj, actualObj, PROPERTIES_TO_COMPARE, PROPERTIES_TO_COMPUTE);

            Map<String, Map.Entry<Object, Object>> inconsistentProperties = result.getLeft();
            Map<String, Object> emptyInActual = result.getMiddle();
            Map<String, Object> emptyInStandard = result.getRight();

            // inconsistentProperties、emptyInActual内部数据才是异常信息。emptyInStandard非异常信息仅提示即可
            // inconsistentProperties为实际值和模版值不一致
            // emptyInActual为实际为空，但模版不为空
            // 输出不一致的属性
            // 输出空在实际参数对象的属性
            // 输出空在标准参数对象的属性

            if (inconsistentProperties.isEmpty() && emptyInActual.isEmpty()) {
                aaListParamsCheckResult.setSimId(actualObj.getSimId());
                aaListParamsCheckResult.setProdType(actualObj.getProdType());
                aaListParamsCheckResult.setCheckDt(DateUtils.getNowDate());
                aaListParamsCheckResult.setStatusCode(0);
                if (!emptyInStandard.isEmpty()) {
                    StringBuilder description = new StringBuilder();
                    emptyInStandard.forEach((prop, val) -> {
                        description.append(prop).append("+").append(";");
                    });
                    aaListParamsCheckResult.setDescription(description.toString());
                } else {
                    aaListParamsCheckResult.setDescription("Ok.");
                }
            } else {
                StringBuilder description = new StringBuilder();
                if (!inconsistentProperties.isEmpty()) {
                    inconsistentProperties.forEach((prop, map) -> {
                        if (map != null) {
                            Object modelVal = map.getKey();
                            Object actualVal = map.getValue();
                            description.append(prop).append(":").append(actualVal).append("!=").append(modelVal).append(";");
                        }
                    });
                }

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

                aaListParamsCheckResult.setSimId(actualObj.getSimId());
                aaListParamsCheckResult.setProdType(actualObj.getProdType());
                aaListParamsCheckResult.setCheckDt(DateUtils.getNowDate());
                aaListParamsCheckResult.setStatusCode(1);
                aaListParamsCheckResult.setDescription(description.toString());
            }
            kafkaTemplate.send("qtech_im_aa_list_checked_topic", JSON.toJSONString(aaListParamsCheckResult));
            log.info(">>>>> Check message complete!");
        }
    }
}