package com.qtech.pulsar.service;

/**
 * author :  gaozhilin
 * email  :  gaoolin@gmail.com
 * date   :  2023/08/11 14:09:53
 * desc   :  TODO
 */


public interface IPulsarProducerService<T> {

    int send(String topic, T msg);

    int sendAsync(String topic, T msg);
}
