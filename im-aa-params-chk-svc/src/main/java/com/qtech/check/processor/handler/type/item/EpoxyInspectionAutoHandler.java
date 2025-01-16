package com.qtech.check.processor.handler.type.item;

import com.qtech.check.algorithm.model.ItemEpoxyInspectionParser;
import com.qtech.check.processor.handler.type.AaListCommandHandler;
import com.qtech.share.aa.pojo.ImAaListCommand;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import java.util.Arrays;

/**
 * author :  gaozhilin
 * email  :  gaoolin@gmail.com
 * date   :  2024/05/28 11:21:44
 * desc   :  EpoxyInspectionAuto
 */
@Component
public class EpoxyInspectionAutoHandler extends AaListCommandHandler<ImAaListCommand> {
    private static final Logger logger = LoggerFactory.getLogger(EpoxyInspectionAutoHandler.class);

    @Override
    public ImAaListCommand handle(String[] parts, String prefixCmd) {
        try {
            return ItemEpoxyInspectionParser.apply(parts, prefixCmd);
        } catch (Exception e) {
            logger.error(">>>>> {} handle error for parts: {}, prefixCommand: {}. Error: {}",
                    this.getClass().getName(), Arrays.toString(parts), prefixCmd, e.getMessage(), e);
        }
        return null;
    }
}