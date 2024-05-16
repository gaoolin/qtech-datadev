package com.qtech.olp.entity;

import org.junit.jupiter.api.Test;

import static com.qtech.olp.utils.ToCamelCaseConverter.doConvert;

/**
 * author :  gaozhilin
 * email  :  gaoolin@gmail.com
 * date   :  2024/05/14 14:05:57
 * desc   :
 */


class AaListParamsMessageTest {

    @Test
    void underscoreToCamelCase() {
//        String camelCase = doConvert("ClampOnOff");
//        System.out.println(camelCase);

        // 定义包含逗号分隔值的字符串
         String commaSeparatedValues = "Destroy_Start,Init,Grab,ClampOnOff,ReInit,SenserReset,SID,VCM_Hall,VCM_Init,VCM_Hall2,VCMPowerOff,VCMPowerOn,VCM_Top,VCM_TopHall," +
                 "VCM_Z,VCM_ZHall,VCMOISInit,ChartAlignment1,AA1,AA2,MTF_Check,AA3,MTF_Check2,LP_On,LP_OC_Check,LP_OC,LP_On_Blemish,Blemish,LP_Off," +
                 "ChartAlignment,VCMMoveToZ#,Delay,VCMPowerOffCheck,RecordPosition,Dispense,EpoxyInspection_Auto,EpoxyInspection,BackToPosition,UVON," +
                 "Y_Level,UVOFF,GripperOpen,Save_OC,Save_MTF,Destroy,MoveToBlemishPos,MTF_Check3,MTF_OffAxisCheck1,MTF_OffAxisCheck2,MTF_OffAxisCheck3," +
                 "LP_Blemish,ChartAlignment2,VCMMoveToZPos,Z_Offset,Open_Check";
//        String commaSeparatedValues = "VCM_ZHall";

        // 使用split方法按逗号分割字符串并创建数组
        String[] arrayValues = commaSeparatedValues.split(",");

        // 打印数组内容以验证
        for (String value : arrayValues) {
            System.out.println(doConvert(value));
        }
    }
}