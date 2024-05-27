package com.qtech.check.algorithm.model;

import com.qtech.check.pojo.AaListCommand;

import java.util.ArrayList;
import java.util.List;

/**
 * author :  gaozhilin
 * email  :  gaoolin@gmail.com
 * date   :  2024/05/27 14:24:19
 * desc   :
 */


public class ParsingModel {

    public static List<AaListCommand> parseRowStartWithList(String[] stringList) {
        List<AaListCommand> aaListCommandList = new ArrayList<>();
        for (String line : stringList) {
            if (line.startsWith("LIST")) {
                String[] parts = line.split("\\s+");
                int num = Integer.parseInt(parts[1]);
                String command = parts[2];
                String subsystem = parts[3];
                String enable = parts[parts.length - 1];
                AaListCommand currentAaListCommand = new AaListCommand(num, command, subsystem, enable);
                aaListCommandList.add(currentAaListCommand);
            }
        }
        return aaListCommandList;
    }

    public static List<AaListCommand> parseRowStartWithItem(String[] stringList) {
        List<AaListCommand> aaListCommandList = new ArrayList<>();
        for (String line : stringList) {
            if (line.startsWith("ITEM")) {
                String[] parts = line.split("\\s+");
                int num = Integer.parseInt(parts[1]);
                String command = parts[2];
                String subsystem = parts[3];
                String enable = parts[parts.length - 1];
                AaListCommand currentAaListCommand = new AaListCommand(num, command, subsystem, enable);
                aaListCommandList.add(currentAaListCommand);
            }
        }
        return aaListCommandList;
    }
}
