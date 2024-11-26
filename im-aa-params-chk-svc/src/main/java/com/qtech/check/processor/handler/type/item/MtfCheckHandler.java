package com.qtech.check.processor.handler.type.item;

import com.qtech.check.algorithm.model.ItemCcParser;
import com.qtech.check.algorithm.model.ItemLensFieldOfViewTestParser;
import com.qtech.check.processor.handler.type.AaListCommandHandler;
import com.qtech.share.aa.pojo.ImAaListCommand;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import java.util.Arrays;

/**
 * author :  gaozhilin
 * email  :  gaoolin@gmail.com
 * date   :  2024/05/28 11:18:51
 * desc   :  MtfCheck
 * <p>
 * mtfCheck命令只可能作为单独的命令，不能作为其他命令的子命令
 */

@Component
public class MtfCheckHandler extends AaListCommandHandler<ImAaListCommand> {
    private static final Logger logger = LoggerFactory.getLogger(MtfCheckHandler.class);

    @Override
    public ImAaListCommand handle(String[] parts, String prefixCmd) {
        try {
            return ItemLensFieldOfViewTestParser.apply(parts, prefixCmd);
        } catch (Exception e) {
            logger.error(">>>>> {} handle error for parts: {}, prefixCommand: {}. Error: {}",
                    this.getClass().getName(), Arrays.toString(parts), prefixCmd, e.getMessage(), e);
        }
        return null;
    }
}
