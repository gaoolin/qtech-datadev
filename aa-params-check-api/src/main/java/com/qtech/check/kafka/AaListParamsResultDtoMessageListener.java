package com.qtech.check.kafka;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.TypeReference;
import com.qtech.check.pojo.AaListParamsCheckResult;
import com.qtech.check.service.IAaListParamsCheckResultService;
import lombok.extern.slf4j.Slf4j;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.config.ConcurrentKafkaListenerContainerFactory;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * author :  gaozhilin
 * email  :  gaoolin@gmail.com
 * date   :  2024/05/19 09:20:11
 * desc   :
 */

@Slf4j
@Component
public class AaListParamsResultDtoMessageListener {

    @Autowired
    private ConcurrentKafkaListenerContainerFactory<String, String> kafkaListenerContainerFactory;

    @Autowired
    private IAaListParamsCheckResultService aaListParamsCheckResultService;

    /**
     * @param records
     * @return void
     * @description 当我们在@KafkaListener注解中不指定containerFactory属性时，Spring Kafka会自动创建一个默认的ConcurrentKafkaListenerContainerFactory实例来处理消息监听。如果你想要自定义KafkaMessageListenerContainer的行为，比如设置消费者配置，你需要提供一个自定义的ConcurrentKafkaListenerContainerFactory实例，并通过@KafkaListener的containerFactory属性引用它。
     * 我们创建了一个自定义的ConcurrentKafkaListenerContainerFactory并注入到了CustomKafkaListener类中。虽然@KafkaListener注解没有直接引用这个工厂，但在Spring容器中，如果一个类有一个ConcurrentKafkaListenerContainerFactory类型的bean，Spring Kafka会自动使用这个bean来创建KafkaMessageListenerContainer实例。
     * 因此，虽然@KafkaListener注解没有明确指定containerFactory，但通过依赖注入，Spring会自动选择并使用kafkaListenerContainerFactory方法创建的bean来创建和配置KafkaMessageListenerContainer，从而影响listen方法中消息的处理方式。
     */
    @KafkaListener(topics = "qtech_im_aa_list_checked_topic", groupId = "aaList-checked-dto-group", containerFactory = "kafkaListenerContainerFactory")
    public void listenBatchMessages(List<ConsumerRecord<String, String>> records) {
        for (ConsumerRecord<String, String> record : records) {
            // 解析和处理消息
            String key = record.key();
            String value = record.value();

            // value是一个JSON字符串，将其转换为一个对象
            AaListParamsCheckResult pojo = JSON.parseObject(value, new TypeReference<AaListParamsCheckResult>() {
            });
            log.info("key:{},value:{}", key, value);

            int i = aaListParamsCheckResultService.save(pojo);
        }
    }
}