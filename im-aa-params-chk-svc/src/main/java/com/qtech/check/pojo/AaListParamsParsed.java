package com.qtech.check.pojo;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.qtech.share.aa.pojo.ImAaListCommand;
import com.qtech.share.aa.pojo.ImAaListParams;
import com.qtech.share.aa.util.ToCamelCaseConverter;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;
import lombok.experimental.Accessors;

import java.lang.reflect.Field;
import java.util.*;
import java.util.stream.Collectors;

/**
 * author :  gaozhilin
 * email  :  gaoolin@gmail.com
 * date   :  2024/05/14 11:50:36
 * desc   :
 */
@Data
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
@Accessors(chain = true)  // 注解用于启用链式调用风格，这意味着在调用 setter 方法时，可以返回当前对象，从而使得多个 setter 方法可以链式调用。
@JsonIgnoreProperties(ignoreUnknown = true)
public class AaListParamsParsed extends ImAaListParams {

    private String simId;

    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date receivedTime;

    @Override
    public void reset() {
        this.simId = null;
        this.receivedTime = null;
        super.reset();  // 调用父类的 reset 方法，重置父类的字段
    }

    @Override
    public void fillWithData(List<ImAaListCommand> aaListCommands) {
        if (aaListCommands.isEmpty()) {
            return;
        }

        List<Map<String, String>> camelCaseData = aaListCommands.stream().filter(ImAaListCommand::nonNull).map(aaListCommand -> {
            Map<String, String> map = new HashMap<>();
            Optional.ofNullable(aaListCommand.getIntegration()).ifPresent(integration -> {
                String cmdObjVal = aaListCommand.getValue();
                if (cmdObjVal == null) {
                    String metricsMin = String.join("", ToCamelCaseConverter.doConvert(integration), "Min");
                    String metricsMax = String.join("", ToCamelCaseConverter.doConvert(integration), "Max");
                    map.put(metricsMin, aaListCommand.getRange().getMin());
                    map.put(metricsMax, aaListCommand.getRange().getMax());
                } else {
                    map.put(ToCamelCaseConverter.doConvert(integration), cmdObjVal);
                }
            });
            return map;
        }).filter(map -> !map.isEmpty()).collect(Collectors.toList());

        try {
            setFieldsFromData(this, camelCaseData);
        } catch (IllegalAccessException e) {
            throw new IllegalStateException("Failed to set properties due to reflection error", e);
        }
    }

    private void setFieldsFromData(Object target, List<Map<String, String>> data) throws IllegalAccessException {
        Class<?> clazz = target.getClass();
        while (clazz != null) {
            for (Field field : clazz.getDeclaredFields()) {
                String camelCaseKey = field.getName();
                for (Map<String, String> map : data) {
                    if (map.containsKey(camelCaseKey)) {
                        field.setAccessible(true);
                        field.set(target, map.get(camelCaseKey));
                        break;
                    }
                }
            }
            clazz = clazz.getSuperclass();
        }
    }
}