package com.qtech.mq.serializer;

import org.apache.kafka.common.serialization.Serializer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.ByteArrayOutputStream;
import java.io.DataOutputStream;
import java.io.IOException;

/**
 * author :  gaozhilin
 * email  :  gaoolin@gmail.com
 * date   :  2024/08/24 21:00:42
 * desc   :
 */

public class CompositeKeySerializer implements Serializer<CompositeKey> {
    private static final Logger logger = LoggerFactory.getLogger(CompositeKeySerializer.class);
    @Override
    public byte[] serialize(String topic, CompositeKey data) {
        // 实现序列化逻辑
        // 假设 CompositeKey 有两个字段 simId 和 prodType
        // 将其转换为字节数组并返回
        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        try (DataOutputStream dataOutputStream = new DataOutputStream(outputStream)) {
            dataOutputStream.writeUTF(data.getSimId());
            dataOutputStream.writeUTF(data.getProdType());
            dataOutputStream.writeUTF(data.getChkDt());
        } catch (IOException e) {
            // 处理异常
            logger.error(">>>>> Failed to serialize CompositeKey: " + e.getMessage());
        }
        return outputStream.toByteArray();
    }
}
