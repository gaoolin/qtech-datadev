package com.qtech.check.processor.handler.type.item;

import com.qtech.check.algorithm.model.ItemUtXyzMoveParser;
import com.qtech.check.pojo.AaListCommand;
import com.qtech.check.processor.handler.type.AaListCommandHandler;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

/**
 * author :  gaozhilin
 * email  :  gaoolin@gmail.com
 * date   :  2024/05/28 11:23:36
 * desc   :  ZOffset，vcm马达上台下拉，固定值
 */

@Component
public class RecordPositionHandler extends AaListCommandHandler<AaListCommand> {
    private static final Logger logger = LoggerFactory.getLogger(RecordPositionHandler.class);

    @Override
    public AaListCommand handle(String[] parts, String prefixCommand) {
        try {
            return ItemUtXyzMoveParser.apply(parts, prefixCommand);
        } catch (Exception e) {
            logger.error(">>>>> RecordPositionHandler handle error: " + e.getMessage());
        }
        return null;
    }
}
