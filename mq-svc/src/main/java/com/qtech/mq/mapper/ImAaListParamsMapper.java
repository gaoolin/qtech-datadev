package com.qtech.mq.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.qtech.mq.domain.AaListParams;
import org.apache.ibatis.annotations.Mapper;

/**
 * @author zhilin.gao
 * @description 针对表【im_aa_list_parsed_detail(OLAP)】的数据库操作Mapper
 * @createDate 2024-10-28 11:20:23
 * @Entity com.qtech.mq.domain.ImAaListParams
 */
// 如果使用了MapperScanner，则无需加此注解
@Mapper
public interface ImAaListParamsMapper extends BaseMapper<AaListParams> {
}




