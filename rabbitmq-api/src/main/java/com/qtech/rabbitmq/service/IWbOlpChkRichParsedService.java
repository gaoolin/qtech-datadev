package com.qtech.rabbitmq.service;

import com.qtech.rabbitmq.domain.WbOlpChkRichParsed;

import java.util.List;

/**
 * author :  gaozhilin
 * email  :  gaoolin@gmail.com
 * date   :  2024/06/15 20:37:35
 * desc   :
 */


public interface IWbOlpChkRichParsedService {

    int add(WbOlpChkRichParsed wbOlpChkRichParsed);

    void addBatch(List<WbOlpChkRichParsed> wbOlpChkRichParseds);

}
