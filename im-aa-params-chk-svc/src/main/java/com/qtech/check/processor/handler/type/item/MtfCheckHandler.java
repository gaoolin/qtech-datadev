package com.qtech.check.processor.handler.type.item;

import com.qtech.check.algorithm.model.ItemCcParser;
import com.qtech.check.pojo.AaListCommand;
import com.qtech.check.processor.handler.type.AaListCommandHandler;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

/**
 * author :  gaozhilin
 * email  :  gaoolin@gmail.com
 * date   :  2024/05/28 11:18:51
 * desc   :  MtfCheck
 */

@Component
public class MtfCheckHandler extends AaListCommandHandler<AaListCommand> {
    private static final Logger logger = LoggerFactory.getLogger(MtfCheckHandler.class);

    @Override
    public AaListCommand handle(String[] parts, String prefixCommand) {
        try {
            return ItemCcParser.apply(parts, prefixCommand);
        } catch (Exception e) {
            logger.error(">>>>> MtfCheckHandler handle error: " + e.getMessage());
        }
        return null;
    }
}
