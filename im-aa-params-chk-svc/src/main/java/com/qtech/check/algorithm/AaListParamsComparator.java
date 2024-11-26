package com.qtech.check.algorithm;

import com.google.common.collect.Maps;
import com.qtech.share.aa.model.Range;
import org.apache.commons.lang3.tuple.ImmutableTriple;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.lang.reflect.Field;
import java.util.*;

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
public class AaListParamsComparator {
    private static final Logger logger = LoggerFactory.getLogger(AaListParamsComparator.class);

    /**
     * 比较两个对象的指定属性并返回不一致的属性及其值。
     *
     * @param standardObj         标准对象
     * @param actualObj           实际对象
     * @param propertiesToCompare 需要比较的属性列表
     * @param propertiesToCompute 需要额外处理的属性列表
     * @return 包含不一致属性、实际对象中为空的属性、标准对象中为空的属性的Triple
     */
    public static ImmutableTriple<Map<String, Map.Entry<Object, Object>>, Map<String, Object>, Map<String, Object>> compareObjectsWithStandardAndActual(Object standardObj, Object actualObj, List<String> propertiesToCompare, List<String> propertiesToCompute) {

        if ((propertiesToCompare == null || propertiesToCompare.isEmpty()) && (propertiesToCompute == null || propertiesToCompute.isEmpty())) {
            // 如果没有属性需要比较，则直接返回表示没有不一致的结果
            return new ImmutableTriple<>(new HashMap<>(), new HashMap<>(), new HashMap<>());
        }

        Map<String, Map.Entry<Object, Object>> inconsistentProperties = new HashMap<>();
        Map<String, Object> emptyInActual = new HashMap<>();
        Map<String, Object> emptyInStandard = new HashMap<>();

        // 合并两个列表进行处理
        Set<String> allProperties = new HashSet<>();
        if (propertiesToCompare != null) {
            allProperties.addAll(propertiesToCompare);
        }
        if (propertiesToCompute != null) {
            allProperties.addAll(propertiesToCompute);
        }

        for (String propertyName : allProperties) {
            try {
                Field modelField = getFieldFromClassHierarchy(standardObj.getClass(), propertyName);
                Field actualField = getFieldFromClassHierarchy(actualObj.getClass(), propertyName);

                modelField.setAccessible(true);
                actualField.setAccessible(true);

                Object modelVal = modelField.get(standardObj);
                Object actualVal = actualField.get(actualObj);

                if (propertiesToCompute != null && propertiesToCompute.contains(propertyName)) {
                    // 对于propertiesToCompute需要特别处理字符串数值
                    if (!compareStringNumbers(modelVal, actualVal)) {
                        addToResult(modelVal, actualVal, propertyName, inconsistentProperties, emptyInActual, emptyInStandard);
                    }
                } else {
                    if (!Objects.equals(modelVal, actualVal)) {
                        addToResult(modelVal, actualVal, propertyName, inconsistentProperties, emptyInActual, emptyInStandard);
                    }
                }
            } catch (NoSuchFieldException e) {
                logger.error(">>>>> Field not found in actualVal: {}", propertyName);
            } catch (IllegalAccessException e) {
                logger.error(">>>>> Access denied for field: {}", propertyName);
            }
        }

        return new ImmutableTriple<>(inconsistentProperties, emptyInActual, emptyInStandard);
    }

    /**
     * 获取类及其所有父类中的字段
     *
     * @param clazz     类对象
     * @param fieldName 字段名称
     * @return 字段对象
     * @throws NoSuchFieldException 如果未找到字段
     */
    private static Field getFieldFromClassHierarchy(Class<?> clazz, String fieldName) throws NoSuchFieldException {
        while (clazz != null) {
            try {
                return clazz.getDeclaredField(fieldName);
            } catch (NoSuchFieldException e) {
                clazz = clazz.getSuperclass();  // 继续查找父类
            }
        }
        throw new NoSuchFieldException("Field " + fieldName + " not found in class hierarchy.");
    }

    /**
     * 比较两个字符串数值是否相等
     *
     * @param modelVal  标准值
     * @param actualVal 实际值
     * @return 是否相等
     */
    private static boolean compareStringNumbers(Object modelVal, Object actualVal) {
        if (modelVal == null && actualVal == null) {
            return true;
        }
        if (modelVal == null || actualVal == null) {
            return false;
        }
        try {
            Double modelNum = Double.parseDouble(modelVal.toString());
            Double actualNum = Double.parseDouble(actualVal.toString());
            return Objects.equals(modelNum, actualNum);
        } catch (NumberFormatException e) {
            return false;
        }
    }

    /**
     * 将比较结果添加到结果集合中
     *
     * @param modelVal               标准值
     * @param actualVal              实际值
     * @param propertyName           属性名称
     * @param inconsistentProperties 不一致的属性集合
     * @param emptyInActual          实际对象为空的属性集合
     * @param emptyInStandard        标准对象为空的属性集合
     */
    private static void addToResult(Object modelVal, Object actualVal, String propertyName, Map<String, Map.Entry<Object, Object>> inconsistentProperties, Map<String, Object> emptyInActual, Map<String, Object> emptyInStandard) {
        if (modelVal == null) {
            emptyInStandard.put(propertyName, null);
        } else if (actualVal == null) {
            emptyInActual.put(propertyName, null);
        } else {
            inconsistentProperties.put(propertyName, Maps.immutableEntry(modelVal, actualVal));
        }
    }

    // 原有的compareObjectsWithRanges方法保持不变
    private static boolean compareObjectsWithRanges(Object obj1, Object obj2, List<String> propertiesToCompare, Map<String, Range<Integer>> propertiesWithRanges) {
        if (obj1 == obj2) return true;
        if (obj1 == null || obj2 == null) return false;

        // 检查范围
        for (Map.Entry<String, Range<Integer>> entry : propertiesWithRanges.entrySet()) {
            try {
                Field field1 = getFieldFromClassHierarchy(obj1.getClass(), entry.getKey());
                Field field2 = getFieldFromClassHierarchy(obj2.getClass(), entry.getKey());

                field1.setAccessible(true);
                field2.setAccessible(true);

                Integer value1 = (Integer) field1.get(obj1);
                Integer value2 = (Integer) field2.get(obj2);

                if (value1 == null || value1 < entry.getValue().getMin() || value1 > entry.getValue().getMax() || value2 == null || value2 < entry.getValue().getMin() || value2 > entry.getValue().getMax()) {
                    return false;
                }
            } catch (NoSuchFieldException | IllegalAccessException e) {
                throw new IllegalArgumentException("Error accessing field: " + entry.getKey(), e);
            }
        }

        return true;
    }
}