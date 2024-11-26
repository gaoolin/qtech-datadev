package com.qtech.check.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.qtech.check.config.dynamic.DataSourceNames;
import com.qtech.check.config.dynamic.DataSourceSwitch;
import com.qtech.check.mapper.AaListParamsCheckResultDetailMapper;
import com.qtech.check.pojo.EqReverseCtrlInfo;
import com.qtech.check.service.IAaListParamsCheckResultDetailService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

/**
 * author :  gaozhilin
 * email  :  gaoolin@gmail.com
 * date   :  2024/11/28 13:19:23
 * desc   :
 */

@Service
public class AaListParamsCheckResultDetailServiceImpl extends ServiceImpl<AaListParamsCheckResultDetailMapper, EqReverseCtrlInfo> implements IAaListParamsCheckResultDetailService {
    private static final Logger logger = LoggerFactory.getLogger(AaListParamsCheckResultDetailServiceImpl.class);

    @DataSourceSwitch(name = DataSourceNames.SECOND)
    @Override
    public boolean save(EqReverseCtrlInfo entity) {
        try {
            return super.save(entity);
        } catch (Exception e) {
            logger.error(">>>>> save aaListParamsCheckResultDetail error:{}", e.getMessage());
            throw new RuntimeException("保存数据时发生异常，请联系系统负责人！");
        }
    }
}
