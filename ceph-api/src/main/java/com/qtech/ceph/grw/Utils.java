package com.qtech.ceph.grw;

import java.io.*;
import java.net.HttpURLConnection;
import java.net.URL;

/**
 * author :  gaozhilin
 * email  :  gaoolin@gmail.com
 * date   :  2023/07/18 09:05:40
 * desc   :  输入流转字节流
 */


public class Utils {

    public static byte[] toByteArray(InputStream input) throws IOException {
        ByteArrayOutputStream output = new ByteArrayOutputStream();
        byte[] buffer = new byte[1024 * 4];
        int n = 0;
        while (-1 != (n = input.read(buffer))) {
            output.write(buffer, 0, n);
        }
        return output.toByteArray();
    }

    // java将图片的url转换成File，File转换成二进制流byte
    public static File urlToFile(String url) throws Exception {
        HttpURLConnection httpUrl = (HttpURLConnection) new URL(url).openConnection();
        httpUrl.connect();
        InputStream ins = httpUrl.getInputStream();
        File file = new File(System.getProperty("java.io.tmpdir") + File.separator + "qt");  // System.getProperty("java.io.tmpdir")缓存
        if (file.exists()) {
            file.delete();  // 如果缓存中存在该文件就删除
        }
        OutputStream os = new FileOutputStream(file);
        int bytesRead;
        int len = 8192;
        byte[] buffer = new byte[len];
        while ((bytesRead = ins.read(buffer, 0, len)) != -1) {
            os.write(buffer, 0, bytesRead);
        }
        os.close();
        ins.close();
        return file;
    }

    //  将File对象转换为byte[]的形式
    public static byte[] fileToByte(File file) {
        FileInputStream fileInputStream = null;
        byte[] imgData = null;

        try {

            imgData = new byte[(int) file.length()];

            //  read file into bytes[]
            fileInputStream = new FileInputStream(file);
            fileInputStream.read(imgData);

        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (fileInputStream != null) {
                try {
                    fileInputStream.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
        return imgData;
    }
}
