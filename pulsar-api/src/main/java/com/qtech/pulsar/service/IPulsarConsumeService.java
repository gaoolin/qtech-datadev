package com.qtech.pulsar.service;

import com.qtech.pulsar.pojo.MessageDto;

/**
 * author :  gaozhilin
 * email  :  gaoolin@gmail.com
 * date   :  2023/08/11 14:13:25
 * desc   :  TODO
 */


public interface IPulsarConsumeService<T> {

    int consume(T msg);

    int starterConsume(T msg);
}
