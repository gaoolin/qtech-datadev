package com.qtech.check.processor.handler.type.item;

import com.qtech.check.pojo.AaListCommand;
import com.qtech.check.processor.handler.type.AaListCommandHandler;
import org.springframework.stereotype.Component;

/**
 * author :  gaozhilin
 * email  :  gaoolin@gmail.com
 * date   :  2024/05/28 11:21:44
 * desc   :  EpoxyInspectionAuto
 */

@Component
public class EpoxyInspectionAutoHandler extends AaListCommandHandler<AaListCommand> {
    @Override
    public AaListCommand handle(String[] parts) {
        String command = parts[2];
        if ("EpoxyInspection".equals(command)) {
            Integer num = Integer.parseInt(parts[1]);
            String val = parts[3];
            return new AaListCommand(null, num, command, null, val, null);
        }
        return null;
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
