package com.qtech.check.algorithm.model;

import com.qtech.common.utils.StringUtils;
import com.qtech.share.aa.model.Range;
import com.qtech.share.aa.pojo.ImAaListCommand;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * author :  gaozhilin
 * email  :  gaoolin@gmail.com
 * date   :  2024/10/08 11:00:04
 * desc   :
 * 处理形如以下格式的字符
 * ITEM	24	RESULT		X_Offset	Check	90.00	-20.00	Log	100.00	100.00	100.00	100.00	1.00	1.00	1.00	1.00	NoCompare		0.00	0.00
 * ITEM	24	RESULT		Y_Offset	Check	60.00	-80.00	Log	100.00	100.00	100.00	100.00	1.00	1.00	1.00	1.00	NoCompare		0.00	0.00
 * <p>
 * 用到此解析器的List 命令包括：
 * Save_OC, Save_MTF, OC_Check
 * <p>
 * 数据库中对应字段：
 * xx_x_offset_min, xx_x_offset_max, xx_y_offset_min, xx_y_offset_max
 * <p>
 * 实例中的属性：
 * xxXOffsetMax, xxXOffsetMin
 */


public class ItemXyOffsetParser {
    private static final Logger logger = LoggerFactory.getLogger(ItemXyOffsetParser.class);

    public static ImAaListCommand apply(String[] parts, String prefixCmd) {
        String command = parts[3];
        if (StringUtils.upperCase("X_Offset").equals(StringUtils.upperCase(command))) {
            Integer num = Integer.parseInt(parts[1]);
            String max = parts[5];
            String min = parts[6];
            Range<String> range = new Range<>(min, max);
            logger.info(">>>>> {}-ItemXyOffsetParser: {}, Max: {}, Min: {}", prefixCmd, command, max, min);
            return new ImAaListCommand(null, num, prefixCmd, command, null, null, range);
        } else if (StringUtils.upperCase("Y_OffSET").equals(StringUtils.upperCase(command))) {
            Integer num = Integer.parseInt(parts[1]);
            String max = parts[5];
            String min = parts[6];
            Range<String> range = new Range<>(min, max);
            logger.info(">>>>> {}-ItemXyOffsetParser: {}, Max: {}, Min: {}", prefixCmd, command, max, min);
            return new ImAaListCommand(null, num, prefixCmd, command, null, null, range);
        }
        return null;
    }
}
