package com.qtech.check.algorithm.model;

import com.qtech.check.pojo.AaListCommand;
import com.qtech.common.utils.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * author :  gaozhilin
 * email  :  gaoolin@gmail.com
 * date   :  2024/10/08 11:02:29
 * desc   :
 */


public class ItemUtXyzMoveParser {
    private static final Logger logger = LoggerFactory.getLogger(ItemUtXyzMoveParser.class);

    public static AaListCommand apply(String[] parts, String prefixCommand) {
        Integer num = Integer.parseInt(parts[1]);
        String command = parts[2];
        if (StringUtils.upperCase("UTXYZMove").equals(StringUtils.upperCase(command))) {
            String val = parts[5];
            logger.info(">>>>> {}-ItemUtXyzMoveParser: UTXYZMove: {}", prefixCommand, val);
            return new AaListCommand(null, num, prefixCommand, command, null, val, null);
        }
        return null;
    }
}
