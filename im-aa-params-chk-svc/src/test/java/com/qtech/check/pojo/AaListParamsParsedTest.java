package com.qtech.check.pojo;

import com.google.common.collect.Maps;
import com.qtech.share.aa.util.ToCamelCaseConverter;
import org.apache.commons.codec.DecoderException;
import org.apache.commons.codec.binary.Hex;
import org.junit.jupiter.api.Test;

import java.util.Arrays;
import java.util.Map;
import java.util.Objects;

/**
 * author :  gaozhilin
 * email  :  gaoolin@gmail.com
 * date   :  2024/05/14 14:05:57
 * desc   :
 */


class AaListParamsParsedTest {

    @Test
    void underscoreToCamelCase() {
//        String camelCase = doConvert("ClampOnOff");
//        System.out.println(camelCase);

        // 定义包含逗号分隔值的字符串
//        String commaSeparatedValues = "Destroy_Start,Init,Grab,ClampOnOff,ReInit,SenserReset,SID,VCM_Hall,VCM_Init,VCM_Hall2,VCMPowerOff,VCMPowerOn,VCM_Top,VCM_TopHall," +
//                "VCM_Z,VCM_ZHall,VCMOISInit,ChartAlignment1,AA1,AA2,MTF_Check,AA3,MTF_Check2,LP_On,LP_OC_Check,LP_OC,LP_On_Blemish,Blemish,LP_Off," +
//                "ChartAlignment,VCMMoveToZ#,Delay,VCMPowerOffCheck,RecordPosition,Dispense,EpoxyInspection_Auto,EpoxyInspection,BackToPosition,UVON," +
//                "Y_Level,UVOFF,GripperOpen,Save_OC,Save_MTF,Destroy,MoveToBlemishPos,MTF_Check3,MTF_OffAxisCheck1,MTF_OffAxisCheck2,MTF_OffAxisCheck3," +
//                "LP_Blemish,ChartAlignment2,VCMMoveToZPos,Z_Offset,Open_Check";
        String commaSeparatedValues = "xResMin, xResMax";

        // 使用split方法按逗号分割字符串并创建数组
        String[] arrayValues = commaSeparatedValues.split(",");

        // 打印数组内容以验证
        for (String value : arrayValues) {
            System.out.println(ToCamelCaseConverter.doConvert(value));
        }
    }

    @Test
    void comparisonTest() {

        String modelVal = null;
        String actualVal = null;
        String propertyName = "propertyName";
        Map<String, Map.Entry<Object, Object>> inconsistentProperties = Maps.newHashMap();
        Map<String, Object> emptyInStandard = Maps.newHashMap();
        Map<String, Object> emptyInActual = Maps.newHashMap();
        if (!Objects.equals(modelVal, actualVal)) {
            // 使用逻辑运算符简化条件判断，减少代码重复
            if (modelVal == null) {
                // 当modelVal为null，且actualVal不为null时，添加到emptyInStandard
                if (actualVal != null) {
                    emptyInStandard.put(propertyName, null);
                }
            } else if (actualVal == null) {
                // 当modelVal不为null，且actualVal为null时，添加到emptyInActual
                emptyInActual.put(propertyName, null);
            } else {
                // 当modelVal和actualVal均不为null且不相等时，添加到inconsistentProperties
                inconsistentProperties.put(propertyName, Maps.immutableEntry(modelVal, actualVal));
            }
        }
        System.out.println(inconsistentProperties);
        System.out.println(emptyInActual);
        System.out.println(emptyInStandard);
        System.out.println(modelVal);
        System.out.println(actualVal);
    }


    @Test
    void test() {

        String hexString = "09A234567812345678";

        try {
            byte[] byteArray = Hex.decodeHex(hexString.toCharArray());
            System.out.println(Arrays.toString(byteArray));
        } catch (DecoderException e) {
            e.printStackTrace();
        }
    }

}
