package com.qtech.check.processor.handler.type.item;

import com.qtech.check.pojo.AaListCommand;
import com.qtech.check.processor.handler.type.AaListCommandHandler;
import org.springframework.stereotype.Component;

/**
 * author :  gaozhilin
 * email  :  gaoolin@gmail.com
 * date   :  2024/05/28 11:18:51
 * desc   :  BackToPosition
 */

@Component
public class BackToPositionHandler extends AaListCommandHandler<AaListCommand> {

    @Override
    public AaListCommand handle(String[] parts) {
        Integer num = Integer.parseInt(parts[1]);
        String command = parts[2];
        String subSystem = parts[3];
        String val = parts[5];
        return new AaListCommand(null, num, command, subSystem, val);
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
