package com.qtech.check.kafka;

/**
 * author :  gaozhilin
 * email  :  gaoolin@gmail.com
 * date   :  2024/05/17 16:05:57
 * desc   :
 */

/*@Slf4j
@Component
public class AaListParamsDtoMessageConsumer {

    @Autowired
    private ConcurrentKafkaListenerContainerFactory<String, String> kafkaListenerContainerFactory;

    @Autowired
    private IAaListParamsService aaListParamsService;

    @KafkaListener(topics = "qtech_im_aa_list_parsed_topic", groupId = "aaList-parsed-serializer-group", containerFactory = "kafkaListenerContainerFactory")
    public void listenBatchMessages(List<ConsumerRecord<String, String>> records) {
        for (ConsumerRecord<String, String> record : records) {
            // 解析和处理消息
            String key = record.key();
            String value = record.value();

            // 获取头部信息
            Headers headers = record.headers();
            Header timeHeader = headers.lastHeader("received-timestamp");
            Date timestamp = null;
            if (timeHeader != null) {
                String timestampStr = new String(timeHeader.value(), StandardCharsets.UTF_8);
                try {
                    long timestampLong = Long.parseLong(timestampStr);
                    timestamp = new Date(timestampLong);
                } catch (NumberFormatException e) {
                    log.error(">>>>> Failed to parse received-timestamp header: {}", timestampStr, e);
                }
            }

            AaListParamsParsed pojo = JSON.parseObject(value, new TypeReference<AaListParamsParsed>() {
            });
            // 设置接收时间
            pojo.setReceivedTime(timestamp);

            try {
                aaListParamsService.insertAaListParams(pojo);
                log.info(">>>> Inserted parsed message with prodType: {}, simId: {}", pojo.getProdType(), pojo.getSimId());
            } catch (Exception e) {
                log.error(">>>> Failed to insert parsed message with prodType: {}, simId: {}", pojo.getProdType(), pojo.getSimId());
            }

            // 如果需要发送响应或回执，可以使用kafkaTemplate
            // kafkaTemplate.send("responseTopic", key, "Processed successfully");
        }
    }
}*/

