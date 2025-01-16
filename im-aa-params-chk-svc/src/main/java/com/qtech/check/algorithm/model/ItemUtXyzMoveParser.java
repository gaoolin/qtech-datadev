package com.qtech.check.algorithm.model;

import com.qtech.common.utils.StringUtils;
import com.qtech.share.aa.pojo.ImAaListCommand;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * author :  gaozhilin
 * email  :  gaoolin@gmail.com
 * date   :  2024/10/08 11:02:29
 * desc   :
 * 处理形如以下格式的字符
 * ITEM	14	UTXYZMove #X #Y  -10
 * <p>
 * 用到此解析器的List 命令包括：
 * RecordPosition
 * <p>
 * 数据库中对应字段：
 * record_position_ut_xyz_move
 * <p>
 * 实例中的属性：
 * recordPositionUtXyzMove
 * 上述属性名作废，变更为：
 * utXyzMoveVal
 */


public class ItemUtXyzMoveParser {
    private static final Logger logger = LoggerFactory.getLogger(ItemUtXyzMoveParser.class);

    public static ImAaListCommand apply(String[] parts, String prefixCommand) {
        Integer num = Integer.parseInt(parts[1]);
        String command = parts[2];
        if (StringUtils.upperCase("UTXYZMove").equals(StringUtils.upperCase(command))) {
            String val = parts[5];
            logger.info(">>>>> {}-ItemUtXyzMoveParser: UTXYZMove: {}", prefixCommand, val);
            // 采集数据中的参数名称为 UTXYZMove，不具备驼峰规则，需手动转换
            return new ImAaListCommand(null, num, null, null, "UtXyzMoveVal", val, null);
        }
        return null;
    }
}
