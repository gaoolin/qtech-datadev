package com.qtech.check.pojo;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

import java.util.Date;

/**
 * author :  gaozhilin
 * email  :  gaoolin@gmail.com
 * date   :  2024/05/14 11:50:36
 * desc   :
 */


@Data
@EqualsAndHashCode(callSuper = true)
@Accessors(chain = true)  // 注解用于启用链式调用风格，这意味着在调用 setter 方法时，可以返回当前对象，从而使得多个 setter 方法可以链式调用。
public class AaListParams extends AaListParamsBaseEntity {

    private String simId;
    private String prodType;

    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date receivedTime;
}