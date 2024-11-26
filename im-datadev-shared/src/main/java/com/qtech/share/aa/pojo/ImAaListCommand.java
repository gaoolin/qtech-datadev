package com.qtech.share.aa.pojo;

import com.qtech.common.utils.StringUtils;
import com.qtech.share.aa.model.Range;

import java.io.Serializable;

/**
 * author :  gaozhilin
 * email  :  gaoolin@gmail.com
 * date   :  2024/05/15 15:49:51
 * desc   :
 */

public class ImAaListCommand implements Serializable {
    private static final long serialVersionUID = 1L;

    private String integration;
    private Integer num;
    private String prefixCommand;
    private String command;
    private String subsystem;
    private String value;

    private Range<String> range;

    public ImAaListCommand(String integration, Integer num, String prefixCommand, String command, String subsystem, String value, Range<String> range) {
        this.integration = integration;
        this.num = num;
        this.prefixCommand = prefixCommand;
        this.command = command;
        this.subsystem = subsystem;
        this.value = value;
        this.range = range;
    }

    // 定义静态方法 nonNull
    public static boolean nonNull(ImAaListCommand imAaListCommand) {
        return imAaListCommand != null;
    }

    public String getIntegration() {
        if (StringUtils.isBlank(integration)) {
            StringBuilder sb = new StringBuilder();
            boolean hasContent = false;

            if (StringUtils.isNotBlank(prefixCommand)) {
                sb.append(prefixCommand);
                hasContent = true;
            }
            if (StringUtils.isNotBlank(command)) {
                if (hasContent) sb.append("_");
                sb.append(command);
                hasContent = true;
            }
            if (StringUtils.isNotBlank(subsystem)) {
                if (hasContent) sb.append("_");
                sb.append(subsystem);
            }

            return sb.toString();
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

    public String getPrefixCommand() {
        return prefixCommand;
    }

    public void setPrefixCommand(String prefixCommand) {
        this.prefixCommand = prefixCommand;
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

    public Range<String> getRange() {
        return range;
    }

    public void setRange(Range<String> range) {
        this.range = range;
    }
}