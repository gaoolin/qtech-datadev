package com.qtech.service.controller.chk;

import com.qtech.service.controller.BaseController;
import com.qtech.service.utils.chk.ControlMode;
import com.qtech.service.utils.chk.ControlModeFlag;
import com.qtech.service.utils.response.ApiResponse;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiOperation;
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
// 单个接口允许跨域：
// @CrossOrigin(origins = "http://localhost:8080")
@ApiModel(value = "控制模式设置控制器", description = "控制模式设置控制器")
@RestController
@RequestMapping(value = "/im/aa/control-mode")
public class CtrlModeSettingController extends BaseController {

    /**
     * 更改控制模式。
     *
     * @param mode 新的控制模式字符串
     * @return 成功或错误的消息响应
     */
    @ApiOperation(value = "更改控制模式", notes = "更改控制模式")
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

    /**
     * 获取当前控制模式。
     *
     * @return 当前控制模式的消息响应
     */
    @ApiOperation(value = "获取当前控制模式", notes = "获取当前控制模式")
    @GetMapping("/current")
    public ApiResponse<String> getControlMode() {
        return ApiResponse.success("Current control mode is: " + ControlModeFlag.controlMode, ControlModeFlag.controlMode.name());
    }

    /**
     * 获取控制模式的列表。
     *
     * @return 控制模式的列表的消息响应
     */
    @ApiOperation(value = "获取控制模式的列表", notes = "获取控制模式的列表")
    @GetMapping("/list")
    public ApiResponse<ControlMode[]> getControlModeList() {
        return ApiResponse.success("Current control mode list is: " + ControlModeFlag.controlMode, ControlMode.values());
    }
}