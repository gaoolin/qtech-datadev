package com.qtech.check.processor.handler.type.item;

import com.qtech.share.aa.pojo.ImAaListCommand;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * author :  gaozhilin
 * email  :  gaoolin@gmail.com
 * date   :  2024/09/26 14:58:19
 * desc   :  Save Oc 和 Oc Check是同一个东西
 */


public class SaveOcHandler extends OcCheckHandler {
    private static final Logger logger = LoggerFactory.getLogger(SaveOcHandler.class);

    @Override
    public ImAaListCommand handle(String[] parts, String prefixCommand) {
        return super.handle(parts, prefixCommand);
    }
}