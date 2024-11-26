package com.qtech.check.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.qtech.check.mapper.AaListParamsStdModelMapper;
import com.qtech.check.pojo.AaListParamsStdModel;
import com.qtech.check.service.IAaListParamsStdModelService;
import org.springframework.stereotype.Service;

/**
 * author :  gaozhilin
 * email  :  gaoolin@gmail.com
 * date   :  2024/05/21 09:13:40
 * desc   :
 */

@Service
public class AaListParamsStdModelServiceImpl extends ServiceImpl<AaListParamsStdModelMapper, AaListParamsStdModel> implements IAaListParamsStdModelService {


    /**
     * @param entity
     * @return
     */
    @Override
    public boolean save(AaListParamsStdModel entity) {
        return super.save(entity);
    }
}