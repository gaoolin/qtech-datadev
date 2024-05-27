package com.qtech.check.utils;

import java.util.concurrent.Future;

/**
 * author :  gaozhilin
 * email  :  gaoolin@gmail.com
 * date   :  2024/05/13 13:45:36
 * desc   :
 */


public class SendResultWrapper {
    private Future<Integer> status;

    public SendResultWrapper(Future<Integer> status) {
        this.status = status;
    }

    public Future<Integer> getStatus() {
        return status;
    }
}
