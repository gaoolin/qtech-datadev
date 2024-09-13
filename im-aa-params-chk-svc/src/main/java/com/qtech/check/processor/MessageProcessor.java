package com.qtech.check.processor;

import com.qtech.check.processor.handler.MessageHandler;
import com.qtech.check.processor.handler.MessageHandlerRegistry;
import org.apache.commons.codec.DecoderException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.DependsOn;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Component;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * author :  gaozhilin
 * email  :  gaoolin@gmail.com
 * date   :  2024/05/12 22:11:52
 * desc   :
 */


@Component
@DependsOn("messageHandlerRegistry")
@Lazy
public class MessageProcessor {

    private static final Logger logger = LoggerFactory.getLogger(MessageProcessor.class);
    private final Map<Class<?>, MessageHandler<?>> handlerCache = new ConcurrentHashMap<>();
    @Autowired
    private MessageHandlerRegistry messageHandlerRegistry;

    public <R> R processMessage(Class<R> clazz, String msg) {
        MessageHandler<?> messageHandler = handlerCache.computeIfAbsent(clazz, messageHandlerRegistry::getMessageHandlerForType);
        if (messageHandler != null) {
            try {
                return clazz.cast(messageHandler.handleByType(clazz, msg));
            } catch (ClassCastException | DecoderException e) {
                throw new IllegalStateException("Handler did not return expected type.", e);
            }
        } else {
            logger.warn(">>>>> No handler found for message type: {}", clazz.getSimpleName());
            return null;
        }
    }
}


/**
 * @description 这个注解会告诉Spring在实例化该Bean之后，但在该Bean的任何方法被调用之前，执行init()方法。这样可以确保在使用applicationContext.getBean()方法之前，ApplicationContext已经被刷新。
 * @param
 * @return void
 * @PostConstruct public void init() {
 * this.messageHandlerRegistry = applicationContext.getBean(MessageHandlerRegistry.class);
 * }
 */