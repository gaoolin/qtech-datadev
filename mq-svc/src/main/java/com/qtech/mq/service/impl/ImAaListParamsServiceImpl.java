package com.qtech.mq.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.qtech.mq.common.dynamic.DataSourceNames;
import com.qtech.mq.common.dynamic.DataSourceSwitch;
import com.qtech.mq.domain.AaListParams;
import com.qtech.mq.mapper.ImAaListParamsMapper;
import com.qtech.mq.service.IImAaListParamsService;
import org.springframework.stereotype.Service;

/**
 * @author zhilin.gao
 * @description 针对表【im_aa_list_parsed_detail(OLAP)】的数据库操作Service实现
 * @createDate 2024-10-28 11:20:23
 */
@Service
public class ImAaListParamsServiceImpl extends ServiceImpl<ImAaListParamsMapper, AaListParams>
        implements IImAaListParamsService {
    @DataSourceSwitch(name = DataSourceNames.SECOND)
    @Override
    public boolean save(AaListParams entity) {
        return super.save(entity);
    }
}



