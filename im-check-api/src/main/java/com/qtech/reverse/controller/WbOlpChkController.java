package com.qtech.reverse.controller;

import com.qtech.reverse.service.IWbOlpChkService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * author :  gaozhilin
 * email  :  gaoolin@gmail.com
 * date   :  2024/06/13 15:58:09
 * desc   :
 */

@RestController
@RequestMapping(value = "/im/chk/api")
public class WbOlpChkController {
    @Autowired
    private IWbOlpChkService wbOlpChkService;

    @GetMapping("/wb/{simId}")
    public String getOlpCheckResult(
            // @ApiParam(name = "项目名称", value = "例如：wb_comparison", defaultValue = "wb_comparison", required = true)
            // @ApiParam(name = "盒子编码", value = "例如：86xxxx", required = true)
            @PathVariable("simId") String simId) {

        return wbOlpChkService.getOlpCheckResult(simId);
    }
}
