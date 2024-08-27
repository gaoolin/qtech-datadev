package com.qtech.mq.serializer;

import org.apache.kafka.common.serialization.Deserializer;
import org.apache.kafka.common.serialization.Serde;
import org.apache.kafka.common.serialization.Serializer;

/**
 * author :  gaozhilin
 * email  :  gaoolin@gmail.com
 * date   :  2024/08/24 20:04:10
 * desc   :
 */


public class RecordSerde  implements Serde<Record> {
    @Override
    public Serializer<Record> serializer() {
        return new RecordValueSerializer();
    }

    @Override
    public Deserializer<Record> deserializer() {
        return new RecordValueDeserializer();
    }
}
