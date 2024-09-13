package com.qtech.check.processor.handler;

import org.apache.commons.codec.DecoderException;

/**
 * author :  gaozhilin
 * email  :  gaoolin@gmail.com
 * date   :  2024/05/28 13:30:35
 * desc   :
 */


public interface QtechBaseHandler<T> {


    default <R> R handleByType(Class<R> clazz, String line) throws DecoderException {
        return null;
    }

    default <U> boolean supportsType(Class<U> clazz) {
        return false;
    }

    default String getMessageType() {
        return this.getClass().getSimpleName().replace("Handler", "");
    }
}
