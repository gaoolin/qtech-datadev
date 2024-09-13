package com.qtech.service.controller.chk;

import com.qtech.service.common.QtechValidSimId;
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
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;

import static com.qtech.service.common.Constants.EQ_REVERSE_CTRL_INFO_REDIS_KEY_PREFIX;
import static com.qtech.service.utils.chk.ControlModeResponseHandler.handleResponse;

/**
 * author :  gaozhilin
 * email  :  gaoolin@gmail.com
 * date   :  2024/08/13 17:24:23
 * desc   :
 */
@RestController
@RequestMapping("/im/chk")
@Validated // 这个注解是启用方法级别参数校验的关键
@ApiOperation(value = "智能制造参数点检反控接口")
public class EqReverseCtrlInfoController extends BaseController {
    private static final Logger logger = LoggerFactory.getLogger(EqReverseCtrlInfoController.class);
    private final IEqReverseCtrlService eqReverseCtrlService;
    private final RedisTemplate<String, EqReverseCtrlInfo> eqReverseCtrlInfoRedisTemplate;

    @Autowired
    public EqReverseCtrlInfoController(IEqReverseCtrlService eqReverseCtrlService, RedisTemplate<String, EqReverseCtrlInfo> eqReverseCtrlInfoRedisTemplate) {
        this.eqReverseCtrlService = eqReverseCtrlService;
        this.eqReverseCtrlInfoRedisTemplate = eqReverseCtrlInfoRedisTemplate;
    }

    @GetMapping("/{simId}")
    public R<String> getEqReverseCtrlInfo(@ApiParam(name = "盒子号", value = "例如：86xxxx", required = true)
                                          @PathVariable
                                          @QtechValidSimId
                                          String simId,
                                          HttpServletRequest request) {

        // 从redis获取数据
        EqReverseCtrlInfo eqReverseCtrlInfoRedis = null;
        try {
            eqReverseCtrlInfoRedis = eqReverseCtrlInfoRedisTemplate.opsForValue().get(EQ_REVERSE_CTRL_INFO_REDIS_KEY_PREFIX + simId);
        } catch (Exception e) {
            logger.error(">>>>> Error occurred while retrieving data from Redis for SIM ID {}: {}. Request URI: {}", simId, e.getMessage(), request.getRequestURI(), e);
        }

        if (eqReverseCtrlInfoRedis != null) {
            return handleResponse(eqReverseCtrlInfoRedis);
        } else {
            EqReverseCtrlInfo eqReverseCtrlInfo = null;
            try {
                eqReverseCtrlInfo = eqReverseCtrlService.selectEqReverseCtrlInfoBySimId(simId);
            } catch (Exception e) {
                logger.error(">>>>> Error occurred while retrieving data from database for SIM ID {}: {}. Request URI: {}", simId, e.getMessage(), request.getRequestURI(), e);
                return new R<String>().setCode(ResponseCode.SUCCESS.getCode()).setMsg("Database query error").setData(null);
            }

            if (eqReverseCtrlInfo == null) {
                return new R<String>().setCode(ResponseCode.SUCCESS.getCode()).setMsg("No data found").setData(null);
            }
            return handleResponse(eqReverseCtrlInfo);
        }
    }
}