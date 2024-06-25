package com.qtech.reverse.controller;

import com.qtech.reverse.entity.EquipmentReverseControlInfo;
import com.qtech.reverse.service.IEquipmentReverseControlInfoService;
import com.qtech.reverse.utils.HttpStatus;
import com.qtech.reverse.utils.R;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

/**
 * author :  gaozhilin
 * email  :  gaoolin@gmail.com
 * date   :  2024/06/21 08:23:36
 * desc   :
 */


@RestController
@RequestMapping(value = "/im/chk/api")
public class EquipmentReverseControlInfoController {

    @Autowired
    private IEquipmentReverseControlInfoService equipmentReverseControlInfoService;
    @RequestMapping(value = "/{simId}", produces = "application/json", method = RequestMethod.GET)
    public R getEquipmentReverseControlInfo(
            // @ApiParam(name = "项目名称", value = "例如：qtech_comparison", defaultValue = "qtech_comparison", required = true)
            // @ApiParam(name = "盒子编码", value = "例如：86xxxx", required = true)
            @PathVariable("simId") String simId) {

        EquipmentReverseControlInfo equipmentReverseControlInfo = equipmentReverseControlInfoService.selectEquipmentReverseControlInfoBySimId(simId);
        if (equipmentReverseControlInfo == null) {
            return R.restResult(null);
        }

        return R.restResult(equipmentReverseControlInfo);
    }
}
