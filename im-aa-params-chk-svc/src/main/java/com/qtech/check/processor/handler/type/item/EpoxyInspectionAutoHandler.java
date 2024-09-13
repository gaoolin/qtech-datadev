package com.qtech.check.processor.handler.type.item;

import com.qtech.check.algorithm.model.ItemEpoxyInspectionParser;
import com.qtech.check.pojo.AaListCommand;
import com.qtech.check.processor.handler.type.AaListCommandHandler;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

/**
 * author :  gaozhilin
 * email  :  gaoolin@gmail.com
 * date   :  2024/05/28 11:21:44
 * desc   :  EpoxyInspectionAuto
 */
@Component
public class EpoxyInspectionAutoHandler extends AaListCommandHandler<AaListCommand> {
    private static final Logger logger = LoggerFactory.getLogger(EpoxyInspectionAutoHandler.class);

    @Override
    public AaListCommand handle(String[] parts, String prefixCommand) {
        try {
            return ItemEpoxyInspectionParser.apply(parts, prefixCommand);
        } catch (Exception e) {
            logger.error(">>>>> EpoxyInspectionAutoHandler: Error: {}", e.getMessage());
        }
        return null;
    }
}
