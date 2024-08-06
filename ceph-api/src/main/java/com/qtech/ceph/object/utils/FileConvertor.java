package com.qtech.ceph.object.utils;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.*;
import java.net.HttpURLConnection;
import java.net.URL;

/**
 * author :  gaozhilin
 * email  :  gaoolin@gmail.com
 * date   :  2023/07/18 09:05:40
 * desc   :  工具类，包含各种常用的实用方法
 */
public class FileConvertor {

    private static final Logger logger = LoggerFactory.getLogger(Utils.class);

    /**
     * 将InputStream转换为byte数组
     *
     * @param input 输入流
     * @return byte数组
     * @throws IOException
     */
    public static byte[] toByteArray(InputStream input) throws IOException {
        ByteArrayOutputStream output = new ByteArrayOutputStream();
        byte[] buffer = new byte[1024 * 4];
        int n;
        while (-1 != (n = input.read(buffer))) {
            output.write(buffer, 0, n);
        }
        return output.toByteArray();
    }

    /**
     * 将图片的URL转换为File对象，并将其保存到临时目录
     *
     * @param url 图片的URL
     * @return File对象
     * @throws Exception
     */
    public static File urlToFile(String url) throws Exception {
        HttpURLConnection httpUrl = (HttpURLConnection) new URL(url).openConnection();
        httpUrl.connect();
        InputStream ins = httpUrl.getInputStream();
        File file = new File(System.getProperty("java.io.tmpdir") + File.separator + "qt");  // System.getProperty("java.io.tmpdir")缓存
        if (file.exists()) {
            file.delete();  // 如果缓存中存在该文件就删除
        }
        try (OutputStream os = new FileOutputStream(file)) {
            byte[] buffer = new byte[8192];
            int bytesRead;
            while ((bytesRead = ins.read(buffer, 0, buffer.length)) != -1) {
                os.write(buffer, 0, bytesRead);
            }
        }
        ins.close();
        return file;
    }

    /**
     * 将File对象转换为byte数组
     *
     * @param file File对象
     * @return byte数组
     */
    public static byte[] fileToByte(File file) {
        try (FileInputStream fileInputStream = new FileInputStream(file)) {
            byte[] imgData = new byte[(int) file.length()];
            fileInputStream.read(imgData);
            return imgData;
        } catch (IOException e) {
            logger.error("Error converting file to byte array: {}", e.getMessage(), e);
        }
        return null;
    }

    /**
     * 读取InputStream并转换为byte数组
     *
     * @param inputStream 输入流
     * @return byte数组
     * @throws IOException
     */
    public static byte[] readInputStreamToByte(InputStream inputStream) throws IOException {
        return toByteArray(inputStream);
    }
}
