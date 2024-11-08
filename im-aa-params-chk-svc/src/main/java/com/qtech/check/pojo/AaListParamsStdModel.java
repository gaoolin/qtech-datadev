package com.qtech.check.pojo;

import lombok.Data;
import lombok.EqualsAndHashCode;
import org.apache.commons.lang3.builder.ReflectionToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

/**
 * author :  gaozhilin
 * email  :  gaoolin@gmail.com
 * date   :  2024/05/21 08:45:18
 * desc   :
 */

@Data
@EqualsAndHashCode(callSuper = true)
public class AaListParamsStdModel extends AaListParams {

    private Long id;
    private String prodType;

    public void reset() {
        this.prodType = null;
        super.reset();
    }

    //  @Data 注解，它已经包含了 @Getter、@Setter、@ToString、@EqualsAndHashCode 和 @RequiredArgsConstructor 等注解。因此在子类中，您不需要重复定义 getter 和 setter 方法。
    //  继承 equals 和 hashCode 方法: 子类自动继承了基类的 equals 和 hashCode 方法。
    @Override
    public String toString() {
        return ReflectionToStringBuilder.toString(this, ToStringStyle.MULTI_LINE_STYLE);
    }

    // equals 和 hashCode 方法使用 reflectionEquals 和 reflectionHashCode 方法，它们会递归地比较和计算对象的所有字段，因此无需额外处理父类的字段
}
