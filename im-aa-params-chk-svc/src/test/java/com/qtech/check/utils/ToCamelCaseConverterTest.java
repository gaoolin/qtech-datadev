package com.qtech.check.utils;

import com.qtech.share.aa.util.ToCamelCaseConverter;
import org.junit.jupiter.api.Test;

/**
 * author :  gaozhilin
 * email  :  gaoolin@gmail.com
 * date   :  2024/07/09 17:29:50
 * desc   :
 */


class ToCamelCaseConverterTest {

    @Test
    void doConvert() {
        String s = ToCamelCaseConverter.doConvert("ClampOnOff");
        System.out.println(s);
    }
}