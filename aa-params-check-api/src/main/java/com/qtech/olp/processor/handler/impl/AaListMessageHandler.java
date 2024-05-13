package com.qtech.olp.processor.handler.impl;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.qtech.common.utils.DateUtils;
import com.qtech.olp.entity.AaListMessage;
import com.qtech.olp.exception.AaListParseListActionIsEmptyException;
import com.qtech.olp.processor.handler.MessageHandler;
import org.springframework.stereotype.Component;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.StringTokenizer;

/**
 * author :  gaozhilin
 * email  :  gaoolin@gmail.com
 * date   :  2024/05/12 22:07:16
 * desc   :
 */

@Component
public class AaListMessageHandler implements MessageHandler<AaListMessage> {
    private final AaListMessage aaListMessage = new AaListMessage();

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
        if (clazz == AaListMessage.class) {
            JSONObject jsonObject = JSON.parseObject(msg);
            String aaListParamStr = jsonObject.getString("FactoryName");
            AaListMessage aaListMessageObj = doAaParamsParse(aaListParamStr);
            aaListMessageObj.setMcId(jsonObject.getString("OpCode"));
            Date eventTime = DateUtils.getNowDate();
            aaListMessageObj.setEventTime(eventTime);
            aaListMessageObj.setProdType(jsonObject.getString("WoCode"));
            return clazz.cast(aaListMessageObj);
        }
        throw new UnsupportedOperationException("Unsupported message handling for type: " + clazz.getSimpleName());
    }

    @Override
    public boolean supportsType(Class<AaListMessage> clazz) {
        return clazz == AaListMessage.class;
    }


    public AaListMessage doAaParamsParse(String msg) {
        StringTokenizer tokenizer = new StringTokenizer(msg, " \t\r\n");
        while (tokenizer.hasMoreTokens()) {
            String token = tokenizer.nextToken();
            if ("LIST".equals(token)) {
                parseListStart(tokenizer, aaListMessage, token, listItemMap);
            } else if ("ITEM".equals(token)) {
                // parseItemStart(tokenizer, aaListMessage, token);
                continue;
            }
        }
        // System.out.println(aaListMessage);
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

        // System.out.println(listItemMap);
        // System.out.println(aaListMessage);
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
    }
}