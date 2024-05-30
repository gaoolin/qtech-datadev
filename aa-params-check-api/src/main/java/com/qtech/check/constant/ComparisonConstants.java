package com.qtech.check.constant;

import java.util.Arrays;
import java.util.List;

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
            "openCheck"
            // ... 其他需要比较是否相等的属性名称
    );
//    public static final List<String> PROPERTIES_TO_COMPARE = Arrays.asList("init");

//        public static final Map<String, Range<Integer>> PROPERTIES_WITH_RANGES = new HashMap<>();
//
//        static {
//            PROPERTIES_WITH_RANGES.put("propertyNameForRange1", new Range<>(0, 100)); // 示例范围
//            PROPERTIES_WITH_RANGES.put("propertyNameForRange2", new Range<>(1, 10)); // 示例范围
//            // ... 添加其他需要检查范围的属性及其范围
//        }

//    public static final List<String> ALL_PROPERTIES = Arrays.asList();

    public static final String REDIS_COMPARISON_MODEL_KEY_PREFIX = "qtech:aa:list:params:";
}
