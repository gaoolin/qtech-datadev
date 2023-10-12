package com.qtech.comparison.controller;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.qtech.comparison.service.IComparisonService;
import io.swagger.annotations.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

/**
 * author :  gaozhilin
 * email  :  gaoolin@gmail.com
 * date   :  2023/10/07 09:27:49
 * desc   :
 */

@Api(tags = "ECheck相关接口", value = "返回打线图比对结果", protocols = "HTTP")
@RestController
@RequestMapping(value = "/comparison/api")
public class ComparisonController {

    @Autowired
    IComparisonService comparisonService;


    @ApiOperation(value = "根据项目名称和盒子号获取结果", notes = "需传入项目名称和盒子编号两个参数", httpMethod = "GET", consumes = "application/json", produces = "application/json")
    @ApiResponses({
            @ApiResponse(code = 0, message = "qualified，描述：打线图OK"),
            @ApiResponse(code = 1, message = "lnxxxx，描述：打线图偏移"),
            @ApiResponse(code = 2, message = "lackWire，描述：打线图少线"),
            @ApiResponse(code = 3, message = "overWire，描述：打线图多线")
    })
    @GetMapping("/getRes/{programName}/{simId}")
    public String getComparisonResult(
            // @ApiParam(name = "项目名称", value = "例如：wb_comparison", defaultValue = "wb_comparison", required = true)
            @PathVariable("programName") String programName,
            // @ApiParam(name = "盒子编码", value = "例如：86xxxx", required = true)
            @PathVariable("simId") String simId) {

        return comparisonService.getComparisonResult(programName, simId);
    }


    @ApiOperation(value = "根据项目名称和盒子号更新结果", notes = "需传入项目名称和key-value键值对", httpMethod = "POST", consumes = "application/json", produces = "*/*", hidden = true)
    @PostMapping(value = "/updateRes")
    public Integer addComparisonResult(
            @ApiParam(name = "request", value = "更新Redis数据请求")
            @RequestBody String jsonObject) {
        boolean flag = true;
        JSONObject jsonRedisMap = JSON.parseObject(jsonObject);
        for (Map.Entry<String, Object> entry : jsonRedisMap.entrySet()) {
            String redisKey = entry.getKey();
            String redisVal = (String) entry.getValue();
            Integer i = comparisonService.addComparisonResult(redisKey, redisVal, 1);
            flag = flag && (i == 0);
        }
        return flag ? 0 : -1;
    }
}
