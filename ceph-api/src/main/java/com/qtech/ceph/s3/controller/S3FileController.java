package com.qtech.ceph.s3.controller;

import com.qtech.ceph.common.ApiResponse;
import com.qtech.ceph.s3.service.FileService;
import com.qtech.ceph.s3.utils.S3Constants;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.util.List;
import java.util.Map;

/**
 * author :  gaozhilin
 * email  :  gaoolin@gmail.com
 * date   :  2024/08/09 13:35:50
 * desc   :
 */

@RestController
@RequestMapping("/s3/files")
public class S3FileController {

    @Qualifier("fileServiceSyncImpl")
    @Autowired
    private FileService fileService;

    /**
     * 上传文件到 S3。
     *
     * @param bucketName 存储桶名称
     * @param file       要上传的文件
     * @return 包含操作状态的 ApiResponse
     */
    @PostMapping("/upload")
    public ApiResponse<String> uploadFile(@RequestParam String bucketName,
                                          @RequestParam MultipartFile file) {
        try (InputStream inputStream = file.getInputStream()) {
            fileService.uploadFile(bucketName, file.getOriginalFilename(), inputStream);
            return ApiResponse.success("文件上传成功");
        } catch (IOException e) {
            return ApiResponse.internalServerError("文件上传失败: " + e.getMessage());
        } catch (Exception e) {
            return ApiResponse.internalServerError("发生未知错误: " + e.getMessage());
        }
    }


    /**
     * 下载指定的文件。
     *
     * @param bucketName 存储桶名称
     * @param fileName   文件名称
     * @return 文件内容作为字节数组的 ApiResponse
     */
    @GetMapping("/download")
    public ApiResponse<byte[]> downloadFile(@RequestParam String bucketName,
                                            @RequestParam String fileName) {
        try {
            byte[] fileData = fileService.downloadFileAsBytes(bucketName, fileName);
            return ApiResponse.success(fileData);
        } catch (Exception e) {
            return ApiResponse.internalServerError("文件下载失败: " + e.getMessage());
        }
    }

    /**
     * 获取文件元数据。
     *
     * @param bucketName 存储桶名称
     * @param fileName   文件名称
     * @return 文件的元数据信息
     */
    @GetMapping("/metadata")
    public ApiResponse<Map<String, String>> getFileMetadata(@RequestParam("bucketName") String bucketName,
                                                            @RequestParam("fileName") String fileName) {
        try {
            Map<String, String> metadata = fileService.getFileMetadata(bucketName, fileName);
            return ApiResponse.success(metadata);
        } catch (Exception e) {
            return ApiResponse.internalServerError("获取文件元数据失败：" + e.getMessage());
        }
    }

    /**
     * 列出存储桶中的所有文件。
     *
     * @param bucketName 存储桶名称
     * @return 文件列表
     */
    @GetMapping("/list")
    public ApiResponse<List<String>> listFiles(@RequestParam("bucketName") String bucketName) {
        try {
            List<String> fileList = fileService.listFiles(bucketName);
            return ApiResponse.success(fileList);
        } catch (Exception e) {
            return ApiResponse.internalServerError("列出文件失败：" + e.getMessage());
        }
    }

    /**
     * 生成文件的预签名URL。
     *
     * @param bucketName 存储桶名称
     * @param fileName   文件名称
     * @return 预签名的URL
     */
    @GetMapping("/presigned-url")
    public ApiResponse<URL> generatePresignedUrl(@RequestParam("bucketName") String bucketName,
                                                 @RequestParam("fileName") String fileName) {
        try {
            URL presignedUrl = fileService.generatePresignedUrl(bucketName, fileName);
            return ApiResponse.success(presignedUrl);
        } catch (Exception e) {
            return ApiResponse.internalServerError("生成预签名URL失败：" + e.getMessage());
        }
    }

    /**
     * 删除指定的文件。
     *
     * @param bucketName 存储桶名称
     * @param fileName   文件名称
     * @return 包含操作状态的 ApiResponse
     */
    @DeleteMapping("/delete")
    public ApiResponse<String> deleteFile(@RequestParam String bucketName,
                                          @RequestParam String fileName) {
        try {
            fileService.deleteFile(bucketName, fileName);
            return ApiResponse.success("文件删除成功");
        } catch (Exception e) {
            return ApiResponse.internalServerError("文件删除失败: " + e.getMessage());
        }
    }

    /**
     * 上传文件并生成预签名的URL。
     *
     * @param file       上传的文件
     * @param bucketName 存储桶名称
     * @return 包含预签名URL的响应数据
     */
    @PostMapping("/upload/generate-url")
    public ApiResponse<URL> uploadFileAndGenerateUrl(@RequestParam("file") MultipartFile file,
                                                     @RequestParam("bucketName") String bucketName) {
        try {
            // 首先将文件上传到指定的 S3 存储桶
            String fileName = file.getOriginalFilename();

            // 使用 try-with-resources 语句确保 InputStream 被正确关闭
            try (InputStream inputStream = file.getInputStream()) {
                fileService.uploadFile(bucketName, fileName, inputStream);
            }

            // 然后生成该文件的预签名 URL
            URL presignedUrl = fileService.generatePresignedUrl(bucketName, fileName);

            // 返回成功响应，包含生成的预签名 URL
            return ApiResponse.success("预签名URL有效期为" + S3Constants.DEFAULT_SIGNATURE_DURATION.toMinutes() + "分钟", presignedUrl);
        } catch (Exception e) {
            // 如果出现异常，返回服务器内部错误响应
            return ApiResponse.internalServerError("上传文件失败：" + e.getMessage());
        }
    }

}