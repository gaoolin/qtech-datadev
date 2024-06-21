package com.qtech.reverse.service.impl;

import com.qtech.reverse.entity.EquipmentReverseControlInfo;
import com.qtech.reverse.mapper.EquipmentReverseControlInfoMapper;
import com.qtech.reverse.service.IEquipmentReverseControlInfoService;
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

@Service
public class EquipmentReverseControlInfoServiceImpl implements IEquipmentReverseControlInfoService {

    @Autowired
    private EquipmentReverseControlInfoMapper equipmentReverseControlInfoMapper;

    @Override
    public EquipmentReverseControlInfo selectEquipmentReverseControlInfoBySimId(String simId) {
        List<EquipmentReverseControlInfo> list = equipmentReverseControlInfoMapper.selectQtechImChkResultList(simId);

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
