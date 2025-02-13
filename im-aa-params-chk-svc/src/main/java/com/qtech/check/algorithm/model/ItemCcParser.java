package com.qtech.check.algorithm.model;

import com.qtech.common.utils.StringUtils;
import com.qtech.share.aa.pojo.ImAaListCommand;
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
 * <p>
 * <p>
 * 用到此解析器的List 命令包括：
 * VCM_Check
 * mtf_check
 */
public class ItemCcParser {
    private static final Logger logger = LoggerFactory.getLogger(ItemCcParser.class);
    // 匹配一个形如 [非空字符串] 的字符串，并且该字符串中必须包含至少一个英文字母
    private static final Pattern PATTERN = Pattern.compile("\\[([a-zA-Z].*|.*[a-zA-Z].*)\\]");

    public static ImAaListCommand apply(String[] parts, String prefixCommand) {
        if (parts.length < 7) {
            logger.warn(">>>>> ItemCcParser: Input array length is insufficient. Expected at least 7 elements, but got {}", parts.length);
            logger.warn(">>>>> Invalid Input array: " + String.join(",", parts));
            return null;
        }

        String command = parts[2];
        if ("RESULT".equals(StringUtils.upperCase(command))) {
            try {
                Integer num = Integer.parseInt(parts[1]);

                String subSystem = null;
                Matcher matcher = PATTERN.matcher(parts[3]);
                if (matcher.find()) {
                    subSystem = matcher.group(1);
                } else {
                    logger.error(">>>>> ItemCcParser: String does not match the expected pattern: {}", parts[3]);
                    return null;
                }
                String val = parts[6];
                logger.debug(">>>>> {}-ItemCcParser: Command: {}, subSystem: {}, val: {}", prefixCommand, command, subSystem, val);
                return new ImAaListCommand(null, num, prefixCommand, command, subSystem, val, null);
            } catch (NumberFormatException e) {
                logger.error(">>>>> ItemCcParser: Invalid number format in parts[1]: {}", parts[1], e);
                return null;
            }
        }
        return null;
    }
}