package com.qtech.check.pojo;

import lombok.Data;
import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;
import org.apache.commons.lang3.builder.ReflectionToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

import java.lang.reflect.Field;

/**
 * author :  gaozhilin
 * email  :  gaoolin@gmail.com
 * date   :  2024/05/21 08:45:18
 * desc   :
 */

@Data
public class AaListParamsStdModel extends AaListParamsBaseEntity {
    private String prodType;

    public void reset() {
        this.prodType = null;
        super.reset();
    }

    @Override
    public String toString() {
        return ReflectionToStringBuilder.toString(this, ToStringStyle.MULTI_LINE_STYLE);
    }

    // equals 和 hashCode 方法使用 reflectionEquals 和 reflectionHashCode 方法，它们会递归地比较和计算对象的所有字段，因此无需额外处理父类的字段
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        if (!super.equals(o)) return false;
        AaListParams that = (AaListParams) o;
        return EqualsBuilder.reflectionEquals(this, that, false);
    }

    @Override
    public int hashCode() {
        return HashCodeBuilder.reflectionHashCode(this, false);
    }
}
