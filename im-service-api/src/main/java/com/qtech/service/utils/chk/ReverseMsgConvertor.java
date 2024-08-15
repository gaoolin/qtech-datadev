package com.qtech.service.utils.chk;

import com.qtech.service.entity.EqReverseCtrlInfo;
import com.qtech.service.utils.response.ResponseCode;

/**
 * author :  gaozhilin
 * email  :  gaoolin@gmail.com
 * date   :  2024/08/14 11:11:54
 * desc   :  反控信息转换工具
 */


public class ReverseMsgConvertor {

    public static EqReverseCtrlInfo doCovert(EqReverseCtrlInfo info) {
        if (info == null) {
            return null;
        }
        if (info.getCode() == null) {
            info.setCode(ResponseCode.SUCCESS.getCode());
        }

        return null;
    }
}
