package com.qtech.service.utils.chk;

import com.qtech.service.entity.EqReverseCtrlInfo;
import com.qtech.service.utils.response.R;
import com.qtech.service.utils.response.ResponseCode;

import java.time.DayOfWeek;
import java.time.LocalDateTime;
import java.time.LocalTime;

import static com.qtech.service.utils.chk.MesResponseConvertor.doConvert;

/**
 * author :  gaozhilin
 * email  :  gaoolin@gmail.com
 * date   :  2024/08/15 13:52:19
 * desc   :  控制模式逻辑处理器
 */


public class ControlModeResponseHandler {
    // 根据 EqReverseCtrlInfo 的 source 属性和控制模式，返回不同的响应
    public static R<String> handleResponse(EqReverseCtrlInfo eqReverseCtrlInfo) {
        // 检查 source 属性是否为 "aa-list"
        if ("aa-list".equals(eqReverseCtrlInfo.getSource())) {
            // 获取当前的控制模式
            ControlMode mode = ControlModeFlag.controlMode;

            // 根据控制模式返回不同的结果
            switch (mode) {
                case ALWAYS_NULL:
                    // 始终返回 null
                    return new R<String>().setCode(ResponseCode.SUCCESS.getCode()).setMsg("Control Model: ALWAYS_NULL, always return Ok.").setData(null);
                case DEFAULT:  // 默认，按照工作日时间控制
                    LocalDateTime now = LocalDateTime.now();
                    if (isWithinWorkingHours(now)) {
                        return doConvert(eqReverseCtrlInfo);
                    } else {
                        return new R<String>().setCode(ResponseCode.SUCCESS.getCode()).setMsg("Control Model: DEFAULT. not in working time, return Ok.").setData(null); // 非工作时间返回null
                    }
                case ALWAYS_RETURN:// 始终返回正常数据
                default:
                    // 其余状况，返回正常数据
                    return doConvert(eqReverseCtrlInfo);
            }
        }
        // 如果 source 不是 "aa-list"，返回正常的结果
        return doConvert(eqReverseCtrlInfo);
    }

        /**
     * 判断当前时间是否在工作时间内。
     * @param dateTime 当前日期时间
     * @return 是否在工作时间内
     */
    private static boolean isWithinWorkingHours(LocalDateTime dateTime) {
        DayOfWeek dayOfWeek = dateTime.toLocalDate().getDayOfWeek();
        LocalTime timeNow = dateTime.toLocalTime();
        return (DayOfWeek.MONDAY.getValue() <= dayOfWeek.getValue() && dayOfWeek.getValue() <= DayOfWeek.FRIDAY.getValue())
                && timeNow.isAfter(LocalTime.of(8, 30)) && timeNow.isBefore(LocalTime.of(17, 0));
    }
}
