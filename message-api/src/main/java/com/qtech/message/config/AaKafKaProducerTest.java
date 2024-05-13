package com.qtech.message.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.support.SendResult;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import org.springframework.util.concurrent.ListenableFuture;

/**
 * author :  gaozhilin
 * email  :  gaoolin@gmail.com
 * date   :  2024/05/11 16:16:01
 * desc   :
 */

@Component
@EnableScheduling
public class AaKafKaProducerTest {

    @Autowired
    private KafkaTemplate<String, Object> kafkaTemplate;

    /**
     * 定时任务
     */
    // @Scheduled(cron = "0/1 * * * * ?")
    public void send() {
        ListenableFuture<SendResult<String, Object>> future = kafkaTemplate.send("qtech_im_aa_list_topic",
                "{'OpCode': '864735050116133', 'WoCode': 'C3PS66#', 'FactoryName': 'LIST\t1\tInit\t\tTester\t\t5\t\tContinue\tEnable\t\n" +
                        "LIST\t2\tClampOnOff\tMotion\t\tContinue\tStop    \tEnable\t\n" +
                        "LIST\t3\tReInit\t\tTester\t\tContinue\t-2\t\tEnable\t\n" +
                        "LIST\t4\tPRToBond\tMotion\t\tContinue\t-2\t\tEnable\t\n" +
                        "LIST\t5\tSID\t\tTester\t\tContinue\tContinue\tEnable\t\n" +
                        "LIST\t6\tAA1\t\tActiveAlign\t8\t\tContinue\tEnable\t\n" +
                        "LIST\t7\tAA2\t\tActiveAlign\tContinue\tStop    \tEnable\t\n" +
                        "LIST\t8\tLP_ON_OC\tMotion\t\tContinue\tStop    \tEnable\t\n" +
                        "LIST\t9\tOpticCenter\tCenterAlign\tContinue\tStop    \tEnable\t\n" +
                        "LIST\t10\tLP_ON_Blemish\tMotion\t\tContinue\tStop    \tEnable\t\n" +
                        "LIST\t11\tBlemish\t\tTester\t\tContinue\tStop    \tEnable\t\n" +
                        "LIST\t12\tLP_OFF\t\tMotion\t\tContinue\tStop    \tEnable\t\n" +
                        "LIST\t13\tRecordPosition\tMotion\t\tContinue\tStop    \tEnable\t\n" +
                        "LIST\t14\tDispense\tMotion\t\tContinue\tStop    \tEnable\t\n" +
                        "LIST\t15\tEpoxyInspection Auto\tMotion\t\t17\t\tContinue\tEnable\t\n" +
                        "LIST\t16\tEpoxyInspection\tMotion\t\tContinue\tStop    \tEnable\t\n" +
                        "LIST\t17\tBackToPosition\tMotion\t\tContinue\tStop    \tEnable\t\n" +
                        "LIST\t18\tUVON\t\tSync Motion\tContinue\tStop    \tEnable\t\n" +
                        "LIST\t19\tReinit2\t\tTester\t\tContinue\tContinue\tEnable\t\n" +
                        "LIST\t20\tUVOFF\t\tSync End\tContinue\tStop    \tEnable\t\n" +
                        "LIST\t21\tGripper Open\tMotion\t\tContinue\tStop    \tEnable\t\n" +
                        "LIST\t22\tOC_Check\tTester\t\tContinue\tStop    \tEnable\t\n" +
                        "PRE\t0\n" +
                        "PRE2\t0\n" +
                        "POST\t51\tTester\t\n" +
                        "POST2\t52\tMotion\t\n" +
                        "\n" +
                        "ITEM\t1\tCOMMAND\t\tDestroy+Init\n" +
                        "ITEM\t1\tCOMPARE_MAX\t\t\n" +
                        "ITEM\t1\tCOMPARE_MIN\t\t\n" +
                        "ITEM\t1\tCONNECTION_OPTION\t\t0\n" +
                        "ITEM\t1\tSEPERATE_STATION_MAX_MIN_SETTING\t\t0\n" +
                        "ITEM\t1\tRESULT\t\tinit\tCheck\t100.00\t1.00\tLog\t100.00\t100.00\t100.00\t100.00\t0.00\t0.00\t0.00\t0.00\tNoCompare\t0.00\t0.00\t0.00\n" +
                        "\n" +
                        "ITEM\t2\tUTXYZMove #X #Y -5000\n" +
                        "ITEM\t2\tCLAMP OFF\n" +
                        "ITEM\t2\tSUTVAC OFF\n" +
                        "ITEM\t2\tDelay 200\n" +
                        "ITEM\t2\tSUTVAC ON\n" +
                        "ITEM\t2\tCLAMP ON\n" +
                        "\n" +
                        "ITEM\t3\tCOMMAND\t\tinit\n" +
                        "ITEM\t3\tCOMPARE_MAX\t\t\n" +
                        "ITEM\t3\tCOMPARE_MIN\t\t\n" +
                        "ITEM\t3\tCONNECTION_OPTION\t\t0\n" +
                        "ITEM\t3\tSEPERATE_STATION_MAX_MIN_SETTING\t\t0\n" +
                        "ITEM\t3\tRESULT\t\tinit\tCheck\t100.00\t1.00\tLog\t100.00\t100.00\t100.00\t100.00\t0.00\t0.00\t0.00\t0.00\tNoCompare\t0.00\t0.00\t0.00\n" +
                        "\n" +
                        "ITEM\t4\tPRToBond\n" +
                        "\n" +
                        "ITEM\t5\tCOMMAND\t\tSensorID\n" +
                        "ITEM\t5\tCOMPARE_MAX\t\t\n" +
                        "ITEM\t5\tCOMPARE_MIN\t\t\n" +
                        "ITEM\t5\tCONNECTION_OPTION\t\t0\n" +
                        "ITEM\t5\tSEPERATE_STATION_MAX_MIN_SETTING\t\t1\n" +
                        "ITEM\t5\tRESULT\t\tResult\tNoChk\t999999999999999980000000000000000000000000000000000000000000000000000000000000000000000000000000000000.00\t999999999999999980000000.00\tLog\t999999999999999930000000000000000000000000000.00\t10000000000000001000000000000000000000000000000000000000000000000000000.00\t999999999999999930000000000000000000000000000000000000000000000000000000000.00\t999999999999999950000000000000000000000000000000000000000000.00\t-1000000000000000100000000000000000000000000000000000000.00\t-99999999999999997000000000000000000000000000000000000000000.00\t-999999999999999930000000000000000000000000000.00\t-1000000000000000000000000000000000000000000.00\tNoCompare\t0.00\t0.00\t0.00\n" +
                        "\n" +
                        "ITEM\t6\tCOMMAND\t\tGRAB3+MTF1\n" +
                        "ITEM\t6\tROI\t\tCC\t100.00\t65.00\t60.00\t1\t1\n" +
                        "ITEM\t6\tROI\t\tUL\t100.00\t35.00\t28.00\t1\t2\n" +
                        "ITEM\t6\tROI\t\tUR\t100.00\t35.00\t28.00\t1\t3\n" +
                        "ITEM\t6\tROI\t\tLL\t100.00\t35.00\t28.00\t1\t4\n" +
                        "ITEM\t6\tROI\t\tLR\t100.00\t35.00\t28.00\t1\t5\n" +
                        "ITEM\t6\tROI\t\t[1]\t100.00\t32.00\t-1.00\t0\t6\n" +
                        "ITEM\t6\tROI\t\t[2]\t100.00\t32.00\t-1.00\t0\t7\n" +
                        "ITEM\t6\tROI\t\t[3]\t100.00\t32.00\t-1.00\t0\t8\n" +
                        "ITEM\t6\tROI\t\t[4]\t100.00\t32.00\t-1.00\t0\t9\n" +
                        "ITEM\t6\tROI\t\t[5]\t100.00\t32.00\t-1.00\t0\t10\n" +
                        "ITEM\t6\tROI\t\t[6]\t100.00\t32.00\t-1.00\t0\t11\n" +
                        "ITEM\t6\tROI\t\t[7]\t100.00\t32.00\t-1.00\t0\t12\n" +
                        "ITEM\t6\tROI\t\t[8]\t100.00\t32.00\t-1.00\t0\t13\n" +
                        "ITEM\t6\tROI\t\t[9]\t100.00\t63.00\t-1.00\t0\t14\n" +
                        "ITEM\t6\tROI\t\t[10]\t100.00\t63.00\t-1.00\t0\t15\n" +
                        "ITEM\t6\tROI\t\t[11]\t100.00\t63.00\t-1.00\t0\t16\n" +
                        "ITEM\t6\tROI\t\t[12]\t100.00\t63.00\t-1.00\t0\t17\n" +
                        "ITEM\t6\tROI\t\t[13]\t100.00\t32.00\t-1.00\t0\t18\n" +
                        "ITEM\t6\tROI\t\t[14]\t100.00\t32.00\t-1.00\t0\t19\n" +
                        "ITEM\t6\tROI\t\t[15]\t100.00\t32.00\t-1.00\t0\t20\n" +
                        "ITEM\t6\tROI\t\t[16]\t100.00\t32.00\t-1.00\t0\t21\n" +
                        "ITEM\t6\tROI\t\t[17]\t100.00\t32.00\t-1.00\t0\t22\n" +
                        "ITEM\t6\tROI\t\t[18]\t100.00\t32.00\t-1.00\t0\t23\n" +
                        "ITEM\t6\tROI\t\t[19]\t100.00\t32.00\t-1.00\t0\t24\n" +
                        "ITEM\t6\tROI\t\t[20]\t100.00\t32.00\t-1.00\t0\t25\n" +
                        "ITEM\t6\tROI\t\t[21]\t100.00\t40.00\t-1.00\t0\t26\n" +
                        "ITEM\t6\tROI\t\t[22]\t100.00\t40.00\t-1.00\t0\t27\n" +
                        "ITEM\t6\tROI\t\t[23]\t100.00\t40.00\t-1.00\t0\t28\n" +
                        "ITEM\t6\tROI\t\t[24]\t100.00\t40.00\t-1.00\t0\t29\n" +
                        "ITEM\t6\tROI\t\t[25]\t100.00\t40.00\t-1.00\t0\t30\n" +
                        "ITEM\t6\tROI\t\t[26]\t100.00\t40.00\t-1.00\t0\t31\n" +
                        "ITEM\t6\tROI\t\t[27]\t100.00\t40.00\t-1.00\t0\t32\n" +
                        "ITEM\t6\tROI\t\t[28]\t100.00\t40.00\t-1.00\t0\t33\n" +
                        "ITEM\t6\tROI\t\t[29]\t100.00\t40.00\t-1.00\t0\t34\n" +
                        "ITEM\t6\tROI\t\t[30]\t100.00\t40.00\t-1.00\t0\t35\n" +
                        "ITEM\t6\tROI\t\t[31]\t100.00\t40.00\t-1.00\t0\t36\n" +
                        "ITEM\t6\tROI\t\t[32]\t100.00\t40.00\t-1.00\t0\t37\n" +
                        "ITEM\t6\tROI\t\t[33]\t100.00\t40.00\t-1.00\t0\t38\n" +
                        "ITEM\t6\tROI\t\t[34]\t100.00\t40.00\t-1.00\t0\t39\n" +
                        "ITEM\t6\tROI\t\t[35]\t100.00\t40.00\t-1.00\t0\t40\n" +
                        "ITEM\t6\tROI\t\t[36]\t100.00\t40.00\t-1.00\t0\t41\n" +
                        "ITEM\t6\tROI\t\t[37]\t100.00\t50.00\t-1.00\t0\t42\n" +
                        "ITEM\t6\tROI\t\t[38]\t100.00\t50.00\t-1.00\t0\t43\n" +
                        "ITEM\t6\tROI\t\t[39]\t100.00\t50.00\t-1.00\t0\t44\n" +
                        "ITEM\t6\tROI\t\t[40]\t100.00\t50.00\t-1.00\t0\t45\n" +
                        "ITEM\t6\tROI\t\t[41]\t100.00\t50.00\t-1.00\t0\t46\n" +
                        "ITEM\t6\tROI\t\t[42]\t100.00\t50.00\t-1.00\t0\t47\n" +
                        "ITEM\t6\tROI\t\t[43]\t100.00\t50.00\t-1.00\t0\t48\n" +
                        "ITEM\t6\tROI\t\t[44]\t100.00\t50.00\t-1.00\t0\t49\n" +
                        "ITEM\t6\tROI\t\t[45]\t100.00\t50.00\t-1.00\t0\t50\n" +
                        "ITEM\t6\tROI\t\t[46]\t100.00\t50.00\t-1.00\t0\t51\n" +
                        "ITEM\t6\tROI\t\t[47]\t100.00\t50.00\t-1.00\t0\t52\n" +
                        "ITEM\t6\tROI\t\t[48]\t100.00\t50.00\t-1.00\t0\t53\n" +
                        "ITEM\t6\tROI\t\t[49]\t100.00\t50.00\t-1.00\t0\t54\n" +
                        "ITEM\t6\tROI\t\t[50]\t100.00\t50.00\t-1.00\t0\t55\n" +
                        "ITEM\t6\tROI\t\t[51]\t100.00\t50.00\t-1.00\t0\t56\n" +
                        "ITEM\t6\tROI\t\t[52]\t100.00\t50.00\t-1.00\t0\t57\n" +
                        "ITEM\t6\tX_RES\t\t5.00\t-5.00\t1\n" +
                        "ITEM\t6\tY_RES\t\t5.00\t-5.00\t1\n" +
                        "ITEM\t6\tRESULT_RES\t\t100.00\t-100.00\t0\n" +
                        "ITEM\t6\tCOUNT\t57\n" +
                        "ITEM\t6\tTARGET\t\tBestCenter\n" +
                        "ITEM\t6\tTARGET_AVERAGE_FACTOR\t1.0\n" +
                        "ITEM\t6\tPEAK_CALC\tMaximum\n" +
                        "ITEM\t6\tDYNC_Z_RANGE\tEnable\n" +
                        "ITEM\t6\tSCAN_OPTION\tOnce\n" +
                        "ITEM\t6\tCHECK_SHIFT\t\tDisable\n" +
                        "ITEM\t6\tCHECK_SHIFT_TOL\t5.0\n" +
                        "ITEM\t6\tMTF_OFF_AXIS_CHECK1\t0.0\n" +
                        "ITEM\t6\tMTF_OFF_AXIS_CHECK2\t0.0\n" +
                        "ITEM\t6\tMTF_OFF_AXIS_CHECK3\t0.0\n" +
                        "ITEM\t6\tCC_TO_CORNER_LIMIT\t12.0\n" +
                        "ITEM\t6\tCC_TO_CORNER_LIMIT_MIN\t-12.0\n" +
                        "ITEM\t6\tCC_TO_H_CORNER_LIMIT_MIN\t0.0\n" +
                        "ITEM\t6\tCC_TO_H_CORNER_LIMIT_MAX\t0.0\n" +
                        "ITEM\t6\tCC_TO_V_CORNER_LIMIT_MIN\t0.0\n" +
                        "ITEM\t6\tCC_TO_V_CORNER_LIMIT_MAX\t0.0\n" +
                        "\t0.0\n" +
                        "ITEM\t6\tCOR_PEAK_SEPARATION_SPEC\t0\n" +
                        "ITEM\t6\tFORCE_AA_SCAN\t0\n" +
                        "ITEM\t6\tCORNER_SCORE_DIFFERENCE_REJECT_VALUE\t0.000\n" +
                        "ITEM\t6\tCORNER_SCORE_DIFFERENCE_REJECT_OPTION\t\tDisable\n" +
                        "ITEM\t6\tZ_REF\t\tBondZ\n" +
                        "ITEM\t6\tZ_REF_POS\tAA1\t33880.000\n" +
                        "ITEM\t6\tZ_REF_POS\tAA2\t33220.000\n" +
                        "ITEM\t6\tZ_REF_POS\tAA3\t32940.000\n" +
                        "ITEM\t6\tZ_REF_POS\tAA4\t33220.000\n" +
                        "ITEM\t6\tZ_REF_SHIFT\t0.000\n" +
                        "ITEM\t6\tINIT_TILT\tDisable\n" +
                        "ITEM\t6\tSRCH_DIST\t200.000\n" +
                        "ITEM\t6\tSRCH_STEP\t15.000\n" +
                        "ITEM\t6\tMF_ZC_PREDICT\t11\n" +
                        "ITEM\t6\tPOST_AA\t3\n" +
                        "ITEM\t6\tSRCH_DIR\t\tPosFromRef\n" +
                        "ITEM\t6\tFINAL_Z_OFFSET\t0.000\n" +
                        "ITEM\t6\tPOST_AA_OFFSET\t0.000\n" +
                        "ITEM\t6\tCC_TARGET_SCORE\t0.000\n" +
                        "ITEM\t6\tTARGET_VCM_SCORE\t0.000\n" +
                        "ITEM\t6\tDETAIL_LOG\tEnable\n" +
                        "ITEM\t6\tZ_OFFSET_IMG_TEST\tDisable\n" +
                        "ITEM\t6\tDIAG_PREDICT\tDisable\n" +
                        "ITEM\t6\tCHECK_Z_DAC\t\tDisable\n" +
                        "ITEM\t6\tPRE_LOW_MTF\t10.000\n" +
                        "ITEM\t6\tPRE_MAX_STEP\t30.000\n" +
                        "ITEM\t6\tSPLINE_METHOD\t0\n" +
                        "ITEM\t6\tCHECK_FINAL_POS_IMAGE\t1\n" +
                        "ITEM\t6\tCHECK_FINAL_POS_Z\t1\n" +
                        "ITEM\t6\tDIAG_PREDICT\t0\n" +
                        "ITEM\t6\tDYNC_STEPSIZE\t0\n" +
                        "ITEM\t6\tCC_COUNT_FALLING_STEP\t1\n" +
                        "ITEM\t6\tCORNER_COUNT_FALLING_STEP\t1\n" +
                        "ITEM\t6\tCC_COUNT_FALLING_STEP_CC_MTF\t60.000\n" +
                        "ITEM\t6\tCORNER_COUNT_FALLING_STEP_CORNER_MTF\t35.000\n" +
                        "ITEM\t6\tCC_COUNT_FALLING_STEP_NUM\t1\n" +
                        "ITEM\t6\tCORNER_COUNT_FALLING_STEP_NUM\t1\n" +
                        "ITEM\t6\tDYNC_STEPSIZE_MAX_STEP\t20.000\n" +
                        "ITEM\t6\tDYNC_STEPSIZE_MIN_STEP\t5.000\n" +
                        "ITEM\t6\tDYNC_STEPSIZE_LOW_MTF\t20.000\n" +
                        "ITEM\t6\tDYNC_STEPSIZE_HIGH_MTF\t55.000\n" +
                        "ITEM\t6\tFINAL_PHOTO_DELAY_TIME\t100.000000\n" +
                        "ITEM\t6\tFINAL_POS_Z_MAX_LIMIT\t30800.000000\n" +
                        "ITEM\t6\tFINAL_POS_Z_MIN_LIMIT\t30500.000000\n" +
                        "ITEM\t6\tFINAL_POS_Z_MAX_LIMIT2_1\t31700.000000\n" +
                        "ITEM\t6\tFINAL_POS_Z_MIN_LIMIT2_1\t31500.000000\n" +
                        "ITEM\t6\tFINAL_POS_Z_MAX_LIMIT2_2\t31800.000000\n" +
                        "ITEM\t6\tFINAL_POS_Z_MIN_LIMIT2_2\t31600.000000\n" +
                        "ITEM\t6\tFINAL_POS_Z_MAX_LIMIT2_3\t31970.000000\n" +
                        "ITEM\t6\tFINAL_POS_Z_MIN_LIMIT2_3\t31770.000000\n" +
                        "ITEM\t6\tFINAL_POS_Z_MAX_LIMIT2_4\t31590.000000\n" +
                        "ITEM\t6\tFINAL_POS_Z_MIN_LIMIT2_4\t31390.000000\n" +
                        "ITEM\t6\tOPTICAL_AXIS_CAL\tEnable\n" +
                        "ITEM\t6\tMF_LIMIT\t0.000\n" +
                        "ITEM\t6\tGoldenGlueThicknessMin\t0.000\n" +
                        "ITEM\t6\tGoldenGlueThicknessMax\t300.000\n" +
                        "ITEM\t6\tOPTICAL_AXIS_METHOD\t\tIndividual\n" +
                        "ITEM\t6\tWITH_CHART_ALIGNMENT\tEnable\n" +
                        "ITEM\t6\tCHART_ALIGNMENT_OPTION\t0\n" +
                        "ITEM\t6\tCHART_ALIGNMENT_X_OFFSET\tAA1\t0.000\n" +
                        "ITEM\t6\tCHART_ALIGNMENT_Y_OFFSET\tAA1\t0.000\n" +
                        "ITEM\t6\tCHART_ALIGNMENT_X_OFFSET\tAA2\t0.000\n" +
                        "ITEM\t6\tCHART_ALIGNMENT_Y_OFFSET\tAA2\t0.000\n" +
                        "ITEM\t6\tCHART_ALIGNMENT_X_OFFSET\tAA3\t0.000\n" +
                        "ITEM\t6\tCHART_ALIGNMENT_Y_OFFSET\tAA3\t0.000\n" +
                        "ITEM\t6\tCHART_ALIGNMENT_X_OFFSET\tAA4\t0.000\n" +
                        "ITEM\t6\tCHART_ALIGNMENT_Y_OFFSET\tAA4\t0.000\n" +
                        "\n" +
                        "ITEM\t7\tCOMMAND\t\tGRAB3+MTF1\n" +
                        "ITEM\t7\tROI\t\tCC\t100.00\t62.00\t60.00\t1\t1\n" +
                        "ITEM\t7\tROI\t\tUL\t100.00\t30.00\t28.00\t1\t2\n" +
                        "ITEM\t7\tROI\t\tUR\t100.00\t30.00\t28.00\t1\t3\n" +
                        "ITEM\t7\tROI\t\tLL\t100.00\t30.00\t28.00\t1\t4\n" +
                        "ITEM\t7\tROI\t\tLR\t100.00\t30.00\t28.00\t1\t5\n" +
                        "ITEM\t7\tROI\t\t[1]\t100.00\t27.00\t-1.00\t0\t6\n" +
                        "ITEM\t7\tROI\t\t[2]\t100.00\t27.00\t-1.00\t0\t7\n" +
                        "ITEM\t7\tROI\t\t[3]\t100.00\t27.00\t-1.00\t0\t8\n" +
                        "ITEM\t7\tROI\t\t[4]\t100.00\t27.00\t-1.00\t0\t9\n" +
                        "ITEM\t7\tROI\t\t[5]\t100.00\t27.00\t-1.00\t0\t10\n" +
                        "ITEM\t7\tROI\t\t[6]\t100.00\t27.00\t-1.00\t0\t11\n" +
                        "ITEM\t7\tROI\t\t[7]\t100.00\t27.00\t-1.00\t0\t12\n" +
                        "ITEM\t7\tROI\t\t[8]\t100.00\t27.00\t-1.00\t0\t13\n" +
                        "ITEM\t7\tROI\t\t[9]\t100.00\t60.00\t-1.00\t0\t14\n" +
                        "ITEM\t7\tROI\t\t[10]\t100.00\t60.00\t-1.00\t0\t15\n" +
                        "ITEM\t7\tROI\t\t[11]\t100.00\t60.00\t-1.00\t0\t16\n" +
                        "ITEM\t7\tROI\t\t[12]\t100.00\t60.00\t-1.00\t0\t17\n" +
                        "ITEM\t7\tROI\t\t[13]\t100.00\t27.00\t-1.00\t0\t18\n" +
                        "ITEM\t7\tROI\t\t[14]\t100.00\t27.00\t-1.00\t0\t19\n" +
                        "ITEM\t7\tROI\t\t[15]\t100.00\t27.00\t-1.00\t0\t20\n" +
                        "ITEM\t7\tROI\t\t[16]\t100.00\t27.00\t-1.00\t0\t21\n" +
                        "ITEM\t7\tROI\t\t[17]\t100.00\t27.00\t-1.00\t0\t22\n" +
                        "ITEM\t7\tROI\t\t[18]\t100.00\t27.00\t-1.00\t0\t23\n" +
                        "ITEM\t7\tROI\t\t[19]\t100.00\t27.00\t-1.00\t0\t24\n" +
                        "ITEM\t7\tROI\t\t[20]\t100.00\t27.00\t-1.00\t0\t25\n" +
                        "ITEM\t7\tROI\t\t[21]\t100.00\t36.00\t-1.00\t0\t26\n" +
                        "ITEM\t7\tROI\t\t[22]\t100.00\t36.00\t-1.00\t0\t27\n" +
                        "ITEM\t7\tROI\t\t[23]\t100.00\t36.00\t-1.00\t0\t28\n" +
                        "ITEM\t7\tROI\t\t[24]\t100.00\t36.00\t-1.00\t0\t29\n" +
                        "ITEM\t7\tROI\t\t[25]\t100.00\t36.00\t-1.00\t0\t30\n" +
                        "ITEM\t7\tROI\t\t[26]\t100.00\t36.00\t-1.00\t0\t31\n" +
                        "ITEM\t7\tROI\t\t[27]\t100.00\t36.00\t-1.00\t0\t32\n" +
                        "ITEM\t7\tROI\t\t[28]\t100.00\t36.00\t-1.00\t0\t33\n" +
                        "ITEM\t7\tROI\t\t[29]\t100.00\t36.00\t-1.00\t0\t34\n" +
                        "ITEM\t7\tROI\t\t[30]\t100.00\t36.00\t-1.00\t0\t35\n" +
                        "ITEM\t7\tROI\t\t[31]\t100.00\t36.00\t-1.00\t0\t36\n" +
                        "ITEM\t7\tROI\t\t[32]\t100.00\t36.00\t-1.00\t0\t37\n" +
                        "ITEM\t7\tROI\t\t[33]\t100.00\t36.00\t-1.00\t0\t38\n" +
                        "ITEM\t7\tROI\t\t[34]\t100.00\t36.00\t-1.00\t0\t39\n" +
                        "ITEM\t7\tROI\t\t[35]\t100.00\t36.00\t-1.00\t0\t40\n" +
                        "ITEM\t7\tROI\t\t[36]\t100.00\t36.00\t-1.00\t0\t41\n" +
                        "ITEM\t7\tROI\t\t[37]\t100.00\t45.00\t-1.00\t0\t42\n" +
                        "ITEM\t7\tROI\t\t[38]\t100.00\t45.00\t-1.00\t0\t43\n" +
                        "ITEM\t7\tROI\t\t[39]\t100.00\t45.00\t-1.00\t0\t44\n" +
                        "ITEM\t7\tROI\t\t[40]\t100.00\t45.00\t-1.00\t0\t45\n" +
                        "ITEM\t7\tROI\t\t[41]\t100.00\t45.00\t-1.00\t0\t46\n" +
                        "ITEM\t7\tROI\t\t[42]\t100.00\t45.00\t-1.00\t0\t47\n" +
                        "ITEM\t7\tROI\t\t[43]\t100.00\t45.00\t-1.00\t0\t48\n" +
                        "ITEM\t7\tROI\t\t[44]\t100.00\t45.00\t-1.00\t0\t49\n" +
                        "ITEM\t7\tROI\t\t[45]\t100.00\t45.00\t-1.00\t0\t50\n" +
                        "ITEM\t7\tROI\t\t[46]\t100.00\t45.00\t-1.00\t0\t51\n" +
                        "ITEM\t7\tROI\t\t[47]\t100.00\t45.00\t-1.00\t0\t52\n" +
                        "ITEM\t7\tROI\t\t[48]\t100.00\t45.00\t-1.00\t0\t53\n" +
                        "ITEM\t7\tROI\t\t[49]\t100.00\t45.00\t-1.00\t0\t54\n" +
                        "ITEM\t7\tROI\t\t[50]\t100.00\t45.00\t-1.00\t0\t55\n" +
                        "ITEM\t7\tROI\t\t[51]\t100.00\t45.00\t-1.00\t0\t56\n" +
                        "ITEM\t7\tROI\t\t[52]\t100.00\t45.00\t-1.00\t0\t57\n" +
                        "ITEM\t7\tX_RES\t\t5.00\t-5.00\t1\n" +
                        "ITEM\t7\tY_RES\t\t5.00\t-5.00\t1\n" +
                        "ITEM\t7\tRESULT_RES\t\t100.00\t-100.00\t0\n" +
                        "ITEM\t7\tCOUNT\t57\n" +
                        "ITEM\t7\tTARGET\t\tBestCenter\n" +
                        "ITEM\t7\tTARGET_AVERAGE_FACTOR\t1.0\n" +
                        "ITEM\t7\tPEAK_CALC\tMaximum\n" +
                        "ITEM\t7\tDYNC_Z_RANGE\tEnable\n" +
                        "ITEM\t7\tSCAN_OPTION\tOnce\n" +
                        "ITEM\t7\tCHECK_SHIFT\t\tDisable\n" +
                        "ITEM\t7\tCHECK_SHIFT_TOL\t5.0\n" +
                        "ITEM\t7\tMTF_OFF_AXIS_CHECK1\t0.0\n" +
                        "ITEM\t7\tMTF_OFF_AXIS_CHECK2\t0.0\n" +
                        "ITEM\t7\tMTF_OFF_AXIS_CHECK3\t0.0\n" +
                        "ITEM\t7\tCC_TO_CORNER_LIMIT\t12.0\n" +
                        "ITEM\t7\tCC_TO_CORNER_LIMIT_MIN\t-12.0\n" +
                        "ITEM\t7\tCC_TO_H_CORNER_LIMIT_MIN\t0.0\n" +
                        "ITEM\t7\tCC_TO_H_CORNER_LIMIT_MAX\t0.0\n" +
                        "ITEM\t7\tCC_TO_V_CORNER_LIMIT_MIN\t0.0\n" +
                        "ITEM\t7\tCC_TO_V_CORNER_LIMIT_MAX\t0.0\n" +
                        "\t0.0\n" +
                        "ITEM\t7\tCOR_PEAK_SEPARATION_SPEC\t0\n" +
                        "ITEM\t7\tFORCE_AA_SCAN\t0\n" +
                        "ITEM\t7\tCORNER_SCORE_DIFFERENCE_REJECT_VALUE\t0.000\n" +
                        "ITEM\t7\tCORNER_SCORE_DIFFERENCE_REJECT_OPTION\t\tDisable\n" +
                        "ITEM\t7\tZ_REF\t\tCurPos\n" +
                        "ITEM\t7\tZ_REF_POS\tAA1\t33880.000\n" +
                        "ITEM\t7\tZ_REF_POS\tAA2\t33220.000\n" +
                        "ITEM\t7\tZ_REF_POS\tAA3\t32940.000\n" +
                        "ITEM\t7\tZ_REF_POS\tAA4\t33220.000\n" +
                        "ITEM\t7\tZ_REF_SHIFT\t-50.000\n" +
                        "ITEM\t7\tINIT_TILT\tDisable\n" +
                        "ITEM\t7\tSRCH_DIST\t200.000\n" +
                        "ITEM\t7\tSRCH_STEP\t20.000\n" +
                        "ITEM\t7\tMF_ZC_PREDICT\t0\n" +
                        "ITEM\t7\tPOST_AA\t3\n" +
                        "ITEM\t7\tSRCH_DIR\t\tPosFromRef\n" +
                        "ITEM\t7\tFINAL_Z_OFFSET\t0.000\n" +
                        "ITEM\t7\tPOST_AA_OFFSET\t0.000\n" +
                        "ITEM\t7\tCC_TARGET_SCORE\t0.000\n" +
                        "ITEM\t7\tTARGET_VCM_SCORE\t0.000\n" +
                        "ITEM\t7\tDETAIL_LOG\tEnable\n" +
                        "ITEM\t7\tZ_OFFSET_IMG_TEST\tDisable\n" +
                        "ITEM\t7\tDIAG_PREDICT\tDisable\n" +
                        "ITEM\t7\tCHECK_Z_DAC\t\tDisable\n" +
                        "ITEM\t7\tPRE_LOW_MTF\t10.000\n" +
                        "ITEM\t7\tPRE_MAX_STEP\t30.000\n" +
                        "ITEM\t7\tSPLINE_METHOD\t0\n" +
                        "ITEM\t7\tCHECK_FINAL_POS_IMAGE\t1\n" +
                        "ITEM\t7\tCHECK_FINAL_POS_Z\t0\n" +
                        "ITEM\t7\tDIAG_PREDICT\t0\n" +
                        "ITEM\t7\tDYNC_STEPSIZE\t0\n" +
                        "ITEM\t7\tCC_COUNT_FALLING_STEP\t1\n" +
                        "ITEM\t7\tCORNER_COUNT_FALLING_STEP\t1\n" +
                        "ITEM\t7\tCC_COUNT_FALLING_STEP_CC_MTF\t55.000\n" +
                        "ITEM\t7\tCORNER_COUNT_FALLING_STEP_CORNER_MTF\t30.000\n" +
                        "ITEM\t7\tCC_COUNT_FALLING_STEP_NUM\t1\n" +
                        "ITEM\t7\tCORNER_COUNT_FALLING_STEP_NUM\t1\n" +
                        "ITEM\t7\tDYNC_STEPSIZE_MAX_STEP\t20.000\n" +
                        "ITEM\t7\tDYNC_STEPSIZE_MIN_STEP\t5.000\n" +
                        "ITEM\t7\tDYNC_STEPSIZE_LOW_MTF\t20.000\n" +
                        "ITEM\t7\tDYNC_STEPSIZE_HIGH_MTF\t55.000\n" +
                        "ITEM\t7\tFINAL_PHOTO_DELAY_TIME\t200.000000\n" +
                        "ITEM\t7\tFINAL_POS_Z_MAX_LIMIT\t0.000000\n" +
                        "ITEM\t7\tFINAL_POS_Z_MIN_LIMIT\t0.000000\n" +
                        "ITEM\t7\tFINAL_POS_Z_MAX_LIMIT2_1\t31700.000000\n" +
                        "ITEM\t7\tFINAL_POS_Z_MIN_LIMIT2_1\t31500.000000\n" +
                        "ITEM\t7\tFINAL_POS_Z_MAX_LIMIT2_2\t31800.000000\n" +
                        "ITEM\t7\tFINAL_POS_Z_MIN_LIMIT2_2\t31600.000000\n" +
                        "ITEM\t7\tFINAL_POS_Z_MAX_LIMIT2_3\t31970.000000\n" +
                        "ITEM\t7\tFINAL_POS_Z_MIN_LIMIT2_3\t31770.000000\n" +
                        "ITEM\t7\tFINAL_POS_Z_MAX_LIMIT2_4\t31590.000000\n" +
                        "ITEM\t7\tFINAL_POS_Z_MIN_LIMIT2_4\t31390.000000\n" +
                        "ITEM\t7\tOPTICAL_AXIS_CAL\tEnable\n" +
                        "ITEM\t7\tMF_LIMIT\t0.000\n" +
                        "ITEM\t7\tGoldenGlueThicknessMin\t0.000\n" +
                        "ITEM\t7\tGoldenGlueThicknessMax\t300.000\n" +
                        "ITEM\t7\tOPTICAL_AXIS_METHOD\t\tIndividual\n" +
                        "ITEM\t7\tWITH_CHART_ALIGNMENT\tEnable\n" +
                        "ITEM\t7\tCHART_ALIGNMENT_OPTION\t0\n" +
                        "ITEM\t7\tCHART_ALIGNMENT_X_OFFSET\tAA1\t0.000\n" +
                        "ITEM\t7\tCHART_ALIGNMENT_Y_OFFSET\tAA1\t0.000\n" +
                        "ITEM\t7\tCHART_ALIGNMENT_X_OFFSET\tAA2\t0.000\n" +
                        "ITEM\t7\tCHART_ALIGNMENT_Y_OFFSET\tAA2\t0.000\n" +
                        "ITEM\t7\tCHART_ALIGNMENT_X_OFFSET\tAA3\t0.000\n" +
                        "ITEM\t7\tCHART_ALIGNMENT_Y_OFFSET\tAA3\t0.000\n" +
                        "ITEM\t7\tCHART_ALIGNMENT_X_OFFSET\tAA4\t0.000\n" +
                        "ITEM\t7\tCHART_ALIGNMENT_Y_OFFSET\tAA4\t0.000\n" +
                        "\n" +
                        "ITEM\t8\tLightPanel ON\n" +
                        "ITEM\t8\tLightPanel 14\n" +
                        "ITEM\t8\tDelay 1000\n" +
                        "\n" +
                        "ITEM\t9\tCOMMAND\t\tGRAB3+OpticCenter\n" +
                        "ITEM\t9\tX_RES\t\t5.00\t-5.00\t1\n" +
                        "ITEM\t9\tY_RES\t\t5.00\t-5.00\t1\n" +
                        "ITEM\t9\tRESULT_RES\t\t100.00\t0.00\t0\n" +
                        "ITEM\t9\tRETRY\t\t5\n" +
                        "ITEM\t9\tZJUMP\t\t10.0\n" +
                        "ITEM\t9\tX_OFFSET\tAA1\t0.0\t0\n" +
                        "ITEM\t9\tY_OFFSET\tAA1\t0.0\t0\n" +
                        "ITEM\t9\tX_OFFSET\tAA2\t0.0\t0\n" +
                        "ITEM\t9\tY_OFFSET\tAA2\t0.0\t0\n" +
                        "ITEM\t9\tX_OFFSET\tAA3\t0.0\t0\n" +
                        "ITEM\t9\tY_OFFSET\tAA3\t0.0\t0\n" +
                        "ITEM\t9\tX_OFFSET\tAA4\t0.0\t0\n" +
                        "ITEM\t9\tY_OFFSET\tAA4\t0.0\t0\n" +
                        "ITEM\t9\tZ_REF\t\t1\n" +
                        "ITEM\t9\tZ_REF_POS\tAA1\t0.000\n" +
                        "ITEM\t9\tZ_REF_POS\tAA2\t0.000\n" +
                        "ITEM\t9\tZ_REF_POS\tAA3\t0.000\n" +
                        "ITEM\t9\tZ_REF_POS\tAA4\t0.000\n" +
                        "ITEM\t9\tZ_REF_SHIFT\t\t0.0\n" +
                        "ITEM\t9\tMF_ZC_PREDICT\t\t0\n" +
                        "ITEM\t9\tMF_ZC_PREDICT_WIDTH\t\t0.0\n" +
                        "ITEM\t9\tUSE_SMARTAA_CALIBRATED_Z\t\t0\n" +
                        "ITEM\t9\tALLOW_SKIP_CA\t\t0\n" +
                        "ITEM\t9\tALGN_OPTION\t\t1\n" +
                        "\n" +
                        "ITEM\t10\tLightPanel 12\n" +
                        "ITEM\t10\tDelay 500\n" +
                        "\n" +
                        "ITEM\t11\tCOMMAND\t\tGRAB3+BlemishDefect\n" +
                        "ITEM\t11\tCOMPARE_MAX\t\t\n" +
                        "ITEM\t11\tCOMPARE_MIN\t\t\n" +
                        "ITEM\t11\tCONNECTION_OPTION\t\t0\n" +
                        "ITEM\t11\tSEPERATE_STATION_MAX_MIN_SETTING\t\t0\n" +
                        "ITEM\t11\tRESULT\t\tresult\tCheck\t100.00\t0.50\tLog\t100.00\t100.00\t100.00\t100.00\t1.00\t1.00\t1.00\t1.00\tNoCompare\t0.00\t0.00\t0.00\n" +
                        "\n" +
                        "ITEM\t12\tLightPanel 0\n" +
                        "ITEM\t12\tLightPanel OFF\n" +
                        "\n" +
                        "ITEM\t13\tUTXYZMove #X #Y  -10\n" +
                        "ITEM\t13\tGetPositionWithFinalOffset\n" +
                        "\n" +
                        "ITEM\t14\tDispense\n" +
                        "\n" +
                        "ITEM\t15\tEpoxyInspection  30  Auto\n" +
                        "\n" +
                        "ITEM\t16\tEpoxyInspection   \n" +
                        "\n" +
                        "ITEM\t17\tDelay 300\n" +
                        "ITEM\t17\tBackToPosition\n" +
                        "ITEM\t17\tGetPosition\n" +
                        "\n" +
                        "ITEM\t18\tUVPOWER ON\n" +
                        "ITEM\t18\tRequestLensPick\n" +
                        "\n" +
                        "ITEM\t19\tCOMMAND\t\tDestroy+Init\n" +
                        "ITEM\t19\tCOMPARE_MAX\t\t\n" +
                        "ITEM\t19\tCOMPARE_MIN\t\t\n" +
                        "ITEM\t19\tCONNECTION_OPTION\t\t0\n" +
                        "ITEM\t19\tSEPERATE_STATION_MAX_MIN_SETTING\t\t0\n" +
                        "ITEM\t19\tRESULT\t\tinit\tCheck\t100.00\t1.00\tLog\t100.00\t100.00\t100.00\t100.00\t0.00\t0.00\t0.00\t0.00\tNoCompare\t0.00\t0.00\t0.00\n" +
                        "\n" +
                        "ITEM\t21\tGRIPPER OPEN\n" +
                        "ITEM\t21\tDelay 300\n" +
                        "\n" +
                        "ITEM\t22\tCOMMAND\t\tGRAB3+MTF1\n" +
                        "ITEM\t22\tCOMPARE_MAX\t\t\n" +
                        "ITEM\t22\tCOMPARE_MIN\t\t\n" +
                        "ITEM\t22\tCONNECTION_OPTION\t\t0\n" +
                        "ITEM\t22\tSEPERATE_STATION_MAX_MIN_SETTING\t\t0\n" +
                        "ITEM\t22\tRESULT\t\tX_Offset\tCheck\t40.00\t-120.00\tLog\t100.00\t100.00\t100.00\t100.00\t1.00\t1.00\t1.00\t1.00\tNoCompare\t\t0.00\t0.00\n" +
                        "ITEM\t22\tRESULT\t\tY_Offset\tCheck\t80.00\t-90.00\tLog\t100.00\t100.00\t100.00\t100.00\t1.00\t1.00\t1.00\t1.00\tNoCompare\t\t0.00\t0.00\n" +
                        "\n" +
                        "ITEM\t51\tCOMMAND\t\tDestroy\n" +
                        "ITEM\t51\tCOMPARE_MAX\t\t\n" +
                        "ITEM\t51\tCOMPARE_MIN\t\t\n" +
                        "ITEM\t51\tCONNECTION_OPTION\t\t0\n" +
                        "ITEM\t51\tSEPERATE_STATION_MAX_MIN_SETTING\t\t0\n" +
                        "ITEM\t51\tRESULT\t\tDestroy\tNoChk\t100.00\t1.00\tLog\t100.00\t100.00\t100.00\t100.00\t0.00\t0.00\t0.00\t0.00\tNoCompare\t0.00\t0.00\t0.00\n" +
                        "\n" +
                        "ITEM\t52\tLightPanel OFF\n" +
                        "ITEM\t52\tLightPanel 0\n" +
                        "\n'}");
        future.addCallback(o -> System.out.println("send-消息发送成功"), throwable -> System.out.println("消息发送失败："));
    }
}
