package com.qtech.service.controller.chk;

import com.qtech.service.controller.BaseController;
import com.qtech.service.entity.EqReverseCtrlInfo;
import com.qtech.service.service.chk.IEqReverseCtrlService;
import com.qtech.service.utils.response.R;
import com.qtech.service.utils.response.ResponseCode;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import java.net.URLDecoder;
import java.nio.charset.StandardCharsets;

import static com.qtech.service.utils.chk.ControlModeResponseHandler.handleResponse;

/**
 * author :  gaozhilin
 * email  :  gaoolin@gmail.com
 * date   :  2024/08/13 17:24:23
 * desc   :
 */

@RestController
@RequestMapping("/im/chk")
@ApiOperation(value = "智能制造参数点检反控接口")
public class EqReverseCtrlInfoController extends BaseController {
    private static final Logger logger = LoggerFactory.getLogger(EqReverseCtrlInfoController.class);
    private final IEqReverseCtrlService eqReverseCtrlService;

    @Autowired
    public EqReverseCtrlInfoController(IEqReverseCtrlService eqReverseCtrlService) {
        this.eqReverseCtrlService = eqReverseCtrlService;
    }

    @GetMapping("/{simId}")
    public R<String> getEqReverseCtrlInfo(@ApiParam(name = "盒子号", value = "例如：86xxxx", required = true)
                                          @PathVariable String simId, HttpServletRequest request) {
        // 解码请求参数
        try {
            // 尝试对simId进行URL解码
            String decodedSimId = URLDecoder.decode(simId, StandardCharsets.UTF_8.name());
            if (!decodedSimId.matches("^86\\d+$")) {
                logger.warn("Invalid SIM ID format: {}", decodedSimId);
                return new R<String>().setCode(ResponseCode.BAD_REQUEST.getCode()).setMsg("Invalid SIM ID format, {}").setData(null);
            }
        } catch (Exception e) {
            // 解码失败，视为无效
            logger.warn("Decode simId failed: {}", e.getMessage());
            return new R<String>().setCode(ResponseCode.BAD_REQUEST.getCode()).setMsg("Decode simId failed").setData(null);
        }

        // 打印原始请求路径
        String requestPath = request.getRequestURI();
        logger.info(">>>>> Received request for path: {}", requestPath);
        EqReverseCtrlInfo eqReverseCtrlInfo = eqReverseCtrlService.selectEqReverseCtrlInfoBySimId(simId);
        if (eqReverseCtrlInfo == null) {
            return new R<String>().setCode(ResponseCode.SUCCESS.getCode()).setMsg("No data found").setData(null);
        }
        return handleResponse(eqReverseCtrlInfo);
    }
}