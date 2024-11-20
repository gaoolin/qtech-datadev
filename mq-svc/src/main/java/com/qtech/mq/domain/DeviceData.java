package com.qtech.mq.domain;

import com.fasterxml.jackson.annotation.JsonAlias;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
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
    @JsonAlias("receiveDate")
    private String receiveDate;
    @JsonProperty("device_id")
    @JsonAlias("deviceId")
    private String deviceId;
    @JsonProperty("device_type")
    @JsonAlias("deviceType")
    private String deviceType;
    @JsonProperty("Remote_control")
    @JsonAlias("remoteControl")
    private String remoteControl;

    private String status; // ONLINE / OFFLINE
    private LocalDateTime lastUpdated;
}
