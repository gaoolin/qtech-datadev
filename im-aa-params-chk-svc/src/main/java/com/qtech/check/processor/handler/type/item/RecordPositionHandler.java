package com.qtech.check.processor.handler.type.item;

import com.qtech.check.algorithm.model.ItemRecordPositionParser;
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
 * <p>
 * 此命令在台虹和古城意义不同：
 * 在台虹厂区，仅仅是记录位置的命令，不包含上抬下拉
 * 在古城厂区，包含上抬下拉和记录位置两项参数
 * </p>
 */

@Component
public class RecordPositionHandler extends AaListCommandHandler<ImAaListCommand> {
    private static final Logger logger = LoggerFactory.getLogger(RecordPositionHandler.class);

    @Override
    public ImAaListCommand handle(String[] parts, String prefixCmd) {
        try {
            if (parts.length == 3) {
                return ItemRecordPositionParser.apply(parts, prefixCmd);
            } else {
                return ItemUtXyzMoveParser.apply(parts, prefixCmd);
            }
        } catch (Exception e) {
            logger.error(">>>>> {} handle error for parts: {}, prefixCommand: {}. Error: {}",
                    this.getClass().getName(), Arrays.toString(parts), prefixCmd, e.getMessage(), e);
        }
        return null;
    }
}