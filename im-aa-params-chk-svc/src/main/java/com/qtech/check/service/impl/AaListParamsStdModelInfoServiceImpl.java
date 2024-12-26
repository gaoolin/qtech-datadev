package com.qtech.check.service.impl;

import com.baomidou.mybatisplus.core.conditions.Wrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.qtech.check.mapper.AaListParamsStdModelInfoMapper;
import com.qtech.check.pojo.AaListParamsStdTemplateInfo;
import com.qtech.check.service.IAaListParamsStdModelInfoService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * author :  gaozhilin
 * email  :  gaoolin@gmail.com
 * date   :  2024/07/22 14:34:36
 * desc   :
 */

@Service
public class AaListParamsStdModelInfoServiceImpl extends ServiceImpl<AaListParamsStdModelInfoMapper, AaListParamsStdTemplateInfo> implements IAaListParamsStdModelInfoService {
    private static final Logger logger = LoggerFactory.getLogger(AaListParamsStdModelInfoServiceImpl.class);

    /**
     * @param queryWrapper
     * @return
     */
    @Override
    public List<AaListParamsStdTemplateInfo> list(Wrapper<AaListParamsStdTemplateInfo> queryWrapper) {
        try {
            return super.list(queryWrapper);
        } catch (Exception e) {
            logger.error(e.getMessage());
            throw new RuntimeException("查询数据时发生异常，请联系系统管理员！");
        }
    }

    /**
     * @param queryWrapper
     * @return
     */
    @Override
    public AaListParamsStdTemplateInfo getOne(Wrapper<AaListParamsStdTemplateInfo> queryWrapper) {
        try {
            return super.getOne(queryWrapper);
        } catch (Exception e) {
            logger.error(e.getMessage());
            throw new RuntimeException("查询数据时发生异常，请联系系统管理员！");
        }
    }
}
