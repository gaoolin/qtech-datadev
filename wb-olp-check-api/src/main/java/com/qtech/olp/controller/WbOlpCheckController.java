package com.qtech.olp.controller;

import com.qtech.olp.service.IWbOlpCheckService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * author :  gaozhilin
 * email  :  gaoolin@gmail.com
 * date   :  2023/10/07 09:27:49
 * desc   :
 */

@RestController
@RequestMapping(value = "/comparison/api")
public class WbOlpCheckController {

    @Autowired
    private IWbOlpCheckService wbOlpCheckService;

    @GetMapping("/getRes/{programName}/{simId}")
    public String getOlpCheckResult(
            // @ApiParam(name = "项目名称", value = "例如：wb_comparison", defaultValue = "wb_comparison", required = true)
            @PathVariable("programName") String programName,
            // @ApiParam(name = "盒子编码", value = "例如：86xxxx", required = true)
            @PathVariable("simId") String simId) {

        return wbOlpCheckService.getOlpCheckResult(simId);
    }
}
