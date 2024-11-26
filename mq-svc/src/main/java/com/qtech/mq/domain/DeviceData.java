package com.qtech.mq.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.qtech.mq.serde.DeviceDataStatusDeserializer;
import com.qtech.mq.serde.DeviceDataStatusSerializer;
import lombok.Data;
import lombok.ToString;

import java.time.LocalDateTime;

/**
 * author :  gaozhilin
 * email  :  gaoolin@gmail.com
 * date   :  2024/11/19 09:39:46
 * desc   :
 */

@Data
@ToString
@JsonIgnoreProperties(ignoreUnknown = true)
public class DeviceData {
    @JsonProperty("receive_date")
    private String receiveDate;

    @JsonProperty("device_id")
    private String deviceId;

    @JsonProperty("device_type")
    private String deviceType;

    @JsonProperty("Remote_control")
    private String remoteControl;

    @JsonProperty("Status")
    // @JsonDeserialize(using = DeviceDataStatusDeserializer.class)
    @JsonSerialize(using = DeviceDataStatusSerializer.class)
    private String status;

    private LocalDateTime lastUpdated;
}
