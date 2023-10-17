package com.qtech.comparison.controller;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.qtech.comparison.service.IJobStatusService;
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

    @GetMapping("/getJobRunDt/{jobName}")
    public String getJobRunDt(@PathVariable String jobName) {

        String jobRunDt = jobStatusService.getJobRunDt(jobName);
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


    @GetMapping("/getJobStat/{jobName}")
    public String getJobStat(@PathVariable String jobName) {
        return jobStatusService.getJobRunStat(jobName);
    }

    @PostMapping(value = "/updateJobStat")
    public Integer updateJobStat(@RequestBody String jsonObject) {
        boolean flag = true;
        JSONObject jsonStat = JSON.parseObject(jsonObject);

        if (jsonStat.size() == 1) {
            for (Map.Entry<String, Object> entry : jsonStat.entrySet()) {
                String jobName = entry.getKey();
                String status = (String) entry.getValue();
                Integer i = jobStatusService.updateJobRunStat(jobName, status);
                flag = i == 0;
            }
        }
        return flag ? 0 : -1;
    }
}
