package com.qtech.check.constant;

import java.util.*;

/**
 * author :  gaozhilin
 * email  :  gaoolin@gmail.com
 * date   :  2024/05/20 10:19:44
 * desc   :
 */

public class ComparisonConstants {
    /*
     * 需要点检是否开启的属性名称
     * 即所有的List项
     */
    public static final List<String> PROPERTIES_TO_COMPARE = Arrays.asList(
            "prodType",
            "aa1",
            "aa2",
            "aa3",
            "backToPosition",
            "blemish",
            "clampOnOff",
            "chartAlignment",
            "chartAlignment1",
            "chartAlignment2",
            "delay",
            "destroy",
            "destroyStart",
            "dispense",
            "epoxyInspection",
            "epoxyInspectionAuto",
            "grab",
            "gripperOpen",
            "init",
            "lpBlemish",
            "lpOc",
            "lpOnBlemish",
            "lpOcCheck",
            "lpOn",
            "lpOff",
            "moveToBlemishPos",
            "mtfCheck",
            "mtfCheck1",
            "mtfCheck2",
            "mtfCheck3",
            "openCheck",
            "ocCheck",
            "recordPosition",
            "reInit",
            "saveOc",
            "saveMtf",
            "senserReset",
            "sid",
            "uvon",
            "uvoff",
            "vcmHall",
            "vcmHall2",
            "vcmMoveToZ",
            "vcmMoveToZPos",
            "vcmPowerOffCheck",
            "vcmRun",
            "vcmInit",
            "vcmOisInit",
            "vcmPowerOff",
            "vcmPowerOn",
            "vcmTop",
            "vcmTopHall",
            "vcmZ",
            "vcmZHall",
            "yLevel"
            // ... 其他需要比较是否相等的属性名称
    );

    /*
     * 需要解析对应List项的Item参数值并比对的List项目（不是所有List的Item参数都需要解析，也不是都需要比对和反控）
     * 即List对应的Item项中有哪些参数需要解析，哪些参数需要比对，哪些参数需要反控
     * 需要增减List参数的管控，则可直接在下列列表中添加或删除
     */
    public static final Set<String> CONTROL_LIST_SET = Collections.unmodifiableSet(
            new HashSet<>(
                    Arrays.asList(
                            "AA1",
                            "AA2",
                            "AA3",
                            "ChartAlignment",
                            "ChartAlignment1",
                            "ChartAlignment2",
                            "EpoxyInspection_Auto",
                            "MTF_Check",
                            "MTF_Check1",
                            "MTF_Check2",
                            "MTF_Check3",
                            "RecordPosition",
                            "Save_MTF"
                    )
            )
    );

    /*
     * 需要计算值的Item项名称
     * 上面 CONTROL_LIST_SET 数组中List对应的Item项中，具体command或者subCommand的value
     * 上面的 CONTROL_LIST_SET 数组发生了增减，下面的数组也要对应的增减相关command或者subCommand
     */
    public static final List<String> PROPERTIES_TO_COMPUTE = Arrays.asList(
            "aa1RoiCc", "aa1RoiUl", "aa1RoiUr", "aa1RoiLl", "aa1RoiLr", "aa1FC", "aa1F1", "aa1F2", "aa1F3", "aa1F4", "aa1MtfOffAxisCheck1", "aa1MtfOffAxisCheck2", "aa1MtfOffAxisCheck3",
            "aa2RoiCc", "aa2RoiUl", "aa2RoiUr", "aa2RoiLl", "aa2RoiLr", "aa2FC", "aa2F1", "aa2F2", "aa2F3", "aa2F4", "aa2MtfOffAxisCheck1", "aa2MtfOffAxisCheck2", "aa2MtfOffAxisCheck3",
            "aa3RoiCc", "aa3RoiUl", "aa3RoiUr", "aa3RoiLl", "aa3RoiLr", "aa3FC", "aa3F1", "aa3F2", "aa3F3", "aa3F4", "aa3MtfOffAxisCheck1", "aa3MtfOffAxisCheck2", "aa3MtfOffAxisCheck3",
            "mtfCheckFC", "mtfCheckF1", "mtfCheckF2", "mtfCheckF3", "mtfCheckF4",
            "mtfCheck1FC", "mtfCheck1F1", "mtfCheck1F2", "mtfCheck1F3", "mtfCheck1F4",
            "mtfCheck2FC", "mtfCheck2F1", "mtfCheck2F2", "mtfCheck2F3", "mtfCheck2F4",
            "mtfCheck3FC", "mtfCheck3F1", "mtfCheck3F2", "mtfCheck3F3", "mtfCheck3F4",
            "chartAlignmentXResMin", "chartAlignmentXResMax", "chartAlignmentYResMin", "chartAlignmentYResMax",
            "chartAlignment1XResMin", "chartAlignment1XResMax", "chartAlignment1YResMin", "chartAlignment1YResMax",
            "chartAlignment2XResMin", "chartAlignment2XResMax", "chartAlignment2YResMin", "chartAlignment2YResMax",
            "epoxyInspectionInterval",
            "vcmCheckResultCheckMin", "vcmCheckResultCheckMax",
            "recordPositionUtXyzMove",
            "ocCheckXOffsetMin", "ocCheckXOffsetMax", "ocCheckYOffsetMin", "ocCheckYOffsetMax",
            "saveOcXOffsetMin", "saveOcXOffsetMax", "saveOcYOffsetMin", "saveOcYOffsetMax",
            "saveMtfCcMin", "saveMtfCcMax"
    );

    // redis key prefix
    public static final String REDIS_COMPARISON_MODEL_KEY_PREFIX = "qtech:im:aa:list:model:";
    public static final String REDIS_COMPARISON_MODEL_INFO_KEY_SUFFIX = "qtech:im:aa:list:model:info:";
}
