package com.qtech.pulsar.service.impl;

import com.qtech.pulsar.pojo.MessageDto;
import com.qtech.pulsar.service.IPulsarProducerService;
import io.github.majusko.pulsar.producer.PulsarTemplate;
import org.apache.pulsar.client.api.PulsarClientException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * author :  gaozhilin
 * email  :  gaoolin@gmail.com
 * date   :  2023/08/11 14:07:38
 * desc   :  TODO
 */

@Service
public class PulsarProducerServiceImpl<T> implements IPulsarProducerService<T> {

    @Autowired
    private PulsarTemplate<T> template;

    @Override
    public int send(String topic, T msg) {
        try {
            template.send(topic, msg);
            return 0;
        } catch (PulsarClientException e) {
            e.printStackTrace();
            return -1;
        }
    }

    @Override
    public int sendAsync(String topic, T msg) {
        try {
            template.sendAsync(topic, msg);
            return 0;
        } catch (Exception e) {
            e.printStackTrace();
            return 1;
        }
    }
}
