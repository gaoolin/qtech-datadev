package com.qtech.check.processor.handler.type.item;

import com.qtech.check.algorithm.model.ItemUtXyzMoveParser;
import com.qtech.check.processor.handler.type.AaListCommandHandler;
import com.qtech.share.aa.pojo.ImAaListCommand;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import java.util.Arrays;

/**
 * author :  gaozhilin
 * email  :  gaoolin@gmail.com
 * date   :  2024/05/28 11:23:36
 * desc   :  ZOffset，vcm马达上台下拉，固定值
 */

@Component
public class RecordPositionHandler extends AaListCommandHandler<ImAaListCommand> {
    private static final Logger logger = LoggerFactory.getLogger(RecordPositionHandler.class);

    @Override
    public ImAaListCommand handle(String[] parts, String prefixCmd) {
        try {
            return ItemUtXyzMoveParser.apply(parts, prefixCmd);
        } catch (Exception e) {
            logger.error(">>>>> {} handle error for parts: {}, prefixCommand: {}. Error: {}",
                    this.getClass().getName(), Arrays.toString(parts), prefixCmd, e.getMessage(), e);
        }
        return null;
    }
}
