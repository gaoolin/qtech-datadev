package com.qtech.mq.serializer;

import org.apache.avro.Schema;
import org.apache.avro.generic.GenericDatumReader;
import org.apache.avro.io.Decoder;
import org.apache.avro.io.DecoderFactory;
import org.apache.avro.specific.SpecificDatumReader;
import org.apache.kafka.common.errors.SerializationException;
import org.apache.kafka.common.serialization.Deserializer;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.util.Map;

/**
 * author :  gaozhilin
 * email  :  gaoolin@gmail.com
 * date   :  2024/08/26 11:44:06
 * desc   :
 */

public class WbOlpRawDataRecordDeserializer implements Deserializer<WbOlpRawDataRecord> {

    private static final String SCHEMA_FILE_PATH = "avro/WbOlpRawDataRecord.avsc";
    private static final Schema SCHEMA = loadSchema();
    private final GenericDatumReader<WbOlpRawDataRecord> datumReader = new SpecificDatumReader<>(SCHEMA);

    private static Schema loadSchema() {
        try {
            return new Schema.Parser().parse(WbOlpRawDataRecordDeserializer.class.getClassLoader().getResourceAsStream(WbOlpRawDataRecordDeserializer.SCHEMA_FILE_PATH));
        } catch (IOException e) {
            throw new RuntimeException("Error loading Avro schema.", e);
        }
    }

    @Override
    public void configure(Map<String, ?> configs, boolean isKey) {
        // 可选配置
    }

    @Override
    public WbOlpRawDataRecord deserialize(String topic, byte[] data) {
        try (ByteArrayInputStream inputStream = new ByteArrayInputStream(data)) {
            Decoder decoder = DecoderFactory.get().binaryDecoder(inputStream, null);
            return datumReader.read(null, decoder);
        } catch (IOException e) {
            throw new SerializationException("Error while deserializing Avro to WbOlpRawDataRecord.", e);
        }
    }

    @Override
    public void close() {
        // 清理资源
    }
}
