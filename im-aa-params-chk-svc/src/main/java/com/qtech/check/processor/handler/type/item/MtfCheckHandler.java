package com.qtech.check.processor.handler.type.item;

import com.qtech.check.pojo.AaListCommand;
import com.qtech.check.processor.handler.type.AaListCommandHandler;
import org.springframework.stereotype.Component;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * author :  gaozhilin
 * email  :  gaoolin@gmail.com
 * date   :  2024/05/28 11:18:51
 * desc   :  MtfCheck
 */

@Component
public class MtfCheckHandler extends AaListCommandHandler<AaListCommand> {

    @Override
    public AaListCommand handle(String[] parts) {
        String command = parts[2];
        if ("RESULT".equals(command)) {
            Integer num = Integer.parseInt(parts[1]);

            String subSystem = null;
            Pattern pattern = Pattern.compile("\\[(\\d+)\\]");
            Matcher matcher = pattern.matcher(parts[3]);
            if (matcher.find()) {
                subSystem = matcher.group(1); // Return the first capturing group (the number)
            } else {
                throw new IllegalArgumentException("MtfCheckHandler string does not match the expected pattern");
            }
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
