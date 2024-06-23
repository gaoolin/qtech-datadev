package com.qtech.check.processor.handler.type;

import com.qtech.check.processor.handler.QtechBaseHandler;

/**
 * author :  gaozhilin
 * email  :  gaoolin@gmail.com
 * date   :  2024/05/28 11:44:45
 * desc   :
 */


public abstract class AaListCommandHandler<T> implements QtechBaseHandler<T> {

    public abstract T handle(String[] parts);
}
