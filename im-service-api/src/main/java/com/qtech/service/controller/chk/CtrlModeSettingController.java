package com.qtech.service.controller.chk;

import com.qtech.service.controller.BaseController;
import com.qtech.service.utils.chk.ControlMode;
import com.qtech.service.utils.chk.ControlModeFlag;
import com.qtech.service.utils.response.ApiResponse;
import org.springframework.web.bind.annotation.*;

/**
 * author :  gaozhilin
 * email  :  gaoolin@gmail.com
 * date   :  2024/06/21 08:23:36
 * desc   :
 */


/**
 * 控制设备反向控制信息的控制器。
 */
@RestController
@RequestMapping(value = "/im/control-mode")
public class CtrlModeSettingController extends BaseController {

    /**
     * 更改控制模式。
     *
     * @param mode 新的控制模式字符串
     * @return 成功或错误的消息响应
     */
    @PostMapping
    public ApiResponse<String> changeControlMode(@RequestParam("mode") String mode) {
        try {
            ControlMode newMode = ControlMode.valueOf(mode.toUpperCase());
            ControlModeFlag.controlMode = newMode;
            return ApiResponse.success("Control mode updated to " + newMode, null);
        } catch (IllegalArgumentException e) {
            return ApiResponse.badRequest("Invalid control mode");
        }
    }

    @GetMapping
    public ApiResponse<String> getControlMode() {
        return ApiResponse.success("Current control mode is: " + ControlModeFlag.controlMode, null);
    }
}