package com.qtech.pulsar.service.impl;

import com.qtech.pulsar.common.Constants;
import com.qtech.pulsar.pojo.MessageDto;
import com.qtech.pulsar.service.IPulsarConsumeService;
import io.github.majusko.pulsar.annotation.PulsarConsumer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

/**
 * author :  gaozhilin
 * email  :  gaoolin@gmail.com
 * date   :  2023/08/11 14:13:57
 * desc   :  TODO
 */

//@Slf4j
@Service
public class PulsarConsumeServiceImpl<T> implements IPulsarConsumeService<T> {

    private static final Logger logger = LoggerFactory.getLogger(PulsarConsumeServiceImpl.class);

    @Override
    @PulsarConsumer(topic= Constants.LOG_TOPIC, clazz= MessageDto.class)
    public int consume(T msg) {
        logger.info("PulsarRealConsumer consume msg:{}", msg);
        return 0;
    }

    @Override
    public int starterConsume(T msg) {
        return 0;
    }
}
