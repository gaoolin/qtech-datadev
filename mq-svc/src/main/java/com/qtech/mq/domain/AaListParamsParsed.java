package com.qtech.mq.domain;

import com.baomidou.mybatisplus.annotation.TableName;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.qtech.share.aa.pojo.ImAaListParams;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;
import lombok.experimental.Accessors;

import java.util.Date;

/**
 * author :  gaozhilin
 * email  :  gaoolin@gmail.com
 * date   :  2024/05/14 11:50:36
 * desc   :
 */

@TableName(value = "qtech_eq_dwd.im_aa_list_parsed_detail")
@Data
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
@Accessors(chain = true)  // 注解用于启用链式调用风格，这意味着在调用 setter 方法时，可以返回当前对象，从而使得多个 setter 方法可以链式调用。
public class AaListParamsParsed extends ImAaListParams {

    private String simId;
    private String prodType;

    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date receivedTime;
}