package com.qtech.olp.service.impl;

import com.qtech.olp.entity.AaListMessage;
import com.qtech.olp.mapper.AaParamsMapper;
import com.qtech.olp.service.IAaParamsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * author :  gaozhilin
 * email  :  gaoolin@gmail.com
 * date   :  2024/05/07 16:47:16
 * desc   :
 */

@Service
public class AaParamsServiceImpl implements IAaParamsService {

    @Autowired
    private AaParamsMapper aaParamsMapper;

    @Autowired
    private RedisTemplate<String, Object> redisTemplate;
    @Override
    public List<AaListMessage> selectAaParamsList(AaListMessage aaListMessage) {
        return aaParamsMapper.selectAaParamsList(aaListMessage);
    }

    @Override
    public int insertAaParams(AaListMessage aaListMessage) {

        return aaParamsMapper.insertAaParams(aaListMessage);
    }
}
