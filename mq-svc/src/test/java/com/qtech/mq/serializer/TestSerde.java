package com.qtech.mq.serializer;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.qtech.mq.domain.DeviceData;

import static com.qtech.mq.kafka.eqn.DeviceDataKafkaProcessor.configureObjectMapper;

/**
 * author :  gaozhilin
 * email  :  gaoolin@gmail.com
 * date   :  2024/11/26 11:23:20
 * desc   :
 */


public class TestSerde {
    public static void main(String[] args) throws JsonProcessingException {
        ObjectMapper objectMapper = configureObjectMapper();
        DeviceData deviceData = new DeviceData();
        deviceData.setReceiveDate("2024-11-26 11:14:22");
        deviceData.setDeviceId("17");
        deviceData.setDeviceType("4GLinuxAA");
        deviceData.setRemoteControl("1");
        deviceData.setStatus("1");

        String json = objectMapper.writeValueAsString(deviceData);
        System.out.println(json);


        String a = "{\"Status\":\"1\",\"lastUpdated\":null,\"receive_date\":\"2024-11-26 11:14:22\",\"device_id\":\"17\",\"device_type\":\"4GLinuxAA\",\"Remote_control\":\"1\"}";
        DeviceData deviceData1 = objectMapper.readValue(a, DeviceData.class);
        System.out.println(deviceData1);
        String s = objectMapper.writeValueAsString(deviceData1);
        System.out.println(s);

    }
}