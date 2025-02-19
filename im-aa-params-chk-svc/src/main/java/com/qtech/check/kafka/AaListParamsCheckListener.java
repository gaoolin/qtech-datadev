package com.qtech.check.kafka;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.qtech.check.algorithm.AaListParamsComparator;
import com.qtech.check.pojo.AaListParamsParsed;
import com.qtech.check.pojo.AaListParamsStdTemplate;
import com.qtech.check.pojo.AaListParamsStdTemplateInfo;
import com.qtech.check.pojo.EqReverseCtrlInfo;
import com.qtech.check.utils.RedisUtil;
import com.qtech.common.utils.DateUtils;
import org.apache.commons.lang3.tuple.Triple;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Map;

import static com.qtech.share.aa.constant.ComparisonConstants.*;

/**
 * author :  gaozhilin
 * email  :  gaoolin@gmail.com
 * date   :  2025/02/18
 * desc   :  AA List 参数点检 Kafka 监听器
 */

@Component
public class AaListParamsCheckListener {
    private static final Logger logger = LoggerFactory.getLogger(AaListParamsCheckListener.class);
    @Autowired
    @Qualifier("dateFormat1ObjectMapper")
    private ObjectMapper objectMapper;
    @Autowired
    private KafkaTemplate<String, Object> kafkaTemplate;
    @Autowired
    private RedisUtil redisUtil;
    @Autowired
    private RabbitTemplate rabbitTemplate;

    private static int getStatusCode(Triple<Map<String, Map.Entry<Object, Object>>, Map<String, Object>, Map<String, Object>> result) {
        boolean hasMissingParams = !result.getMiddle().isEmpty();
        boolean hasExtraParams = !result.getRight().isEmpty();
        boolean hasIncorrectValues = !result.getLeft().isEmpty();

        // 确定状态码
        int statusCode;
        if (!hasMissingParams && !hasExtraParams && !hasIncorrectValues) {
            statusCode = 0;  // 正常
        } else if (hasMissingParams && !hasExtraParams && !hasIncorrectValues) {
            statusCode = 2;  // 少参数
        } else if (!hasMissingParams && hasExtraParams && !hasIncorrectValues) {
            statusCode = 4;  // 多参数
        } else if (!hasMissingParams && !hasExtraParams && hasIncorrectValues) {
            statusCode = 3;  // 参数值异常
        } else {
            statusCode = 5;  // 复合异常
        }
        return statusCode;
    }

    @KafkaListener(topics = "qtech_im_aa_list_parsed_topic", groupId = "aaList-do-check-group", containerFactory = "kafkaListenerContainerFactory")
    public void listenBatchMessages(List<ConsumerRecord<String, String>> records) throws JsonProcessingException {
        for (ConsumerRecord<String, String> record : records) {
            processMessage(record);
        }
    }

    private void processMessage(ConsumerRecord<String, String> record) throws JsonProcessingException {
        String messageKey = record.key();
        AaListParamsParsed actualObj = objectMapper.readValue(record.value(), new TypeReference<AaListParamsParsed>() {
        });
        EqReverseCtrlInfo checkResult = initializeCheckResult(actualObj);

        // 获取模板信息
        AaListParamsStdTemplateInfo modelInfoObj = getModelInfo(actualObj.getProdType());
        AaListParamsStdTemplate modelObj = getModel(actualObj.getProdType());

        // 判断模板信息
        if (modelInfoObj == null) {
            handleResult(checkResult, 1, "Missing Template Information.", messageKey);
            return;
        }
        if (modelInfoObj.getStatus() == 0) {
            handleResult(checkResult, 6, "Template Offline.", messageKey);
            return;
        }
        if (modelObj == null) {
            handleResult(checkResult, 7, "Missing Template Detail.", messageKey);
            return;
        }

        // 进行参数对比
        compareAndProcessResults(modelObj, actualObj, checkResult, messageKey);
    }

    private EqReverseCtrlInfo initializeCheckResult(AaListParamsParsed actualObj) {
        EqReverseCtrlInfo checkResult = new EqReverseCtrlInfo();
        checkResult.setSource("aa-list");
        checkResult.setSimId(actualObj.getSimId());
        checkResult.setProdType(actualObj.getProdType());
        checkResult.setChkDt(DateUtils.getNowDate());
        return checkResult;
    }

    // FIXME : 当redis没有模版信息时，更严谨的逻辑是到数据库中查询 模版信息或模版详情，并更新到Redis中
    private AaListParamsStdTemplateInfo getModelInfo(String prodType) {
        return redisUtil.getAaListParamsStdModelInfo(REDIS_COMPARISON_MODEL_INFO_KEY_SUFFIX + prodType);
    }

    private AaListParamsStdTemplate getModel(String prodType) {
        return redisUtil.getAaListParamsStdModel(REDIS_COMPARISON_MODEL_KEY_PREFIX + prodType);
    }

    private void compareAndProcessResults(AaListParamsStdTemplate modelObj, AaListParamsParsed actualObj, EqReverseCtrlInfo checkResult, String messageKey) throws JsonProcessingException {
        Triple<Map<String, Map.Entry<Object, Object>>, Map<String, Object>, Map<String, Object>> result = AaListParamsComparator.compareObjectsWithStandardAndActual(modelObj, actualObj, PROPERTIES_TO_COMPARE, PROPERTIES_TO_COMPUTE);

        int statusCode = getStatusCode(result);

        checkResult.setCode(statusCode);
        checkResult.setDescription(buildDescription(result));

        sendResult(checkResult, messageKey);
    }

    private String buildDescription(Triple<Map<String, Map.Entry<Object, Object>>, Map<String, Object>, Map<String, Object>> result) {
        StringBuilder description = new StringBuilder();

        result.getMiddle().keySet().stream().sorted().forEach(prop -> description.append(prop).append("-").append(";"));

        result.getRight().keySet().stream().sorted().forEach(prop -> description.append(prop).append("+").append(";"));

        result.getLeft().entrySet().stream().sorted(Map.Entry.comparingByKey()).forEach(entry -> {
            String prop = entry.getKey();
            Map.Entry<Object, Object> map = entry.getValue();
            description.append(prop).append(":").append(map.getValue()).append("!=").append(map.getKey()).append(";");
        });

        return description.length() > 0 ? description.toString() : "Ok.";
    }

    private void handleResult(EqReverseCtrlInfo checkResult, int code, String description, String messageKey) throws JsonProcessingException {
        checkResult.setCode(code);
        checkResult.setDescription(description);
        sendResult(checkResult, messageKey);
    }

    private void sendResult(EqReverseCtrlInfo checkResult, String messageKey) throws JsonProcessingException {
        String jsonString = objectMapper.writeValueAsString(checkResult);
        kafkaTemplate.send("qtech_im_aa_list_checked_topic", jsonString);
        rabbitTemplate.convertAndSend("qtechImExchange", "eqReverseCtrlInfoQueue", jsonString);
        logger.info(">>>>> key: {} check message completed, result sent!", messageKey);
    }
}