package com.qtech.reverse.controller;

import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

/**
 * author :  gaozhilin
 * email  :  gaoolin@gmail.com
 * date   :  2024/06/19 14:48:02
 * desc   :
 */

@RequestMapping("/im/chk/aa")
public class AaParamsChkResultController {

    @RequestMapping(value = "/result/{simId}", produces = "application/json", method = RequestMethod.GET)
    public String getResult(@PathVariable String simId) {
        return "result";
    }
}
