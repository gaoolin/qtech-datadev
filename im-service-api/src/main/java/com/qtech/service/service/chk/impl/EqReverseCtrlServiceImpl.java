package com.qtech.service.service.chk.impl;

import com.qtech.service.config.dynamic.DataSourceNames;
import com.qtech.service.config.dynamic.DataSourceSwitch;
import com.qtech.service.entity.EqReverseCtrlInfo;
import com.qtech.service.exception.TooManyResultsException;
import com.qtech.service.mapper.chk.EqReverseCtrlInfoMapper;
import com.qtech.service.service.chk.IEqReverseCtrlService;
import lombok.extern.slf4j.Slf4j;
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

@Slf4j
@Service
public class EqReverseCtrlServiceImpl implements IEqReverseCtrlService {

    private final EqReverseCtrlInfoMapper eqReverseCtrlInfoMapper;
    private final RedisTemplate<String, EqReverseCtrlInfo> eqReverseCtrlInfoRedisTemplate;

    public EqReverseCtrlServiceImpl(EqReverseCtrlInfoMapper eqReverseCtrlInfoMapper, RedisTemplate<String, EqReverseCtrlInfo> eqReverseCtrlInfoRedisTemplate) {
        this.eqReverseCtrlInfoMapper = eqReverseCtrlInfoMapper;
        this.eqReverseCtrlInfoRedisTemplate = eqReverseCtrlInfoRedisTemplate;
    }

    @Override
    public List<EqReverseCtrlInfo> selectEqReverseCtrlInfo(EqReverseCtrlInfo eqReverseCtrlInfo) {
        return eqReverseCtrlInfoMapper.selectEqReverseCtrlInfo(eqReverseCtrlInfo);
    }

    @Override
    public EqReverseCtrlInfo selectOneEqReverseCtrlInfo(EqReverseCtrlInfo eqReverseCtrlInfo) {

        try {
            List<EqReverseCtrlInfo> list = eqReverseCtrlInfoMapper.selectEqReverseCtrlInfo(eqReverseCtrlInfo);
            if (list != null) {
                if (!list.isEmpty()) {
                    return list.get(0);
                } else {
                    log.error("selectEqReverseCtrlInfoBySimId error: ");
                    throw new TooManyResultsException();
                }
            }
            return null;
        } catch (Exception e) {
            log.error("selectEqReverseCtrlInfoBySimId error: ", e);
            throw new RuntimeException(e);
        }
    }

    @DataSourceSwitch(name = DataSourceNames.FIRST)
    @Override
    public EqReverseCtrlInfo selectEqReverseCtrlInfoBySimId(String simId) {
        EqReverseCtrlInfo eqReverseCtrlInfo = null;
        try {
            // 尝试从Redis中获取EqReverseCtrlInfo
            if (Boolean.TRUE.equals(eqReverseCtrlInfoRedisTemplate.hasKey(EQ_REVERSE_CTRL_INFO_REDIS_KEY_PREFIX + simId))) {
                eqReverseCtrlInfo = eqReverseCtrlInfoRedisTemplate.opsForValue().get(EQ_REVERSE_CTRL_INFO_REDIS_KEY_PREFIX + simId);
                return eqReverseCtrlInfo;
            } else {
                try {
                    eqReverseCtrlInfo = eqReverseCtrlInfoMapper.selectEqReverseCtrlInfoBySimId(simId);
                } catch (Exception ex) {
                    log.error("selectEqReverseCtrlInfoBySimId error: ", ex);
                    throw new RuntimeException(ex);
                }
                if (eqReverseCtrlInfo != null) {
                    // 将EqReverseCtrlInfo存入Redis
                    eqReverseCtrlInfoRedisTemplate.opsForValue().set(EQ_REVERSE_CTRL_INFO_REDIS_KEY_PREFIX + simId, eqReverseCtrlInfo, Duration.ofMinutes(30));
                    return eqReverseCtrlInfo;
                } else {
                    // 如果数据库中也没有，则返回null
                    return null;
                }
            }
        } catch (Exception e) {
            log.error("selectEqReverseCtrlInfoBySimId error: ", e);
            throw new RuntimeException(e);
        }
    }
}
