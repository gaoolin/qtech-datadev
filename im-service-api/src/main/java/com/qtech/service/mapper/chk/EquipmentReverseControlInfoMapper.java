package com.qtech.service.mapper.chk;

import com.qtech.service.entity.EqReverseCtrlInfo;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * author :  gaozhilin
 * email  :  gaoolin@gmail.com
 * date   :  2024/06/21 08:36:12
 * desc   :
 */

@Mapper
public interface EquipmentReverseControlInfoMapper {

    List<EqReverseCtrlInfo> selectQtechImChkResultList(@Param("simId") String simId);
}
