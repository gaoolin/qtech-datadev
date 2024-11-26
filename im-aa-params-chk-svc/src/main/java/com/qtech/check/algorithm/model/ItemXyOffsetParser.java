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
 */


public class ItemXyOffsetParser {
    private static final Logger logger = LoggerFactory.getLogger(ItemXyOffsetParser.class);

    public static ImAaListCommand apply(String[] parts, String prefixCmd) {
        String command = parts[3];
        if ("X_Offset".equals(StringUtils.upperCase(command))) {
            Integer num = Integer.parseInt(parts[1]);
            String max = parts[5];
            String min = parts[6];
            Range<String> ocCheckRange = new Range<>(min, max);
            logger.info(">>>>> {}-ItemXyOffsetParser: {}, ocCheckXOffsetMax: {}, ocCheckXOffsetMin: {}", prefixCmd, command, max, min);
            return new ImAaListCommand(null, num, prefixCmd, command, null, null, ocCheckRange);
        } else if ("Y_OffSET".equals(StringUtils.upperCase(command))) {
            Integer num = Integer.parseInt(parts[1]);
            String max = parts[5];
            String min = parts[6];
            Range<String> ocCheckRange = new Range<>(min, max);
            logger.info(">>>>> {}-ItemXyOffsetParser: {}, ocCheckYOffsetMax: {}, ocCheckYOffsetMin: {}", prefixCmd, command, max, min);
            return new ImAaListCommand(null, num, prefixCmd, command, null, null, ocCheckRange);
        }
        return null;
    }
}
