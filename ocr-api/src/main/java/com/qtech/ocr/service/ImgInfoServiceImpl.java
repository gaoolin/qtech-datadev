package com.qtech.ocr.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.HashMap;
import java.util.Map;

/**
 * author :  gaozhilin
 * email  :  gaoolin@gmail.com
 * date   :  2023/07/31 17:04:14
 * desc   :  服务层
 */

@Service
public class ImgInfoServiceImpl {

    @Autowired
    private RestTemplate restTemplate;

    public HttpEntity<Map<String, String>> cephObj(String bucketName, String fileName, String base64Contents) {
        // 拼接请求 URL
        String url = String.format("http://10.170.6.40:31555/ceph/obj/buckets/%s/files/%s/byte", bucketName, fileName);

        // 构造请求体
        Map<String, String> requestParams = new HashMap<>();
        requestParams.put("contents", base64Contents);

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(org.springframework.http.MediaType.APPLICATION_JSON);
        HttpEntity<Map<String, String>> requestEntity = new HttpEntity<>(requestParams, headers);

        // 发送 HTTP POST 请求
        ResponseEntity<String> responseEntity = restTemplate.exchange(
                url,
                HttpMethod.POST,
                requestEntity,
                String.class
        );

        return requestEntity;
    }
}
