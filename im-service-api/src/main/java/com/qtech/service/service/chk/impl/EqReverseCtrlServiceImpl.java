package com.qtech.service.service.chk.impl;

import com.qtech.service.config.dynamic.DataSourceNames;
import com.qtech.service.config.dynamic.DataSourceSwitch;
import com.qtech.service.entity.EqReverseCtrlInfo;
import com.qtech.service.exception.ImChkException;
import com.qtech.service.exception.TooManyResultsException;
import com.qtech.service.mapper.chk.EqReverseCtrlInfoMapper;
import com.qtech.service.service.chk.IEqReverseCtrlService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import java.time.Duration;
import java.util.List;

import static com.qtech.service.common.Constants.EQ_REVERSE_CTRL_INFO_REDIS_KEY_PREFIX;

/**
 * author :  gaozhilin
 * email  :  gaoolin@gmail.com
 * date   :  2024/08/13 17:28:21
 * desc   :
 */

@Service
public class EqReverseCtrlServiceImpl implements IEqReverseCtrlService {
    private static final Logger logger = LoggerFactory.getLogger(EqReverseCtrlServiceImpl.class);
    private final EqReverseCtrlInfoMapper eqReverseCtrlInfoMapper;
    private final RedisTemplate<String, EqReverseCtrlInfo> eqReverseCtrlInfoRedisTemplate;

    public EqReverseCtrlServiceImpl(EqReverseCtrlInfoMapper eqReverseCtrlInfoMapper, RedisTemplate<String, EqReverseCtrlInfo> eqReverseCtrlInfoRedisTemplate) {
        this.eqReverseCtrlInfoMapper = eqReverseCtrlInfoMapper;
        this.eqReverseCtrlInfoRedisTemplate = eqReverseCtrlInfoRedisTemplate;
    }

    @Override
    public List<EqReverseCtrlInfo> selectEqReverseCtrlInfo(EqReverseCtrlInfo eqReverseCtrlInfo) {
        try {
            return eqReverseCtrlInfoMapper.selectEqReverseCtrlInfo(eqReverseCtrlInfo);
        } catch (Exception e) {
            logger.error(">>>>> 查询数据库发生错误，selectEqReverseCtrlInfo error: ", e);
            throw new ImChkException();
        }
    }

    @Override
    public EqReverseCtrlInfo selectOneEqReverseCtrlInfo(EqReverseCtrlInfo eqReverseCtrlInfo) {
        List<EqReverseCtrlInfo> list = eqReverseCtrlInfoMapper.selectEqReverseCtrlInfo(eqReverseCtrlInfo);
        if (list != null) {
            if (!list.isEmpty()) {
                return list.get(0);
            } else {
                logger.error("selectOneEqReverseCtrlInfo error");
                throw new TooManyResultsException();
            }
        }
        return null;
    }

    @DataSourceSwitch(name = DataSourceNames.FIRST)
    @Override
    public EqReverseCtrlInfo selectEqReverseCtrlInfoBySimId(String simId) {
        EqReverseCtrlInfo eqReverseCtrlInfo = null;

        // 尝试从Redis中获取EqReverseCtrlInfo
        if (Boolean.TRUE.equals(eqReverseCtrlInfoRedisTemplate.hasKey(EQ_REVERSE_CTRL_INFO_REDIS_KEY_PREFIX + simId))) {
            eqReverseCtrlInfo = eqReverseCtrlInfoRedisTemplate.opsForValue().get(EQ_REVERSE_CTRL_INFO_REDIS_KEY_PREFIX + simId);
            return eqReverseCtrlInfo;
        } else {
            eqReverseCtrlInfo = eqReverseCtrlInfoMapper.selectEqReverseCtrlInfoBySimId(simId);
            if (eqReverseCtrlInfo != null) {
                // 将EqReverseCtrlInfo存入Redis
                eqReverseCtrlInfoRedisTemplate.opsForValue().set(EQ_REVERSE_CTRL_INFO_REDIS_KEY_PREFIX + simId, eqReverseCtrlInfo, Duration.ofMinutes(30));
                return eqReverseCtrlInfo;
            } else {
                // 如果数据库中也没有，则返回null
                return null;
            }
        }
    }
}
