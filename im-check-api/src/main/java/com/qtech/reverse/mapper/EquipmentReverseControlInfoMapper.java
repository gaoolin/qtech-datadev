package com.qtech.reverse.mapper;

import com.qtech.reverse.entity.EquipmentReverseControlInfo;
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

    List<EquipmentReverseControlInfo> selectQtechImChkResultList(@Param("simId") String simId);
}
