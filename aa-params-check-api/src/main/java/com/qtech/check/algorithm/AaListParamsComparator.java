package com.qtech.check.algorithm;

import com.google.common.collect.Maps;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.tuple.ImmutableTriple;

import java.lang.reflect.Field;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;

/**
 * author :  gaozhilin
 * email  :  gaoolin@gmail.com
 * date   :  2024/05/20 10:59:31
 * desc   :
 */

/*
Objects.equals()方法用于比较两个对象是否相等，包括null值。这意味着Objects.equals(value1, value2)会正确处理null值的情况。如果value1和value2都为null，
Objects.equals()会返回true，否则如果它们不相等（包括一个为null，另一个不为null），它会返回false。因此，if (!Objects.equals(value1, value2))会捕获不相等的值，
包括null和非null的组合。接下来的两个if语句分别检查value1和value2的空值情况，确保不会错过任何情况。
*/

@Slf4j
public class AaListParamsComparator {

    /**
     * 比较两个对象的指定属性并返回不一致的属性及其值。
     *
     * @param standardObj         标准对象
     * @param actualObj           实际对象
     * @param propertiesToCompare 需要比较的属性列表
     * @return 包含不一致属性、实际对象中为空的属性、标准对象中为空的属性的Triple
     */
    public static ImmutableTriple<Map<String, Map.Entry<Object, Object>>, Map<String, Object>, Map<String, Object>> compareObjectsWithStandardAndActual(
            Object standardObj, Object actualObj, List<String> propertiesToCompare) {

        if (propertiesToCompare == null || propertiesToCompare.isEmpty()) {
            // 如果没有属性需要比较，则直接返回表示没有不一致的结果
            return new ImmutableTriple<>(new HashMap<>(), new HashMap<>(), new HashMap<>());
        }

        Map<String, Map.Entry<Object, Object>> inconsistentProperties = new HashMap<>();
        Map<String, Object> emptyInActual = new HashMap<>();
        Map<String, Object> emptyInStandard = new HashMap<>();

        for (String propertyName : propertiesToCompare) {
            try {
                Field field1 = standardObj.getClass().getDeclaredField(propertyName);
                Field field2 = actualObj.getClass().getDeclaredField(propertyName);
                field1.setAccessible(true);
                field2.setAccessible(true);

                Object modelVal = field1.get(standardObj);
                Object actualVal = field2.get(actualObj);

                // 使用Objects.equals简化null检查和相等性检查
                if (!Objects.equals(modelVal, actualVal)) {
                    // 使用逻辑运算符简化条件判断，减少代码重复
                    if (modelVal == null) {
                        emptyInStandard.put(propertyName, null);
                    } else if (actualVal == null) {
                        // 当modelVal不为null，且actualVal为null时，添加到emptyInActual
                        emptyInActual.put(propertyName, null);
                    } else {
                        // 当modelVal和actualVal均不为null且不相等时，添加到inconsistentProperties
                        inconsistentProperties.put(propertyName, Maps.immutableEntry(modelVal, actualVal));
                    }
                }
            } catch (NoSuchFieldException e) {
                // 可以考虑记录日志而非抛出异常
                log.error("Field not found: {}", propertyName);
            } catch (IllegalAccessException e) {
                // 可以考虑记录日志而非抛出异常
                log.error("Access denied for field: {}", propertyName);
            }
        }

        // 假设compareObjectsWithRanges方法已经相应优化
        boolean areEqualAndInRange = compareObjectsWithRanges(standardObj, actualObj, propertiesToCompare);

        return new ImmutableTriple<>(inconsistentProperties, emptyInActual, emptyInStandard);
    }


    // 原有的compareObjectsWithRanges方法保持不变
    public static boolean compareObjectsWithRanges(
//            Object obj1, Object obj2, List<String> propertiesToCompare, Map<String, Range<Integer>> propertiesWithRanges) {
            Object obj1, Object obj2, List<String> propertiesToCompare) {
        if (obj1 == obj2) return true;
        if (obj1 == null || obj2 == null) return false;

        // 比较相等的属性
        for (String propertyName : propertiesToCompare) {
            try {
                Field field1 = obj1.getClass().getDeclaredField(propertyName);
                Field field2 = obj2.getClass().getDeclaredField(propertyName);
                field1.setAccessible(true);
                field2.setAccessible(true);

                Object value1 = field1.get(obj1);
                Object value2 = field2.get(obj2);

                if (!Objects.equals(value1, value2)) {
                    return false;
                }
            } catch (NoSuchFieldException | IllegalAccessException e) {
                throw new IllegalArgumentException("Error accessing field: " + propertyName, e);
            }
        }

        // 检查范围
        /*for (Map.Entry<String, Range<Integer>> entry : propertiesWithRanges.entrySet()) {
            try {
                Field field1 = obj1.getClass().getDeclaredField(entry.getKey());
                Field field2 = obj2.getClass().getDeclaredField(entry.getKey());
                field1.setAccessible(true);
                field2.setAccessible(true);

                Integer value1 = (Integer) field1.get(obj1);
                Integer value2 = (Integer) field2.get(obj2);

                if (value1 == null || value1 < entry.getValue().getMin() || value1 > entry.getValue().getMax()
                        || value2 == null || value2 < entry.getValue().getMin() || value2 > entry.getValue().getMax()) {
                    return false;
                }
            } catch (NoSuchFieldException | IllegalAccessException e) {
                throw new IllegalArgumentException("Error accessing field: " + entry.getKey(), e);
            }
        }*/

        return true;
    }
}


