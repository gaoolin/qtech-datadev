package com.qtech.check.processor.handler.type.item;

import com.qtech.check.algorithm.model.ItemXyOffsetParser;
import com.qtech.check.pojo.AaListCommand;
import com.qtech.check.processor.handler.type.AaListCommandHandler;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * author :  gaozhilin
 * email  :  gaoolin@gmail.com
 * date   :  2024/09/26 14:12:35
 * desc   :  处理List名为OC_Check Save_OC 的命令
 */


public class OcCheckHandler extends AaListCommandHandler<AaListCommand> {
    private static final Logger logger = LoggerFactory.getLogger(OcCheckHandler.class);

    @Override
    public AaListCommand handle(String[] parts, String prefixCmd) {
        try {
            return ItemXyOffsetParser.apply(parts, prefixCmd);
        } catch (Exception e) {
            logger.error(">>>>> OcCheckHandler handle error: " + e.getMessage());
        }
        return null;
    }
}
