package com.qtech.ocr.domain;

import org.springframework.stereotype.Component;

/**
 * author :  gaozhilin
 * email  :  gaoolin@gmail.com
 * date   :  2023/07/31 17:01:15
 * desc   :  TODO
 */

@Component
public class ImgInfo {

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
        return "ImgInfo{" +
                "imgName='" + imgName + '\'' +
                ", byteJson='" + byteJson + '\'' +
                '}';
    }
}
