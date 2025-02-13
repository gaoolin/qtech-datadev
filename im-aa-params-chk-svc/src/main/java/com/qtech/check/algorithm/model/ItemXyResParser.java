package com.qtech.check.algorithm.model;

import com.qtech.common.utils.StringUtils;
import com.qtech.share.aa.model.Range;
import com.qtech.share.aa.pojo.ImAaListCommand;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * author :  gaozhilin
 * email  :  gaoolin@gmail.com
 * date   :  2024/10/08 10:48:37
 * desc   :
 * 处理形如以下格式的字符
 * ITEM	7	X_RES		5.00	-5.00	1
 * ITEM	7	Y_RES		5.00	-5.00	1
 * <p>
 * 用到此解析器的List 命令包括：
 * ChartAlignment, ChartAlignment1, ChartAlignment2
 * 以下List中包含次参数，但未要求解析和管控
 * AA1, AA2, AA3,
 * <p>
 * 数据库中对应字段：
 * chart_alignment_x_res_min, chart_alignment_x_res_max
 * <p>
 * 实例中的属性：
 * chartAlignmentXResMin, chartAlignmentXResMax
 */


public class ItemXyResParser {
    private static final Logger logger = LoggerFactory.getLogger(ItemXyResParser.class);

    public static ImAaListCommand apply(String[] parts, String prefixCommand) {
        String command = StringUtils.upperCase(parts[2]);
        if ("X_RES".equals(StringUtils.upperCase(command)) || "Y_RES".equals(StringUtils.upperCase(command))) {
            Integer num = Integer.parseInt(parts[1]);
            String max = parts[3];
            String min = parts[4];
            Range<String> chartAlignmentRange = new Range<>(min, max);
            logger.debug(">>>>> {}-ItemXyResParser: Command: {}, min: {}, max: {}", prefixCommand, command, min, max);
            return new ImAaListCommand(null, num, prefixCommand, command, null, null, chartAlignmentRange);
        }
        return null;
    }
}
