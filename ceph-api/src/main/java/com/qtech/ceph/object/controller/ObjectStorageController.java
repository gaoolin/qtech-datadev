package com.qtech.ceph.object.controller;

import com.qtech.ceph.common.ApiResponse;
import com.qtech.ceph.common.ResponseCM;
import com.qtech.ceph.object.service.CephStorageService;
import io.swagger.annotations.ApiOperation;
import org.apache.commons.codec.binary.Base64;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import software.amazon.awssdk.services.s3.model.Bucket;
import software.amazon.awssdk.services.s3.model.ListObjectsV2Response;

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
@RequestMapping("/ceph/obj")
public class ObjectStorageController {

    private static final Logger logger = LoggerFactory.getLogger(ObjectStorageController.class);

    @Autowired
    private CephStorageService cephStorageService;

    /**
     * 获取所有存储桶列表
     * @return 存储桶列表
     */
    @ApiOperation(value = "List All Buckets", notes = "Retrieve a list of all buckets")
    @GetMapping("/buckets")
    public ResponseEntity<ApiResponse<List<Bucket>>> listAllBuckets() {
        List<Bucket> buckets = cephStorageService.listAllBuckets();
        return ResponseEntity.ok(new ApiResponse<>(ResponseCM.SUCCESS, buckets));
    }

    /**
     * 获取指定存储桶中的所有对象
     *
     * @param bucketName 存储桶名称
     * @return 对象列表
     */
    @ApiOperation(value = "List Objects in Bucket", notes = "Retrieve a list of all objects in the specified bucket")
    @GetMapping("/buckets/{bucketName}/objects")
    public ResponseEntity<ApiResponse<ListObjectsV2Response>> listObjects(@PathVariable String bucketName) {
        ListObjectsV2Response objectKeys = cephStorageService.listObjects(bucketName);
        return ResponseEntity.ok(new ApiResponse<>(ResponseCM.SUCCESS, objectKeys));
    }

    /**
     * 创建一个新的存储桶
     * @param bucketName 存储桶名称
     * @return 创建结果
     */
    @ApiOperation(value = "Create Bucket", notes = "Create a new bucket with the specified name")
    @PostMapping("/buckets")
    public ResponseEntity<ApiResponse<String>> createBucket(@RequestParam String bucketName) {
        if (bucketName == null || bucketName.isEmpty()) {
            throw new IllegalArgumentException("Bucket name cannot be null or empty");
        }
        String result = cephStorageService.createBucket(bucketName);
        return ResponseEntity.status(HttpStatus.CREATED).body(new ApiResponse<>(ResponseCM.CREATED, result));
    }

    /**
     * 删除指定存储桶
     * @param bucketName 存储桶名称
     * @return 删除结果
     */
    @ApiOperation(value = "Delete Bucket", notes = "Delete the specified bucket")
    @DeleteMapping("/buckets/{bucketName}")
    public ResponseEntity<ApiResponse<String>> deleteBucket(@PathVariable String bucketName) {
        cephStorageService.deleteBucket(bucketName);
        return ResponseEntity.ok(new ApiResponse<>(ResponseCM.SUCCESS, "Bucket deleted successfully"));
    }

    /**
     * 设置指定文件为公开访问
     * @param bucketName 存储桶名称
     * @param fileName 文件名称
     * @return 操作结果
     */
    @ApiOperation(value = "Set File Public", notes = "Set the specified file to be publicly accessible")
    @PostMapping("/buckets/{bucketName}/files/{fileName}/public")
    public ResponseEntity<ApiResponse<String>> setFilePublic(@PathVariable String bucketName, @PathVariable String fileName) {
        cephStorageService.setObjPublic(bucketName, fileName);
        return ResponseEntity.ok(new ApiResponse<>(ResponseCM.SUCCESS, "File set to public successfully"));
    }

    /**
     * 下载指定文件
     * @param bucketName 存储桶名称
     * @param keyName 文件键
     * @param dirName 文件存储路径
     * @return 操作结果
     */
    @ApiOperation(value = "Download File", notes = "Download the specified file to the given directory")
    @GetMapping("/buckets/{bucketName}/files/{keyName}/download")
    public ResponseEntity<ApiResponse<String>> downloadObj(@PathVariable String bucketName, @PathVariable String keyName, @RequestParam String dirName) {
        cephStorageService.downloadObj(bucketName, keyName, dirName);
        return ResponseEntity.ok(new ApiResponse<>(ResponseCM.SUCCESS, "File downloaded successfully"));
    }

    /**
     * 删除指定文件
     * @param bucketName 存储桶名称
     * @param fileName 文件名称
     * @return 删除结果
     */
    @ApiOperation(value = "Delete Object", notes = "Delete the specified object from the bucket")
    @DeleteMapping("/buckets/{bucketName}/files/{fileName}")
    public ResponseEntity<ApiResponse<String>> deleteObject(@PathVariable String bucketName, @PathVariable String fileName) {
        cephStorageService.deleteObj(bucketName, fileName);
        return ResponseEntity.ok(new ApiResponse<>(ResponseCM.SUCCESS, "Object deleted successfully"));
    }

    /**
     * 生成预签名 URL
     * @param bucketName 存储桶名称
     * @param keyName 文件键
     * @return 预签名 URL
     */
    @ApiOperation(value = "Generate Presigned URL", notes = "Generate a presigned URL for the specified object")
    @GetMapping("/buckets/{bucketName}/files/{keyName}/url")
    public ResponseEntity<ApiResponse<URL>> generatePresignedUrl(@PathVariable String bucketName, @PathVariable String keyName) {
        URL url = cephStorageService.generatePresignedUrl(bucketName, keyName);
        return ResponseEntity.ok(new ApiResponse<>(ResponseCM.SUCCESS, url));
    }

    /**
     * 上传文件并获取 URL
     * @param bucketName 存储桶名称
     * @param file 文件对象
     * @param keyName 文件键
     * @return 文件 URL
     */
    @ApiOperation(value = "Upload File And Get URL", notes = "Upload a file and get its URL")
    @PostMapping("/buckets/{bucketName}/files")
    public ResponseEntity<ApiResponse<URL>> uploadFileAndGetUrl(@PathVariable String bucketName, @RequestParam("file") File file, @RequestParam String keyName) {
        URL url = cephStorageService.uploadObjAndGetUrl(bucketName, file, keyName);
        return ResponseEntity.ok(new ApiResponse<>(ResponseCM.SUCCESS, url));
    }

    /**
     * 上传 InputStream 作为文件
     * @param bucketName 存储桶名称
     * @param fileName 文件名称
     * @param input 文件输入流
     * @return 操作结果
     */
    @ApiOperation(value = "Upload InputStream As File", notes = "Upload a file from an InputStream")
    @PostMapping("/buckets/{bucketName}/files/{fileName}/stream")
    public ResponseEntity<ApiResponse<String>> uploadInputStreamAsObj(@PathVariable String bucketName, @PathVariable String fileName, @RequestParam InputStream input) {
        cephStorageService.uploadInputStreamAsObj(bucketName, fileName, input);
        return ResponseEntity.ok(new ApiResponse<>(ResponseCM.SUCCESS, "File uploaded successfully"));
    }

    /**
     * 上传 ByteArray 作为文件
     * @param bucketName 存储桶名称
     * @param fileName 文件名称
     * @param paramMap 请求参数，包含 Base64 编码的文件内容
     * @return 操作结果
     */
    @ApiOperation(value = "Upload ByteArray As File", notes = "Upload a file from a Base64-encoded byte array")
    @PostMapping("/buckets/{bucketName}/files/{fileName}/byte")
    public ResponseEntity<ApiResponse<String>> uploadByteArrayAsObj(@PathVariable String bucketName, @PathVariable String fileName, @RequestBody Map<String, Object> paramMap) {
        try {
            String contents = (String) paramMap.get("contents");
            byte[] bytes = Base64.decodeBase64(contents);
            cephStorageService.uploadByteArrayAsObj(bucketName, fileName, bytes);
            return ResponseEntity.ok(new ApiResponse<>(ResponseCM.SUCCESS, null));
        } catch (Exception e) {
            logger.error("uploadByteArrayAsFile error: " + e.getMessage(), e);
            throw new RuntimeException("Failed to upload byte array as file");
        }
    }

    /**
     * 下载对象并以 InputStream 形式返回
     * @param bucketName 存储桶名称
     * @param fileName 文件名称
     * @param response HTTP 响应
     */
    @ApiOperation(value = "Read Object As InputStream", notes = "Read an object from the bucket as an InputStream")
    @GetMapping("/buckets/{bucketName}/files/{fileName}/stream")
    public void downloadObjAsInputStream(@PathVariable String bucketName, @PathVariable String fileName, HttpServletResponse response) {
        try {
            // 获取文件输入流
            InputStream inputStream = cephStorageService.downloadObjAsInputStream(bucketName, fileName);

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

    /**
     * 下载对象并以 ByteArray 形式返回
     * @param bucketName 存储桶名称
     * @param fileName 文件名称
     * @return 文件内容的 ByteArray
     */
    @ApiOperation(value = "Download Object As ByteArray", notes = "Download an object from the bucket as a byte array")
    @GetMapping("/buckets/{bucketName}/files/{fileName}/download")
    public ResponseEntity<ApiResponse<byte[]>> downloadObjectAsByteArray(@PathVariable String bucketName, @PathVariable String fileName) {
        byte[] data = cephStorageService.downloadObjAsByteArray(bucketName, fileName);
        return ResponseEntity.ok(new ApiResponse<>(ResponseCM.SUCCESS, data));
    }
}
