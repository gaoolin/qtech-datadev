package com.qtech.service.mapper.chk;

import com.qtech.service.entity.EqReverseCtrlInfo;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

/**
 * author :  gaozhilin
 * email  :  gaoolin@gmail.com
 * date   :  2024/08/13 17:29:56
 * desc   :
 */

@Mapper
public interface EqReverseCtrlInfoMapper {
    public List<EqReverseCtrlInfo> selectEqReverseCtrlInfo(EqReverseCtrlInfo eqReverseCtrlInfo);
    public EqReverseCtrlInfo selectEqReverseCtrlInfoBySimId(String simId);
}
