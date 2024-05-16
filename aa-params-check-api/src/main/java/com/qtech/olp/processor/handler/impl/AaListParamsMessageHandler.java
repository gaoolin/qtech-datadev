package com.qtech.olp.processor.handler.impl;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.qtech.common.utils.DateUtils;
import com.qtech.olp.entity.AaListParamsMessage;
import com.qtech.olp.exception.AaListParseListActionIsEmptyException;
import com.qtech.olp.processor.handler.MessageHandler;
import com.qtech.olp.utils.Command;
import org.springframework.stereotype.Component;

import java.util.*;

/**
 * author :  gaozhilin
 * email  :  gaoolin@gmail.com
 * date   :  2024/05/12 22:07:16
 * desc   :
 */

@Component
public class AaListParamsMessageHandler implements MessageHandler<AaListParamsMessage> {
    private final AaListParamsMessage aaListParamsMessage = new AaListParamsMessage();

    private final Map<String, String> listItemMap = new HashMap<>();

    @Override
    public void handle(String msg) {
        if (msg != null) {
            System.out.println("Received message: " + msg);
            // 处理String类型的msg
        } else {
            throw new IllegalArgumentException("Expected a String message, but received: null");
        }
    }

    @Override
    public <R> R handleByType(Class<R> clazz, String msg) {
        if (clazz == AaListParamsMessage.class) {
            JSONObject jsonObject = JSON.parseObject(msg);
            String aaListParamStr = jsonObject.getString("FactoryName");
            AaListParamsMessage aaListParamsMessageObj = doAaParamsParse(aaListParamStr);
            aaListParamsMessageObj.setSimId(jsonObject.getString("OpCode"));
            Date eventTime = DateUtils.getNowDate();
            aaListParamsMessageObj.setEventTime(eventTime);
            aaListParamsMessageObj.setProdType(jsonObject.getString("WoCode").split("#")[0]);
            return clazz.cast(aaListParamsMessageObj);
        }
        throw new UnsupportedOperationException("Unsupported message handling for type: " + clazz.getSimpleName());
    }

    @Override
    public boolean supportsType(Class<AaListParamsMessage> clazz) {
        return clazz == AaListParamsMessage.class;
    }


    public AaListParamsMessage doAaParamsParse(String msg) {

        /*StringTokenizer tokenizer = new StringTokenizer(msg, " \t\r\n");
        while (tokenizer.hasMoreTokens()) {
            String token = tokenizer.nextToken();
            if ("LIST".equals(token)) {
                parseListStart(tokenizer, aaListParamsMessage, token, listItemMap);
            } else if ("ITEM".equals(token)) {
                // parseItemStart(tokenizer, aaListParamsMessage, token);
                continue;
            }
        }*/
        // System.out.println(aaListParamsMessage);
        String[] lines = msg.split("\n");
        List<Command> commands = processStringList(lines);


             aaListParamsMessage.fillWithData(commands);

        System.out.println(aaListParamsMessage);
        return aaListParamsMessage;
    }

    public void parseListStart(StringTokenizer tokenizer, AaListParamsMessage aaListParamsMessage, String token, Map<String, String> listItemMap) {

        String num = tokenizer.nextToken(); // Skip the number (1)
        String listName = tokenizer.nextToken(); // Get the action (Destroy_Start)
        String tester = tokenizer.nextToken(); // Get the action (Destroy_Start)
        String ctu = tokenizer.nextToken(); // Get the action (Destroy_Start)
        String fail = tokenizer.nextToken(); // Get the action (Destroy_Start)
        String status = tokenizer.nextToken(); // Get the status (Enable)
        // break; // Assuming only one such LIST entry per input

        HashMap<String, String> listParamsMap = new HashMap<>();

        // String listName = token + "_" + num + "_" + action;
        listParamsMap.put(listName, status);

        // aaListParamsMessage.fillWithData(listParamsMap);

        // System.out.println(listItemMap);
        // System.out.println(aaListParamsMessage);
    }

    public void parseItemStart(StringTokenizer tokenizer, AaListParamsMessage aaListParamsMessage, String token) {

        String num = tokenizer.nextToken();

        if (listItemMap.isEmpty()) {
            throw new AaListParseListActionIsEmptyException("List action is empty");
            // break;
        } else {
            listItemMap.forEach((key, value) -> {
                if (value.equals(num)) {
                    String param = tokenizer.nextToken();
                    String desc = tokenizer.nextToken();
                    String s = tokenizer.nextToken();
                    String itemName = key + "_" + tokenizer.nextToken();
                    String itemValue = tokenizer.nextToken();
                    HashMap<String, String> aaParamsMap = new HashMap<>();
                    aaParamsMap.put(itemName, itemValue);
                    // aaListParamsMessage.fillWithData(aaParamsMap);
                }
            });
        }
    }

    public static List<Command> processStringList(String[] stringList) {
        List<Command> commandList = new ArrayList<>();

        for (String line : stringList) {
            if (line.startsWith("LIST")) {
                String[] parts = line.split("\\s+");
                int num = Integer.parseInt(parts[1]);
                String command = parts[2];
                String subsystem = parts[3];
                String enable = parts[parts.length - 1]; // Get the last part
                Command currentCommand = new Command(num, command, subsystem, enable);
                commandList.add(currentCommand);
            }
        }

        return commandList;
    }
}