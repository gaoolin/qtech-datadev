package com.qtech.service.controller.chk;

import com.qtech.service.controller.BaseController;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

/**
 * author :  gaozhilin
 * email  :  gaoolin@gmail.com
 * date   :  2024/06/19 14:48:02
 * desc   :
 */

@RestController
@RequestMapping("/im/chk/aa")
public class AaParamsChkResultController extends BaseController {

    @RequestMapping(value = "/result/{simId}", produces = "application/json", method = RequestMethod.GET)
    public String getResult(@PathVariable String simId) {
        return "result";
    }
}
