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
 * date   :  2024/08/24 21:16:04
 * desc   :
 */


public class RecordValueDeserializer implements Deserializer<Record> {
    private static final Logger logger = LoggerFactory.getLogger(RecordValueDeserializer.class);

    @Override
    public Record deserialize(String topic, byte[] data) {
        try (DataInputStream dataInputStream = new DataInputStream(new ByteArrayInputStream(data))) {
            String simId = dataInputStream.readUTF();
            String prodType = dataInputStream.readUTF();
            String chkDt = dataInputStream.readUTF();
            String code = dataInputStream.readUTF();
            String description = dataInputStream.readUTF();
            return new Record(simId, prodType, chkDt, code, description);
        } catch (IOException e) {
            logger.error("Failed to deserialize Record", e);
            return null;
        }
    }
}
