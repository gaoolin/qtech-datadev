package com.qtech.check.processor.handler.type.item;

import com.qtech.check.algorithm.model.ItemCcParser;
import com.qtech.check.pojo.AaListCommand;
import com.qtech.check.processor.handler.type.AaListCommandHandler;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * author :  gaozhilin
 * email  :  gaoolin@gmail.com
 * date   :  2024/09/26 14:38:02
 * desc   :  处理List为Save_MTF的命令
 */
public class SaveMtfHandler extends AaListCommandHandler<AaListCommand> {
    private static final Logger logger = LoggerFactory.getLogger(SaveMtfHandler.class);

    @Override
    public AaListCommand handle(String[] parts, String prefixCmd) {
        try {
            return ItemCcParser.apply(parts, prefixCmd);
        } catch (Exception e) {
            logger.error(">>>>> SaveMtfHandler handle error: " + e.getMessage());
        }
        return null;
    }
}