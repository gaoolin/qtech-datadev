package com.qtech.reverse.utils;

import com.qtech.reverse.controller.EquipmentReverseControlInfoController;

/**
 * author :  gaozhilin
 * email  :  gaoolin@gmail.com
 * date   :  2024/06/26 11:05:55
 * desc   :  控制状态
 */


public class ModeControl {
    public static volatile ControlMode controlMode = ControlMode.DEFAULT; // 默认控制模式
}