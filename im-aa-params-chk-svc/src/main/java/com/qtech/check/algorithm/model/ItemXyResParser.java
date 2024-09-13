package com.qtech.check.algorithm.model;

import com.qtech.check.algorithm.Range;
import com.qtech.check.pojo.AaListCommand;
import com.qtech.common.utils.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * author :  gaozhilin
 * email  :  gaoolin@gmail.com
 * date   :  2024/10/08 10:48:37
 * desc   :
 */


public class ItemXyResParser {
    private static final Logger logger = LoggerFactory.getLogger(ItemXyResParser.class);

    public static AaListCommand apply(String[] parts, String prefixCommand) {
        String command = StringUtils.upperCase(parts[2]);
        if ("X_RES".equals(command) || "Y_RES".equals(command)) {
            Integer num = Integer.parseInt(parts[1]);
            String max = parts[3];
            String min = parts[4];
            Range<String> chartAlignmentRange = new Range<>(min, max);
            logger.info(">>>>> {}-ItemXyResParser: Command: {}, min: {}, max: {}", prefixCommand, command, min, max);
            return new AaListCommand(null, num, prefixCommand, command, null, null, chartAlignmentRange);
        }
        return null;
    }
}
