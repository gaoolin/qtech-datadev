package com.qtech.check.processor.handler.type.item;

import com.qtech.check.algorithm.model.ItemCcParser;
import com.qtech.check.pojo.AaListCommand;
import com.qtech.check.processor.handler.type.AaListCommandHandler;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * author :  gaozhilin
 * email  :  gaoolin@gmail.com
 * date   :  2024/05/28 11:19:56
 * desc   :  VcmRun 好像在采集上来的数据中并没有找到参数
 * <p>
 * VcmRun:相同的list名称
 * 1. Vcm_Hall
 * 2. Vcm_Hall2
 * # 3. Vcm_Check(除外，它的参数值是 Cc值)
 * 4. Vcm_Check_650
 * 5. Vcm_MoveAF
 * 6. MoveAF_Z_Check
 * 7. VCMPowerOffCheck
 */

// FIXME: 待完善
// @Component
public class VcmRunHandler extends AaListCommandHandler<AaListCommand> {
    private static final Logger logger = LoggerFactory.getLogger(VcmRunHandler.class);

    @Override
    public AaListCommand handle(String[] parts, String prefixCommand) {
        try {
            return ItemCcParser.apply(parts, prefixCommand);
        } catch (Exception e) {
            logger.error(">>>>> VcmRunHandler: Error parsing VcmRun command: " + e.getMessage());
        }
        return null;
    }
}