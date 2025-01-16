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
 * date   :  2025/01/15 15:02:52
 * desc   :
 */

@Component
public class UtXyzMoveHandler extends AaListCommandHandler<ImAaListCommand> {
    private static final Logger logger = LoggerFactory.getLogger(UtXyzMoveHandler.class);

    /**
     * 处理命令。
     *
     * @param parts     命令的部分
     * @param prefixCmd 前缀命令（可选）
     * @return 处理结果
     */
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