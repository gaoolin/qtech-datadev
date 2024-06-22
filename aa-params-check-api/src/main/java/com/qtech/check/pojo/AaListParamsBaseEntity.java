package com.qtech.check.pojo;

import com.qtech.check.utils.ToCamelCaseConverter;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

import java.lang.reflect.Field;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

/**
 * author :  gaozhilin
 * email  :  gaoolin@gmail.com
 * date   :  2024/06/06 09:03:13
 * desc   :
 */

@Data
@Accessors(chain = true)
@EqualsAndHashCode(callSuper = false)
public class AaListParamsBaseEntity {
    private String clampOnOff;
    private String destroyStart;
    private String init;
    private String grab;
    private String reInit;
    private String senserReset;
    private String sid;
    private String vcmHall;
    private String vcmInit;
    private String vcmHall2;
    private String vcmPowerOff;
    private String vcmPowerOn;
    private String vcmTop;
    private String vcmTopHall;
    private String vcmZ;
    private String vcmZHall;
    private String vcmOisInit;
    private String chartAlignment1;
    private String AA1;
    private String AA2;
    private String mtfCheck;
    private String AA3;
    private String mtfCheck2;
    private String lpOn;
    private String lpOcCheck;
    private String lpOc;
    private String lpOnBlemish;
    private String blemish;
    private String lpOff;
    private String chartAlignment;
    private String vcmMoveToZ;
    private String delay;
    private String vcmPowerOffCheck;
    private String recordPosition;
    private String dispense;
    private String epoxyInspectionAuto;
    private String epoxyInspection;
    private String backToPosition;
    private String uvon;
    private String yLevel;
    private String uvoff;
    private String gripperOpen;
    private String saveOc;
    private String saveMtf;
    private String destroy;
    private String moveToBlemishPos;
    private String mtfCheck3;
    private String mtfOffAxisCheck1;
    private String mtfOffAxisCheck2;
    private String mtfOffAxisCheck3;
    private String lpBlemish;
    private String chartAlignment2;
    private String vcmMoveToZPos;
    private String zOffset;
    private String openCheck;
    // AA Item 指标
    private String roiCc;
    private String roiUl;
    private String roiUr;
    private String roiLl;
    private String roiLr;
    // mtfCheck Item 指标
    private String result1;
    private String result2;
    private String result3;
    private String result4;
    private String result5;
    private String result6;
    private String result7;
    private String result8;
    private String result9;
    private String result10;
    private String result11;
    private String result12;
    private String result13;
    private String result14;
    private String result15;
    private String result16;
    private String result17;
    private String result18;
    private String result19;
    private String result20;
    private String result21;
    private String result22;
    private String result23;
    private String result24;
    private String result25;
    private String result26;
    private String result27;
    private String result28;
    private String result29;
    private String result30;
    private String result31;
    private String result32;
    private String result33;
    private String result34;
    private String result35;
    private String result36;
    private String result37;
    private String result38;
    private String result39;
    private String result40;
    private String result41;
    private String result42;
    private String result43;
    private String result44;
    private String result45;
    private String result46;
    private String result47;
    private String result48;
    private String result49;
    private String result50;
    private String result51;
    private String result52;
    // chartAlignment Item 指标
    private String xResMin;
    private String xResMax;
    private String yResMin;
    private String yResMax;
    // EpoxyInspection Auto Item 指标
    private String epoxyInspectionInterval;
    // vcmZ Item 指标
    private String resultCheckMin;
    private String resultCheckMax;

    public void reset() {
        try {
            for (Field field : getClass().getDeclaredFields()) {
                field.setAccessible(true);
                if (field.getType().equals(String.class)) {
                    field.set(this, null);
                }
            }
        } catch (IllegalAccessException e) {
            throw new IllegalStateException("Failed to reset properties due to reflection error", e);
        }
    }

    public void fillWithData(List<AaListCommand> aaListCommands) {
        if (aaListCommands.isEmpty()) {
            return;
        }

        List<Map<String, String>> camelCaseData = aaListCommands.stream()
                .filter(AaListCommand::nonNull)
                .map(aaListCommand -> {
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
                })
                .filter(map -> !map.isEmpty())
                .collect(Collectors.toList());

        try {
            for (Field field : this.getClass().getDeclaredFields()) {
                String camelCaseKey = field.getName();
                for (Map<String, String> map : camelCaseData) {
                    if (map.containsKey(camelCaseKey)) {
                        field.setAccessible(true);
                        field.set(this, map.get(camelCaseKey));
                        break;
                    }
                }
            }
        } catch (IllegalAccessException e) {
            throw new IllegalStateException("Failed to set properties due to reflection error", e);
        }
    }
}
