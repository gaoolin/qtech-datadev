package com.qtech.share.aa.pojo;

import lombok.Data;

import java.io.Serializable;
import java.util.Objects;

/**
 * author :  gaozhilin
 * email  :  gaoolin@gmail.com
 * date   :  2024/12/26 09:18:56
 * desc   :  AA List 模板概要
 */

@Data
public class ImAaListStdTemplateInfo implements Serializable {
    private static final long serialVersionUID = 529L;

    private String prodType;
    private Integer listParams;
    private Integer itemParams;
    private Integer status;

    // 重写equals和hashCode方法，用于判断对象的对应属性是否相等
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        ImAaListStdTemplateInfo that = (ImAaListStdTemplateInfo) o;
        return Objects.equals(prodType, that.prodType) && Objects.equals(listParams, that.listParams) && Objects.equals(itemParams, that.itemParams) && Objects.equals(status, that.status);
    }

    @Override
    public int hashCode() {
        return Objects.hash(prodType, listParams, itemParams, status);
    }
}