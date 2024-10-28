package com.qtech.check.algorithm.model;

import com.qtech.check.pojo.AaListCommand;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * author :  gaozhilin
 * email  :  gaoolin@gmail.com
 * date   :  2024/10/08 10:52:49
 * desc   :
 */


public class ItemEpoxyInspectionParser {
    private static final Logger logger = LoggerFactory.getLogger(ItemEpoxyInspectionParser.class);

    public static AaListCommand apply(String[] parts, String prefixCommand) {
        String command = parts[2];
        if ("EpoxyInspection".equals(command)) {
            Integer num = Integer.parseInt(parts[1]);
            String val = parts[3];
            logger.info(">>>>> {}-ItemEpoxyInspectionParser: EpoxyInspection: {}", prefixCommand, val);
            return new AaListCommand(null, num, prefixCommand, command, null, val, null);
        }
        return null;
    }
}