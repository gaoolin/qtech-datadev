package com.qtech;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import org.apache.pulsar.client.admin.PulsarAdmin;
import org.apache.pulsar.client.admin.PulsarAdminException;
import org.apache.pulsar.client.api.*;
import org.apache.pulsar.common.policies.data.PartitionedTopicStats;
import org.junit.Test;

import java.util.Arrays;
import java.util.HashMap;

import static org.junit.Assert.assertTrue;

/**
 * Unit test for simple App.
 */
public class AppTest {
    /**
     * Rigorous Test :-)
     */
    @Test
    public void shouldAnswerWithTrue() {
        assertTrue(true);
    }

    public static void main(String[] args) throws PulsarClientException, PulsarAdminException {
        /*
         * 需要把pulsar-api服务停掉，生产者下线，这时才能删掉topic
         */

        String url = "http://broker-svc:8080";  // kt connect需要 service的dns
        PulsarAdmin admin = PulsarAdmin.builder()
                .serviceHttpUrl(url)
                .build();

//        admin.topics().createPartitionedTopic("persistent://qtech-datadev/qtech-eq-aa/test-topic-1", 4);

//        admin.topics().updatePartitionedTopic("persistent://qtech-datadev/qtech-eq-aa/test-topic-1", 5);

        PulsarClient client = PulsarClient.builder()
                .serviceUrl("pulsar://qtech-pulsar-broker.pulsar:6650")
                .build();

        Consumer<byte[]> subscribe = client.newConsumer().topic("persistent://qtech-datadev/qtech-eq-aa/aaList").subscriptionName("aaList-flink").subscribe();

        byte[] data = subscribe.receive().getData();

        System.out.println(Arrays.toString(data));

        /*Producer<String> producer = client.newProducer(Schema.STRING)
                .topic("persistent://qtech-datadev/qtech-eq-aa/test-topic-1")
                .blockIfQueueFull(true)
                .create();

        for (int i = 0; i < 100; ++i) {
            producer.newMessage().value("test").send();
        }
        producer.close();
        client.close();

        PartitionedTopicStats stats = admin.topics().getPartitionedStats("persistent://qtech-datadev/qtech-eq-aa/test-topic-1", false);
        System.out.println(stats.getMsgInCounter());*/

//        admin.topics().deletePartitionedTopic("persistent://qtech-datadev/qtech-eq-aa/test-topic-1");

//        admin.topics().deleteSubscription("persistent://qtech-datadev/qtech-eq-aa/aaList","byteTest", true);

//        admin.topics().delete("persistent://qtech-datadev/qtech-eq-aa/pojpTest", true);


    }
}
