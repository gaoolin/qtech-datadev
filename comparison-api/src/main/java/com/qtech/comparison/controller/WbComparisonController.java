package com.qtech.comparison.controller;

import com.qtech.comparison.service.IWbComparisonService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
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
@RequestMapping(value = "/comparison")
public class WbComparisonController {

    @Autowired
    IWbComparisonService wbComparisonService;

    @GetMapping("/{simId}")
    public String getComparisonResult(@PathVariable String simId) {

        return wbComparisonService.getComparisonResult(simId);
    }
}
