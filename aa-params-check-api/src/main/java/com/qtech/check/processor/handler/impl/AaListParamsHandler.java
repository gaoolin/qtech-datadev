package com.qtech.check.processor.handler.impl;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.qtech.check.pojo.AaListCommand;
import com.qtech.check.pojo.AaListParams;
import com.qtech.check.exception.AaListParseListActionEmptyException;
import com.qtech.check.processor.handler.MessageHandler;
import org.apache.commons.codec.DecoderException;
import org.apache.commons.codec.binary.Hex;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import java.util.*;

/**
 * author :  gaozhilin
 * email  :  gaoolin@gmail.com
 * date   :  2024/05/12 22:07:16
 * desc   :
 */

@Component
public class AaListParamsHandler implements MessageHandler<AaListParams> {

    private static final Logger logger = LoggerFactory.getLogger(AaListParamsHandler.class);
    private static final ThreadLocal<AaListParams> threadLocalAaListParamsMessage = ThreadLocal.withInitial(AaListParams::new);
    private final Map<String, String> listItemMap = new HashMap<>();

    @Override
    public void handle(String msg) {
        if (msg != null) {
            logger.info("Received message: " + msg);
        } else {
            throw new IllegalArgumentException("Expected a String message, but received: null");
        }
    }

    @Override
    public <R> R handleByType(Class<R> clazz, String msg) throws DecoderException {
        if (clazz == AaListParams.class) {
            AaListParams aaListParamsObj = threadLocalAaListParamsMessage.get();
            aaListParamsObj.reset();
            JSONObject jsonObject = JSON.parseObject(msg);
            String aaListParamHexStr = jsonObject.getString("FactoryName");
            String aaListParamStr = new String(Hex.decodeHex(aaListParamHexStr));
            aaListParamsObj = doParse(aaListParamStr);
            aaListParamsObj.setSimId(jsonObject.getString("OpCode"));
            aaListParamsObj.setProdType(jsonObject.getString("WoCode").split("#")[0]);
            return clazz.cast(aaListParamsObj);
        }
        throw new UnsupportedOperationException("Unsupported message handling for type: " + clazz.getSimpleName());
    }

    @Override
    public boolean supportsType(Class<AaListParams> clazz) {
        return clazz == AaListParams.class;
    }

    public AaListParams doParse(String msg) {
        AaListParams aaListParams = threadLocalAaListParamsMessage.get();
        // 不想每次都重新创建一个新的 AaListParams 对象，而是希望重用同一个对象实例，那么可以去掉 remove() 调用，并保留 reset() 调用
        aaListParams.reset();
        String[] lines = msg.split("\n");
        List<AaListCommand> aaListCommands = processStringList(lines);
        aaListParams.fillWithData(aaListCommands);
        return aaListParams;
    }


    public static List<AaListCommand> processStringList(String[] stringList) {
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

    public void parseListStart(StringTokenizer tokenizer, AaListParams aaListParams, String token, Map<String, String> listItemMap) {
        String num = tokenizer.nextToken();
        String listName = tokenizer.nextToken();
        String tester = tokenizer.nextToken();
        String ctu = tokenizer.nextToken();
        String fail = tokenizer.nextToken();
        String status = tokenizer.nextToken();
        HashMap<String, String> listParamsMap = new HashMap<>();
        listParamsMap.put(listName, status);
    }

    public void parseItemStart(StringTokenizer tokenizer, AaListParams aaListParams, String token) {
        String num = tokenizer.nextToken();
        if (listItemMap.isEmpty()) {
            throw new AaListParseListActionEmptyException("List action is empty");
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
                }
            });
        }
    }
}
