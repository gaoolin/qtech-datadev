package com.qtech.message.Controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.web.bind.annotation.*;

/**
 * author :  gaozhilin
 * email  :  gaoolin@gmail.com
 * date   :  2025/02/11 11:13:56
 * desc   :
 */
@Slf4j
@RestController
@RequestMapping(value = "/message/api/glue")
public class AAGlueKafkaController {
    @Autowired
    private KafkaTemplate<String, Object> kafkaTemplate;

    @PostMapping(value = "/heartbeat")
    public ResponseEntity<String> send(@RequestBody String msg) {
        try {
            // 启动异步发送消息到Kafka，但不等待结果
            kafkaTemplate.send("qtech_im_aa_glue_heartbeat", msg);
            log.info("message已发送至Kafka");
            return ResponseEntity.status(200).body("0");
        } catch (Exception e) {
            // 捕获并处理异常
            log.error("Error occurred while sending message to Kafka: ", e);
            // 返回一个带有错误信息的响应
            String errorMessage = "Failed to process request due to an error: " + e.getMessage();
            return ResponseEntity.status(500).body(errorMessage);
        }
    }

    @PostMapping("/log")
    public ResponseEntity<String> log(@RequestBody String msg) {
        try {
            // 启动异步发送消息到Kafka，但不等待结果
            kafkaTemplate.send("qtech_im_aa_glue_images_logs", msg);
            log.info("message已发送至Kafka");
            return ResponseEntity.status(200).body("0");
        } catch (Exception e) {
            // 捕获并处理异常
            log.error("Error occurred while sending message to Kafka: ", e);
            // 返回一个带有错误信息的响应
            String errorMessage = "Failed to process request due to an error: " + e.getMessage();
            return ResponseEntity.status(500).body(errorMessage);
        }
    }
}
