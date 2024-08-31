package com.qtech.mq.serializer;

import org.apache.avro.Schema;
import org.apache.avro.generic.GenericDatumReader;
import org.apache.avro.io.Decoder;
import org.apache.avro.io.DecoderFactory;
import org.apache.avro.specific.SpecificDatumReader;
import org.apache.kafka.common.errors.SerializationException;
import org.apache.kafka.common.serialization.Deserializer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.ByteArrayInputStream;
import java.io.DataInputStream;
import java.io.IOException;
import java.util.Map;

/**
 * author :  gaozhilin
 * email  :  gaoolin@gmail.com
 * date   :  2024/08/24 21:16:04
 * desc   :
 */

public class RecordValueDeserializer implements Deserializer<Record> {
    private static final Logger logger = LoggerFactory.getLogger(RecordValueDeserializer.class);
    private static final String SCHEMA_FILE_PATH = "avro/Record.avsc";
    private static final Schema SCHEMA = loadSchema();

    private static Schema loadSchema() {
        try {
            return new Schema.Parser().parse(RecordValueDeserializer.class.getClassLoader().getResourceAsStream(SCHEMA_FILE_PATH));
        } catch (IOException e) {
            logger.error("Error loading Avro schema.", e);
            throw new RuntimeException("Error loading Avro schema.", e);
        }
    }

    @Override
    public void configure(Map<String, ?> configs, boolean isKey) {
        // 可选配置
    }

    @Override
    public Record deserialize(String topic, byte[] data) {
        if (data == null) {
            return null;
        }

        try (ByteArrayInputStream inputStream = new ByteArrayInputStream(data)) {
            Decoder decoder = DecoderFactory.get().binaryDecoder(inputStream, null);
            GenericDatumReader<Record> datumReader = new SpecificDatumReader<>(SCHEMA);
            return datumReader.read(null, decoder);
        } catch (IOException e) {
            logger.error("Error while deserializing Avro to Record.", e);
            throw new SerializationException("Error while deserializing Avro to Record.", e);
        }
    }

    @Override
    public void close() {
        // 清理资源
    }
}