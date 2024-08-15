package com.qtech.service.controller.chk;

import com.qtech.service.controller.BaseController;
import com.qtech.service.entity.EqReverseCtrlInfo;
import com.qtech.service.service.chk.IEqReverseCtrlService;
import com.qtech.service.utils.chk.SimIdValidator;
import com.qtech.service.utils.response.R;
import com.qtech.service.utils.response.ResponseCode;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import static com.qtech.service.utils.chk.ControlModeResponseHandler.handleResponse;

/**
 * author :  gaozhilin
 * email  :  gaoolin@gmail.com
 * date   :  2024/08/13 17:24:23
 * desc   :
 */

@Slf4j
@RestController
@RequestMapping("/im/chk")
@ApiOperation(value = "智能制造参数点检反控接口")
public class EqReverseCtrlInfoController extends BaseController {

    private final IEqReverseCtrlService eqReverseCtrlService;

    @Autowired
    public EqReverseCtrlInfoController(IEqReverseCtrlService eqReverseCtrlService) {
        this.eqReverseCtrlService = eqReverseCtrlService;
    }

    @GetMapping("/{simId}")
    public R<String> getEqReverseCtrlInfo(@ApiParam(name = "盒子号", value = "例如：86xxxx", required = true) @PathVariable String simId) {
        if (!SimIdValidator.validateSimId(simId)) {
            return new R<String>().setCode(ResponseCode.SUCCESS.getCode()).setMsg("Invalid simId format").setData(null);
        }
        EqReverseCtrlInfo eqReverseCtrlInfo = eqReverseCtrlService.selectEqReverseCtrlInfoBySimId(simId);
        if (eqReverseCtrlInfo == null) {
            return new R<String>().setCode(ResponseCode.SUCCESS.getCode()).setMsg("No data found").setData(null);
        }
        return handleResponse(eqReverseCtrlInfo);
    }
}