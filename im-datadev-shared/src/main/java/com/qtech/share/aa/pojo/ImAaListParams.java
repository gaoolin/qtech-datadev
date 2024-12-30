package com.qtech.share.aa.pojo;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.qtech.share.aa.util.ToCamelCaseConverter;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;
import lombok.experimental.Accessors;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.Serializable;
import java.lang.reflect.Field;
import java.util.*;
import java.util.stream.Collectors;

/**
 * author :  gaozhilin
 * email  :  gaoolin@gmail.com
 * date   :  2024/11/27 15:41:48
 * desc   :  AA List参数
 */
@Data
@Accessors(chain = true) // 表示启用链式调用
@EqualsAndHashCode(callSuper = false)
@ToString(callSuper = true)
@JsonIgnoreProperties(ignoreUnknown = true)
public class ImAaListParams implements Serializable {
    private static final long serialVersionUID = 529L;
    private static final Logger logger = LoggerFactory.getLogger(ImAaListParams.class);
    private String prodType;
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

    // 新增 2024-10-28
    private String aa1Target;
    private String aa1CcToCornerLimit;
    private String aa1CcToCornerLimitMin;
    private String aa1CornerScoreDifferenceRejectValue;
    private String aa1ZRef;
    private String aa1SrchStep;
    private String aa1GoldenGlueThicknessMin;
    private String aa1GoldenGlueThicknessMax;

    private String aa2RoiCc;
    private String aa2RoiUl;
    private String aa2RoiUr;
    private String aa2RoiLl;
    private String aa2RoiLr;

    // 新增 2024-10-28
    private String aa2Target;
    private String aa2CcToCornerLimit;
    private String aa2CcToCornerLimitMin;
    private String aa2CornerScoreDifferenceRejectValue;
    private String aa2ZRef;
    private String aa2SrchStep;
    private String aa2GoldenGlueThicknessMin;
    private String aa2GoldenGlueThicknessMax;

    private String aa3RoiCc;
    private String aa3RoiUl;
    private String aa3RoiUr;
    private String aa3RoiLl;
    private String aa3RoiLr;

    // 新增 2024-10-28
    private String aa3Target;
    private String aa3CcToCornerLimit;
    private String aa3CcToCornerLimitMin;
    private String aa3CornerScoreDifferenceRejectValue;
    private String aa3ZRef;
    private String aa3SrchStep;
    private String aa3GoldenGlueThicknessMin;
    private String aa3GoldenGlueThicknessMax;

    // mtfCheck Item 指标
    private String mtfCheckF;

    private String mtfCheck1F;

    private String mtfCheck2F;

    private String mtfCheck3F;

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

    public void fillWithData(List<ImAaListCommand> imAaListCommands) {
        if (imAaListCommands.isEmpty()) {
            logger.warn(">>>>> imAaListCommands is empty, No data to fill");
            return;
        }

        List<Map<String, String>> camelCaseData = imAaListCommands.stream().filter(ImAaListCommand::nonNull).map(imAaListCommand -> {
            Map<String, String> map = new HashMap<>();
            Optional.ofNullable(imAaListCommand.getIntegration()).ifPresent(integration -> {
                String cmdObjVal = imAaListCommand.getValue();
                if (cmdObjVal == null) {
                    String metricsMin = String.join("", ToCamelCaseConverter.doConvert(integration), "Min");
                    String metricsMax = String.join("", ToCamelCaseConverter.doConvert(integration), "Max");
                    map.put(metricsMin, imAaListCommand.getRange().getMin());
                    map.put(metricsMax, imAaListCommand.getRange().getMax());
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
