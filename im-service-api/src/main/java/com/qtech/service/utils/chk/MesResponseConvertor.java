package com.qtech.service.utils.chk;

import com.qtech.service.entity.EqReverseCtrlInfo;
import com.qtech.service.utils.response.R;
import com.qtech.service.utils.response.ResponseCode;

import static com.qtech.service.utils.chk.IoTReverseMsgBuilder.buildResponseMessage;

/**
 * author :  gaozhilin
 * email  :  gaoolin@gmail.com
 * date   :  2024/08/14 11:17:29
 * desc   :  数采反控信息转换工具
 */


public class MesResponseConvertor {

    public static R<String> doConvert(EqReverseCtrlInfo eqReverseCtrlInfo) {
        if (eqReverseCtrlInfo == null) {
            return new R<String>().setCode(ResponseCode.SUCCESS.getCode()).setMsg("No data found").setData(null);
        }
        if (eqReverseCtrlInfo.getCode() == 0) {
            return new R<String>().setCode(ResponseCode.SUCCESS.getCode()).setMsg(buildResponseMessage(eqReverseCtrlInfo)).setData(null);
        } else {
            return new R<String>().setCode(ResponseCode.FOUND.getCode()).setMsg(buildResponseMessage(eqReverseCtrlInfo)).setData(null);
        }
    }
}
