package com.qtech.reverse.controller;

import com.qtech.reverse.entity.EquipmentReverseControlInfo;
import com.qtech.reverse.service.IEquipmentReverseControlInfoService;
import com.qtech.reverse.utils.ControlMode;
import com.qtech.reverse.utils.ModeControl;
import com.qtech.reverse.utils.R;
import org.springframework.beans.factory.annotation.Autowired;
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


@RestController
@RequestMapping(value = "/im/chk/api")
public class EquipmentReverseControlInfoController {

    @Autowired
    private IEquipmentReverseControlInfoService equipmentReverseControlInfoService;

    @RequestMapping(value = "/{simId}", produces = "application/json", method = RequestMethod.GET)
    public R getEquipmentReverseControlInfo(@PathVariable("simId") String simId) {
        ControlMode currentMode = ModeControl.controlMode;

        if (currentMode == ControlMode.ALWAYS_RETURN) {
            // 总是返回逻辑
            EquipmentReverseControlInfo info = equipmentReverseControlInfoService.selectEquipmentReverseControlInfoBySimId(simId);
            return info != null ? R.restResult(info) : R.restResult(null);
        } else if (currentMode == ControlMode.ALWAYS_NULL) {
            // 总是返回null
            return R.restResult(null);
        } else { // ControlMode.DEFAULT
            // 按照工作日时间控制
            LocalDateTime now = LocalDateTime.now();
            DayOfWeek dayOfWeek = now.toLocalDate().getDayOfWeek();
            LocalTime timeNow = now.toLocalTime();

            if ((DayOfWeek.MONDAY.getValue() <= dayOfWeek.getValue() && dayOfWeek.getValue() <= DayOfWeek.FRIDAY.getValue()) && timeNow.isAfter(LocalTime.of(8, 30)) && timeNow.isBefore(LocalTime.of(17, 0))) {
                EquipmentReverseControlInfo info = equipmentReverseControlInfoService.selectEquipmentReverseControlInfoBySimId(simId);
                return info != null ? R.restResult(info) : R.restResult(null);
            } else {
                return R.restResult(null); // 非工作时间返回null
            }
        }
    }

    // 控制模式变更API
    @PostMapping("/control-mode")
    public ResponseEntity<String> changeControlMode(@RequestParam("mode") String mode) {
        try {
            ControlMode newMode = ControlMode.valueOf(mode.toUpperCase());
            ModeControl.controlMode = newMode;
            return ResponseEntity.ok("Control mode updated to " + newMode);
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().body("Invalid control mode");
        }
    }
}
