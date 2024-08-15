package com.qtech.service.service.chk;

import com.qtech.service.entity.EqReverseCtrlInfo;

/**
 * author :  gaozhilin
 * email  :  gaoolin@gmail.com
 * date   :  2024/06/21 09:21:34
 * desc   :
 */


public interface IEquipmentReverseControlInfoService {

    public EqReverseCtrlInfo selectEquipmentReverseControlInfoBySimId(String simId);
}
