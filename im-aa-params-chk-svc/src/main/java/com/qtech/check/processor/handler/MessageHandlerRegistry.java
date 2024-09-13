package com.qtech.check.processor.handler;

import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

import javax.annotation.Nullable;
import javax.annotation.PostConstruct;
import java.util.HashMap;
import java.util.Map;

/**
 * author :  gaozhilin
 * email  :  gaoolin@gmail.com
 * date   :  2024/05/12 22:31:05
 * desc   :
 */


@Component
@Order(1) // 设置较低的数字，使其优先初始化
public class MessageHandlerRegistry implements ApplicationContextAware {

    private final Map<String, MessageHandler<?>> messageHandlerMap = new HashMap<>();
    private ApplicationContext applicationContext;

    @PostConstruct
    public void init() {
        // 获取所有MessageHandler的Bean实例
        String[] beanNames = applicationContext.getBeanNamesForType(MessageHandler.class);
        for (String beanName : beanNames) {
            // 使用通配符 ? 来避免原始类型的警告
            MessageHandler<?> handler = applicationContext.getBean(beanName, MessageHandler.class);
            messageHandlerMap.put(handler.getMessageType(), handler);
        }
    }

    public <T> MessageHandler<T> getMessageHandlerForType(Class<T> clazz) {
        String messageType = clazz.getSimpleName().replace("Handler", "");
        MessageHandler<?> handler = messageHandlerMap.get(messageType);

        // FIXME 添加类型断言并进行类型检查
        if (handler != null) {
            @SuppressWarnings("unchecked") // 添加抑制警告注解，因为前面进行了类型检查
            MessageHandler<T> typedHandler = (MessageHandler<T>) handler;
            return typedHandler;
        } else {
            throw new IllegalArgumentException("No suitable handler found for type: " + clazz.getName());
        }
    }

    public Map<String, MessageHandler<?>> getMessageHandlerMap() {
        return messageHandlerMap;
    }

    public ApplicationContext getApplicationContext() {
        return applicationContext;
    }

    @Override
    public void setApplicationContext(@Nullable ApplicationContext applicationContext) throws BeansException {
        this.applicationContext = applicationContext;
    }
}
