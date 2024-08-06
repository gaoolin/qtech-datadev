package com.qtech.ceph.file.service;

import com.ceph.fs.CephMount;
import com.ceph.fs.CephStat;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletResponse;
import java.io.*;

/**
 * author :  gaozhilin
 * email  :  gaoolin@gmail.com
 * date   :  2023/07/14 13:57:20
 * desc   :  文件存储相关方法
 */


@Service
public class CephFsServiceImpl {
    Logger logger = LoggerFactory.getLogger(CephFsServiceImpl.class);
    private CephMount mount = null;

    public Boolean mountCephFsByRoot() {
        try {
            this.mount = new CephMount("admin");
            this.mount.conf_set("mon_host", "10.96.179.58;10.96.141.70;10.96.137.48");
            //System.out.println(mount.conf_get("mon_host"));
            this.mount.conf_set("key", "AQDztv1j2RZSOxAAb+KA/gKvLu8e4P1GR+C3AQ==");
            this.mount.mount("/");
            return true;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }

    public String[] createDirByPath(String path) {
        String[] dirList = null;
        try {
            if (this.mount == null) {
                return null;
            }
            this.mount.mkdirs(path, 0777);
            dirList = this.mount.listdir("/");
            return dirList;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    public String[] deleteDirByPath(String path) {
        String[] dirList = null;
        try {
            if (this.mount == null) {
                return null;
            }
            this.mount.rmdir(path);
            dirList = this.mount.listdir("/");
            return dirList;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    public CephStat getFileStatusByPath(String path) {
        CephStat stat = new CephStat();
        try {
            if (this.mount == null) {
                return null;
            }
            this.mount.lstat(path, stat);
            return stat;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    public String readFileByPath(String path) {
        CephStat stat = new CephStat();
        String context = null;
        try {
            if (this.mount == null) {
                return null;
            }
            int fd = this.mount.open(path, CephMount.O_RDONLY, 0);
            this.mount.fstat(fd, stat);
            byte[] buffer = new byte[(int) stat.size];
            this.mount.read(fd, buffer, stat.size, 0);
            context = new String(buffer);
            this.mount.close(fd);
            return context;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    public Boolean uploadFileByPath(String filePath, String fileName) {

        // exit with null if not mount
        if (this.mount == null) {
            logger.info("Ceph fs not mount!");
            return null;
        }

        // file definition
        char pathChar = File.separatorChar;
        String fileFullName = "";
        Long fileLength = 0l;
        Long uploadedLength = 0l;
        File file = null;

        // Io
        FileInputStream fis = null;

        // get local file info
        fileFullName = filePath + pathChar + fileName;
        logger.info("fileInfo:{}", fileFullName);
        file = new File(fileFullName);
        if (!file.exists()) {
            logger.info("File not exist!");
            return false;
        }
        fileLength = file.length();

        // get io from local file
        try {
            fis = new FileInputStream(file);
        } catch (FileNotFoundException e) {
            logger.info("Read local file failed!");
            e.printStackTrace();
        }

        // if file exists or not
        String[] dirList = null;
        Boolean fileExist = false;
        try {
            dirList = this.mount.listdir("/");
            for (String fileInfo : dirList) {
                logger.info("fileInfo:{}", dirList);
                if (fileInfo.equals(fileName)) {
                    fileExist = true;
                }
            }
        } catch (FileNotFoundException e) {
            logger.info("File Path not exist!");
            e.printStackTrace();
        }

        // transfer file by diff pattern
        if (!fileExist) {
            try {
                // create file and set mode WRITE
                this.mount.open(fileName, CephMount.O_CREAT, 0);
                int fd = this.mount.open(fileName, CephMount.O_RDWR, 0);

                // start transfer
                int length = 0;
                byte[] bytes = new byte[1024];
                while ((length = fis.read(bytes, 0, bytes.length)) != -1) {
                    // write
                    this.mount.write(fd, bytes, length, uploadedLength);

                    // update length
                    uploadedLength += length;

                    // output transfer rate
                    float rate = (float) uploadedLength * 100 / (float) fileLength;
                    String rateValue = (int) rate + "%";
                    System.out.println(rateValue);

                    // complete flag
                    if (uploadedLength == fileLength) {
                        break;
                    }
                }
                System.out.println("文件传输成功！");

                // chmod
                this.mount.fchmod(fd, 0666);

                // close
                this.mount.close(fd);
                if (fis != null) {
                    fis.close();
                }
                return true;
            } catch (Exception e) {
                logger.info("File transfer failed!");
                e.printStackTrace();
            }
        } else if (fileExist) {
            try {
                // get file length
                CephStat stat = new CephStat();
                this.mount.stat(fileName, stat);
                uploadedLength = stat.size;
                int fd = this.mount.open(fileName, CephMount.O_RDWR, 0);

                // start transfer
                int length = 0;
                byte[] bytes = new byte[1024];
                fis.skip(uploadedLength);
                while ((length = fis.read(bytes, 0, bytes.length)) != -1) {
                    // write
                    this.mount.write(fd, bytes, length, uploadedLength);

                    // update length
                    uploadedLength += length;

                    // output transfer rate
                    float rate = (float) uploadedLength * 100 / (float) fileLength;
                    String rateValue = (int) rate + "%";
                    System.out.println(rateValue);

                    // complete flag
                    if (uploadedLength == fileLength) {
                        break;
                    }
                }
                System.out.println("断点文件传输成功！");

                // chmod
                this.mount.fchmod(fd, 0666);

                // close
                this.mount.close(fd);
                if (fis != null) {
                    fis.close();
                }
                return true;
            } catch (Exception e) {
                logger.info("BreakPoint transfer failed!");
                e.printStackTrace();
            }
        } else {
            try {
                if (fis != null) {
                    fis.close();
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
            return false;
        }

        return false;
    }

    public Boolean downloadFileByPath(String filePath, String fileName) {

        // exit with null if not mount
        if (this.mount == null) {
            logger.info("Ceph fs not mount!");
            return null;
        }

        // file definition
        char pathChar = File.separatorChar;
        String fileFullName = "";
        Long fileLength = 0l;
        Long downloadedLength = 0l;
        File file = null;

        // IO
        FileOutputStream fos = null;
        RandomAccessFile raf = null;

        // new file object
        fileFullName = filePath + pathChar + fileName;
        file = new File(fileFullName);

        // get cephfs file size
        try {
            CephStat stat = new CephStat();
            this.mount.stat(fileName, stat);
            fileLength = stat.size;
        } catch (Exception e) {
            logger.info("Fail to get file size.");
            e.printStackTrace();
        }

        if (fileLength != 0) {
            if (!file.exists()) {
                // download file
                int length = 10240;
                byte[] bytes = new byte[length];
                try {
                    int fd = this.mount.open(fileName, CephMount.O_RDONLY, 0);
                    fos = new FileOutputStream(file);
                    float rate = 0;
                    String rateValue = "";
                    while ((fileLength - downloadedLength) >= length && (this.mount.read(fd, bytes, (long) length, downloadedLength)) != -1) {
                        fos.write(bytes, 0, length);
                        fos.flush();
                        downloadedLength += (long) length;

                        // output transfer rate
                        rate = (float) downloadedLength * 100 / (float) fileLength;
                        rateValue = (int) rate + "%";
                        System.out.println(rateValue);

                        if (downloadedLength == fileLength) {
                            break;
                        }
                    }
                    if (downloadedLength != fileLength) {
                        this.mount.read(fd, bytes, fileLength - downloadedLength, downloadedLength);
                        fos.write(bytes, 0, (int) (fileLength - downloadedLength));
                        fos.flush();
                        downloadedLength = fileLength;

                        // output transfer rate
                        rate = (float) downloadedLength * 100 / (float) fileLength;
                        rateValue = (int) rate + "%";
                        System.out.println(rateValue);
                    }

                    System.out.println("Download Success!");
                    fos.close();
                    this.mount.close(fd);
                    return true;
                } catch (Exception e) {
                    logger.info("First download fail!");
                    e.printStackTrace();
                }
            } else if (file.exists()) {
                // download file
                int length = 10240;
                byte[] bytes = new byte[length];
                Long filePoint = file.length();
                try {
                    int fd = this.mount.open(fileName, CephMount.O_RDONLY, 0);
                    raf = new RandomAccessFile(file, "rw");
                    raf.seek(filePoint);
                    downloadedLength = filePoint;
                    float rate = 0;
                    String rateValue = "";
                    while ((fileLength - downloadedLength) >= length && (this.mount.read(fd, bytes, (long) length, downloadedLength)) != -1) {
                        raf.write(bytes, 0, length);
                        downloadedLength += (long) length;

                        // output transfer rate
                        rate = (float) downloadedLength * 100 / (float) fileLength;
                        rateValue = (int) rate + "%";
                        System.out.println(rateValue);

                        if (downloadedLength == fileLength) {
                            break;
                        }
                    }
                    if (downloadedLength != fileLength) {
                        this.mount.read(fd, bytes, fileLength - downloadedLength, downloadedLength);
                        raf.write(bytes, 0, (int) (fileLength - downloadedLength));
                        downloadedLength = fileLength;

                        // output transfer rate
                        rate = (float) downloadedLength * 100 / (float) fileLength;
                        rateValue = (int) rate + "%";
                        System.out.println(rateValue);
                    }

                    System.out.println("Cut Point Download Success!");
                    raf.close();
                    this.mount.close(fd);
                    return true;
                } catch (Exception e) {
                    logger.info("Continue download fail!");
                    e.printStackTrace();
                }
            } else {
                logger.info("Unknown Error!");
                return false;
            }
        }
        return false;
    }

    public Boolean uploadFileByMultipart(MultipartFile uploadFile) {
        String fileName = uploadFile.getOriginalFilename();
        InputStream inputStream = null;
        try {
            inputStream = uploadFile.getInputStream();
        } catch (IOException e) {
            e.printStackTrace();
        }
        // exit with null if not mount
        if (this.mount == null) {
            logger.info("Ceph fs not mount!");
            return null;
        }

        // file definition
        char pathChar = File.separatorChar;
        String fileFullName = "";
        Long fileLength = 0l;
        fileLength = uploadFile.getSize();
        Long uploadedLength = 0l;

        // if file exists or not
        String[] dirList = null;
        Boolean fileExist = false;
        try {
            dirList = this.mount.listdir("/");
            for (String fileInfo : dirList) {
                logger.info("fileInfo:{}", dirList);
                if (fileInfo.equals(fileName)) {
                    fileExist = true;
                }
            }
        } catch (FileNotFoundException e) {
            logger.info("File Path not exist!");
            e.printStackTrace();
        }

        // transfer file by diff pattern
        if (!fileExist) {
            try {
                // create file and set mode WRITE
                this.mount.open(fileName, CephMount.O_CREAT, 0);
                int fd = this.mount.open(fileName, CephMount.O_RDWR, 0);

                // start transfer
                int length = 0;
                byte[] bytes = new byte[1024];
                while ((length = inputStream.read(bytes, 0, bytes.length)) != -1) {
                    // write
                    this.mount.write(fd, bytes, length, uploadedLength);

                    // update length
                    uploadedLength += length;

                    // output transfer rate
                    float rate = (float) uploadedLength * 100 / (float) fileLength;
                    String rateValue = (int) rate + "%";
                    System.out.println(rateValue);

                    // complete flag
                    if (uploadedLength == fileLength) {
                        break;
                    }
                }
                System.out.println("文件传输成功！");

                // chmod
                this.mount.fchmod(fd, 0666);

                // close
                this.mount.close(fd);
                if (inputStream != null) {
                    inputStream.close();
                }
                return true;
            } catch (Exception e) {
                logger.info("File transfer failed!");
                e.printStackTrace();
            }
        } else if (fileExist) {
            try {
                // get file length
                CephStat stat = new CephStat();
                this.mount.stat(fileName, stat);
                uploadedLength = stat.size;
                int fd = this.mount.open(fileName, CephMount.O_RDWR, 0);

                // start transfer
                int length = 0;
                byte[] bytes = new byte[1024];
                inputStream.skip(uploadedLength);
                while ((length = inputStream.read(bytes, 0, bytes.length)) != -1) {
                    // write
                    this.mount.write(fd, bytes, length, uploadedLength);

                    // update length
                    uploadedLength += length;

                    // output transfer rate
                    float rate = (float) uploadedLength * 100 / (float) fileLength;
                    String rateValue = (int) rate + "%";
                    System.out.println(rateValue);

                    // complete flag
                    if (uploadedLength == fileLength) {
                        break;
                    }
                }
                System.out.println("断点文件传输成功！");

                // chmod
                this.mount.fchmod(fd, 0666);

                // close
                this.mount.close(fd);
                if (inputStream != null) {
                    inputStream.close();
                }
                return true;
            } catch (Exception e) {
                logger.info("BreakPoint transfer failed!");
                e.printStackTrace();
            }
        } else {
            try {
                if (inputStream != null) {
                    inputStream.close();
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
            return false;
        }
        return false;
    }


    public void downloadFile(String fileName, HttpServletResponse resp) {
        resp.setHeader("content-type", "application/octet-stream");
        resp.setContentType("application/octet-stream");
        resp.setHeader("Content-Disposition", "attachment;filename=" + fileName);
        // exit with null if not mount
        if (this.mount == null) {
            logger.info("Ceph fs not mount!");
            return;
        }

        // file definition
        Long fileLength = 0l;
        Long downloadedLength = 0l;


        // IO
        OutputStream os = null;
        RandomAccessFile raf = null;
        try {
            os = resp.getOutputStream();
        } catch (IOException e) {
            e.printStackTrace();
        }

        // get cephfs file size
        try {
            CephStat stat = new CephStat();
            this.mount.stat(fileName, stat);
            fileLength = stat.size;
        } catch (Exception e) {
            logger.info("Fail to get file size.");
            e.printStackTrace();
        }

        if (fileLength != 0) {

            // download file
            int length = 10240;
            byte[] bytes = new byte[length];
            try {
                int fd = this.mount.open(fileName, CephMount.O_RDONLY, 0);
                float rate = 0;
                String rateValue = "";
                while ((fileLength - downloadedLength) >= length && (this.mount.read(fd, bytes, (long) length, downloadedLength)) != -1) {
                    os.write(bytes, 0, length);
                    os.flush();
                    downloadedLength += (long) length;

                    // output transfer rate
                    rate = (float) downloadedLength * 100 / (float) fileLength;
                    rateValue = (int) rate + "%";
                    System.out.println(rateValue);

                    if (downloadedLength == fileLength) {
                        break;
                    }
                }
                if (downloadedLength != fileLength) {
                    this.mount.read(fd, bytes, fileLength - downloadedLength, downloadedLength);
                    os.write(bytes, 0, (int) (fileLength - downloadedLength));
                    os.flush();
                    downloadedLength = fileLength;
                    // output transfer rate
                    rate = (float) downloadedLength * 100 / (float) fileLength;
                    rateValue = (int) rate + "%";
                    System.out.println(rateValue);
                }

                System.out.println("Download Success!");
                os.close();
                this.mount.close(fd);
                return;
            } catch (Exception e) {
                logger.info("First download fail!");
                e.printStackTrace();
            }
        }
    }
}
