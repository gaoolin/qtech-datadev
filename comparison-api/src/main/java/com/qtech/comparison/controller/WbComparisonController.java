package com.qtech.comparison.controller;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.qtech.comparison.service.IWbComparisonService;
import io.swagger.annotations.Api;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

/**
 * author :  gaozhilin
 * email  :  gaoolin@gmail.com
 * date   :  2023/10/07 09:27:49
 * desc   :
 */

@Api(tags = "PulsarProducerController", description = "Comparison功能测试")
@RestController
@RequestMapping(value = "/comparison")
public class WbComparisonController {

    @Autowired
    IWbComparisonService wbComparisonService;

    @GetMapping("/getRedisRes/{simId}")
    public String getComparisonResult(@PathVariable String simId) {

        return wbComparisonService.getComparisonResult(simId);
    }

    @PostMapping(value = "/updateRedisRes")
    public Integer addComparisonResult(@RequestBody String jsonObject) {
        boolean flag = true;
        JSONObject jsonRedisMap = JSON.parseObject(jsonObject);
        for (Map.Entry<String, Object> entry : jsonRedisMap.entrySet()) {
            String redisKey = entry.getKey();
            String redisVal = (String) entry.getValue();
            Integer i = wbComparisonService.addComparisonResult(redisKey, redisVal, 5);
            flag = flag && (i == 0);
        }
        return flag ? 0 : -1;
    }

    @GetMapping("/getJobStat")
    public String getComparisonJobStatus() {
        return wbComparisonService.getComparisonJobStatus();
    }

    @PostMapping(value = "/updateRedisStat")
    public Integer updateJobStatus(@RequestBody String jsonObject) {
        boolean flag;
        JSONObject jsonStat = JSON.parseObject(jsonObject);
        String status = jsonStat.getString("status");
        Integer i = wbComparisonService.updateJobStatus("status", status);
        flag = i == 0;
        return flag ? 0 : -1;
    }
}
