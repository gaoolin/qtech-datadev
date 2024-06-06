package com.qtech.check.constant;

import java.util.*;

/**
 * author :  gaozhilin
 * email  :  gaoolin@gmail.com
 * date   :  2024/05/20 10:19:44
 * desc   :
 */


public class ComparisonConstants {
    public static final List<String> PROPERTIES_TO_COMPARE = Arrays.asList(
            "prodType",
            "clampOnOff",
            "destroyStart",
            "init",
            "grab",
            "reInit",
            "senserReset",
            "sid",
            "vcmHall",
            "vcmInit",
            "vcmHall2",
            "vcmPowerOff",
            "vcmPowerOn",
            "vcmTop",
            "vcmTopHall",
            "vcmZ",
            "vcmZHall",
            "vcmOisInit",
            "chartAlignment1",
            "AA1",
            "AA2",
            "mtfCheck",
            "AA3",
            "mtfCheck2",
            "lpOn",
            "lpOcCheck",
            "lpOc",
            "lpOnBlemish",
            "blemish",
            "lpOff",
            "chartAlignment",
            "vcmMoveToZ",
            "delay",
            "vcmPowerOffCheck",
            "recordPosition",
            "dispense",
            "epoxyInspectionAuto",
            "epoxyInspection",
            "backToPosition",
            "uvon",
            "yLevel",
            "uvoff",
            "gripperOpen",
            "saveOc",
            "saveMtf",
            "destroy",
            "moveToBlemishPos",
            "mtfCheck3",
            "mtfOffAxisCheck1",
            "mtfOffAxisCheck2",
            "mtfOffAxisCheck3",
            "lpBlemish",
            "chartAlignment2",
            "vcmMoveToZPos",
            "zOffset",
            "openCheck",
            "roiCc", "roiUl", "roiUr", "roiLl", "roiLr",
            "result1", "result2", "result3", "result4", "result5", "result6", "result7", "result8", "result9", "result10",
            "result11", "result12", "result13", "result14", "result15", "result16", "result17", "result18", "result19", "result20",
            "result21", "result22", "result23", "result24", "result25", "result26", "result27", "result28", "result29", "result30",
            "result31", "result32", "result33", "result34", "result35", "result36", "result37", "result38", "result39", "result40",
            "result41", "result42", "result43", "result44", "result45", "result46", "result47", "result48", "result49", "result50",
            "result51", "result52",
            "xResMin", "xResMax", "yResMin", "yResMax",
            "epoxyInspectionInterval",
            "resultCheckMin", "resultCheckMax"
            // ... 其他需要比较是否相等的属性名称
    );

    public static final String REDIS_COMPARISON_MODEL_KEY_PREFIX = "qtech:aa:list:params:";

    public static final Set<String> CONTROL_LIST_SET = Collections.unmodifiableSet(new HashSet<>(Arrays.asList(
            "AA1", "AA2", "AA3", "ChartAlignment", "ChartAlignment1", "ChartAlignment2", "EpoxyInspection_Auto", "LP_OC", "MTF_Check", "MTF_Check2", "MTF_Check3",
            "MTF_OffAxisCheck1", "MTF_OffAxisCheck2", "MTF_OffAxisCheck3", "Save_MTF", "VCM_Init", "VCMPowerOffCheck", "Z_Offset")));
}
