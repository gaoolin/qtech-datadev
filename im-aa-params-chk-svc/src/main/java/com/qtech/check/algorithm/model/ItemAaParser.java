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
 * date   :  2024/10/08 10:40:42
 * desc   :  解析AA相关参数的解析器
 */


public class ItemAaParser {
    private static final Logger logger = LoggerFactory.getLogger(ItemAaParser.class);

    public static AaListCommand apply(String[] parts, String prefixCommand) {
        String command = parts[2];
        if ("ROI".equals(StringUtils.upperCase(command))) {  // 光学性能测试
            Integer num = Integer.parseInt(parts[1]);
            String subSystem = parts[3];
            String val = parts[5];
            if (!"CC".equals(StringUtils.upperCase(subSystem)) && !"UL".equals(StringUtils.upperCase(subSystem)) && !"UR".equals(StringUtils.upperCase(subSystem)) && !"LL".equals(StringUtils.upperCase(subSystem)) && !"LR".equals(StringUtils.upperCase(subSystem))) {
                Pattern pattern = Pattern.compile("\\[(\\d+)\\]");
                Matcher matcher = pattern.matcher(subSystem);
                if (matcher.find()) {
                    subSystem = matcher.group(1); // Return the first capturing group (the number)
                } else {
                    logger.error(">>>>> {}-AaHandler string does not match the expected pattern", prefixCommand);
                }
            }
            logger.info(">>>>> {}-ItemAaParser: ROI: subSystem: {}, {}", prefixCommand, subSystem, val);
            return new AaListCommand(null, num, prefixCommand, command, subSystem, val, null);
        } else if (StringUtils.startsWith(StringUtils.upperCase(command), "MTF_OFF_AXIS_CHECK")) {
            String val = parts[3];
            Integer num = Integer.parseInt(parts[1]);
            logger.info(">>>>> {}-ItemAaParser: MTF_OFF_AXIS_CHECK: {}", prefixCommand, val);
            return new AaListCommand(null, num, prefixCommand, command, null, val, null);
        } else if ("TARGET".equals(StringUtils.upperCase(command))) {  // AA方式
            int num = Integer.parseInt(parts[1]);
            String value = parts[3];
            logger.info(">>>>> {}-ItemAaParser: {}, value: {}", prefixCommand, command, value);
            return new AaListCommand(null, num, prefixCommand, command, null, value, null);
        } else if ("CC_TO_CORNER_LIMIT".equals(StringUtils.upperCase(command))) {  // 场曲
            Integer num = Integer.parseInt(parts[1]);
            String value = parts[3];
            logger.info(">>>>> {}-ItemAaParser: {}, value: {}", prefixCommand, command, value);
            return new AaListCommand(null, num, prefixCommand, command, null, value, null);
        } else if ("CC_TO_CORNER_LIMIT_MIN".equals(StringUtils.upperCase(command))) {
            Integer num = Integer.parseInt(parts[1]);
            String value = parts[3];
            logger.info(">>>>> {}-ItemAaParser: {}, value: {}", prefixCommand, command, value);
            return new AaListCommand(null, num, prefixCommand, command, null, value, null);
        } else if ("CORNER_SCORE_DIFFERENCE_REJECT_VALUE".equals(StringUtils.upperCase(command))) {  // 四角均匀性
            Integer num = Integer.parseInt(parts[1]);
            String value = parts[3];
            logger.info(">>>>> {}-ItemAaParser: {}, value: {}", prefixCommand, command, value);
            return new AaListCommand(null, num, prefixCommand, command, null, value, null);
        } else if ("Z_REF".equals(StringUtils.upperCase(command))) {  // AA Z的位置
            int num = Integer.parseInt(parts[1]);
            String value = parts[3];
            logger.info(">>>>> {}-ItemAaParser: {}, value: {}", prefixCommand, command, value);
            return new AaListCommand(null, num, prefixCommand, command, null, value, null);
        } else if ("SRCH_STEP".equals(StringUtils.upperCase(command))) {  // 步距（3步)
            int num = Integer.parseInt(parts[1]);
            String value = parts[3];
            logger.info(">>>>> {}-ItemAaParser: {}, value: {}", prefixCommand, command, value);
            return new AaListCommand(null, num, prefixCommand, command, null, value, null);
        } else if ("GoldenGlueThicknessMin".equals(StringUtils.upperCase(command))) {  // 压合后厚度
            int num = Integer.parseInt(parts[1]);
            String value = parts[3];
            logger.info(">>>>> {}-ItemAaParser: {}, value: {}", prefixCommand, command, value);
            return new AaListCommand(null, num, prefixCommand, command, null, value, null);
        } else if ("GoldenGlueThicknessMax".equals(StringUtils.upperCase(command))) {
            int num = Integer.parseInt(parts[1]);
            String value = parts[3];
            logger.info(">>>>> {}-ItemAaParser: {}, value: {}", prefixCommand, command, value);
            return new AaListCommand(null, num, prefixCommand, command, null, value, null);
        } else {
            return null;
        }
    }
}