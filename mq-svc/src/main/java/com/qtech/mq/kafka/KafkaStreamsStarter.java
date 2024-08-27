package com.qtech.mq.kafka;

import org.apache.kafka.streams.KafkaStreams;
import org.apache.kafka.streams.StreamsBuilder;
import org.apache.kafka.streams.StreamsConfig;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.kafka.annotation.EnableKafkaStreams;
import org.springframework.stereotype.Component;

import java.util.Properties;

/**
 * author :  gaozhilin
 * email  :  gaoolin@gmail.com
 * date   :  2024/08/25 18:45:47
 * desc   :
 */

@Component
@EnableKafkaStreams
public class KafkaStreamsStarter {

    private final WbOlpChkKafkaDeduplicationTopology wbOlpChkKafkaDeduplicationTopology;
    private final WbOlpRawDataKafkaDeduplicationTopology wbOlpRawDataKafkaDeduplicationTopology;

    @Autowired
    public KafkaStreamsStarter(WbOlpChkKafkaDeduplicationTopology wbOlpChkKafkaDeduplicationTopology,
                               WbOlpRawDataKafkaDeduplicationTopology wbOlpRawDataKafkaDeduplicationTopology) {
        this.wbOlpChkKafkaDeduplicationTopology = wbOlpChkKafkaDeduplicationTopology;
        this.wbOlpRawDataKafkaDeduplicationTopology = wbOlpRawDataKafkaDeduplicationTopology;
    }

    @Bean
    public KafkaStreams kafkaStreams() {
        StreamsBuilder streamsBuilder = new StreamsBuilder();
        wbOlpChkKafkaDeduplicationTopology.createTopology(streamsBuilder);
        wbOlpRawDataKafkaDeduplicationTopology.createTopology(streamsBuilder);

        Properties props = new Properties();
        // 给每个流一个独特的应用 ID
        props.put(StreamsConfig.APPLICATION_ID_CONFIG, "deduplication-application-wb-olp"); // 或其他唯一标识符
        props.put(StreamsConfig.BOOTSTRAP_SERVERS_CONFIG, "10.170.6.24:9092,10.170.6.25:9092,10.170.6.26:9092");

        KafkaStreams streams = new KafkaStreams(streamsBuilder.build(), props);
        streams.start();

        Runtime.getRuntime().addShutdownHook(new Thread(streams::close));

        return streams;
    }
}
