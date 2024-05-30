package com.qtech.check.processor.handler.type.list;

import com.qtech.check.pojo.AaListCommand;
import com.qtech.check.processor.handler.type.AaListCommandHandler;
import org.springframework.stereotype.Component;

/**
 * author :  gaozhilin
 * email  :  gaoolin@gmail.com
 * date   :  2024/05/28 11:25:27
 * desc   :  List解析项
 */

@Component
public class ListHandler extends AaListCommandHandler<AaListCommand> {

    @Override
    public AaListCommand handle(String[] parts) {
        String command = parts[2];
        String enable = parts[parts.length - 1];
        return new AaListCommand(null, null, command, null, enable);
    }

    @Override
    public <R> R handleByType(Class<R> clazz, String msg) {
        if (AaListCommand.class.equals(clazz)) {
            String[] parts = msg.split("\\s+");
            return clazz.cast(handle(parts));
        }
        return null;
    }

    @Override
    public <U> boolean supportsType(Class<U> clazz) {
        return AaListCommand.class.equals(clazz);
    }
}