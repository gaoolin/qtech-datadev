package com.qtech.check.pojo;

import com.qtech.check.utils.ToCamelCaseConverter;
import com.qtech.common.utils.StringUtils;

/**
 * author :  gaozhilin
 * email  :  gaoolin@gmail.com
 * date   :  2024/05/15 15:49:51
 * desc   :
 */


public class AaListCommand {

    String integration;
    Integer num;
    String command;
    String subsystem;
    String value;

    public String getIntegration() {
        if (StringUtils.isBlank(integration)) {
            if (StringUtils.isBlank(subsystem)) {
                return command;
            }
            return StringUtils.joinWith("_", command, subsystem);
        }
        return integration;
    }

    public void setIntegration(String integration) {
        this.integration = integration;
    }

    public Integer getNum() {
        return num;
    }

    public void setNum(Integer num) {
        this.num = num;
    }

    public String getCommand() {
        return command;
    }

    public void setCommand(String command) {
        this.command = command;
    }

    public String getSubsystem() {
        return subsystem;
    }

    public void setSubsystem(String subsystem) {
        this.subsystem = subsystem;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }

    public AaListCommand(String integration, Integer num, String command, String subsystem, String value) {
        this.integration = integration;
        this.num = num;
        this.command = command;
        this.subsystem = subsystem;
        this.value = value;
    }
}