package com.qtech.service.controller.database;

import com.qtech.service.entity.database.ImAaGlueHeartBeat;
import com.qtech.service.entity.database.ImAaGlueLog;
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

    @PutMapping("/sparkJobInfo/{jobName}")
    public ApiResponse<Boolean> updateSparkJobInfo(@PathVariable String jobName, @RequestBody ImSparkJobInfo imSparkJobInfo) {
        imSparkJobInfo.setJobName(jobName);
        return ApiResponse.success("success", oracleService.updateSparkJobInfo(imSparkJobInfo));
    }

    @GetMapping("/sparkJobSql/{jobName}")
    public ApiResponse<String> getSparkJobSql(@PathVariable String jobName) {
        String sql = oracleService.getSparkJobSql(jobName);
        return ApiResponse.success("success", sql);
    }

    @PutMapping("/aa/glue/log")
    public ApiResponse<Boolean> addGlueLog(@RequestBody ImAaGlueLog log) {
        return ApiResponse.success("success", oracleService.addGlueLog(log));
    }

    @PutMapping("/aa/glue/heartBeat")
    public ApiResponse<Boolean> addGlueHeartBeat(@RequestBody ImAaGlueHeartBeat heartBeat) {
        return ApiResponse.success("success", oracleService.addGlueHeartBeat(heartBeat));
    }

}