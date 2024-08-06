package com.qtech.ceph.file.controller;

import com.ceph.fs.CephStat;
import com.qtech.ceph.file.service.CephFileService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * Controller for handling Ceph file system operations.
 * Provides endpoints for mounting, directory management, file operations, and file uploads/downloads.
 * <p>
 * author : gaozhilin
 * email  : gaoolin@gmail.com
 * date   : 2023/07/14 14:28:35
 */

@Api(tags = "Ceph File System Operations")
@RestController
@RequestMapping("/ceph/file")
public class FileStorageController {

    @Autowired
    private CephFileService cephFileService;

    /**
     * Mounts the Ceph file system.
     *
     * @return true if the file system was successfully mounted, false otherwise
     */
    @ApiOperation(value = "Mount the Ceph file system", notes = "Mounts the Ceph file system.")
    @PostMapping("/mount")
    public Boolean mountCephFileSystem() {
        return cephFileService.mountCephFileSystem();
    }

    /**
     * Creates a directory at the specified path.
     *
     * @param path the path of the directory to be created
     * @return an array of file names in the root directory after creation
     */
    @ApiOperation(value = "Create a directory", notes = "Creates a directory at the specified path.")
    @PostMapping("/directories")
    public String[] createDirectory(@RequestParam("path") String path) {
        return cephFileService.createDirectory(path);
    }

    /**
     * Deletes a directory at the specified path.
     *
     * @param path the path of the directory to be deleted
     * @return an array of file names in the root directory after deletion
     */
    @ApiOperation(value = "Delete a directory", notes = "Deletes a directory at the specified path.")
    @DeleteMapping("/directories")
    public String[] deleteDirectory(@RequestParam("path") String path) {
        return cephFileService.deleteDirectory(path);
    }

    /**
     * Retrieves the status of a file or directory at the specified path.
     *
     * @param path the path of the file or directory
     * @return the status of the file or directory
     */
    @ApiOperation(value = "Get file status", notes = "Retrieves the status of a file or directory at the specified path.")
    @GetMapping("/status")
    public CephStat getFileStatus(@RequestParam("path") String path) {
        return cephFileService.getFileStatus(path);
    }

    /**
     * Reads the content of a file at the specified path.
     *
     * @param path the path of the file
     * @return the content of the file
     */
    @ApiOperation(value = "Read file content", notes = "Reads the content of a file at the specified path.")
    @GetMapping("/files")
    public String readFile(@RequestParam("path") String path) {
        return cephFileService.readFile(path);
    }

    /**
     * Uploads a file to the Ceph file system.
     *
     * @param filePath the local path of the file to be uploaded
     * @param fileName the name of the file to be uploaded
     * @return true if the file was successfully uploaded, false otherwise
     */
    @ApiOperation(value = "Upload a file", notes = "Uploads a file to the Ceph file system.")
    @PostMapping("/files/upload")
    public Boolean uploadFile(@RequestParam("filePath") String filePath, @RequestParam("fileName") String fileName) {
        return cephFileService.uploadFile(filePath, fileName);
    }

    /**
     * Downloads a file from the Ceph file system.
     *
     * @param filePath the local path where the file will be downloaded
     * @param fileName the name of the file to be downloaded
     * @return true if the file was successfully downloaded, false otherwise
     */
    @ApiOperation(value = "Download a file", notes = "Downloads a file from the Ceph file system.")
    @GetMapping("/files/download")
    public Boolean downloadFile(@RequestParam("filePath") String filePath, @RequestParam("fileName") String fileName) {
        return cephFileService.downloadFile(filePath, fileName);
    }

    /**
     * Uploads a file using multipart upload.
     *
     * @param file the multipart file to be uploaded
     * @return true if the file was successfully uploaded, false otherwise
     */
    @ApiOperation(value = "Upload a file (multipart)", notes = "Uploads a file using multipart upload.")
    @PostMapping("/files/upload/multipart")
    public Boolean uploadFile(@RequestParam("file") MultipartFile file) {
        return cephFileService.uploadFile(file);
    }

    /**
     * Downloads a file and writes it to the HTTP response output stream.
     *
     * @param fileName the name of the file to be downloaded
     * @param response the HTTP response to which the file content will be written
     * @throws IOException if an I/O error occurs
     */
    @ApiOperation(value = "Download a file (streaming)", notes = "Downloads a file and writes it to the HTTP response output stream.")
    @GetMapping("/files/download/stream")
    public void downloadFile(@RequestParam("fileName") String fileName, HttpServletResponse response) throws IOException {
        cephFileService.downloadFile(response, fileName);
    }
}
