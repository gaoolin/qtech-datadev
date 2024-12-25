package com.qtech.check.pojo;

import com.baomidou.mybatisplus.annotation.TableName;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.qtech.share.aa.pojo.ImAaListParams;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

import java.lang.reflect.Field;
import java.util.*;

/**
 * author :  gaozhilin
 * email  :  gaoolin@gmail.com
 * date   :  2024/05/21 08:45:18
 * desc   :
 */

@Data
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
@TableName("IMBIZ.IM_AA_LIST_PARAMS_STD_MODEL")
@JsonIgnoreProperties(ignoreUnknown = true) // 忽略未知属性
public class AaListParamsStdModel extends ImAaListParams {

    private Long id;
    private String prodType;

    @Override
    public void reset() {
        this.id = null;
        this.prodType = null;
        super.reset();
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

    private List<Field> getAllFields(Class<?> clazz) {
        List<Field> fields = new ArrayList<>();
        while (clazz != null) {
            fields.addAll(Arrays.asList(clazz.getDeclaredFields()));
            clazz = clazz.getSuperclass();
        }
        return fields;
    }
}
