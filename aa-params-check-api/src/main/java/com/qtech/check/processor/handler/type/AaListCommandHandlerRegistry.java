package com.qtech.check.processor.handler.type;

import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.stereotype.Component;

import javax.annotation.Nullable;
import javax.annotation.PostConstruct;
import java.util.HashMap;
import java.util.Map;

/**
 * author :  gaozhilin
 * email  :  gaoolin@gmail.com
 * date   :  2024/05/28 11:36:48
 * desc   :
 */

@Component
public class AaListCommandHandlerRegistry implements ApplicationContextAware {

    private final Map<String, AaListCommandHandler<?>> commandHandlerMap = new HashMap<>();
    private ApplicationContext applicationContext;

    @Override
    public void setApplicationContext(@Nullable ApplicationContext applicationContext) throws BeansException {
        this.applicationContext = applicationContext;
    }

    @PostConstruct
    public void init() {
        String[] beanNamesForType = applicationContext.getBeanNamesForType(AaListCommandHandler.class);
        for (String beanName : beanNamesForType) {
            AaListCommandHandler<?> handler = applicationContext.getBean(beanName, AaListCommandHandler.class);
            commandHandlerMap.put(handler.getMessageType(), handler);
        }
    }

    public <T> AaListCommandHandler<T> getCommandHandlerForType(String commandType) {
        AaListCommandHandler<?> handler = commandHandlerMap.get(commandType);
        if (handler != null) {
            @SuppressWarnings("unchecked") // 添加抑制警告注解，因为前面进行了类型检查
            AaListCommandHandler<T> typedHandler = (AaListCommandHandler<T>) handler;
            return typedHandler;
        } else {
            throw new IllegalArgumentException("No handler found for type: " + commandType);
        }
    }

    public Map<String, AaListCommandHandler<?>> getCommandHandlerMap() {
        return commandHandlerMap;
    }

    public ApplicationContext getApplicationContext() {
        return applicationContext;
    }
}
