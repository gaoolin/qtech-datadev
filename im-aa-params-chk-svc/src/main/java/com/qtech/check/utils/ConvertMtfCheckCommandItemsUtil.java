package com.qtech.check.utils;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.qtech.common.utils.StringUtils;
import com.qtech.share.aa.pojo.ImAaListCommand;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.*;
import java.util.stream.Collectors;

import static com.qtech.share.aa.constant.ComparisonConstants.AGG_MTF_CHECK_ITEMS_FILTER_PREFIX;
import static com.qtech.share.aa.constant.ComparisonConstants.AGG_MTF_CHECK_ITEMS_RESULT_SUFFIX;

/**
 * author :  gaozhilin
 * email  :  gaoolin@gmail.com
 * date   :  2024/12/18 09:00:38
 * desc   :  用于将 MTF_CHECK 命令的 ITEMS 转换为 Json格式字符串
 */
public class ConvertMtfCheckCommandItemsUtil {
    private static final Logger logger = LoggerFactory.getLogger(ConvertMtfCheckCommandItemsUtil.class);
    private static final ObjectMapper objectMapper = new ObjectMapper();

    public static List<ImAaListCommand> convert(List<ImAaListCommand> commands) {
        // 一次遍历生成 filteredCommands 和 remainingCommands
        List<ImAaListCommand> filteredCommands = new ArrayList<>();
        List<ImAaListCommand> remainingCommands = new ArrayList<>();
        for (ImAaListCommand command : commands) {
            if (isValidCommand(command)) {
                filteredCommands.add(command);
            } else {
                remainingCommands.add(command);
            }
        }

        if (filteredCommands.isEmpty()) return remainingCommands;

        // 按前缀分组并过滤无效命令
        Map<String, List<ImAaListCommand>> groupedCommands = filteredCommands.stream()
                .filter(cmd -> cmd.getValue() != null && cmd.getSubsystem() != null)
                .collect(Collectors.groupingBy(ImAaListCommand::getPrefixCommand));

        List<ImAaListCommand> resultCommands = new ArrayList<>();
        for (Map.Entry<String, List<ImAaListCommand>> entry : groupedCommands.entrySet()) {
            List<ImAaListCommand> sameGroupNotNull = entry.getValue();

            // 根据subSystem进行排序
            sameGroupNotNull.sort(Comparator.comparingInt(cmd -> Integer.parseInt(cmd.getSubsystem())));

            // 构建 Map，subSystem 作为键，value 作为值
            Map<String, String> subSystemValueMap = sameGroupNotNull.stream()
                    .collect(Collectors.toMap(
                            ImAaListCommand::getSubsystem,
                            ImAaListCommand::getValue,
                            (existing, replacement) -> existing, // 处理键冲突的情况
                            () -> new TreeMap<>(Comparator.comparingInt(Integer::parseInt)) // 使用 TreeMap 并提供自定义比较器
                    ));

            String sameGroupJsonStr = null;
            try {
                sameGroupJsonStr = objectMapper.writeValueAsString(subSystemValueMap);
            } catch (JsonProcessingException e) {
                logger.error(">>>>> JSON解析失败, msg: {}", sameGroupNotNull, e);
            }

            ImAaListCommand temp = new ImAaListCommand(null, null, null, StringUtils.joinWith("_", entry.getKey(), AGG_MTF_CHECK_ITEMS_RESULT_SUFFIX), null, sameGroupJsonStr, null);

            resultCommands.add(temp);
        }
        // 将剩余未通过过滤的命令添加到结果中
        resultCommands.addAll(remainingCommands);

        return resultCommands;
    }

    // FIXME : 以后需要把内置AA命令的mtfCheck去除
    private static boolean isValidCommand(ImAaListCommand command) {
        if (command == null || command.getPrefixCommand() == null) {
            return false;
        }
        if (command.getSubsystem() == null) {
            return false;
        }
        // boolean containsAny = StringUtils.containsAny(command.getSubsystem(), "CC", "UL", "UR", "LL", "LR");
        return command.getPrefixCommand().startsWith(AGG_MTF_CHECK_ITEMS_FILTER_PREFIX);
    }
}
