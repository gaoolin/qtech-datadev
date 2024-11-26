package com.qtech.check.service.impl;

import com.qtech.check.pojo.EqReverseCtrlInfo;
import com.qtech.check.service.IAaListParamsCheckResultDetailService;
import com.qtech.check.service.IAaListParamsCheckResultLatestService;
import com.qtech.check.service.IAaListParamsCheckResultService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * author :  gaozhilin
 * email  :  gaoolin@gmail.com
 * date   :  2024/05/22 10:46:24
 * desc   :
 */

@Service
public class AaListParamsCheckResultServiceImpl implements IAaListParamsCheckResultService<EqReverseCtrlInfo> {
    private static final Logger logger = LoggerFactory.getLogger(AaListParamsCheckResultServiceImpl.class);

    @Autowired
    private IAaListParamsCheckResultDetailService aaListParamsCheckResultDetailService;
    @Autowired
    private IAaListParamsCheckResultLatestService aaListParamsCheckResultLatestService;


    /**
     * @param entity
     * @return
     */
    @Override
    public boolean save(EqReverseCtrlInfo entity) {
        try {
            boolean a = aaListParamsCheckResultDetailService.save(entity);
            boolean b = aaListParamsCheckResultLatestService.save(entity);
            return a && b;
        } catch (Exception e) {
            logger.error(">>>>> save AaListParamsCheckResult error:{}", e.getMessage());
            throw new RuntimeException("保存数据时发生异常，请联系系统负责人！");
        }
    }
}