package com.qtech.check.processor.handler.type.integrated;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.type.TypeFactory;
import com.qtech.check.constant.ListItemMultiKeyMapConstants;
import com.qtech.check.exception.AaListParseListActionEmptyException;
import com.qtech.check.pojo.AaListCommand;
import com.qtech.check.pojo.AaListParams;
import com.qtech.check.processor.CommandProcessor;
import com.qtech.check.processor.handler.MessageHandler;
import com.qtech.check.processor.handler.type.AaListCommandHandler;
import com.qtech.common.utils.StringUtils;
import org.apache.commons.codec.DecoderException;
import org.apache.commons.codec.binary.Hex;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.*;
import java.util.stream.Collectors;

import static com.qtech.check.constant.ComparisonConstants.CONTROL_LIST_SET;
import static com.qtech.check.utils.AggregateCommandsUtil.aggregateMtfCheckCommands;

/**
 * author :  gaozhilin
 * email  :  gaoolin@gmail.com
 * date   :  2024/05/27 14:24:19
 * desc   :  List、Item 级联解析
 */
@Component
public class AaListParamsHandler extends MessageHandler<AaListCommand> {
    private static final Logger logger = LoggerFactory.getLogger(AaListParamsHandler.class);
    private static final ThreadLocal<HashMap<Integer, String>> listItemMapper = ThreadLocal.withInitial(HashMap::new);
    private static final ThreadLocal<AaListParams> threadLocalAaListParamsMessage = ThreadLocal.withInitial(AaListParams::new);
    private final Map<String, String> listItemMap = new HashMap<>();

    @Autowired
    private CommandProcessor commandProcessor;

    @Autowired
    private ListItemMultiKeyMapConstants listItemMultiKeyMapConstants;

    @Autowired
    private ObjectMapper objectMapper;

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

    public AaListCommand parseRowStartWithList(String[] parts) {
        try {
            // 获取处理器
            AaListCommandHandler<AaListCommand> handler = commandProcessor.getCommandHandler("List");
            // 使用处理器处理命令
            return handler.handle(parts);
        } catch (RuntimeException e) {
            // 处理未找到处理器的情况
            logger.warn(">>>>> 未找到List处理器: {}", e.getMessage());
        }
        return null;
    }

    // FIXME : 优化 根据mapper 获取对应的commandHandler
    public AaListCommand parseRowStartWithItem(String[] parts, String handlerName, String command) {
        if (handlerName != null) {
            try {
                // 获取处理器
                AaListCommandHandler<AaListCommand> handler = commandProcessor.getCommandHandler(handlerName);
                // 使用处理器处理命令
                return handler.handle(parts, command);
            } catch (RuntimeException e) {
                // 处理未找到处理器的情况
                logger.warn(">>>>> 未找到Item处理器:{}\n{}", handlerName, e.getMessage());
                return null;
            }
        } else {
            logger.warn(">>>>> listItemMapper is empty");
            return null;
        }
    }

    public AaListParams doFullParse(String msg) {
        AaListParams aaListParams = getThreadLocalAaListParams();
        HashMap<Integer, String> mapper = getThreadListItemMapper();

        // 将每一行数据拆分并转换为 AaListCommand 对象
        List<AaListCommand> aaListCommandList = Arrays.stream(msg.split("\n"))
                .map(String::trim)  // 去除每行的空白字符
                .filter(line -> !line.isEmpty())  // 跳过空行
                .map(line -> line.split("\\s+"))  // 按空格分割每一行
                .filter(parts -> parts.length > 0)  // 过滤掉空的行
                .map(parts -> {
                    String startWithStr = parts[0];
                    try {
                        if ("LIST".equals(startWithStr)) {
                            String listNmb = parts[1];
                            String command = parts[2];
                            if (CONTROL_LIST_SET.contains(command)) {
                                mapper.put(Integer.parseInt(listNmb), command);
                            }
                            return parseRowStartWithList(parts);  // 解析 LIST 行
                        } else if ("ITEM".equals(startWithStr)) {
                            Integer key = Integer.parseInt(parts[1]);
                            String command = mapper.get(key);
                            String handlerName = listItemMultiKeyMapConstants.get(command);
                            if (!StringUtils.isEmpty(handlerName)) {
                                return parseRowStartWithItem(parts, handlerName, command);  // 解析 ITEM 行
                            }
                        } else {
                            logger.warn(">>>>> Unsupported line: {}", Arrays.toString(parts));
                        }
                    } catch (ArrayIndexOutOfBoundsException e) {
                        logger.error(">>>>> ArrayIndexOutOfBoundsException: {}", e.getMessage(), e);
                    } catch (Exception e) {
                        logger.error(">>>>> Exception occurred while processing line: {}", Arrays.toString(parts), e);
                    }
                    return null;  // 如果处理失败返回 null
                })
                .filter(Objects::nonNull)  // 过滤掉 null 的结果
                .collect(Collectors.toList());  // 收集到列表中

        // 聚合 AaListCommand
        List<AaListCommand> aggregatedCommands = aggregateMtfCheckCommands(aaListCommandList);
        // 将命令列表填充到 aaListParams 中
        aaListParams.fillWithData(aggregatedCommands);
        return aaListParams;
    }

    @Override
    public <R> R handleByType(Class<R> clazz, String msg) throws DecoderException {
        if (clazz == AaListParams.class) {
            AaListParams aaListParamsObj = null;
            try {
                Map<String, Object> jsonObject = objectMapper.readValue(msg, TypeFactory.defaultInstance().constructMapType(Map.class, String.class, Object.class));
                String aaListParamHexStr = (String) jsonObject.get("FactoryName");
                String aaListParamStr = null;
                try {
                    aaListParamStr = new String(Hex.decodeHex(aaListParamHexStr));
                } catch (DecoderException e) {
                    logger.error(">>>>> Hex解码异常，机型: {}", jsonObject.get("WoCode"));
                    return null;
                }
                aaListParamsObj = doFullParse(aaListParamStr);
                aaListParamsObj.setSimId((String) jsonObject.get("OpCode"));
                aaListParamsObj.setProdType(((String) jsonObject.get("WoCode")).split("#")[0]);
                return clazz.cast(aaListParamsObj);
            } catch (JsonProcessingException e) {
                logger.error(">>>>> JSON 解析异常", e);
                return null;
            }
        }
        throw new UnsupportedOperationException("Unsupported message handling for type: " + clazz.getSimpleName());
    }

    @Override
    public <U> boolean supportsType(Class<U> clazz) {
        return clazz.equals(AaListParams.class);
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