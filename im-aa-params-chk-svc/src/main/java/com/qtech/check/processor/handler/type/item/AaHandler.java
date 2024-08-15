package com.qtech.check.processor.handler.type.item;

import com.qtech.check.pojo.AaListCommand;
import com.qtech.check.processor.handler.type.AaListCommandHandler;
import org.springframework.stereotype.Component;

/**
 * author :  gaozhilin
 * email  :  gaoolin@gmail.com
 * date   :  2024/05/28 10:57:30
 * desc   :  AA Item
 */

@Component
public class AaHandler extends AaListCommandHandler<AaListCommand> {
    @Override
    public AaListCommand handle(String[] parts) {
        String command = parts[2];
        String subSystem = parts[3];
        if ("ROI".equals(command) && ("CC".equals(subSystem) || "UL".equals(subSystem) || "UR".equals(subSystem) || "LL".equals(subSystem) || "LR".equals(subSystem))) {
            Integer num = Integer.parseInt(parts[1]);
            String val = parts[6];
            return new AaListCommand(null, num, command, subSystem, val, null);
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
