package com.qtech.check.pojo;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.qtech.share.aa.pojo.ImAaListStdTemplateInfo;
import lombok.Data;
import lombok.ToString;

/**
 * author :  gaozhilin
 * email  :  gaoolin@gmail.com
 * date   :  2024/07/22 14:20:43
 * desc   :
 */

@Data
@ToString(callSuper = true)
@TableName(value = "IMBIZ.IM_AA_LIST_PARAMS_STD_MODEL_INFO")
@JsonIgnoreProperties(ignoreUnknown = true)
public class AaListParamsStdTemplateInfo extends ImAaListStdTemplateInfo {
    @TableId(value = "id", type = IdType.AUTO)
    private Long id;
    @JsonIgnore
    private String provider;
    @JsonIgnore
    private String belongTo;
    @JsonIgnore
    private String createBy;
    @JsonIgnore
    private String createTime;
    @JsonIgnore
    private String updateBy;
    @JsonIgnore
    private String updateTime;
    @JsonIgnore
    private String remark;

    @Override
    public boolean equals(Object o) {
        return super.equals(o);
    }

    @Override
    public int hashCode() {
        return super.hashCode();
    }
}
