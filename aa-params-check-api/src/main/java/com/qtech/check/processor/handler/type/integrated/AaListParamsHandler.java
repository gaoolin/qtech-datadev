package com.qtech.check.processor.handler.type.integrated;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.qtech.check.constant.ListItemMultiKeyMapConstants;
import com.qtech.check.pojo.AaListCommand;
import com.qtech.check.pojo.AaListParams;
import com.qtech.check.processor.CommandProcessor;
import com.qtech.check.processor.handler.MessageHandler;
import com.qtech.check.processor.handler.type.AaListCommandHandler;
import com.qtech.check.utils.ToCamelCaseConverter;
import com.qtech.common.utils.StringUtils;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.codec.DecoderException;
import org.apache.commons.codec.binary.Hex;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 * author :  gaozhilin
 * email  :  gaoolin@gmail.com
 * date   :  2024/05/27 14:24:19
 * desc   :  List、Item 级联解析
 */

@Slf4j
@Component
public class AaListParamsHandler extends MessageHandler<AaListCommand> {

    @Autowired
    private CommandProcessor commandProcessor;

    @Autowired
    private ListItemMultiKeyMapConstants listItemMultiKeyMapConstants;

    private static final ThreadLocal<HashMap<Integer, String>> listItemMapper = ThreadLocal.withInitial(HashMap::new);
    private static final ThreadLocal<AaListParams> threadLocalAaListParamsMessage = ThreadLocal.withInitial(AaListParams::new);

    public AaListCommand parseRowStartWithList(String[] parts) {
        try {
            // 获取处理器
            AaListCommandHandler<AaListCommand> handler = commandProcessor.getCommandHandler("List");
            // 使用处理器处理命令
            return handler.handle(parts);
            // 处理结果...
        } catch (RuntimeException e) {
            // 处理未找到处理器的情况
            // 其他错误处理逻辑...
            log.warn("未找到List处理器: {}", e.getMessage());
        }
        return null;
    }

    // FIXME : 优化 根据mapper 获取对应的commandHandler
    public AaListCommand parseRowStartWithItem(String[] parts, String listItemMapperKey) {
        if (listItemMapperKey != null) {
            try {
                String normalizedCommandStr = ToCamelCaseConverter.doConvert(listItemMapperKey);
                // System.out.println("@@@@" + normalizedCommandStr);
                // 获取处理器
                AaListCommandHandler<AaListCommand> handler = commandProcessor.getCommandHandler(normalizedCommandStr);
                // 使用处理器处理命令
                return handler.handle(parts);
                // 处理结果...
            } catch (RuntimeException e) {
                // 处理未找到处理器的情况
                // 其他错误处理逻辑...
                log.warn("未找到List处理器: {}", e.getMessage());
                return null;
            }
        } else {
            log.warn("listItemMapper is empty");
            return null;
        }
    }

    private static HashMap<Integer, String> getThreadListItemMapper() {
        HashMap<Integer, String> map = listItemMapper.get();
        map.clear();
        return map;
    }

    private AaListParams getThreadLocalAaListParams() {
        AaListParams aaListParams = threadLocalAaListParamsMessage.get();
        aaListParams.reset();
        return aaListParams;
    }

    public AaListParams doParse(String msg) {
        AaListParams aaListParams = getThreadLocalAaListParams();
        HashMap<Integer, String> mapper = getThreadListItemMapper();
        String[] lines = msg.split("\n");
        List<AaListCommand> aaListCommandList = new ArrayList<>();
        for (String line : lines) {
            if (line.trim().isEmpty()) {
                continue; // 跳过空行
            }
            String[] parts = line.split("\\s+");
            // System.out.println("&&&&" + Arrays.toString(parts));
            if (parts.length == 0) {
                log.warn("Empty line encountered: " + line);
                continue;
            }
            String startWithStr = parts[0];
            try {
                if ("LIST".equals(startWithStr)) {
                    mapper.put(Integer.parseInt(parts[1]), parts[3]);
                    aaListCommandList.add(parseRowStartWithList(parts));
                } else if ("ITEM".equals(startWithStr)) {
                    Integer key = Integer.parseInt(parts[1]);
                    if (!StringUtils.isEmpty(listItemMultiKeyMapConstants.get(mapper.get(key)))) {
                        aaListCommandList.add(parseRowStartWithItem(parts, listItemMultiKeyMapConstants.get(mapper.get(key))));
                    } else {
                        log.warn("Key not found in listItemMultiKeyMapConstants for key: " + key);
                    }
                } else {
                    log.warn("Unsupported line: " + line);
                }
            } catch (ArrayIndexOutOfBoundsException e) {
                log.error("ArrayIndexOutOfBoundsException: " + e.getMessage() + " for line: " + line, e);
            } catch (Exception e) {
                log.error("Exception occurred while processing line: " + line, e);
            }
        }

        aaListParams.fillWithData(aaListCommandList);
        return aaListParams;
    }

    @Override
    public <R> R handleByType(Class<R> clazz, String msg) throws DecoderException {
        if (clazz == AaListParams.class) {
            AaListParams aaListParamsObj = null;
            JSONObject jsonObject = JSON.parseObject(msg);
            String aaListParamHexStr = jsonObject.getString("FactoryName");
            String aaListParamStr = null;
            try {
                aaListParamStr = new String(Hex.decodeHex(aaListParamHexStr));
            } catch (DecoderException e) {
                log.error("Hex解码异常，机型: {}", jsonObject.getString("WoCode"));
                return null;
            }
            aaListParamsObj = doParse(aaListParamStr);
            aaListParamsObj.setSimId(jsonObject.getString("OpCode"));
            aaListParamsObj.setProdType(jsonObject.getString("WoCode").split("#")[0]);
            return clazz.cast(aaListParamsObj);
        }
        throw new UnsupportedOperationException("Unsupported message handling for type: " + clazz.getSimpleName());
    }

    @Override
    public <U> boolean supportsType(Class<U> clazz) {
        return clazz.equals(AaListParams.class);
    }
}
