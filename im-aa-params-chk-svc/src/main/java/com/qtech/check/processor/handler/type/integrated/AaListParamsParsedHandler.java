package com.qtech.check.processor.handler.type.integrated;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.type.TypeFactory;
import com.qtech.check.exception.AaListParseListActionEmptyException;
import com.qtech.check.pojo.AaListParamsParsed;
import com.qtech.check.processor.CommandProcessor;
import com.qtech.check.processor.handler.MessageHandler;
import com.qtech.check.processor.handler.type.AaListCommandHandler;
import com.qtech.common.utils.StringUtils;
import com.qtech.share.aa.constant.ListItemMultiKeyMapConstants;
import com.qtech.share.aa.pojo.ImAaListCommand;
import org.apache.commons.codec.DecoderException;
import org.apache.commons.codec.binary.Hex;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.*;
import java.util.stream.Collectors;

import static com.qtech.check.utils.ConvertMtfCheckCommandItemsUtil.convert;
import static com.qtech.share.aa.constant.ComparisonConstants.CONTROL_LIST_SET;

/**
 * author :  gaozhilin
 * email  :  gaoolin@gmail.com
 * date   :  2024/05/27 14:24:19
 * desc   :  List、Item 级联解析
 */
@Component
public class AaListParamsParsedHandler extends MessageHandler<ImAaListCommand> {
    private static final Logger logger = LoggerFactory.getLogger(AaListParamsParsedHandler.class);
    private static final ThreadLocal<HashMap<Integer, String>> listItemMapper = ThreadLocal.withInitial(HashMap::new);
    private static final ThreadLocal<AaListParamsParsed> threadLocalAaListParamsMessage = ThreadLocal.withInitial(AaListParamsParsed::new);
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

    private AaListParamsParsed getThreadLocalAaListParams() {
        AaListParamsParsed aaListParamsParsed = threadLocalAaListParamsMessage.get();
        aaListParamsParsed.reset();
        return aaListParamsParsed;
    }

    public ImAaListCommand parseRowStartWithList(String[] parts) {
        try {
            // 获取处理器
            AaListCommandHandler<ImAaListCommand> handler = commandProcessor.getCommandHandler("List");
            // 使用处理器处理命令
            return handler.handle(parts);
        } catch (RuntimeException e) {
            // 处理未找到处理器的情况
            logger.warn(">>>>> 未找到List处理器: {}", e.getMessage());
        }
        return null;
    }

    // FIXME : 优化 根据mapper 获取对应的commandHandler
    public ImAaListCommand parseRowStartWithItem(String[] parts, String handlerName, String command) {
        if (handlerName != null) {
            try {
                // 获取处理器
                AaListCommandHandler<ImAaListCommand> handler = commandProcessor.getCommandHandler(handlerName);
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

    public AaListParamsParsed doFullParse(String msg) {
        AaListParamsParsed aaListParamsParsed = getThreadLocalAaListParams();
        HashMap<Integer, String> mapper = getThreadListItemMapper();

        // 将每一行数据拆分并转换为 AaListCommand 对象
        List<ImAaListCommand> aaListCommandList = Arrays.stream(msg.split("\n"))
                .map(String::trim)  // 去除每行的空白字符
                .filter(line -> !line.isEmpty())  // 跳过空行
                .filter(line -> StringUtils.startsWith(line, "LIST") || StringUtils.startsWith(line, "ITEM"))
                .map(line -> line.split("\\s+"))  // 按空格分割每一行
                // .filter(parts -> parts.length > 3)  // 过滤掉小于3个分隔的行， 可能到导致某些Item参数过滤掉，而解析不到
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

        // 聚合 AaListCommand，并返回聚合后的列表， 聚合 MTF_CHECK 命令的解析结果
        List<ImAaListCommand> aggregatedCommands = convert(aaListCommandList);
        // 将命令列表填充到 aaListParamsParsed 中
        aaListParamsParsed.fillWithData(aggregatedCommands);
        return aaListParamsParsed;
    }

    @Override
    public <R> R handleByType(Class<R> clazz, String msg) throws DecoderException {
        if (clazz == AaListParamsParsed.class) {
            AaListParamsParsed aaListParamsParsedObj = null;
            try {
                Map<String, Object> jsonObject = objectMapper.readValue(msg, TypeFactory.defaultInstance().constructMapType(Map.class, String.class, Object.class));

                // 用于调试
                // String s = ((String) jsonObject.get("WoCode")).split("#")[0];
                // if (s.equals("C4DF05")) {
                //     logger.info(">>>>> 检测到WoCode机型: {}", jsonObject.get("WoCode"));
                // }

                String aaListParamHexStr = (String) jsonObject.get("FactoryName");
                String aaListParamStr = null;
                try {
                    aaListParamStr = new String(Hex.decodeHex(aaListParamHexStr));
                } catch (DecoderException e) {
                    logger.error(">>>>> Hex解码异常，机型: {}", jsonObject.get("WoCode"));
                    return null;
                }
                aaListParamsParsedObj = doFullParse(aaListParamStr);
                String simId = jsonObject.get("OpCode").toString();
                aaListParamsParsedObj.setSimId(simId);
                String prodType = StringUtils.trim(jsonObject.get("WoCode").toString().split("#")[0]);
                aaListParamsParsedObj.setProdType(prodType);
                return clazz.cast(aaListParamsParsedObj);
            } catch (JsonProcessingException e) {
                logger.error(">>>>> JSON 解析异常", e);
                return null;
            }
        }
        throw new UnsupportedOperationException("Unsupported message handling for type: " + clazz.getSimpleName());
    }

    @Override
    public <U> boolean supportsType(Class<U> clazz) {
        return clazz.equals(AaListParamsParsed.class);
    }

    public void parseListStart(StringTokenizer tokenizer, AaListParamsParsed aaListParamsParsed, String token, Map<String, String> listItemMap) {
        String num = tokenizer.nextToken();
        String listName = tokenizer.nextToken();
        String tester = tokenizer.nextToken();
        String ctu = tokenizer.nextToken();
        String fail = tokenizer.nextToken();
        String status = tokenizer.nextToken();
        HashMap<String, String> listParamsMap = new HashMap<>();
        listParamsMap.put(listName, status);
    }

    public void parseItemStart(StringTokenizer tokenizer, AaListParamsParsed aaListParamsParsed, String token) {
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