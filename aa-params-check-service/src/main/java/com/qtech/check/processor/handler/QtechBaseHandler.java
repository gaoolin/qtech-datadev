package com.qtech.check.processor.handler;

import org.apache.commons.codec.DecoderException;

/**
 * author :  gaozhilin
 * email  :  gaoolin@gmail.com
 * date   :  2024/05/28 13:30:35
 * desc   :
 */


public interface QtechBaseHandler<T> {


    <R> R handleByType(Class<R> clazz, String line) throws DecoderException;

    <U> boolean supportsType(Class<U> clazz);

    default String getMessageType() {
        return this.getClass().getSimpleName().replace("Handler", "");
    }
}
