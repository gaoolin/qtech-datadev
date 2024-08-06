package com.qtech.ceph.object.controller;

import com.amazonaws.services.s3.model.Bucket;
import com.amazonaws.services.s3.model.ObjectListing;
import com.qtech.ceph.common.ApiResponse;
import com.qtech.ceph.common.ResponseCode;
import com.qtech.ceph.object.service.CephStorageService;
import io.swagger.annotations.ApiOperation;
import org.apache.commons.codec.binary.Base64;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.URL;
import java.util.List;
import java.util.Map;

/**
 * author :  gaozhilin
 * email  :  gaoolin@gmail.com
 * date   :  2023/07/17 08:25:47
 * desc   :  对象存储控制器 (原名: CephGrwController)
 */

@RestController
@RequestMapping(value = "/ceph/object")
public class ObjectStorageController {

    private static final Logger logger = LoggerFactory.getLogger(ObjectStorageController.class);

    @Autowired
    private CephStorageService cephStorageService;

    @ApiOperation(value = "List All Buckets", notes = "List All Buckets")
    @GetMapping("/buckets")
    public ResponseEntity<ApiResponse<List<Bucket>>> listAllBuckets() {
        List<Bucket> buckets = cephStorageService.listAllBuckets();
        return ResponseEntity.ok(new ApiResponse<>(ResponseCode.SUCCESS, buckets));
    }

    @ApiOperation(value = "List Objects in Bucket", notes = "List Objects in Bucket")
    @GetMapping("/buckets/{bucketName}/objects")
    public ResponseEntity<ApiResponse<ObjectListing>> listObjects(@PathVariable String bucketName) {
        ObjectListing objectListing = cephStorageService.listObjects(bucketName);
        return ResponseEntity.ok(new ApiResponse<>(ResponseCode.SUCCESS, objectListing));
    }

    @ApiOperation(value = "Create Bucket", notes = "Create Bucket")
    @PostMapping("/buckets")
    public ResponseEntity<ApiResponse<String>> createBucket(@RequestParam String bucketName) {
        if (bucketName == null || bucketName.isEmpty()) {
            throw new IllegalArgumentException("Bucket name cannot be null or empty");
        }
        String result = cephStorageService.createBucket(bucketName);
        return ResponseEntity.status(HttpStatus.CREATED).body(new ApiResponse<>(ResponseCode.CREATED, result));
    }

    @ApiOperation(value = "Delete Bucket", notes = "Delete Bucket")
    @DeleteMapping("/buckets/{bucketName}")
    public ResponseEntity<ApiResponse<String>> deleteBucket(@PathVariable String bucketName) {
        cephStorageService.deleteBucket(bucketName);
        return ResponseEntity.ok(new ApiResponse<>(ResponseCode.SUCCESS, "Bucket deleted successfully"));
    }

    @ApiOperation(value = "Set File Public", notes = "Set File Public")
    @PostMapping("/buckets/{bucketName}/files/{fileName}/public")
    public ResponseEntity<ApiResponse<String>> setFilePublic(@PathVariable String bucketName, @PathVariable String fileName) {
        cephStorageService.setObjPublic(bucketName, fileName);
        return ResponseEntity.ok(new ApiResponse<>(ResponseCode.SUCCESS, "File set to public successfully"));
    }

    /**
     * @description
     * @param bucketName
     * @param keyName 存储对象的指定存储桶中的键
     * @param dirName
     * @return org.springframework.http.ResponseEntity<com.qtech.ceph.common.ApiResponse<java.lang.String>>
     */
    @ApiOperation(value = "Download File", notes = "Download File")
    @GetMapping("/buckets/{bucketName}/files/{keyName}/download")
    public ResponseEntity<ApiResponse<String>> downloadObj(@PathVariable String bucketName, @PathVariable String keyName, @RequestParam String dirName) {
        cephStorageService.downloadObj(bucketName, keyName, dirName);
        return ResponseEntity.ok(new ApiResponse<>(ResponseCode.SUCCESS, "File downloaded successfully"));
    }

    @ApiOperation(value = "Delete Object", notes = "Delete Object")
    @DeleteMapping("/buckets/{bucketName}/files/{fileName}")
    public ResponseEntity<ApiResponse<String>> deleteObject(@PathVariable String bucketName, @PathVariable String fileName) {
        cephStorageService.deleteObject(bucketName, fileName);
        return ResponseEntity.ok(new ApiResponse<>(ResponseCode.SUCCESS, "Object deleted successfully"));
    }

    @ApiOperation(value = "Generate Presigned URL", notes = "Generate Presigned URL")
    @GetMapping("/buckets/{bucketName}/files/{keyName}/url")
    public ResponseEntity<ApiResponse<URL>> generatePresignedUrl(@PathVariable String bucketName, @PathVariable String keyName) {
        URL url = cephStorageService.generatePresignedUrl(bucketName, keyName);
        return ResponseEntity.ok(new ApiResponse<>(ResponseCode.SUCCESS, url));
    }

    /**
     * @description web端上传文件时，需要将 MultipartFile 转换为 File， File file = convertMultipartFileToFile(multipartFile);再将file传参
     * @param bucketName 存储桶名称
     * @param file file为文件对象
     * @param keyName keyName为存储对象的指定存储桶中的键
     * @return org.springframework.http.ResponseEntity<com.qtech.ceph.common.ApiResponse<java.net.URL>>
     */
    @ApiOperation(value = "Upload File And Get URL", notes = "Upload File And Get URL")
    @PostMapping("/buckets/{bucketName}/files")
    public ResponseEntity<ApiResponse<URL>> uploadFileAndGetUrl(@PathVariable String bucketName, @RequestParam File file, @RequestParam String keyName) {
        URL url = cephStorageService.uploadObjAndGetUrl(bucketName, file, keyName);
        return ResponseEntity.ok(new ApiResponse<>(ResponseCode.SUCCESS, url));
    }

    @ApiOperation(value = "Upload InputStream As File", notes = "Upload InputStream As File")
    @PostMapping("/buckets/{bucketName}/files/{fileName}/stream")
    public ResponseEntity<ApiResponse<String>> uploadInputStreamAsObj(@PathVariable String bucketName, @PathVariable String fileName, @RequestParam InputStream input) {
        cephStorageService.uploadInputStreamAsObj(bucketName, fileName, input);
        return ResponseEntity.ok(new ApiResponse<>(ResponseCode.SUCCESS, "File uploaded successfully"));
    }

    /**
     * @description 上传文件
     * @param bucketName
     * @param fileName
     * @param paramMap paramMap的key为contents，value为Base64编码后的文件内容
     * @return org.springframework.http.ResponseEntity<com.qtech.ceph.common.ApiResponse<java.lang.String>>
     */
    @ApiOperation(value = "Upload ByteArray As File", notes = "Upload ByteArray As File")
    @PostMapping("/buckets/{bucketName}/files/{fileName}/byte")
    public ResponseEntity<ApiResponse<String>> uploadByteArrayAsFile(@PathVariable String bucketName, @PathVariable String fileName, @RequestBody Map<String, Object> paramMap) {
        try {
            String contents = (String) paramMap.get("contents");
            byte[] bytes = Base64.decodeBase64(contents);
            cephStorageService.uploadByteArrayAsObj(bucketName, fileName, bytes);
            return ResponseEntity.ok(new ApiResponse<>(ResponseCode.SUCCESS, "File uploaded successfully"));
        } catch (Exception e) {
            logger.error("uploadByteArrayAsFile error: " + e.getMessage(), e);
            throw new RuntimeException("Failed to upload byte array as file");
        }
    }

    /**
     * 获取输入流：从服务中获取 InputStream。
     * 设置响应头：配置响应头以提示浏览器进行文件下载。
     * 写入输出流：将 InputStream 内容流式地写入 HttpServletResponse 的输出流。
     * 异常处理：捕获并记录异常，确保流的关闭和资源的释放。
     */
    @ApiOperation(value = "Read Object As InputStream", notes = "Read Object As InputStream")
    @GetMapping("/buckets/{bucketName}/files/{fileName}/stream")
    public void downloadObjectAsInputStream(@PathVariable String bucketName, @PathVariable String fileName, HttpServletResponse response) {
        try {
            // 获取文件输入流
            InputStream inputStream = cephStorageService.downloadObjectAsInputStream(bucketName, fileName);

            // 设置响应头
            response.setContentType("application/octet-stream");
            response.setHeader("Content-Disposition", "attachment; filename=\"" + fileName + "\"");

            // 将输入流写入响应输出流
            try (OutputStream outputStream = response.getOutputStream()) {
                byte[] buffer = new byte[8192];
                int bytesRead;
                while ((bytesRead = inputStream.read(buffer)) != -1) {
                    outputStream.write(buffer, 0, bytesRead);
                }
            }
        } catch (Exception e) {
            logger.error("Error reading object: ", e);
            throw new RuntimeException("Failed to read object");
        }
    }

    @ApiOperation(value = "Download Object As ByteArray", notes = "Download Object As ByteArray")
    @GetMapping("/buckets/{bucketName}/files/{fileName}/download")
    public ResponseEntity<ApiResponse<byte[]>> downloadObjectAsByteArray(@PathVariable String bucketName, @PathVariable String fileName) {
        byte[] data = cephStorageService.downloadObjectAsByteArray(bucketName, fileName);
        return ResponseEntity.ok(new ApiResponse<>(ResponseCode.SUCCESS, data));
    }
}