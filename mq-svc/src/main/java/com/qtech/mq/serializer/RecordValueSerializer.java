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
 * date   :  2024/08/24 21:11:12
 * desc   :
 */


public class RecordValueSerializer implements Serializer<Record> {
    private static final Logger logger = LoggerFactory.getLogger(RecordValueSerializer.class);

    @Override
    public byte[] serialize(String topic, Record data) {
        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        try (DataOutputStream dataOutputStream = new DataOutputStream(outputStream)) {
            dataOutputStream.writeUTF(data.getSimId());
            dataOutputStream.writeUTF(data.getProdType());
            dataOutputStream.writeUTF(data.getChkDt());
            dataOutputStream.writeUTF(data.getCode());
            dataOutputStream.writeUTF(data.getDescription());
        } catch (IOException e) {
            logger.error(">>>>> Failed to serialize Record:" + e.getMessage());
        }
        return outputStream.toByteArray();
    }
}
