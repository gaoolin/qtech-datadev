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
 * date   :  2024/10/08 10:40:42
 * desc   :  解析AA相关参数的解析器
 * 处理形如以下格式的字符
 * ITEM	8	ROI		CC	100.00	58.00	30.00	1	1
 * ITEM	8	ROI		UL	100.00	35.00	20.00	1	2
 * ITEM	8	ROI		UR	100.00	35.00	20.00	1	3
 * ITEM	8	ROI		LL	100.00	35.00	20.00	1	4
 * ITEM	8	ROI		LR	100.00	35.00	20.00	1	5
 * <p>
 * 用到此解析器的List 命令包括：
 * AA1
 * AA2
 */

public class ItemAaParser {
    private static final Logger logger = LoggerFactory.getLogger(ItemAaParser.class);
    private static final Pattern PATTERN = Pattern.compile("\\[(\\S+)\\]");
    private static final String COMMAND_ROI = "ROI";
    private static final String COMMAND_MTF_OFF_AXIS_CHECK = "MTF_OFF_AXIS_CHECK";
    private static final String COMMAND_TARGET = "TARGET";
    private static final String COMMAND_CC_TO_CORNER_LIMIT = "CC_TO_CORNER_LIMIT";
    private static final String COMMAND_CC_TO_CORNER_LIMIT_MIN = "CC_TO_CORNER_LIMIT_MIN";
    private static final String COMMAND_CORNER_SCORE_DIFFERENCE_REJECT_VALUE = "CORNER_SCORE_DIFFERENCE_REJECT_VALUE";
    private static final String COMMAND_Z_REF = "Z_REF";
    private static final String COMMAND_SRCH_STEP = "SRCH_STEP";
    private static final String COMMAND_GOLDEN_GLUE_THICKNESS_MIN = "GOLDENGLUETHICKNESSMIN";
    private static final String COMMAND_GOLDEN_GLUE_THICKNESS_MAX = "GOLDENGLUETHICKNESSMAX";

    public static ImAaListCommand apply(String[] parts, String prefixCommand) {
        if (parts == null || parts.length < 4) {
            logError(prefixCommand, "Invalid input array");
            return null;
        }

        String command = parts[2];
        int num;
        try {
            num = Integer.parseInt(parts[1]);
        } catch (NumberFormatException e) {
            logError(prefixCommand, "Invalid number format: " + parts[1]);
            return null;
        }

        switch (StringUtils.upperCase(command)) {
            case COMMAND_ROI:
                return parseRoi(parts, prefixCommand, num);
            case COMMAND_MTF_OFF_AXIS_CHECK:
                return parseMtfOffAxisCheck(parts, prefixCommand, num);
            case COMMAND_TARGET:
                return parseTarget(parts, prefixCommand, num);
            case COMMAND_CC_TO_CORNER_LIMIT:
                return parseCcToCornerLimit(parts, prefixCommand, num);
            case COMMAND_CC_TO_CORNER_LIMIT_MIN:
                return parseCcToCornerLimitMin(parts, prefixCommand, num);
            case COMMAND_CORNER_SCORE_DIFFERENCE_REJECT_VALUE:
                return parseCornerScoreDifferenceRejectValue(parts, prefixCommand, num);
            case COMMAND_Z_REF:
                return parseZRef(parts, prefixCommand, num);
            case COMMAND_SRCH_STEP:
                return parseSrchStep(parts, prefixCommand, num);
            case COMMAND_GOLDEN_GLUE_THICKNESS_MIN:
                return parseGoldenGlueThicknessMin(parts, prefixCommand, num);
            case COMMAND_GOLDEN_GLUE_THICKNESS_MAX:
                return parseGoldenGlueThicknessMax(parts, prefixCommand, num);
            default:
                // logWarn(prefixCommand, "Unsupported command: " + command);
                return null;
        }
    }

    private static ImAaListCommand parseRoi(String[] parts, String prefixCommand, int num) {
        if (parts.length < 6) {
            logError(prefixCommand, "ROI: Invalid number of parts in the command: " + parts.length);
            return null;
        }

        String subSystem = null;

        Matcher matcher = PATTERN.matcher(parts[3]);

        if (matcher.find()) {
            subSystem = matcher.group(1);
            if (isValidSubSystem(subSystem)) {
                String val = parts[5];
                logInfo(prefixCommand, "ROI: subSystem: " + subSystem + ", " + val);
                return new ImAaListCommand(null, num, prefixCommand, COMMAND_ROI, subSystem, val, null);
            } else {
                logError(prefixCommand, "Invalid subsystem: " + subSystem);
                return null;
            }
        } else {
            // logError(prefixCommand, "AaHandler string does not match the expected pattern");
            return null;
        }
    }

    private static ImAaListCommand parseMtfOffAxisCheck(String[] parts, String prefixCommand, int num) {
        String val = parts[3];
        logInfo(prefixCommand, "MTF_OFF_AXIS_CHECK: " + val);
        return new ImAaListCommand(null, num, prefixCommand, COMMAND_MTF_OFF_AXIS_CHECK, null, val, null);
    }

    private static ImAaListCommand parseTarget(String[] parts, String prefixCommand, int num) {
        String value = parts[3];
        logInfo(prefixCommand, COMMAND_TARGET + ": value: " + value);
        return new ImAaListCommand(null, num, prefixCommand, COMMAND_TARGET, null, value, null);
    }

    private static ImAaListCommand parseCcToCornerLimit(String[] parts, String prefixCommand, int num) {
        String value = parts[3];
        logInfo(prefixCommand, COMMAND_CC_TO_CORNER_LIMIT + ": value: " + value);
        return new ImAaListCommand(null, num, prefixCommand, COMMAND_CC_TO_CORNER_LIMIT, null, value, null);
    }

    private static ImAaListCommand parseCcToCornerLimitMin(String[] parts, String prefixCommand, int num) {
        String value = parts[3];
        logInfo(prefixCommand, COMMAND_CC_TO_CORNER_LIMIT_MIN + ": value: " + value);
        return new ImAaListCommand(null, num, prefixCommand, COMMAND_CC_TO_CORNER_LIMIT_MIN, null, value, null);
    }

    private static ImAaListCommand parseCornerScoreDifferenceRejectValue(String[] parts, String prefixCommand, int num) {
        String value = parts[3];
        logInfo(prefixCommand, COMMAND_CORNER_SCORE_DIFFERENCE_REJECT_VALUE + ": value: " + value);
        return new ImAaListCommand(null, num, prefixCommand, COMMAND_CORNER_SCORE_DIFFERENCE_REJECT_VALUE, null, value, null);
    }

    private static ImAaListCommand parseZRef(String[] parts, String prefixCommand, int num) {
        String value = parts[3];
        logInfo(prefixCommand, COMMAND_Z_REF + ": value: " + value);
        return new ImAaListCommand(null, num, prefixCommand, COMMAND_Z_REF, null, value, null);
    }

    private static ImAaListCommand parseSrchStep(String[] parts, String prefixCommand, int num) {
        String value = parts[3];
        logInfo(prefixCommand, COMMAND_SRCH_STEP + ": value: " + value);
        return new ImAaListCommand(null, num, prefixCommand, COMMAND_SRCH_STEP, null, value, null);
    }

    private static ImAaListCommand parseGoldenGlueThicknessMin(String[] parts, String prefixCommand, int num) {
        String value = parts[3];
        logInfo(prefixCommand, COMMAND_GOLDEN_GLUE_THICKNESS_MIN + ": value: " + value);
        return new ImAaListCommand(null, num, prefixCommand, COMMAND_GOLDEN_GLUE_THICKNESS_MIN, null, value, null);
    }

    private static ImAaListCommand parseGoldenGlueThicknessMax(String[] parts, String prefixCommand, int num) {
        String value = parts[3];
        logInfo(prefixCommand, COMMAND_GOLDEN_GLUE_THICKNESS_MAX + ": value: " + value);
        return new ImAaListCommand(null, num, prefixCommand, COMMAND_GOLDEN_GLUE_THICKNESS_MAX, null, value, null);
    }

    private static boolean isValidSubSystem(String subSystem) {
        return "CC".equals(subSystem) || "UL".equals(subSystem) || "UR".equals(subSystem) || "LL".equals(subSystem) || "LR".equals(subSystem);
    }

    private static void logError(String prefixCommand, String message) {
        logger.error(">>>>> {}-ItemAaParser: {}", prefixCommand, message);
    }

    private static void logWarn(String prefixCommand, String message) {
        logger.warn(">>>>> {}-ItemAaParser: {}", prefixCommand, message);
    }

    private static void logInfo(String prefixCommand, String message) {
        logger.info(">>>>> {}-ItemAaParser: {}", prefixCommand, message);
    }
}