package com.qtech.check.algorithm.model;

import com.qtech.common.utils.StringUtils;
import com.qtech.share.aa.pojo.ImAaListCommand;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * author :  gaozhilin
 * email  :  gaoolin@gmail.com
 * date   :  2024/10/08 10:55:55
 * desc   :
 */


public class ItemDelayParser {
    private static final Logger logger = LoggerFactory.getLogger(ItemDelayParser.class);

    public static ImAaListCommand apply(String[] parts, String prefixCommand) {
        String command = parts[2];
        if (StringUtils.upperCase("Delay").equals(StringUtils.upperCase(command))) {
            int num = Integer.parseInt(parts[1]);

            String val = parts[3];
            logger.info(">>>>> {}-LpOcHandler: Delay: {}", prefixCommand, val);
            return new ImAaListCommand(null, num, prefixCommand, command, null, val, null);
        }
        // logger.error(">>>>> LpOcHandler: Unsupported command: {}", command);
        return null;
    }
}
