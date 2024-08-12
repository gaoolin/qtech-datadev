package com.qtech.service.entity;

/**
 * author :  gaozhilin
 * email  :  gaoolin@gmail.com
 * date   :  2023/07/31 17:01:15
 * desc   :  图片对象实体类
 */

public class OcrLabelInfo {

    String imgName;

    String byteJson;

    public String getImgName() {
        return imgName;
    }

    public void setImgName(String imgName) {
        this.imgName = imgName;
    }

    public String getByteJson() {
        return byteJson;
    }

    public void setByteJson(String byteJson) {
        this.byteJson = byteJson;
    }

    @Override
    public String toString() {
        return "OcrLabelInfo{" +
                "imgName='" + imgName + '\'' +
                ", byteJson='" + byteJson + '\'' +
                '}';
    }
}
