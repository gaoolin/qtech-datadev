package com.qtech.service.service.chk.impl;

import com.qtech.service.entity.EquipmentReverseControlInfo;
import com.qtech.service.mapper.chk.EquipmentReverseControlInfoMapper;
import com.qtech.service.service.chk.IEquipmentReverseControlInfoService;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections4.CollectionUtils;
import org.apache.ibatis.exceptions.TooManyResultsException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * author :  gaozhilin
 * email  :  gaoolin@gmail.com
 * date   :  2024/06/21 09:21:56
 * desc   :
 */

@Slf4j
@Service
public class EquipmentReverseControlInfoServiceImpl implements IEquipmentReverseControlInfoService {

    @Autowired
    private EquipmentReverseControlInfoMapper equipmentReverseControlInfoMapper;

    @Override
    public EquipmentReverseControlInfo selectEquipmentReverseControlInfoBySimId(String simId) {
        List<EquipmentReverseControlInfo> list = null;
        try {
            list = equipmentReverseControlInfoMapper.selectQtechImChkResultList(simId);
        } catch (Exception e) {
            log.error("selectEquipmentReverseControlInfoBySimId error:{} simId: {}", e.getMessage(), simId);
        }

        if (CollectionUtils.isNotEmpty(list)) {
            int size = list.size();
            if (size > 1) {
                throw new TooManyResultsException(String.format("Expected one result (or null) to be returned by selectOne(), but found: %s", size));
            }
            return list.get(0);
        }
        return null;
    }
}
