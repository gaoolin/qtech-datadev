package com.qtech.check.processor.handler.type.item;

import com.qtech.check.algorithm.model.ItemAaParser;
import com.qtech.check.pojo.AaListCommand;
import com.qtech.check.processor.handler.type.AaListCommandHandler;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

/**
 * author :  gaozhilin
 * email  :  gaoolin@gmail.com
 * date   :  2024/05/28 10:57:30
 * desc   :  AA Item
 */

@Component
public class AaHandler extends AaListCommandHandler<AaListCommand> {
    private static final Logger logger = LoggerFactory.getLogger(AaHandler.class);

    @Override
    public AaListCommand handle(String[] parts, String prefixCommand) {
        try {
            return ItemAaParser.apply(parts, prefixCommand);
        } catch (Exception e) {
            logger.error(">>>>> AaHandler handle error: " + e.getMessage());
        }
        return null;
    }
}
