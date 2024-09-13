package com.qtech.check.pojo;

import com.qtech.check.utils.ToCamelCaseConverter;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.lang.reflect.Field;
import java.util.*;
import java.util.stream.Collectors;

/**
 * author :  gaozhilin
 * email  :  gaoolin@gmail.com
 * date   :  2024/06/06 09:03:13
 * desc   :
 */
@Data
@Accessors(chain = true) // 表示启用链式调用
@EqualsAndHashCode(callSuper = false)
public class AaListParamsBaseEntity {
    private static final Logger logger = LoggerFactory.getLogger(AaListParamsBaseEntity.class);
    private String aa1;
    private String aa2;
    private String aa3;
    private String backToPosition;
    private String blemish;
    private String clampOnOff;
    private String chartAlignment;
    private String chartAlignment1;
    private String chartAlignment2;
    private String delay;
    private String destroy;
    private String destroyStart;
    private String dispense;
    private String epoxyInspection;
    private String epoxyInspectionAuto;
    private String grab;
    private String gripperOpen;
    private String init;
    private String lpBlemish;
    private String lpOc;
    private String lpOcCheck;
    private String lpOn;
    private String lpOnBlemish;
    private String lpOff;
    private String moveToBlemishPos;
    private String mtfCheck;
    private String mtfCheck1;
    private String mtfCheck2;
    private String mtfCheck3;
    private String openCheck;
    private String ocCheck;
    private String recordPosition;
    private String reInit;
    private String saveOc;
    private String saveMtf;
    private String senserReset;
    private String sid;
    private String uvon;
    private String uvoff;
    private String vcmHall;
    private String vcmHall2;
    private String vcmMoveToZ;
    private String vcmMoveToZPos;
    private String vcmPowerOffCheck;
    private String vcmRun;
    private String vcmInit;
    private String vcmOisInit;
    private String vcmPowerOff;
    private String vcmPowerOn;
    private String vcmTop;
    private String vcmTopHall;
    private String vcmZ;
    private String vcmZHall;
    private String yLevel;

    // AA Item 指标
    private String aa1RoiCc;
    private String aa1RoiUl;
    private String aa1RoiUr;
    private String aa1RoiLl;
    private String aa1RoiLr;
    private String aa1FC;
    private String aa1F1;
    private String aa1F2;
    private String aa1F3;
    private String aa1F4;
    private String aa1MtfOffAxisCheck1;
    private String aa1MtfOffAxisCheck2;
    private String aa1MtfOffAxisCheck3;

    private String aa2RoiCc;
    private String aa2RoiUl;
    private String aa2RoiUr;
    private String aa2RoiLl;
    private String aa2RoiLr;
    private String aa2FC;
    private String aa2F1;
    private String aa2F2;
    private String aa2F3;
    private String aa2F4;
    private String aa2MtfOffAxisCheck1;
    private String aa2MtfOffAxisCheck2;
    private String aa2MtfOffAxisCheck3;

    private String aa3RoiCc;
    private String aa3RoiUl;
    private String aa3RoiUr;
    private String aa3RoiLl;
    private String aa3RoiLr;
    private String aa3FC;
    private String aa3F1;
    private String aa3F2;
    private String aa3F3;
    private String aa3F4;
    private String aa3MtfOffAxisCheck1;
    private String aa3MtfOffAxisCheck2;
    private String aa3MtfOffAxisCheck3;

    // mtfCheck Item 指标
    private String mtfCheckFC;
    private String mtfCheckF1;
    private String mtfCheckF2;
    private String mtfCheckF3;
    private String mtfCheckF4;

    private String mtfCheck1FC;
    private String mtfCheck1F1;
    private String mtfCheck1F2;
    private String mtfCheck1F3;
    private String mtfCheck1F4;

    private String mtfCheck2FC;
    private String mtfCheck2F1;
    private String mtfCheck2F2;
    private String mtfCheck2F3;
    private String mtfCheck2F4;

    private String mtfCheck3FC;
    private String mtfCheck3F1;
    private String mtfCheck3F2;
    private String mtfCheck3F3;
    private String mtfCheck3F4;

    // chartAlignment Item 指标
    private String chartAlignmentXResMin;
    private String chartAlignmentXResMax;
    private String chartAlignmentYResMin;
    private String chartAlignmentYResMax;

    private String chartAlignment1XResMin;
    private String chartAlignment1XResMax;
    private String chartAlignment1YResMin;
    private String chartAlignment1YResMax;

    private String chartAlignment2XResMin;
    private String chartAlignment2XResMax;
    private String chartAlignment2YResMin;
    private String chartAlignment2YResMax;

    // EpoxyInspection Auto Item 指标
    private String epoxyInspectionInterval;

    // vcmCheck 指标
    private String vcmCheckResultCheckMin;
    private String vcmCheckResultCheckMax;

    // RecordPosition 指标
    private String recordPositionUtXyzMove;

    // OcCheck 指标 Save Oc
    private String ocCheckXOffsetMin;
    private String ocCheckXOffsetMax;
    private String ocCheckYOffsetMin;
    private String ocCheckYOffsetMax;

    private String saveOcXOffsetMin;
    private String saveOcXOffsetMax;
    private String saveOcYOffsetMin;
    private String saveOcYOffsetMax;

    // SaveMtf 指标
    private String saveMtfCcMin;
    private String saveMtfCcMax;

    // Vcm Run 指标


    public void reset() {
        resetFields(this.getClass());
    }

    private void resetFields(Class<?> clazz) {
        if (clazz == null) {
            return;
        }

        Arrays.stream(clazz.getDeclaredFields()).filter(field -> String.class.equals(field.getType()) || Date.class.equals(field.getType())).forEach(field -> {
            field.setAccessible(true);
            try {
                field.set(this, null);  // 重置字段为 null
            } catch (IllegalAccessException e) {
                throw new IllegalStateException("Failed to reset properties due to reflection error", e);
            }
        });

        resetFields(clazz.getSuperclass());  // 递归处理父类
    }

    public void fillWithData(List<AaListCommand> aaListCommands) {
        if (aaListCommands.isEmpty()) {
            logger.warn(">>>>> aaListCommands is empty, No data to fill");
            return;
        }

        List<Map<String, String>> camelCaseData = aaListCommands.stream().filter(AaListCommand::nonNull).map(aaListCommand -> {
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