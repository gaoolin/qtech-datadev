package com.qtech.message.Controller;

import lombok.Getter;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.web.bind.annotation.*;

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
        // 启动异步发送消息到Kafka，但不等待结果
        kafkaTemplate.send("qtech_im_aa_list_topic", msg);

        // 返回一个响应，告知消息已接收并开始处理
        return ResponseEntity.status(200).body("0");
    }

    @Getter
    static class SendResultWrapper {
        private final Future<Integer> status;

        public SendResultWrapper(Future<Integer> status) {
            this.status = status;
        }
    }
}
