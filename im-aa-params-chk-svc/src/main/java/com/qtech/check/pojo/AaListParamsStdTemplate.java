package com.qtech.check.pojo;

import com.baomidou.mybatisplus.annotation.TableName;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.qtech.share.aa.pojo.ImAaListParams;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

import java.lang.reflect.Field;
import java.util.List;
import java.util.Map;

/**
 * author :  gaozhilin
 * email  :  gaoolin@gmail.com
 * date   :  2024/05/21 08:45:18
 * desc   :
 */

@Data
@ToString(callSuper = true)
@TableName("IMBIZ.IM_AA_LIST_PARAMS_STD_MODEL")
@JsonIgnoreProperties(ignoreUnknown = true) // 忽略未知属性
public class AaListParamsStdTemplate extends ImAaListParams {

    private Long id;

    @Override
    public void reset() {
        this.id = null;
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

    @Override
    public boolean equals(Object o) {
        return super.equals(o);
    }

    @Override
    public int hashCode() {
        return super.hashCode();
    }
}
