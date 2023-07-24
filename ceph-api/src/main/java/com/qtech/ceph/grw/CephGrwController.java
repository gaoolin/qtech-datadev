package com.qtech.ceph.grw;

import com.amazonaws.services.s3.model.Bucket;
import com.amazonaws.services.s3.model.ObjectListing;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.io.File;
import java.io.InputStream;
import java.net.URL;
import java.util.List;
import java.util.Map;

/**
 * author :  gaozhilin
 * email  :  gaoolin@gmail.com
 * date   :  2023/07/17 08:25:47
 * desc   :  对象存储Controller
 */

@RestController
@RequestMapping(value = "cephgrw/api")
public class CephGrwController {

    @Autowired
    private CephGrwServiceImpl cephGrwService;

    @ApiOperation(value = "BucketList", notes = "BucketList")
    @RequestMapping(value = "bucketlist", method = RequestMethod.GET)
    public List<Bucket> getBucketList() {
        return cephGrwService.getBucketList();
    }

    @ApiOperation(value = "BucketIsCreated", notes = "BucketIsCreated")
    @RequestMapping(value = "/bucketiscreate", method = RequestMethod.GET)
    public boolean getBucketIsCreated() {
        return cephGrwService.getBucketIsCreated();
    }

    @ApiOperation(value = "CurrentDateBucketName", notes = "CurrentDateBucketName")
    @RequestMapping(value = "/currentDateBucketName", method = RequestMethod.GET)
    public String getCurrentDateBucketName() {
        return cephGrwService.getCurrentDateBucketName();
    }

    @ApiOperation(value = "ObjectListing", notes = "ObjectListing")
    @RequestMapping(value = "/objectlisting", method = RequestMethod.GET)
    public ObjectListing getObjectListing(String bucketName) {
        return cephGrwService.getObjectListing(bucketName);
    }

    @ApiOperation(value = "CreateBucket", notes = "CreateBucket")
    @RequestMapping(value = "/createbucket", method = RequestMethod.GET)
    public String createBucket(@RequestParam(value = "bucketName", required = false) String bucketName) {
        if (bucketName == null) {
            return cephGrwService.createBucket();
        }
        return cephGrwService.createBucket(bucketName);
    }

    @ApiOperation(value = "DeleteBucket", notes = "DeleteBucket")
    @RequestMapping(value = "/deletebucket", method = RequestMethod.GET)
    public void deleteBucket(String bucket) {
        cephGrwService.deleteBucket(bucket);
    }

    @ApiOperation(value = "ModifyPub", notes = "ModifyPub")
    @RequestMapping(value = "/modifypub", method = RequestMethod.GET)
    public void modifyPub(String bucketName, String fileName) {
        cephGrwService.modifyPub(bucketName, fileName);
    }

    @ApiOperation(value = "DownloadFile", notes = "DownloadFile")
    @RequestMapping(value = "/downloadFile", method = RequestMethod.GET)
    public void downloadFile(@PathVariable String bucketName, @PathVariable String keyName, @PathVariable String dirName) {
        cephGrwService.downloadFile(bucketName, keyName, dirName);
    }

    @ApiOperation(value = "DeleteObject", notes = "DeleteObject")
    @RequestMapping(value = "/deleteObject", method = RequestMethod.GET)
    public void deleteObject(String bucketName, String fileName) {
        cephGrwService.deleteObject(bucketName, fileName);
    }

    @ApiOperation(value = "GetUrl", notes = "GetUrl")
    @RequestMapping(value = "/url", method = RequestMethod.GET)
    public URL getUrl(String bucketName, String keyName) {
        return cephGrwService.geturl(bucketName, keyName);
    }

    @ApiOperation(value = "UploadFileToUrl", notes = "UploadFileToUrl")
    @RequestMapping(value = "/uploadFileToUrl", method = RequestMethod.GET)
    public URL uploadFileToUrl(String bucketName, File file, String keyName) {
        return cephGrwService.uploadFileToUrl(bucketName, file, keyName);
    }

    @ApiOperation(value = "UploadInputStream", notes = "UploadInputStream")
    @RequestMapping(value = "/uploadInputStream", method = RequestMethod.GET)
    public void uploadInputStream(String bucketName, String fileName, InputStream input) {
        cephGrwService.uploadInputStream(bucketName, fileName, input);
    }

    @ApiOperation(value = "UploadByte", notes = "UploadByte")
    @RequestMapping(value = "/uploadByte", method = RequestMethod.POST)
    public void uploadByte(@RequestBody Map<String,Object> paramMap) {
        String bucketName = (String) paramMap.get("bucketName");
        String fileName = (String) paramMap.get("fileName");
        byte[] contents = (byte[]) paramMap.get("contents");
        cephGrwService.uploadByte(bucketName, fileName, contents);
    }

    @ApiOperation(value = "ReadStreamObject", notes = "ReadStreamObject")
    @RequestMapping(value = "/readStreamObject", method = RequestMethod.GET)
    public InputStream readStreamObject(String bucketName, String fileName) {
        return cephGrwService.readStreamObject(bucketName, fileName);
    }

    @ApiOperation(value = "DownloadObjectByByte", notes = "DownloadObjectByByte")
    @RequestMapping(value = "/dwnloadObjectByByte", method = RequestMethod.GET)
    public byte[] DownloadObjectByByte(String bucketName, String fileName) {
        return cephGrwService.downloadObjectByByte(bucketName, fileName);
    }
}
