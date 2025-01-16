package com.qtech.check.processor.handler.type.item;

import com.qtech.check.algorithm.model.ItemCcParser;
import com.qtech.check.processor.handler.type.AaListCommandHandler;
import com.qtech.share.aa.pojo.ImAaListCommand;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import java.util.Arrays;

/**
 * author :  gaozhilin
 * email  :  gaoolin@gmail.com
 * date   :  2024/09/26 14:38:02
 * desc   :  处理List为Save_MTF的命令
 */
@Component
public class SaveMtfHandler extends AaListCommandHandler<ImAaListCommand> {
    private static final Logger logger = LoggerFactory.getLogger(SaveMtfHandler.class);

    @Override
    public ImAaListCommand handle(String[] parts, String prefixCmd) {
        try {
            return ItemCcParser.apply(parts, prefixCmd);
        } catch (Exception e) {
            logger.error(">>>>> {} handle error for parts: {}, prefixCommand: {}. Error: {}", this.getClass().getName(), Arrays.toString(parts), prefixCmd, e.getMessage(), e);
        }
        return null;
    }
}