package com.qtech.check.processor.handler.type.list;

import com.qtech.check.pojo.AaListCommand;
import com.qtech.check.processor.handler.type.AaListCommandHandler;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

/**
 * author :  gaozhilin
 * email  :  gaoolin@gmail.com
 * date   :  2024/05/28 11:25:27
 * desc   :  List解析项
 */

@Component
public class ListHandler extends AaListCommandHandler<AaListCommand> {
    private static final Logger logger = LoggerFactory.getLogger(ListHandler.class);

    @Override
    public AaListCommand handle(String[] parts, String prefixCmd) {
        return null;
    }

    @Override
    public AaListCommand handle(String[] parts) {
        String command = parts[2];
        String enable = parts[parts.length - 1];
        logger.info(">>>>> ListHandler: Command: {}, status: {}", command, enable);
        return new AaListCommand(null, null, null, command, null, enable, null);
    }
}