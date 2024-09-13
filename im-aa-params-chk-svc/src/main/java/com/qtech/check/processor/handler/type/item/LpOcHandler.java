package com.qtech.check.processor.handler.type.item;

import com.qtech.check.algorithm.model.ItemDelayParser;
import com.qtech.check.pojo.AaListCommand;
import com.qtech.check.processor.handler.type.AaListCommandHandler;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

/**
 * author :  gaozhilin
 * email  :  gaoolin@gmail.com
 * date   :  2024/09/20 11:52:53
 * desc   :  这项目前没有管控要求，是光源板移动到产品上方 亮度14 延时500意思
 */

@Component
public class LpOcHandler extends AaListCommandHandler<AaListCommand> {
    private static final Logger logger = LoggerFactory.getLogger(LpOcHandler.class);

    @Override
    public AaListCommand handle(String[] parts, String prefixCommand) {
        try {
            return ItemDelayParser.apply(parts, prefixCommand);
        } catch (Exception e) {
            logger.error(">>>>> LpOcHandler handle error: " + e.getMessage());
        }
        return null;
    }
}
