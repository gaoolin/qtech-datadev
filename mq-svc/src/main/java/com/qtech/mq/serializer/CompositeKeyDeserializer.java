package com.qtech.mq.serializer;

import org.apache.kafka.common.serialization.Deserializer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.ByteArrayInputStream;
import java.io.DataInputStream;
import java.io.IOException;

/**
 * author :  gaozhilin
 * email  :  gaoolin@gmail.com
 * date   :  2024/08/24 21:01:32
 * desc   :
 */

public class CompositeKeyDeserializer implements Deserializer<CompositeKey> {
    private static final Logger logger = LoggerFactory.getLogger(CompositeKeyDeserializer.class);
    @Override
    public CompositeKey deserialize(String topic, byte[] data) {
        // 实现反序列化逻辑
        try (DataInputStream dataInputStream = new DataInputStream(new ByteArrayInputStream(data))) {
            String simId = dataInputStream.readUTF();
            String prodType = dataInputStream.readUTF();
            String chkDt = dataInputStream.readUTF();
            return new CompositeKey(simId, prodType, chkDt);
        } catch (IOException e) {
            // 处理异常
            logger.error(">>>>> Failed to deserialize CompositeKey", e);
            return null;
        }
    }
}
