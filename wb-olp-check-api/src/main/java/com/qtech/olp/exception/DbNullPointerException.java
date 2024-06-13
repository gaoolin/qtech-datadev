package com.qtech.olp.exception;

/**
 * author :  gaozhilin
 * email  :  gaoolin@gmail.com
 * date   :  2023/10/13 10:54:44
 * desc   :
 */


public class DbNullPointerException extends NullPointerException {

    public DbNullPointerException(String msg) {
        super(msg);
    }

    public DbNullPointerException() {
        super("数据库中Job存储时间为空！");
    }
}
