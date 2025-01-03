package com.qtech.service.controller.database;

import com.qtech.service.entity.database.ImSparkJobInfo;
import com.qtech.service.service.database.IOracleService;
import com.qtech.service.utils.response.ApiResponse;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;

/**
 * author :  gaozhilin
 * email  :  gaoolin@gmail.com
 * date   :  2025/01/03 14:30:19
 * desc   :
 */

@RestController
@RequestMapping("/im/db/oracle")
public class OracleController {

    @Resource
    private IOracleService oracleService;

    @GetMapping("/sparkJobInfo/{jobName}")
    public ApiResponse<ImSparkJobInfo> getSparkJobInfo(@PathVariable String jobName) {
        ImSparkJobInfo sparkJobInfo = oracleService.getSparkJobInfo(jobName);
        return ApiResponse.success("success", sparkJobInfo);
    }

    @PostMapping("/sparkJobInfo")
    public ApiResponse<Boolean> getSparkJobInfo(@RequestBody ImSparkJobInfo imSparkJobInfo) {
        boolean affected = oracleService.updateSparkJobInfo(imSparkJobInfo);
        return ApiResponse.success("success", affected);
    }
}
