package com.qtech.service.controller.chk;

import com.qtech.service.controller.BaseController;
import com.qtech.service.entity.EqReverseCtrlInfo;
import com.qtech.service.service.chk.IEquipmentReverseControlInfoService;
import com.qtech.service.utils.chk.ControlMode;
import com.qtech.service.utils.chk.ModeFlag;
import com.qtech.service.utils.response.R;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.DayOfWeek;
import java.time.LocalDateTime;
import java.time.LocalTime;

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
@RequestMapping(value = "/im/aa/ctrl")
public class EquipmentReverseControlInfoController extends BaseController {

    private final IEquipmentReverseControlInfoService equipmentReverseControlInfoService;

    public EquipmentReverseControlInfoController(IEquipmentReverseControlInfoService equipmentReverseControlInfoService) {
        this.equipmentReverseControlInfoService = equipmentReverseControlInfoService;
    }

    /**
     * 根据SIM ID获取设备反向控制信息。
     * @param simId 设备的SIM ID
     * @return 包含设备控制信息的结果对象，或特定控制模式下的默认响应
     */
    @GetMapping("/{simId}")
    public R getEquipmentReverseControlInfo(@PathVariable("simId") String simId) {
        ControlMode currentMode = ModeFlag.controlMode;

        switch (currentMode) {
            case ALWAYS_RETURN:
                EqReverseCtrlInfo info = equipmentReverseControlInfoService.selectEquipmentReverseControlInfoBySimId(simId);
                // return info != null ? R.restResult(info) : R.restResult(null);
            case ALWAYS_NULL:
                // return R.restResult(null);
            default: // 默认，按照工作日时间控制
                LocalDateTime now = LocalDateTime.now();
                if (isWithinWorkingHours(now)) {
                    info = equipmentReverseControlInfoService.selectEquipmentReverseControlInfoBySimId(simId);
                    // return info != null ? R.restResult(info) : R.restResult(null);
                } else {
                    // return R.restResult(null); // 非工作时间返回null
                }
        }
        return null;
    }

    /**
     * 判断当前时间是否在工作时间内。
     * @param dateTime 当前日期时间
     * @return 是否在工作时间内
     */
    private boolean isWithinWorkingHours(LocalDateTime dateTime) {
        DayOfWeek dayOfWeek = dateTime.toLocalDate().getDayOfWeek();
        LocalTime timeNow = dateTime.toLocalTime();
        return (DayOfWeek.MONDAY.getValue() <= dayOfWeek.getValue() && dayOfWeek.getValue() <= DayOfWeek.FRIDAY.getValue())
                && timeNow.isAfter(LocalTime.of(8, 30)) && timeNow.isBefore(LocalTime.of(17, 0));
    }

    /**
     * 更改控制模式。
     * @param mode 新的控制模式字符串
     * @return 成功或错误的消息响应
     */
    @PostMapping("/control-mode")
    public ResponseEntity<String> changeControlMode(@RequestParam("mode") String mode) {
        try {
            ControlMode newMode = ControlMode.valueOf(mode.toUpperCase());
            ModeFlag.controlMode = newMode;
            return ResponseEntity.ok("Control mode updated to " + newMode);
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().body("Invalid control mode");
        }
    }
}