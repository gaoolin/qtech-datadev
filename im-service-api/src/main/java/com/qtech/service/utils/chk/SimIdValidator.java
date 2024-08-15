package com.qtech.service.utils.chk;

import java.util.regex.Pattern;

/**
 * author :  gaozhilin
 * email  :  gaoolin@gmail.com
 * date   :  2024/08/14 09:47:01
 * desc   :  sim卡号校验工具
 */


public class SimIdValidator {

    private static final Pattern SIM_ID_PATTERN = Pattern.compile("^86\\d+$");

    /**
     * 校验simId是否符合以86开头的纯数字格式。
     *
     * @param simId 要校验的simId
     * @return 如果simId格式正确返回true，否则返回false
     */
    public static boolean validateSimId(String simId) {
        return SIM_ID_PATTERN.matcher(simId).matches();
    }
}