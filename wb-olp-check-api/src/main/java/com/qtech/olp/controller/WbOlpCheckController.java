package com.qtech.olp.controller;

import com.qtech.olp.service.IWbOlpCheckService;
import io.swagger.annotations.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * author :  gaozhilin
 * email  :  gaoolin@gmail.com
 * date   :  2023/10/07 09:27:49
 * desc   :
 */

@Api(tags = "ECheck相关接口", value = "返回打线图比对结果", protocols = "HTTP")
@RestController
@RequestMapping(value = "/comparison/api")
public class WbOlpCheckController {

    @Autowired
    private IWbOlpCheckService wbOlpCheckService;

    @ApiOperation(value = "根据项目名称和盒子号获取结果", notes = "需传入项目名称和盒子编号两个参数", httpMethod = "GET", consumes = "application/json", produces = "application/json")
    @ApiResponses({
            @ApiResponse(code = 0, message = "qualified，描述：打线图OK"),
            @ApiResponse(code = 1, message = "lnxxxx，描述：打线图偏移"),
            @ApiResponse(code = 2, message = "lackWire，描述：打线图少线"),
            @ApiResponse(code = 3, message = "overWire，描述：打线图多线")
    })
    @GetMapping("/getRes/{programName}/{simId}")
    public String getOlpCheckResult(
            // @ApiParam(name = "项目名称", value = "例如：wb_comparison", defaultValue = "wb_comparison", required = true)
            @PathVariable("programName") String programName,
            // @ApiParam(name = "盒子编码", value = "例如：86xxxx", required = true)
            @PathVariable("simId") String simId) {

        return wbOlpCheckService.getOlpCheckResult(simId);
    }
}
