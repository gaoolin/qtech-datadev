package com.qtech.message.Controller;

import lombok.Getter;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.concurrent.Future;

/**
 * author :  gaozhilin
 * email  :  gaoolin@gmail.com
 * date   :  2024/05/11 16:01:06
 * desc   :
 */
@Slf4j
@RestController
@RequestMapping(value = "/message/api/list")
public class AaListKafkaController {

    @Autowired
    private KafkaTemplate<String, Object> kafkaTemplate;

    @PostMapping("/send")
    public ResponseEntity<String> send(@RequestBody String msg) {
        try {
            // 启动异步发送消息到Kafka，但不等待结果
            kafkaTemplate.send("qtech_im_aa_list_topic", msg);

            log.info("message已发送至Kafka");

            // 返回一个响应，告知消息已接收并开始处理
            return ResponseEntity.status(200).body("0");
        } catch (Exception e) {
            // 捕获并处理异常
            log.error("Error occurred while sending message to Kafka: ", e);

            // 返回一个带有错误信息的响应
            String errorMessage = "Failed to process request due to an error: " + e.getMessage();
            return ResponseEntity.status(500).body(errorMessage);
        }
    }

    @Getter
    static class SendResultWrapper {
        private final Future<Integer> status;

        public SendResultWrapper(Future<Integer> status) {
            this.status = status;
        }
    }
}
