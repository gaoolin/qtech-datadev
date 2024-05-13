package com.qtech.olp.processor.handler.impl;

import com.qtech.olp.entity.AaListMessage;
import com.qtech.olp.exception.AaListParseListActionIsEmptyException;

import java.util.HashMap;
import java.util.Map;
import java.util.StringTokenizer;

/**
 * author :  gaozhilin
 * email  :  gaoolin@gmail.com
 * date   :  2024/05/08 14:21:32
 * desc   :
 */


public class LineParserProcessor {
    private final AaListMessage aaListMessage = new AaListMessage();
    private final Map<String, String> listMap = new HashMap<>();
    private final Map<String, String> listItemMap = new HashMap<>();

    public AaListMessage doAaParamsParse(String msg) {
        StringTokenizer tokenizer = new StringTokenizer(msg, " \t\r\n");
        while (tokenizer.hasMoreTokens()) {
            String token = tokenizer.nextToken();
            if ("LIST".equals(token)) {
                parseListStart(tokenizer, aaListMessage, token, listItemMap);
            } else if ("ITEM".equals(token)) {
                parseItemStart(tokenizer, aaListMessage, token);
            }
        }
        System.out.println(aaListMessage);
        return aaListMessage;
    }

    public void parseListStart(StringTokenizer tokenizer, AaListMessage aaListMessage, String token, Map<String, String> listItemMap) {

        String num = tokenizer.nextToken(); // Skip the number (1)
        String action = tokenizer.nextToken(); // Get the action (Destroy_Start)
        String tester = tokenizer.nextToken(); // Get the action (Destroy_Start)
        String ctu = tokenizer.nextToken(); // Get the action (Destroy_Start)
        String fail = tokenizer.nextToken(); // Get the action (Destroy_Start)
        String status = tokenizer.nextToken(); // Get the status (Enable)
        // break; // Assuming only one such LIST entry per input

        HashMap<String, String> aaParamsMap = new HashMap<>();

        String listName = token + "_" + num + "_" + action;
        aaParamsMap.put(listName, status);
        listItemMap.put(action, num);

        aaListMessage.fillWithData(aaParamsMap);

        System.out.println(listItemMap);
        System.out.println(aaListMessage);
    }

    public void parseItemStart(StringTokenizer tokenizer, AaListMessage aaListMessage, String token) {

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
                    aaListMessage.fillWithData(aaParamsMap);
                }
            });
        }
/*        if ("3".equals(num)) {
            String param = tokenizer.nextToken();
            if ("ROI".equals(param)) {
                String desc = tokenizer.nextToken();
                String celling = tokenizer.nextToken();
                String actual = tokenizer.nextToken();
                String floor = tokenizer.nextToken();
                String itemName = token + "_" + num + "_" + param + "_" + desc;
                HashMap<String, String> aaParamsMap = new HashMap<>();
                aaParamsMap.put(itemName, actual);
                aaParams.fillWithData(aaParamsMap);
            } else if ("TARGET".equals(param)) {
                String desc = tokenizer.nextToken();
                String itemName = token + "_" + num + "_" + param;
                HashMap<String, String> aaParamsMap = new HashMap<>();
                aaParamsMap.put(itemName, desc);
                aaParams.fillWithData(aaParamsMap);
            } else if ("CC_TO_CORNER_LIMIT".equals(param)) {
                String desc = tokenizer.nextToken();
                String itemName = token + "_" + num + "_" + param;
                HashMap<String, String> aaParamsMap = new HashMap<>();
                aaParamsMap.put(itemName, desc);
                aaParams.fillWithData(aaParamsMap);
            }
        } else if ("4".equals(num)) {
            String param = tokenizer.nextToken();
            if ("".equals(param)) {
                String desc = tokenizer.nextToken();
            }
        }*/
    }
}
