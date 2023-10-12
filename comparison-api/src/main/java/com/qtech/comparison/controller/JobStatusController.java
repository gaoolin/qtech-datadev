package com.qtech.comparison.controller;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.qtech.comparison.service.IJobStatusService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import springfox.documentation.annotations.ApiIgnore;

import java.util.Map;

/**
 * author :  gaozhilin
 * email  :  gaoolin@gmail.com
 * date   :  2023/10/11 09:37:53
 * desc   :
 */

@ApiIgnore
@RestController
@RequestMapping(value = "/job/api")
public class JobStatusController {

    @Autowired
    IJobStatusService jobStatusService;

    @GetMapping("/getJobRunDt/{programName}")
    public String getJobRunDt(@PathVariable String programName) {

        String jobRunDt = jobStatusService.getJobRunDt(programName);
        System.out.println(jobRunDt);
        return jobRunDt;
    }

    @PostMapping("/updateJobRunDt")
    public Integer updateJobRunDt(@RequestBody String jsonObject) {
        boolean flag = true;
        JSONObject jsonRedisMap = JSON.parseObject(jsonObject);
        for (Map.Entry<String, Object> entry : jsonRedisMap.entrySet()) {
            String redisKey = entry.getKey();
            String redisVal = (String) entry.getValue();
            Integer i = jobStatusService.updateJobRunDt(redisKey, redisVal);
            flag = flag && (i == 0);
        }
        return flag ? 0 : -1;
    }


    @GetMapping("/getJobStat/{programName}")
    public String getJobStat(@PathVariable String programName) {
        return jobStatusService.getJobRunStat(programName);
    }

    @PostMapping(value = "/updateJobStat")
    public Integer updateJobStat(@RequestBody String jsonObject) {
        boolean flag;
        JSONObject jsonStat = JSON.parseObject(jsonObject);
        String status = jsonStat.getString("status");
        Integer i = jobStatusService.updateJobRunStat("status", status);
        flag = i == 0;
        return flag ? 0 : -1;
    }
}
