package com.qtech.check.algorithm;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.common.collect.Maps;
import com.qtech.common.utils.StringUtils;
import org.apache.commons.lang3.tuple.ImmutableTriple;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.lang.reflect.Field;
import java.util.*;
import java.util.concurrent.ConcurrentHashMap;

/**
 * author :  gaozhilin
 * email  :  gaoolin@gmail.com
 * date   :  2024/05/20 10:59:31
 * desc   : 比较对象的属性，记录不一致的属性。
 */
public class AaListParamsComparator {
    private static final Logger logger = LoggerFactory.getLogger(AaListParamsComparator.class);
    private static final ObjectMapper objectMapper = new ObjectMapper();
    private static final Map<String, Field> fieldCache = new ConcurrentHashMap<>();

    // 比较两个对象的指定属性并返回不一致的属性及其值

    /**
     * 比较两个对象的指定属性并返回不一致的属性及其值。
     *
     * @param standardObj         标准对象
     * @param actualObj           实际对象
     * @param propertiesToCompare 需要比较的属性列表
     * @param propertiesToCompute 需要额外处理的属性列表
     * @return ImmutableTriple<>(inconsistentProperties, emptyInActual, emptyInStandard);包含不一致属性、实际对象中为空的属性、标准对象中为空的属性的Triple
     */
    public static ImmutableTriple<Map<String, Map.Entry<Object, Object>>, Map<String, Object>, Map<String, Object>> compareObjectsWithStandardAndActual(
            Object standardObj, Object actualObj, List<String> propertiesToCompare, List<String> propertiesToCompute) {

        if ((propertiesToCompare == null || propertiesToCompare.isEmpty()) &&
                (propertiesToCompute == null || propertiesToCompute.isEmpty())) {
            return new ImmutableTriple<>(new HashMap<>(), new HashMap<>(), new HashMap<>());
        }

        Map<String, Map.Entry<Object, Object>> inconsistentProperties = new HashMap<>();
        Map<String, Object> emptyInActual = new HashMap<>();
        Map<String, Object> emptyInStandard = new HashMap<>();

        // 合并两个列表进行处理
        Set<String> allProperties = new HashSet<>();
        if (propertiesToCompare != null) allProperties.addAll(propertiesToCompare);
        if (propertiesToCompute != null) allProperties.addAll(propertiesToCompute);

        // 遍历所有属性并进行比较
        for (String propertyName : allProperties) {
            try {
                Field modelField = getFieldFromCache(standardObj.getClass(), propertyName);
                Field actualField = getFieldFromCache(actualObj.getClass(), propertyName);

                modelField.setAccessible(true);
                actualField.setAccessible(true);

                Object modelVal = modelField.get(standardObj);
                Object actualVal = actualField.get(actualObj);

                // 统一处理所有 propertiesToCompute 中的属性（List需要管控Item参数值的属性）
                if (propertiesToCompute != null && propertiesToCompute.contains(propertyName)) {
                    handleComputedProperties(propertyName, modelVal, actualVal, inconsistentProperties, emptyInActual, emptyInStandard);
                } else {
                    // 处理PROPERTIES_TO_COMPARE的List是否开启的逻辑
                    compareRegularValues(modelVal, actualVal, propertyName, inconsistentProperties, emptyInActual, emptyInStandard);
                }

            } catch (NoSuchFieldException e) {
                logger.error(">>>>> Field not found: {}", propertyName);
            } catch (IllegalAccessException e) {
                logger.error(">>>>> Access denied for field: {}", propertyName);
            }
        }


        return new ImmutableTriple<>(inconsistentProperties, emptyInActual, emptyInStandard);
    }

    /**
     * 统一处理 propertiesToCompute 中的所有属性，包括特殊处理的字段、数值类型和字符串类型
     */
    private static void handleComputedProperties(String propertyName, Object modelVal, Object actualVal,
                                                 Map<String, Map.Entry<Object, Object>> inconsistentProperties,
                                                 Map<String, Object> emptyInActual, Map<String, Object> emptyInStandard) {
        if (StringUtils.startsWith(propertyName, "mtfCheck") && StringUtils.endsWith(propertyName, "F")) {
            // 处理 mtfCheck 逻辑
            handleMtfCheck(propertyName, modelVal, actualVal, inconsistentProperties, emptyInActual, emptyInStandard);
        } else if ("epoxyInspectionInterval".equals(propertyName)) {
            // 处理 epoxyInspectionAuto 逻辑
            handleEpoxyInspectionAuto(propertyName, modelVal, actualVal, inconsistentProperties, emptyInActual, emptyInStandard);
        } else {
            // 判断是否为数值类型的字符串
            if (isNumeric(modelVal) && isNumeric(actualVal)) {
                // 比较数值
                if (!compareStringNumbers(modelVal, actualVal)) {
                    addToResult(modelVal, actualVal, propertyName, inconsistentProperties, emptyInActual, emptyInStandard);
                }
            } else {
                // 直接比较字符串
                compareRegularValues(modelVal, actualVal, propertyName, inconsistentProperties, emptyInActual, emptyInStandard);
            }
        }
    }

    /**
     * 判断一个对象的值是否是数值类型的字符串
     */
    private static boolean isNumeric(Object value) {
        if (value == null) {
            return false;
        }
        String str = value.toString().trim();
        return str.matches("-?\\d+(\\.\\d+)?");
    }

    private static Field getFieldFromCache(Class<?> clazz, String fieldName) throws NoSuchFieldException {
        String cacheKey = clazz.getName() + "." + fieldName;
        return fieldCache.computeIfAbsent(cacheKey, key -> {
            try {
                return getFieldFromClassHierarchy(clazz, fieldName);
            } catch (NoSuchFieldException e) {
                throw new RuntimeException(e);
            }
        });
    }

    /**
     * 获取类及其父类中的字段
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
                clazz = clazz.getSuperclass();  // Continue searching in parent classes
            }
        }
        throw new NoSuchFieldException("Field " + fieldName + " not found in class hierarchy.");
    }

    // 处理常规属性值比较
    private static void compareRegularValues(Object modelVal, Object actualVal, String propertyName,
                                             Map<String, Map.Entry<Object, Object>> inconsistentProperties,
                                             Map<String, Object> emptyInActual, Map<String, Object> emptyInStandard) {
        if (!Objects.equals(modelVal, actualVal)) {
            addToResult(modelVal, actualVal, propertyName, inconsistentProperties, emptyInActual, emptyInStandard);
        }
    }

    // 处理特殊属性（如 mtfCheck 和 epoxyInspectionAuto）
    private static void handleSpecialProperties(String propertyName, Object modelVal, Object actualVal,
                                                Map<String, Map.Entry<Object, Object>> inconsistentProperties,
                                                Map<String, Object> emptyInActual, Map<String, Object> emptyInStandard) {
        if (StringUtils.startsWith(propertyName, "mtfCheck") && StringUtils.endsWith(propertyName, "F")) {
            handleMtfCheck(propertyName, modelVal, actualVal, inconsistentProperties, emptyInActual, emptyInStandard);
        } else if ("epoxyInspectionInterval".equals(propertyName)) {
            handleEpoxyInspectionAuto(propertyName, modelVal, actualVal, inconsistentProperties, emptyInActual, emptyInStandard);
        } else {
            // 数字类型处理
            if (!compareStringNumbers(modelVal, actualVal)) {
                addToResult(modelVal, actualVal, propertyName, inconsistentProperties, emptyInActual, emptyInStandard);
            }
        }
    }

    // 处理 mtfCheck 特殊逻辑
    private static void handleMtfCheck(String propertyName, Object modelVal, Object actualVal,
                                       Map<String, Map.Entry<Object, Object>> inconsistentProperties,
                                       Map<String, Object> emptyInActual, Map<String, Object> emptyInStandard) {
        if (modelVal == null && actualVal == null) {
            logger.info(">>>>> both modelVal and actualVal are empty for {}", propertyName);
            return;
        }

        if (modelVal != null && actualVal != null) {
            try {
                Map<String, Double> modelMap = objectMapper.readValue(modelVal.toString(), new TypeReference<Map<String, Double>>() {
                });
                Map<String, Double> actualMap = objectMapper.readValue(actualVal.toString(), new TypeReference<Map<String, Double>>() {
                });

                if (modelMap.equals(actualMap)) {
                    return; // 直接返回，不做额外存储
                }
                compareMaps(modelMap, actualMap, propertyName, inconsistentProperties, emptyInActual, emptyInStandard);
            } catch (JsonProcessingException e) {
                logger.error("JSON parsing error in {}: {}, treating as raw string comparison", propertyName, e.getMessage());
                addToResult(modelVal, actualVal, propertyName, inconsistentProperties, emptyInActual, emptyInStandard);
            }
        } else {
            addToResult(modelVal, actualVal, propertyName, inconsistentProperties, emptyInActual, emptyInStandard);
        }
    }


    // 处理 epoxyInspectionAuto 特殊逻辑
    private static void handleEpoxyInspectionAuto(String propertyName, Object modelVal, Object actualVal,
                                                  Map<String, Map.Entry<Object, Object>> inconsistentProperties,
                                                  Map<String, Object> emptyInActual, Map<String, Object> emptyInStandard) {
        if (modelVal == null && actualVal == null) {
            // modelVal 和 actualVal 都为空，不记录
            logger.info(">>>>> both modelVal and actualVal are empty propertyName: {}", propertyName);
            return;
        }

        if (modelVal != null) {
            // 确保 modelVal 是数字类型，转换成整数后进行比较
            int modelValInt = Integer.parseInt(modelVal.toString());

            if (actualVal == null || StringUtils.isBlank(actualVal.toString())) {
                // actualVal 为空，记录 modelVal 和 null 之间的差异
                addToResult(modelValInt, null, propertyName, inconsistentProperties, emptyInActual, emptyInStandard);
            } else {
                int actualValInt = Integer.parseInt(actualVal.toString());
                // 检查 actualVal 是否在有效范围内 (5 到 30)
                if (actualValInt < 5 || actualValInt > 30) {
                    addToResult("[ 5 <= epoxyInspectionAuto <= 30 ]", actualValInt, propertyName, inconsistentProperties, emptyInActual, emptyInStandard);
                }
            }
        } else {
            // modelVal 为空，但 actualVal 不为空，仍需记录
            addToResult(null, actualVal, propertyName, inconsistentProperties, emptyInActual, emptyInStandard);
        }
    }


    // 比较两个字符串类型的数字

    /**
     * 比较两个数值类型的属性是否相等
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
            return Math.abs(modelNum - actualNum) < 0.0001;
        } catch (NumberFormatException e) {
            return false;
        }
    }

    // 比较两个 JSON 字符串表示的 Map<Integer, Double>
    private static void compareJsonMaps(Object modelVal, Object actualVal, String propertyName,
                                        Map<String, Map.Entry<Object, Object>> inconsistentProperties,
                                        Map<String, Object> emptyInActual, Map<String, Object> emptyInStandard) {
        try {
            Map<String, Double> modelMap = objectMapper.readValue(modelVal.toString(), new TypeReference<Map<String, Double>>() {
            });
            Map<String, Double> actualMap = objectMapper.readValue(actualVal.toString(), new TypeReference<Map<String, Double>>() {
            });

            compareMaps(modelMap, actualMap, propertyName, inconsistentProperties, emptyInActual, emptyInStandard);
        } catch (JsonProcessingException e) {
            logger.error("JSON processing error in {}: {}, using raw values for comparison", propertyName, e.getMessage());
            addToResult(modelVal, actualVal, propertyName, inconsistentProperties, emptyInActual, emptyInStandard);
        }
    }


    private static void compareMaps(Map<String, Double> modelMap, Map<String, Double> actualMap, String propertyName,
                                    Map<String, Map.Entry<Object, Object>> inconsistentProperties,
                                    Map<String, Object> emptyInActual, Map<String, Object> emptyInStandard) {
        Set<String> allKeys = new HashSet<>(modelMap.keySet());
        allKeys.addAll(actualMap.keySet());

        for (String key : allKeys) {
            Double modelValue = modelMap.get(key);
            Double actualValue = actualMap.get(key);
            if (!Objects.equals(modelValue, actualValue)) {
                addToResult(modelValue, actualValue, key, inconsistentProperties, emptyInActual, emptyInStandard);
            }
        }
    }

    // 将不一致的属性添加到结果中

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
    private static void addToResult(Object modelVal, Object actualVal, String propertyName,
                                    Map<String, Map.Entry<Object, Object>> inconsistentProperties,
                                    Map<String, Object> emptyInActual, Map<String, Object> emptyInStandard) {
        if (modelVal == null) {
            emptyInStandard.put(propertyName, null);
        } else if (actualVal == null) {
            emptyInActual.put(propertyName, null);
        } else {
            inconsistentProperties.put(propertyName, Maps.immutableEntry(modelVal, actualVal));
        }
    }

}

