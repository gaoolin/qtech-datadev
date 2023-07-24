package com.qtech.ceph.fs.controller;

import com.ceph.fs.CephStat;
import com.qtech.ceph.fs.service.CephFsServiceImpl;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletResponse;

/**
 * author :  gaozhilin
 * email  :  gaoolin@gmail.com
 * date   :  2023/07/14 14:28:35
 * desc   :  文件存储相关操作
 */


@RestController
@RequestMapping(value = "/cephfs/api")
public class CephFsController {

    //    private CephFsServiceImpl cephFsService = new CephFsServiceImpl();
    @Autowired
    private CephFsServiceImpl cephFsService;

    @ApiOperation(value = "Mount", notes = "Mount")
    @RequestMapping(value = "/mount", method = RequestMethod.GET)
    public Boolean mountCephFsByRoot() {
        return cephFsService.mountCephFsByRoot();
    }

    @ApiOperation(value = "CreateDir", notes = "CreateDir")
    @RequestMapping(value = "/createdir", method = RequestMethod.POST)
    public String[] createDirByPath(@RequestParam(value = "DirPath") String path) {
        return cephFsService.createDirByPath(path);
    }

    @ApiOperation(value = "DeleteDir", notes = "DeleteDir")
    @RequestMapping(value = "/deletedir", method = RequestMethod.DELETE)
    public String[] deleteDirByPath(@RequestParam(value = "DirPath") String path) {
        return cephFsService.deleteDirByPath(path);
    }

    @ApiOperation(value = "FileStatus", notes = "FileStatus")
    @RequestMapping(value = "/getfilestatus", method = RequestMethod.GET)
    public CephStat getFileStatusByPath(@RequestParam(value = "DirPath") String path) {
        return cephFsService.getFileStatusByPath(path);
    }

    @ApiOperation(value = "FileContext", notes = "FileContext")
    @RequestMapping(value = "/getfilecontext", method = RequestMethod.GET)
    public String readFileByPath(@RequestParam(value = "DirPath") String path) {
        return cephFsService.readFileByPath(path);
    }

    @ApiOperation(value = "UploadFile", notes = "UploadFile")
    @RequestMapping(value = "/uploadfile", method = RequestMethod.GET)
    public boolean uploadFileByPath(String filePath, String fileName) {
        return cephFsService.uploadFileByPath(filePath, fileName);
    }

    @ApiOperation(value = "DownloadFile", notes = "DownloadFile")
    @RequestMapping(value = "/downloadfile", method = RequestMethod.GET)
    public Boolean downloadFileByPath(String filePath, String fileName) {
        return cephFsService.downloadFileByPath(filePath, fileName);
    }

    @ApiOperation(value = "UploadFile2", notes = "UploadFile2")
    @RequestMapping(value = "/uploadfile2", method = RequestMethod.POST)
    public Boolean uploadFileByMultipart(MultipartFile uploadFile) {
        return cephFsService.uploadFileByMultipart(uploadFile);
    }

    @ApiOperation(value = "DownloadFile", notes = "DownloadFile")
    @RequestMapping(value = "/downloadfile2", method = RequestMethod.GET)
    public void downloadFile(String fileName, HttpServletResponse res) {
        cephFsService.downloadFile(fileName, res);
    }
}
