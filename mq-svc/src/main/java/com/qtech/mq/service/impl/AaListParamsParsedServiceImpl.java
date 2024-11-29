package com.qtech.mq.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.qtech.mq.common.dynamic.DataSourceNames;
import com.qtech.mq.common.dynamic.DataSourceSwitch;
import com.qtech.mq.domain.AaListParamsParsed;
import com.qtech.mq.mapper.AaListParamsParsedMapper;
import com.qtech.mq.service.IAaListParamsParsedService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

/**
 * author :  gaozhilin
 * email  :  gaoolin@gmail.com
 * date   :  2024/08/16 14:03:59
 * desc   :
 */

@Service
public class AaListParamsParsedServiceImpl extends ServiceImpl<AaListParamsParsedMapper, AaListParamsParsed> implements IAaListParamsParsedService {
    private static final Logger logger = LoggerFactory.getLogger(AaListParamsParsedServiceImpl.class);

    @DataSourceSwitch(name = DataSourceNames.SECOND)
    @Override
    public boolean save(AaListParamsParsed aaListParams) {
        try {
            return super.save(aaListParams);
        } catch (Exception e) {
            logger.error(">>>>> save aaListParamsParsed error:{}", e.getMessage());
            throw new RuntimeException("保存数据时发生异常，请联系系统管理员！");
        }
    }
}
