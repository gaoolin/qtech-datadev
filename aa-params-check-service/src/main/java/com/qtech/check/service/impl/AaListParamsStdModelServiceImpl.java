package com.qtech.check.service.impl;

import com.qtech.check.service.IAaListParamsStdModelService;
import com.qtech.check.pojo.AaListParamsStdModel;
import com.qtech.check.mapper.AaListParamsStdModelMapper;
import org.apache.commons.collections4.CollectionUtils;
import org.apache.ibatis.exceptions.TooManyResultsException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * author :  gaozhilin
 * email  :  gaoolin@gmail.com
 * date   :  2024/05/21 09:13:40
 * desc   :
 */

@Service
public class AaListParamsStdModelServiceImpl implements IAaListParamsStdModelService {

    @Autowired
    private AaListParamsStdModelMapper aaListParamsStdModelMapper;

    @Override
    public AaListParamsStdModel selectOneAaListParamsStdModel(AaListParamsStdModel aaListParamsStdModel) {
        List<AaListParamsStdModel> list = aaListParamsStdModelMapper.selectAaListParamsStdModelList(aaListParamsStdModel);

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