package com.qtech.olp.processor;

import com.qtech.olp.processor.handler.MessageHandlerRegistry;
import com.qtech.olp.processor.handler.MessageHandler;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.DependsOn;
import org.springframework.stereotype.Component;

/**
 * author :  gaozhilin
 * email  :  gaoolin@gmail.com
 * date   :  2024/05/12 22:11:52
 * desc   :
 */

@Component
@DependsOn("messageHandlerRegistry")
public class MessageProcessor {

    @Autowired
    private ApplicationContext applicationContext;

    public <R> R processMessage(Class<R> clazz, String msg) {
        // 使用ApplicationContext获取MessageHandler，确保init()已经被调用
        MessageHandlerRegistry messageHandlerRegistry = applicationContext.getBean(MessageHandlerRegistry.class);
        MessageHandler<?> messageHandler = messageHandlerRegistry.getMessageHandlerForType(clazz);
        if (messageHandler != null) {
            try {
                return clazz.cast(messageHandler.handleByType(clazz, msg));
            } catch (ClassCastException e) {
                throw new IllegalStateException("Handler did not return expected type.", e);
            }
        } else {
            String messageType = clazz.getSimpleName().replace("Handler", "");
            System.out.println("No handler found for message type: " + messageType);
            return null; // 或者抛出异常
        }
    }
}
