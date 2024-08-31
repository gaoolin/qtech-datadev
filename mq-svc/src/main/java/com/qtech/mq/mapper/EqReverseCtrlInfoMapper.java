package com.qtech.mq.mapper;


import com.qtech.mq.domain.EqReverseCtrlInfo;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

/**
 * author :  gaozhilin
 * email  :  gaoolin@gmail.com
 * date   :  2024/08/13 17:29:56
 * desc   :
 */

@Mapper
public interface EqReverseCtrlInfoMapper {
    int upsertDoris(EqReverseCtrlInfo eqReverseCtrlInfo);
    int addAaListDoris(EqReverseCtrlInfo eqReverseCtrlInfo);
    int addWbOlpChkDoris(EqReverseCtrlInfo eqReverseCtrlInfo);
    int upsertOracle(EqReverseCtrlInfo eqReverseCtrlInfo);
}
