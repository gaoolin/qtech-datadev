package com.qtech.check.utils;

import com.qtech.common.utils.StringUtils;
import com.qtech.share.aa.pojo.ImAaListCommand;

import java.util.*;
import java.util.stream.Collectors;

import static com.qtech.share.aa.constant.ComparisonConstants.AGG_MTF_CHECK_COMMANDS;
import static com.qtech.share.aa.constant.ComparisonConstants.AGG_MTF_CHECK_SUB_SYSTEM;

/**
 * author :  gaozhilin
 * email  :  gaoolin@gmail.com
 * date   :  2024/09/24 17:06:10
 * desc   :
 */
public class AggregateCommandsUtil {

    public static List<ImAaListCommand> aggregateMtfCheckCommands(List<ImAaListCommand> commands) {
        // 过滤符合条件的命令
        // FIXME 只需要聚合mtfCheck的AAListCommand即可，AA命令中不允许挂载mtfCheck
        // List<ImAaListCommand> filteredCommands = commands.stream().filter(command -> (command != null && command.getPrefixCommand() != null) && (command.getPrefixCommand().startsWith("MTF_Check") || ((command.getPrefixCommand().startsWith("AA")) && "ROI".equals(command.getCommand()) && !StringUtils.containsAny(command.getSubsystem(), "CC", "UL", "UR", "LL", "LR")))).collect(Collectors.toList());

        List<ImAaListCommand> filteredCommands = commands.stream()
                .filter(AggregateCommandsUtil::isValidCommand)
                .collect(Collectors.toList());

        // 获取剩余未通过过滤的命令
        List<ImAaListCommand> remainingCommands = new ArrayList<>(commands);
        remainingCommands.removeAll(filteredCommands); // 从原始列表中移除过滤后的命令

        // 按前缀分组
        Map<String, List<ImAaListCommand>> groupedCommands = filteredCommands.stream().collect(Collectors.groupingBy(ImAaListCommand::getPrefixCommand));

        List<ImAaListCommand> resultCommands = new ArrayList<>();
        for (Map.Entry<String, List<ImAaListCommand>> entry : groupedCommands.entrySet()) {
            List<ImAaListCommand> sameGroup = entry.getValue();

            ArrayList<ImAaListCommand> tempAgg = new ArrayList<>();

            // 根据subSystem进行排序
            sameGroup.sort(Comparator.comparingInt(cmd -> Integer.parseInt(cmd.getSubsystem())));

            int commandCounter = 1; // 每组初始化计数器

            for (int i = 0; i < sameGroup.size(); ) {
                ImAaListCommand currentCommand = sameGroup.get(i);
                if (currentCommand.getValue() == null || currentCommand.getSubsystem() == null) {
                    i++;
                    continue; // 跳过无效命令
                }

                String currentVal = currentCommand.getValue();
                List<ImAaListCommand> aggregatedGroup = new ArrayList<>();
                aggregatedGroup.add(currentCommand);

                int subSystemIndex = Integer.parseInt(currentCommand.getSubsystem());
                i++;

                while (i < sameGroup.size()) {
                    ImAaListCommand nextCommand = sameGroup.get(i);
                    if (nextCommand.getValue() != null && nextCommand.getSubsystem() != null && nextCommand.getValue().equals(currentVal) && Integer.parseInt(nextCommand.getSubsystem()) == subSystemIndex + 1) {
                        aggregatedGroup.add(nextCommand);
                        subSystemIndex++;
                        i++;
                    } else {
                        break;
                    }
                }

                // 检查是否所有subSystem在9到12之间
                boolean allSubsystemsInRange = aggregatedGroup.stream().allMatch(cmd -> {
                    int subSysValue = Integer.parseInt(cmd.getSubsystem());
                    return subSysValue >= 9 && subSysValue <= 12;
                });

                // 创建新的AaListCommand并添加到聚合列表
                ImAaListCommand aggregatedCommand;
                if (allSubsystemsInRange) {
                    aggregatedCommand = createAggregatedCommand(aggregatedGroup, AGG_MTF_CHECK_SUB_SYSTEM); // subSystem设置为"c"
                } else {
                    aggregatedCommand = createAggregatedCommand(aggregatedGroup, String.valueOf(commandCounter)); // 使用计数器
                    commandCounter++; // 增加计数器
                }
                tempAgg.add(aggregatedCommand);
            }
            // 合并subSystem为1和2的对象
            mergeSubsystems(tempAgg);
            resultCommands.addAll(tempAgg);
        }

        // 将剩余未通过过滤的命令添加到结果中
        resultCommands.addAll(remainingCommands);

        return resultCommands;
    }

    private static void mergeSubsystems(List<ImAaListCommand> tempAgg) {
        List<ImAaListCommand> subsystemsOneAndTwo = tempAgg.stream().filter(cmd -> cmd.getSubsystem() != null && (cmd.getSubsystem().equals("1") || cmd.getSubsystem().equals("2"))).collect(Collectors.toList());

        if (subsystemsOneAndTwo.size() == 2) {
            ImAaListCommand command1 = subsystemsOneAndTwo.get(0);
            ImAaListCommand command2 = subsystemsOneAndTwo.get(1);

            if (command1.getValue().equals(command2.getValue())) {
                // 合并逻辑
                tempAgg.remove(command1);
                tempAgg.remove(command2);

                ImAaListCommand mergedCommand = new ImAaListCommand(null, command1.getNum() + command2.getNum(), // 数量
                        command1.getPrefixCommand(), AGG_MTF_CHECK_COMMANDS, // 使用固定的字符 "F"
                        "1", // subSystem设置为"1"
                        command1.getValue(), null);

                tempAgg.add(mergedCommand);

                // 其他对象的value减1
                for (ImAaListCommand command : tempAgg) {
                    if (!command.getSubsystem().equals(AGG_MTF_CHECK_SUB_SYSTEM) && Integer.parseInt(command.getSubsystem()) > 1) {
                        command.setSubsystem(String.valueOf(Integer.parseInt(command.getSubsystem()) - 1));
                    }
                }
            }
        }
    }

    private static ImAaListCommand createAggregatedCommand(List<ImAaListCommand> commands, String subSystemValue) {
        ImAaListCommand firstCommand = commands.get(0);
        return new ImAaListCommand(null, commands.size(), firstCommand.getPrefixCommand(), "F", // 使用固定的字符 "F"
                subSystemValue, // 设置为传入的subSystem值
                firstCommand.getValue(), null);
    }

    private static boolean isValidCommand(ImAaListCommand command) {
        if (command == null || command.getPrefixCommand() == null) {
            return false;
        }
        boolean containsAny = StringUtils.containsAny(command.getSubsystem(), "CC", "UL", "UR", "LL", "LR");
        return command.getPrefixCommand().startsWith("MTF_Check")
                || (command.getPrefixCommand().startsWith("AA")
                && Objects.equals(command.getCommand(), "ROI")
                && !containsAny);
    }
}