package com.qtech.check.processor.handler.type.item;

import com.qtech.check.algorithm.Range;
import com.qtech.check.pojo.AaListCommand;
import com.qtech.check.processor.handler.type.AaListCommandHandler;
import org.springframework.stereotype.Component;

/**
 * author :  gaozhilin
 * email  :  gaoolin@gmail.com
 * date   :  2024/05/28 11:19:56
 * desc   :  VcmCheck
 */

@Component
public class VcmCheckHandler extends AaListCommandHandler<AaListCommand> {

    @Override
    public AaListCommand handle(String[] parts) {
        String command = parts[2];
        String subsystem = parts[4];
        if ("RESULT".equals(command) && "Check".equals(subsystem)) {
            Integer num = Integer.parseInt(parts[1]);
            String max = parts[6];
            String min = parts[5];
            Range<String> vcmZRange = new Range<>(min, max);
            return new AaListCommand(null, num, command, subsystem, null, vcmZRange);
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
