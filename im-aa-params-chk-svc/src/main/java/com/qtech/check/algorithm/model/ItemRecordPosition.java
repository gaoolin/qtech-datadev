package com.qtech.check.algorithm.model;

import com.qtech.share.aa.pojo.ImAaListCommand;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * author :  gaozhilin
 * email  :  gaoolin@gmail.com
 * date   :  2025/01/15 13:19:27
 * desc   :
 * 处理形如以下格式的字符
 * ITEM	14	GetPositionWithFinalOffset
 * <p>
 * 数据库中对应字段：
 * record_position_name
 * <p>
 * 实例中的属性：
 * recordPositionName
 */


public class ItemRecordPosition {
    private static final Logger logger = LoggerFactory.getLogger(ItemRecordPosition.class);

    public static ImAaListCommand apply(String[] parts, String prefixCommand) {
        int num = Integer.parseInt(parts[1]);
        String val = parts[2];
        logger.info(">>>>> {}-ItemRecordPosition: {}", prefixCommand, val);
        return new ImAaListCommand(null, num, null, "recordPositionName", null, val, null);
    }
}
