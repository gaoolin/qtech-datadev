package com.qtech.reverse.service;

import com.qtech.reverse.entity.EquipmentReverseControlInfo;

/**
 * author :  gaozhilin
 * email  :  gaoolin@gmail.com
 * date   :  2024/06/21 09:21:34
 * desc   :
 */


public interface IEquipmentReverseControlInfoService {

    public EquipmentReverseControlInfo selectEquipmentReverseControlInfoBySimId(String simId);
}
