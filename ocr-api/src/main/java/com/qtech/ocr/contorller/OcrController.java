package com.qtech.ocr.contorller; // 注意包名修正

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.qtech.common.utils.HttpConnectUtils;
import com.qtech.ocr.common.ApiResponse;
import com.qtech.ocr.service.ImgInfoService;
import io.swagger.annotations.ApiOperation;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.Map;

import static com.qtech.ocr.Constants.OCR_HTTP_URL;

/**
 * author :  gaozhilin
 * email  :  gaoolin@gmail.com
 * date   :  2023/07/31 17:00:23
 * desc   :  OCR API
 */

@RestController
@RequestMapping("/im/ocr")
public class OcrController extends BaseController {
    private static final Logger logger = LoggerFactory.getLogger(OcrController.class);
    private final ObjectMapper objectMapper = new ObjectMapper(); // 使用单例模式

    @Autowired
    private ImgInfoService imgInfoService;

    /**
     * 获取 OCR 信息并上传文件
     *
     * @param request 请求体，包含文件信息
     * @return OCR API 返回的响应
     */
    @ApiOperation(value = "get ocr result", notes = "Get OCR info and upload file")
    @RequestMapping(value = "/label", method = RequestMethod.POST, consumes = MediaType.APPLICATION_JSON_VALUE)
    public ApiResponse<String> getOcrResult(@RequestBody Map<String, String> request) throws JsonProcessingException { // 更改请求体类型
        // 从请求体中提取信息
        String bucketName = "qtech-20230717";
        String fileName = request.get("fileName");
        String base64Contents = request.get("contents");

        String response = imgInfoService.S3Obj(bucketName, fileName, base64Contents);
        logger.info("S3Obj: " + response);

        ApiResponse<String> apiResponse = objectMapper.readValue(response, new TypeReference<ApiResponse<String>>() {
        }); // 使用Jackson解析
        int responseCode = apiResponse.getCode();
        String data = apiResponse.getData();

        // 检查响应内容
        if (responseCode == 200) {
            Map<String, String> map = new HashMap<>();
            if (data != null) {

                // 使用TypeReference确保类型安全
                HashMap<String, String> fileNameResponse = null;
                try {
                    fileNameResponse = objectMapper.readValue(data, new TypeReference<HashMap<String, String>>() {
                    });
                } catch (Exception e) {
                    logger.error("Error parsing response: " + e.getMessage());
                }
                String newFileName = null;
                if (fileNameResponse != null) {
                    newFileName = fileNameResponse.getOrDefault("newFileName", "");
                }
                String originalFileName = null;
                if (fileNameResponse != null) {
                    originalFileName = fileNameResponse.getOrDefault("originalFileName", "");
                }
                map.put("file_name", newFileName);
                map.put("new_file_name", newFileName);
                map.put("original_file_name", originalFileName);
            } else {
                map.put("file_name", fileName);
            }
            try {
                String labelRes = HttpConnectUtils.post(OCR_HTTP_URL, objectMapper.writeValueAsString(map)); // 使用Jackson序列化
                logger.info("labelRes: " + labelRes);
                return ApiResponse.success("success", labelRes);
            } catch (Exception e) {
                return ApiResponse.badRequest("请求ocr服务时发生错误");
            }
        }
        return apiResponse;
    }
}
