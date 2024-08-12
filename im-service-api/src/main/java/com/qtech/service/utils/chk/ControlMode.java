package com.qtech.service.utils.chk;

/**
 * author :  gaozhilin
 * email  :  gaoolin@gmail.com
 * date   :  2024/06/26 11:34:46
 * desc   :  
 */


public enum ControlMode {
    DEFAULT, // 默认，按照工作日时间控制
    ALWAYS_RETURN, // 全天正常返回
    ALWAYS_NULL // 全天返回null
}