package com.qtech.check.algorithm.model;

import com.qtech.check.pojo.AaListCommand;
import com.qtech.common.utils.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * author :  gaozhilin
 * email  :  gaoolin@gmail.com
 * date   :  2024/10/08 09:37:58
 * desc   :  CC处理器
 * 处理形如以下格式的字符
 * ITEM	18	RESULT		[CC]	Check	30.00	0.00	Log	100.00	100.00	100.00	100.00	30.00	30.00	50.00	1.00	NoCompare	0.00	0.00	0.00
 *
 * 用到此解析器的List 命令包括：
 * VCM_Check
 * mtf_check
 *
 */
public class ItemCcParser {
    private static final Logger logger = LoggerFactory.getLogger(ItemCcParser.class);

    public static AaListCommand apply(String[] parts, String prefixCommand) {
        String command = parts[2];
        if ("RESULT".equals(StringUtils.upperCase(command))) {
            Integer num = Integer.parseInt(parts[1]);

            String subSystem = null;
            Pattern pattern = Pattern.compile("\\[(\\d+)\\]");
            Matcher matcher = pattern.matcher(parts[3]);
            if (matcher.find()) {
                subSystem = matcher.group(1); // Return the first capturing group (the number)
            } else {
                throw new IllegalArgumentException(">>>>> ItemCcParser string does not match the expected pattern");
            }
            String val = parts[6];
            logger.info(">>>>> {}-ItemCcParser: Command: {}, subSystem: {}, val: {}", prefixCommand, command, subSystem, val);
            return new AaListCommand(null, num, prefixCommand, command, subSystem, val, null);
        }
        return null;
    }
}
