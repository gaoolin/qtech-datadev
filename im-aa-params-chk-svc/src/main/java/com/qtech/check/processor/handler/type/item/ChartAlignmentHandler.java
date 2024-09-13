package com.qtech.check.processor.handler.type.item;

import com.qtech.check.algorithm.model.ItemXyResParser;
import com.qtech.check.pojo.AaListCommand;
import com.qtech.check.processor.handler.type.AaListCommandHandler;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

/**
 * author :  gaozhilin
 * email  :  gaoolin@gmail.com
 * date   :  2024/05/28 11:20:49
 * desc   :  ChartAlignment
 */

@Component
public class ChartAlignmentHandler extends AaListCommandHandler<AaListCommand> {
    private static final Logger logger = LoggerFactory.getLogger(ChartAlignmentHandler.class);

    @Override
    public AaListCommand handle(String[] parts, String prefixCommand) {
        try {
            return ItemXyResParser.apply(parts, prefixCommand);
        } catch (Exception e) {
            logger.error(">>>>> ChartAlignmentHandler handle error: " + e.getMessage());
        }
        return null;
    }
}