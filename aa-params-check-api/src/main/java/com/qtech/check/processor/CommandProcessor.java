package com.qtech.check.processor;

import com.qtech.check.processor.handler.type.AaListCommandHandler;
import com.qtech.check.processor.handler.type.AaListCommandHandlerRegistry;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * author :  gaozhilin
 * email  :  gaoolin@gmail.com
 * date   :  2024/05/28 15:23:12
 * desc   :  Command Processor
 */

@Component
public class CommandProcessor {

    private static final Logger logger = LoggerFactory.getLogger(CommandProcessor.class);

    @Autowired
    private AaListCommandHandlerRegistry aaListCommandHandlerRegistry;

    private final Map<String, AaListCommandHandler<?>> commandHandlerCache = new ConcurrentHashMap<>();

    public <T> AaListCommandHandler<T> getCommandHandler(String handlerName) {
        // 使用缓存来获取处理器
        AaListCommandHandler<?> cachedHandler = commandHandlerCache.computeIfAbsent(handlerName, key -> {
            try {
                return aaListCommandHandlerRegistry.getCommandHandlerForType(key);
            } catch (IllegalArgumentException e) {
                return null;
            }
        });

        if (cachedHandler == null) {
            logger.warn("Handler not found for type: {}", handlerName);
            return null;
        }

        @SuppressWarnings("unchecked")
        AaListCommandHandler<T> typedHandler = (AaListCommandHandler<T>) cachedHandler;
        return typedHandler;
    }
}
