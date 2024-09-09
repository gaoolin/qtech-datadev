package com.qtech.ceph.s3.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.qtech.ceph.common.ApiResponse;
import com.qtech.ceph.s3.service.FileService;
import com.qtech.ceph.s3.utils.FileNameUtils;
import com.qtech.ceph.s3.utils.S3Constants;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.util.Base64;
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

    private final ObjectMapper objectMapper = new ObjectMapper();

    private final FileService fileService;

    public S3FileController(@Qualifier("fileServiceImpl") FileService fileService) {
        this.fileService = fileService;
    }

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
            boolean flag = fileService.doesFileExist(bucketName, file.getOriginalFilename());
            if (flag) {
                return ApiResponse.conflict("文件已存在");
            }
            fileService.uploadFile(bucketName, file.getOriginalFilename(), inputStream);
            return ApiResponse.success("文件上传成功");
        } catch (IOException e) {
            return ApiResponse.internalServerError("文件上传失败: " + e.getMessage());
        } catch (Exception e) {
            return ApiResponse.internalServerError("发生未知错误: " + e.getMessage());
        }
    }

    @PostMapping("/upload/bytes")
    public ApiResponse<String> uploadFileFromBytes(@RequestParam String bucketName,
                                                   @RequestParam String fileName,
                                                   @RequestBody byte[] contents) {
        try {
            boolean flag = fileService.doesFileExist(bucketName, fileName);
            if (flag) {
                return ApiResponse.conflict("文件已存在");
            }
            fileService.uploadByteArrayAsObj(bucketName, fileName, contents);
            return ApiResponse.success("文件上传成功", null);
        } catch (Exception e) {
            return ApiResponse.internalServerError("文件上传失败: " + e.getMessage());
        }
    }

    @PostMapping("/upload/json")
    public ApiResponse<String> uploadFileFromJson(@RequestParam String bucketName,
                                                  @RequestParam String fileName,
                                                  @RequestBody String contents) {
        try {
            boolean flag = fileService.doesFileExist(bucketName, fileName);
            Map<String, String> responseData = FileNameUtils.getFileNameWithPossibleRename(fileName, flag);
            String actualFileName = responseData != null ? responseData.get("newFileName") : fileName;

            byte[] decode = Base64.getDecoder().decode(contents);
            fileService.uploadByteArrayAsObj(bucketName, actualFileName, decode);

            // 返回响应
            if (responseData == null) {
                return ApiResponse.success("文件上传成功", null);
            } else {
                return ApiResponse.success("文件上传成功", objectMapper.writeValueAsString(responseData));
            }
        } catch (Exception e) {
            return ApiResponse.internalServerError("文件上传失败: " + e.getMessage());
        }
    }


    /**
     * 下载指定的文件。
     * 注意：当Spring Boot序列化字节数组为JSON时，默认会将字节数组编码为Base64字符串。这是因为JSON格式不支持直接传递二进制数据，通常需要对二进制数据进行编码处理（如Base64）。
     * 如果希望Java端直接返回字节数组，可以考虑不将数据封装在 ApiResponse 中，直接返回字节流（如 ResponseEntity<byte[]>）。不过，这样做会影响到你已有的API响应格式。
     *
     * @param bucketName 存储桶名称
     * @param fileName   文件名称
     * @return 文件内容作为字节数组的 ApiResponse
     * @GetMapping("/download") public ResponseEntity<byte[]> downloadFile(@RequestParam String bucketName,
     * @RequestParam String fileName) {
     * try {
     * boolean flag = fileService.doesFileExist(bucketName, fileName);
     * if (!flag) {
     * return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
     * }
     * byte[] fileData = fileService.downloadFileAsBytes(bucketName, fileName);
     * return ResponseEntity.ok().contentType(MediaType.APPLICATION_OCTET_STREAM).body(fileData);
     * } catch (Exception e) {
     * return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
     * }
     * }
     * Spring Boot 确实有能力在序列化为 JSON 时将 byte[] 数据编码为 Base64，但它是 在特定情况下 自动进行的。如果你将 byte[] 数据直接作为响应主体返回，它通常会根据 MIME 类型决定如何处理。如果响应类型是 application/json 并且 byte[] 是 JSON 对象的一部分，那么 byte[] 会被自动编码为 Base64 字符串。
     * 但是，为什么你仍然可能需要手动编码？
     * 自定义响应格式：在你的情况下，你使用了 ApiResponse 包装器。因为 byte[] 被包裹在 ApiResponse 中，Spring 会将整个对象序列化为 JSON。由于 byte[] 在 JSON 中表现为一个字节数组（而不是字符串），JSON 本身并不会理解该数组应如何被转换为 Base64。
     * 确保一致性：如果你手动将 byte[] 转换为 Base64 编码字符串，可以确保客户端收到的数据格式是你预期的，无论是通过 ApiResponse 还是其他方式传输。
     * 总结
     * 虽然 Spring Boot 可以在某些情况下自动处理 Base64 编码，但为了确保兼容性和一致性，尤其是在使用自定义响应包装类时，手动将 byte[] 转换为 Base64 编码的字符串是最佳实践。这样可以确保你的客户端能够正确解码和处理返回的数据。
     * 因此，在你提供的代码中，手动编码为 Base64 是合理的做法，不需要依赖 Spring Boot 的自动编码功能。
     */
    @GetMapping("/download/json")
    public ApiResponse<String> downloadFile(@RequestParam String bucketName,
                                            @RequestParam String fileName) {
        try {
            boolean flag = fileService.doesFileExist(bucketName, fileName);
            if (!flag) {
                return ApiResponse.notFound("文件不存在");
            }
            // 图片数据编码问题 确保图片数据在传输前进行了适当的编码。对于图片等二进制数据，通常需要将其编码为Base64字符串，以便通过JSON安全地传输。
            // 使用Base64编码有助于避免因字符集或编码问题导致的数据损坏。
            // 读取图片数据为字节数组
            byte[] fileData = fileService.downloadFileAsBytes(bucketName, fileName); // 从文件或输入流中获取图片字节数组
            // 将字节数组编码为Base64字符串
            String encodedImage = Base64.getEncoder().encodeToString(fileData);
            return ApiResponse.success(encodedImage);
        } catch (Exception e) {
            return ApiResponse.internalServerError("文件下载失败: " + e.getMessage());
        }
    }

    @GetMapping("/download/bytes")
    public ResponseEntity<byte[]> downloadFileAsBytes(@RequestParam String bucketName,
                                                      @RequestParam String fileName) {
        try {
            boolean flag = fileService.doesFileExist(bucketName, fileName);
            if (!flag) {
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
            }
            byte[] fileData = fileService.downloadFileAsBytes(bucketName, fileName);
            return ResponseEntity.ok().contentType(MediaType.APPLICATION_OCTET_STREAM).body(fileData);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
        }
    }

    @GetMapping("/chk-exist")
    public ApiResponse<Boolean> checkFileExist(@RequestParam String bucketName,
                                               @RequestParam String fileName) {
        try {
            boolean fileExists = fileService.doesFileExist(bucketName, fileName);
            if (fileExists) {
                return ApiResponse.conflict("文件已存在");
            } else {
                return ApiResponse.notFound("文件不存在");
            }
        } catch (Exception e) {
            return ApiResponse.internalServerError("文件检查失败: " + e.getMessage());
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
            if (!fileService.doesFileExist(bucketName, fileName)) {
                return ApiResponse.notFound("文件不存在");
            }
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
     * 预签名URL可以用于上传和下载文件，具体取决于生成预签名URL时指定的操作权限。以下是两种常见的使用场景：
     * <p>
     * 1. 用于上传文件：
     * 你可以生成一个具有PUT权限的预签名URL，客户端可以使用这个URL直接上传文件到存储桶。
     * 使用场景：你希望外部用户或服务将文件上传到你的存储系统（例如S3、Ceph），但不希望他们有完整的存储访问权限。生成预签名URL后，用户可以通过该URL在一定时间内上传文件。
     * 流程：
     * <p>
     * 服务端生成一个预签名URL，允许客户端上传文件。
     * 客户端用PUT请求将文件上传到预签名URL。
     * // Example: Generate a presigned URL for uploading a file
     * GeneratePresignedUrlRequest request = new GeneratePresignedUrlRequest(bucketName, fileName)
     * .withMethod(HttpMethod.PUT);  // Grant upload (PUT) permissions
     * URL presignedUrl = s3Client.generatePresignedUrl(request);
     * <p>
     * 2. 用于下载文件：
     * 你可以生成一个具有GET权限的预签名URL，允许客户端通过该URL下载文件。
     * 使用场景：你希望提供给外部用户一个临时下载链接，他们可以通过该链接下载存储在系统中的文件，而不需要登录或具有访问权限。
     * 流程：
     * <p>
     * 服务端生成一个具有GET权限的预签名URL。
     * 客户端用GET请求访问该URL并下载文件。
     * // Example: Generate a presigned URL for downloading a file
     * GeneratePresignedUrlRequest request = new GeneratePresignedUrlRequest(bucketName, fileName)
     * .withMethod(HttpMethod.GET);  // Grant download (GET) permissions
     * URL presignedUrl = s3Client.generatePresignedUrl(request);
     * <p>
     * 决定生成预签名URL时的操作权限。如果你希望生成用于下载的预签名URL，需要确保生成时指定的是GET操作。
     *
     * @param bucketName 存储桶名称
     * @param fileName   文件名称
     * @return 预签名的URL
     */
    @GetMapping("/presigned-url")
    public ApiResponse<URL> generatePresignedUrl(@RequestParam("bucketName") String bucketName,
                                                 @RequestParam("fileName") String fileName) {
        try {
            boolean flag = fileService.doesFileExist(bucketName, fileName);
            if (flag) {
                return ApiResponse.conflict("文件已存在");
            }
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
            boolean flag = fileService.doesFileExist(bucketName, fileName);
            if (!flag) {
                return ApiResponse.notFound("文件不存在");
            }
            fileService.deleteFile(bucketName, fileName);
            return ApiResponse.success("文件删除成功", null);
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
            boolean flag = fileService.doesFileExist(bucketName, fileName);
            if (flag) {
                return ApiResponse.conflict("文件已存在");
            }

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

    @GetMapping("/rename")
    public ApiResponse<String> renameFile(@RequestParam String bucketName,
                                          @RequestParam String currentFileName,
                                          @RequestParam String newFileName) {
        try {
            boolean flag = fileService.doesFileExist(bucketName, currentFileName);
            if (!flag) {
                return ApiResponse.notFound("文件不存在");
            }
            fileService.renameFile(bucketName, currentFileName, newFileName);
            return ApiResponse.success("文件重命名成功", null);
        } catch (Exception e) {
            return ApiResponse.internalServerError("文件重命名失败: " + e.getMessage());
        }
    }
}