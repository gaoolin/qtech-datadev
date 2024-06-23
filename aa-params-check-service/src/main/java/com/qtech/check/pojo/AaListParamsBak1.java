package com.qtech.check.pojo;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.qtech.check.utils.ToCamelCaseConverter;
import com.qtech.common.utils.StringUtils;

import java.lang.reflect.Field;
import java.util.*;
import java.util.stream.Collectors;

/**
 * author :  gaozhilin
 * email  :  gaoolin@gmail.com
 * date   :  2024/05/14 11:50:36
 * desc   :
 */


public class AaListParamsBak1 {

    private String simId;
    private String prodType;
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date receivedTime; // 新增字段
    private String clampOnOff;
    private String destroyStart;
    private String init;
    private String grab;
    private String reInit;
    private String senserReset;
    private String sid;
    private String vcmHall;
    private String vcmInit;
    private String vcmHall2;
    private String vcmPowerOff;
    private String vcmPowerOn;
    private String vcmTop;
    private String vcmTopHall;
    private String vcmZ;
    private String vcmZHall;
    private String vcmOisInit;
    private String chartAlignment1;
    private String AA1;
    private String AA2;
    private String mtfCheck;
    private String AA3;
    private String mtfCheck2;
    private String lpOn;
    private String lpOcCheck;
    private String lpOc;
    private String lpOnBlemish;
    private String blemish;
    private String lpOff;
    private String chartAlignment;
    private String vcmMoveToZ;
    private String delay;
    private String vcmPowerOffCheck;
    private String recordPosition;
    private String dispense;
    private String epoxyInspectionAuto;
    private String epoxyInspection;
    private String backToPosition;
    private String uvon;
    private String yLevel;
    private String uvoff;
    private String gripperOpen;
    private String saveOc;
    private String saveMtf;
    private String destroy;
    private String moveToBlemishPos;
    private String mtfCheck3;
    private String mtfOffAxisCheck1;
    private String mtfOffAxisCheck2;
    private String mtfOffAxisCheck3;
    private String lpBlemish;
    private String chartAlignment2;
    private String vcmMoveToZPos;
    private String zOffset;
    private String openCheck;
    // AA Item 指标
    private String roiCc;
    private String roiUl;
    private String roiUr;
    private String roiLl;
    private String roiLr;
    // mtfCheck Item 指标
    private String result1;
    private String result2;
    private String result3;
    private String result4;
    private String result5;
    private String result6;
    private String result7;
    private String result8;
    private String result9;
    private String result10;
    private String result11;
    private String result12;
    private String result13;
    private String result14;
    private String result15;
    private String result16;
    private String result17;
    private String result18;
    private String result19;
    private String result20;
    private String result21;
    private String result22;
    private String result23;
    private String result24;
    private String result25;
    private String result26;
    private String result27;
    private String result28;
    private String result29;
    private String result30;
    private String result31;
    private String result32;
    private String result33;
    private String result34;
    private String result35;
    private String result36;
    private String result37;
    private String result38;
    private String result39;
    private String result40;
    private String result41;
    private String result42;
    private String result43;
    private String result44;
    private String result45;
    private String result46;
    private String result47;
    private String result48;
    private String result49;
    private String result50;
    private String result51;
    private String result52;
    // chartAlignment Item 指标
    private String xResMin;
    private String xResMax;
    private String yResMin;
    private String yResMax;
    // EpoxyInspection Auto Item 指标
    private String epoxyInspectionInterval;
    // vcmZ Item 指标
    private String resultCheckMin;
    private String resultCheckMax;
    // zOffset Item 指标


    public String getSimId() {
        return simId;
    }

    public void setSimId(String simId) {
        this.simId = simId;
    }

    public String getProdType() {
        return prodType;
    }

    public void setProdType(String prodType) {
        this.prodType = prodType;
    }

    public Date getReceivedTime() {
        return receivedTime;
    }

    public void setReceivedTime(Date receivedTime) {
        this.receivedTime = receivedTime;
    }

    public String getClampOnOff() {
        return clampOnOff;
    }

    public void setClampOnOff(String clampOnOff) {
        this.clampOnOff = clampOnOff;
    }

    public String getDestroyStart() {
        return destroyStart;
    }

    public void setDestroyStart(String destroyStart) {
        this.destroyStart = destroyStart;
    }

    public String getInit() {
        return init;
    }

    public void setInit(String init) {
        this.init = init;
    }

    public String getGrab() {
        return grab;
    }

    public void setGrab(String grab) {
        this.grab = grab;
    }

    public String getReInit() {
        return reInit;
    }

    public void setReInit(String reInit) {
        this.reInit = reInit;
    }

    public String getSenserReset() {
        return senserReset;
    }

    public void setSenserReset(String senserReset) {
        this.senserReset = senserReset;
    }

    public String getSid() {
        return sid;
    }

    public void setSid(String sid) {
        this.sid = sid;
    }

    public String getVcmHall() {
        return vcmHall;
    }

    public void setVcmHall(String vcmHall) {
        this.vcmHall = vcmHall;
    }

    public String getVcmInit() {
        return vcmInit;
    }

    public void setVcmInit(String vcmInit) {
        this.vcmInit = vcmInit;
    }

    public String getVcmHall2() {
        return vcmHall2;
    }

    public void setVcmHall2(String vcmHall2) {
        this.vcmHall2 = vcmHall2;
    }

    public String getVcmPowerOff() {
        return vcmPowerOff;
    }

    public void setVcmPowerOff(String vcmPowerOff) {
        this.vcmPowerOff = vcmPowerOff;
    }

    public String getVcmPowerOn() {
        return vcmPowerOn;
    }

    public void setVcmPowerOn(String vcmPowerOn) {
        this.vcmPowerOn = vcmPowerOn;
    }

    public String getVcmTop() {
        return vcmTop;
    }

    public void setVcmTop(String vcmTop) {
        this.vcmTop = vcmTop;
    }

    public String getVcmTopHall() {
        return vcmTopHall;
    }

    public void setVcmTopHall(String vcmTopHall) {
        this.vcmTopHall = vcmTopHall;
    }

    public String getVcmZ() {
        return vcmZ;
    }

    public void setVcmZ(String vcmZ) {
        this.vcmZ = vcmZ;
    }

    public String getVcmZHall() {
        return vcmZHall;
    }

    public void setVcmZHall(String vcmZHall) {
        this.vcmZHall = vcmZHall;
    }

    public String getVcmOisInit() {
        return vcmOisInit;
    }

    public void setVcmOisInit(String vcmOisInit) {
        this.vcmOisInit = vcmOisInit;
    }

    public String getChartAlignment1() {
        return chartAlignment1;
    }

    public void setChartAlignment1(String chartAlignment1) {
        this.chartAlignment1 = chartAlignment1;
    }

    public String getAA1() {
        return AA1;
    }

    public void setAA1(String AA1) {
        this.AA1 = AA1;
    }

    public String getAA2() {
        return AA2;
    }

    public void setAA2(String AA2) {
        this.AA2 = AA2;
    }

    public String getMtfCheck() {
        return mtfCheck;
    }

    public void setMtfCheck(String mtfCheck) {
        this.mtfCheck = mtfCheck;
    }

    public String getAA3() {
        return AA3;
    }

    public void setAA3(String AA3) {
        this.AA3 = AA3;
    }

    public String getMtfCheck2() {
        return mtfCheck2;
    }

    public void setMtfCheck2(String mtfCheck2) {
        this.mtfCheck2 = mtfCheck2;
    }

    public String getLpOn() {
        return lpOn;
    }

    public void setLpOn(String lpOn) {
        this.lpOn = lpOn;
    }

    public String getLpOcCheck() {
        return lpOcCheck;
    }

    public void setLpOcCheck(String lpOcCheck) {
        this.lpOcCheck = lpOcCheck;
    }

    public String getLpOc() {
        return lpOc;
    }

    public void setLpOc(String lpOc) {
        this.lpOc = lpOc;
    }

    public String getLpOnBlemish() {
        return lpOnBlemish;
    }

    public void setLpOnBlemish(String lpOnBlemish) {
        this.lpOnBlemish = lpOnBlemish;
    }

    public String getBlemish() {
        return blemish;
    }

    public void setBlemish(String blemish) {
        this.blemish = blemish;
    }

    public String getLpOff() {
        return lpOff;
    }

    public void setLpOff(String lpOff) {
        this.lpOff = lpOff;
    }

    public String getChartAlignment() {
        return chartAlignment;
    }

    public void setChartAlignment(String chartAlignment) {
        this.chartAlignment = chartAlignment;
    }

    public String getVcmMoveToZ() {
        return vcmMoveToZ;
    }

    public void setVcmMoveToZ(String vcmMoveToZ) {
        this.vcmMoveToZ = vcmMoveToZ;
    }

    public String getDelay() {
        return delay;
    }

    public void setDelay(String delay) {
        this.delay = delay;
    }

    public String getVcmPowerOffCheck() {
        return vcmPowerOffCheck;
    }

    public void setVcmPowerOffCheck(String vcmPowerOffCheck) {
        this.vcmPowerOffCheck = vcmPowerOffCheck;
    }

    public String getRecordPosition() {
        return recordPosition;
    }

    public void setRecordPosition(String recordPosition) {
        this.recordPosition = recordPosition;
    }

    public String getDispense() {
        return dispense;
    }

    public void setDispense(String dispense) {
        this.dispense = dispense;
    }

    public String getEpoxyInspectionAuto() {
        return epoxyInspectionAuto;
    }

    public void setEpoxyInspectionAuto(String epoxyInspectionAuto) {
        this.epoxyInspectionAuto = epoxyInspectionAuto;
    }

    public String getEpoxyInspection() {
        return epoxyInspection;
    }

    public void setEpoxyInspection(String epoxyInspection) {
        this.epoxyInspection = epoxyInspection;
    }

    public String getBackToPosition() {
        return backToPosition;
    }

    public void setBackToPosition(String backToPosition) {
        this.backToPosition = backToPosition;
    }

    public String getUvon() {
        return uvon;
    }

    public void setUvon(String uvon) {
        this.uvon = uvon;
    }

    public String getyLevel() {
        return yLevel;
    }

    public void setyLevel(String yLevel) {
        this.yLevel = yLevel;
    }

    public String getUvoff() {
        return uvoff;
    }

    public void setUvoff(String uvoff) {
        this.uvoff = uvoff;
    }

    public String getGripperOpen() {
        return gripperOpen;
    }

    public void setGripperOpen(String gripperOpen) {
        this.gripperOpen = gripperOpen;
    }

    public String getSaveOc() {
        return saveOc;
    }

    public void setSaveOc(String saveOc) {
        this.saveOc = saveOc;
    }

    public String getSaveMtf() {
        return saveMtf;
    }

    public void setSaveMtf(String saveMtf) {
        this.saveMtf = saveMtf;
    }

    public String getDestroy() {
        return destroy;
    }

    public void setDestroy(String destroy) {
        this.destroy = destroy;
    }

    public String getMoveToBlemishPos() {
        return moveToBlemishPos;
    }

    public void setMoveToBlemishPos(String moveToBlemishPos) {
        this.moveToBlemishPos = moveToBlemishPos;
    }

    public String getMtfCheck3() {
        return mtfCheck3;
    }

    public void setMtfCheck3(String mtfCheck3) {
        this.mtfCheck3 = mtfCheck3;
    }

    public String getMtfOffAxisCheck1() {
        return mtfOffAxisCheck1;
    }

    public void setMtfOffAxisCheck1(String mtfOffAxisCheck1) {
        this.mtfOffAxisCheck1 = mtfOffAxisCheck1;
    }

    public String getMtfOffAxisCheck2() {
        return mtfOffAxisCheck2;
    }

    public void setMtfOffAxisCheck2(String mtfOffAxisCheck2) {
        this.mtfOffAxisCheck2 = mtfOffAxisCheck2;
    }

    public String getMtfOffAxisCheck3() {
        return mtfOffAxisCheck3;
    }

    public void setMtfOffAxisCheck3(String mtfOffAxisCheck3) {
        this.mtfOffAxisCheck3 = mtfOffAxisCheck3;
    }

    public String getLpBlemish() {
        return lpBlemish;
    }

    public void setLpBlemish(String lpBlemish) {
        this.lpBlemish = lpBlemish;
    }

    public String getChartAlignment2() {
        return chartAlignment2;
    }

    public void setChartAlignment2(String chartAlignment2) {
        this.chartAlignment2 = chartAlignment2;
    }

    public String getVcmMoveToZPos() {
        return vcmMoveToZPos;
    }

    public void setVcmMoveToZPos(String vcmMoveToZPos) {
        this.vcmMoveToZPos = vcmMoveToZPos;
    }

    public String getzOffset() {
        return zOffset;
    }

    public void setzOffset(String zOffset) {
        this.zOffset = zOffset;
    }

    public String getOpenCheck() {
        return openCheck;
    }

    public void setOpenCheck(String openCheck) {
        this.openCheck = openCheck;
    }

    public String getRoiCc() {
        return roiCc;
    }

    public void setRoiCc(String roiCc) {
        this.roiCc = roiCc;
    }

    public String getRoiUl() {
        return roiUl;
    }

    public void setRoiUl(String roiUl) {
        this.roiUl = roiUl;
    }

    public String getRoiUr() {
        return roiUr;
    }

    public void setRoiUr(String roiUr) {
        this.roiUr = roiUr;
    }

    public String getRoiLl() {
        return roiLl;
    }

    public void setRoiLl(String roiLl) {
        this.roiLl = roiLl;
    }

    public String getRoiLr() {
        return roiLr;
    }

    public void setRoiLr(String roiLr) {
        this.roiLr = roiLr;
    }

    public String getResult1() {
        return result1;
    }

    public void setResult1(String result1) {
        this.result1 = result1;
    }

    public String getResult2() {
        return result2;
    }

    public void setResult2(String result2) {
        this.result2 = result2;
    }

    public String getResult3() {
        return result3;
    }

    public void setResult3(String result3) {
        this.result3 = result3;
    }

    public String getResult4() {
        return result4;
    }

    public void setResult4(String result4) {
        this.result4 = result4;
    }

    public String getResult5() {
        return result5;
    }

    public void setResult5(String result5) {
        this.result5 = result5;
    }

    public String getResult6() {
        return result6;
    }

    public void setResult6(String result6) {
        this.result6 = result6;
    }

    public String getResult7() {
        return result7;
    }

    public void setResult7(String result7) {
        this.result7 = result7;
    }

    public String getResult8() {
        return result8;
    }

    public void setResult8(String result8) {
        this.result8 = result8;
    }

    public String getResult9() {
        return result9;
    }

    public void setResult9(String result9) {
        this.result9 = result9;
    }

    public String getResult10() {
        return result10;
    }

    public void setResult10(String result10) {
        this.result10 = result10;
    }

    public String getResult11() {
        return result11;
    }

    public void setResult11(String result11) {
        this.result11 = result11;
    }

    public String getResult12() {
        return result12;
    }

    public void setResult12(String result12) {
        this.result12 = result12;
    }

    public String getResult13() {
        return result13;
    }

    public void setResult13(String result13) {
        this.result13 = result13;
    }

    public String getResult14() {
        return result14;
    }

    public void setResult14(String result14) {
        this.result14 = result14;
    }

    public String getResult15() {
        return result15;
    }

    public void setResult15(String result15) {
        this.result15 = result15;
    }

    public String getResult16() {
        return result16;
    }

    public void setResult16(String result16) {
        this.result16 = result16;
    }

    public String getResult17() {
        return result17;
    }

    public void setResult17(String result17) {
        this.result17 = result17;
    }

    public String getResult18() {
        return result18;
    }

    public void setResult18(String result18) {
        this.result18 = result18;
    }

    public String getResult19() {
        return result19;
    }

    public void setResult19(String result19) {
        this.result19 = result19;
    }

    public String getResult20() {
        return result20;
    }

    public void setResult20(String result20) {
        this.result20 = result20;
    }

    public String getResult21() {
        return result21;
    }

    public void setResult21(String result21) {
        this.result21 = result21;
    }

    public String getResult22() {
        return result22;
    }

    public void setResult22(String result22) {
        this.result22 = result22;
    }

    public String getResult23() {
        return result23;
    }

    public void setResult23(String result23) {
        this.result23 = result23;
    }

    public String getResult24() {
        return result24;
    }

    public void setResult24(String result24) {
        this.result24 = result24;
    }

    public String getResult25() {
        return result25;
    }

    public void setResult25(String result25) {
        this.result25 = result25;
    }

    public String getResult26() {
        return result26;
    }

    public void setResult26(String result26) {
        this.result26 = result26;
    }

    public String getResult27() {
        return result27;
    }

    public void setResult27(String result27) {
        this.result27 = result27;
    }

    public String getResult28() {
        return result28;
    }

    public void setResult28(String result28) {
        this.result28 = result28;
    }

    public String getResult29() {
        return result29;
    }

    public void setResult29(String result29) {
        this.result29 = result29;
    }

    public String getResult30() {
        return result30;
    }

    public void setResult30(String result30) {
        this.result30 = result30;
    }

    public String getResult31() {
        return result31;
    }

    public void setResult31(String result31) {
        this.result31 = result31;
    }

    public String getResult32() {
        return result32;
    }

    public void setResult32(String result32) {
        this.result32 = result32;
    }

    public String getResult33() {
        return result33;
    }

    public void setResult33(String result33) {
        this.result33 = result33;
    }

    public String getResult34() {
        return result34;
    }

    public void setResult34(String result34) {
        this.result34 = result34;
    }

    public String getResult35() {
        return result35;
    }

    public void setResult35(String result35) {
        this.result35 = result35;
    }

    public String getResult36() {
        return result36;
    }

    public void setResult36(String result36) {
        this.result36 = result36;
    }

    public String getResult37() {
        return result37;
    }

    public void setResult37(String result37) {
        this.result37 = result37;
    }

    public String getResult38() {
        return result38;
    }

    public void setResult38(String result38) {
        this.result38 = result38;
    }

    public String getResult39() {
        return result39;
    }

    public void setResult39(String result39) {
        this.result39 = result39;
    }

    public String getResult40() {
        return result40;
    }

    public void setResult40(String result40) {
        this.result40 = result40;
    }

    public String getResult41() {
        return result41;
    }

    public void setResult41(String result41) {
        this.result41 = result41;
    }

    public String getResult42() {
        return result42;
    }

    public void setResult42(String result42) {
        this.result42 = result42;
    }

    public String getResult43() {
        return result43;
    }

    public void setResult43(String result43) {
        this.result43 = result43;
    }

    public String getResult44() {
        return result44;
    }

    public void setResult44(String result44) {
        this.result44 = result44;
    }

    public String getResult45() {
        return result45;
    }

    public void setResult45(String result45) {
        this.result45 = result45;
    }

    public String getResult46() {
        return result46;
    }

    public void setResult46(String result46) {
        this.result46 = result46;
    }

    public String getResult47() {
        return result47;
    }

    public void setResult47(String result47) {
        this.result47 = result47;
    }

    public String getResult48() {
        return result48;
    }

    public void setResult48(String result48) {
        this.result48 = result48;
    }

    public String getResult49() {
        return result49;
    }

    public void setResult49(String result49) {
        this.result49 = result49;
    }

    public String getResult50() {
        return result50;
    }

    public void setResult50(String result50) {
        this.result50 = result50;
    }

    public String getResult51() {
        return result51;
    }

    public void setResult51(String result51) {
        this.result51 = result51;
    }

    public String getResult52() {
        return result52;
    }

    public void setResult52(String result52) {
        this.result52 = result52;
    }

    public String getxResMin() {
        return xResMin;
    }

    public void setxResMin(String xResMin) {
        this.xResMin = xResMin;
    }

    public String getxResMax() {
        return xResMax;
    }

    public void setxResMax(String xResMax) {
        this.xResMax = xResMax;
    }

    public String getyResMin() {
        return yResMin;
    }

    public void setyResMin(String yResMin) {
        this.yResMin = yResMin;
    }

    public String getyResMax() {
        return yResMax;
    }

    public void setyResMax(String yResMax) {
        this.yResMax = yResMax;
    }

    public String getEpoxyInspectionInterval() {
        return epoxyInspectionInterval;
    }

    public void setEpoxyInspectionInterval(String epoxyInspectionInterval) {
        this.epoxyInspectionInterval = epoxyInspectionInterval;
    }

    public String getResultCheckMin() {
        return resultCheckMin;
    }

    public void setResultCheckMin(String resultCheckMin) {
        this.resultCheckMin = resultCheckMin;
    }

    public String getResultCheckMax() {
        return resultCheckMax;
    }

    public void setResultCheckMax(String resultCheckMax) {
        this.resultCheckMax = resultCheckMax;
    }

    public void fillWithData(List<AaListCommand> aaListCommands) {
        if (aaListCommands.isEmpty()) {
            return;
        }

        List<Map<String, String>> camelCaseData = aaListCommands.stream().filter(AaListCommand::nonNull).map(aaListCommand -> {
                    Map<String, String> map = new HashMap<>();
                    Optional.ofNullable(aaListCommand.getIntegration()).ifPresent(integration -> {
                        String cmdObjVal = aaListCommand.getValue();
                        if (cmdObjVal == null) {
                            String metricsMin = StringUtils.join(ToCamelCaseConverter.doConvert(integration), "Min");
                            String metricsMax = StringUtils.join(ToCamelCaseConverter.doConvert(integration), "Max");
                            map.put(metricsMin, aaListCommand.getRange().getMin());
                            map.put(metricsMax, aaListCommand.getRange().getMax());
                        } else {
                            map.put(ToCamelCaseConverter.doConvert(integration), cmdObjVal);
                        }
                    });
                    return map;
                }).filter(map -> !map.isEmpty()) // 过滤掉空的 Map
                .collect(Collectors.toList());

        try {
            for (Field field : getClass().getDeclaredFields()) {
                String camelCaseKey = field.getName();
                for (Map<String, String> map : camelCaseData) {
                    if (map.containsKey(camelCaseKey)) {
                        field.setAccessible(true);
                        field.set(this, map.get(camelCaseKey));
                        break; // Once the field is set, no need to check further maps
                    }
                }
            }
        } catch (IllegalAccessException e) {
            throw new IllegalStateException("Failed to set properties due to reflection error", e);
        }
    }

    public void reset() {
        this.simId = null;
        this.prodType = null;
        this.receivedTime = null;
        this.clampOnOff = null;
        this.destroyStart = null;
        this.init = null;
        this.grab = null;
        this.reInit = null;
        this.senserReset = null;
        this.sid = null;
        this.vcmHall = null;
        this.vcmInit = null;
        this.vcmHall2 = null;
        this.vcmPowerOff = null;
        this.vcmPowerOn = null;
        this.vcmTop = null;
        this.vcmTopHall = null;
        this.vcmZ = null;
        this.vcmZHall = null;
        this.vcmOisInit = null;
        this.chartAlignment1 = null;
        this.AA1 = null;
        this.AA2 = null;
        this.mtfCheck = null;
        this.AA3 = null;
        this.mtfCheck2 = null;
        this.lpOn = null;
        this.lpOcCheck = null;
        this.lpOc = null;
        this.lpOnBlemish = null;
        this.blemish = null;
        this.lpOff = null;
        this.chartAlignment = null;
        this.vcmMoveToZ = null;
        this.delay = null;
        this.vcmPowerOffCheck = null;
        this.recordPosition = null;
        this.dispense = null;
        this.epoxyInspectionAuto = null;
        this.epoxyInspection = null;
        this.backToPosition = null;
        this.uvon = null;
        this.yLevel = null;
        this.uvoff = null;
        this.gripperOpen = null;
        this.saveOc = null;
        this.saveMtf = null;
        this.destroy = null;
        this.moveToBlemishPos = null;
        this.mtfCheck3 = null;
        this.mtfOffAxisCheck1 = null;
        this.mtfOffAxisCheck2 = null;
        this.mtfOffAxisCheck3 = null;
        this.lpBlemish = null;
        this.chartAlignment2 = null;
        this.vcmMoveToZPos = null;
        this.zOffset = null;
        this.openCheck = null;
        this.roiCc = null;
        this.roiUl = null;
        this.roiUr = null;
        this.roiLl = null;
        this.roiLr = null;
        this.result1 = null;
        this.result2 = null;
        this.result3 = null;
        this.result4 = null;
        this.result5 = null;
        this.result6 = null;
        this.result7 = null;
        this.result8 = null;
        this.result9 = null;
        this.result10 = null;
        this.result11 = null;
        this.result12 = null;
        this.result13 = null;
        this.result14 = null;
        this.result15 = null;
        this.result16 = null;
        this.result17 = null;
        this.result18 = null;
        this.result19 = null;
        this.result20 = null;
        this.result21 = null;
        this.result22 = null;
        this.result23 = null;
        this.result24 = null;
        this.result25 = null;
        this.result26 = null;
        this.result27 = null;
        this.result28 = null;
        this.result29 = null;
        this.result30 = null;
        this.result31 = null;
        this.result32 = null;
        this.result33 = null;
        this.result34 = null;
        this.result35 = null;
        this.result36 = null;
        this.result37 = null;
        this.result38 = null;
        this.result39 = null;
        this.result40 = null;
        this.result41 = null;
        this.result42 = null;
        this.result43 = null;
        this.result44 = null;
        this.result45 = null;
        this.result46 = null;
        this.result47 = null;
        this.result48 = null;
        this.result49 = null;
        this.result50 = null;
        this.result51 = null;
        this.xResMin = null;
        this.xResMax = null;
        this.yResMin = null;
        this.yResMax = null;
        this.epoxyInspectionInterval = null;
    }

    @Override
    public String toString() {
        return "AaListParams{" +
                "simId='" + simId + '\'' +
                ", prodType='" + prodType + '\'' +
                ", receivedTime=" + receivedTime +
                ", clampOnOff='" + clampOnOff + '\'' +
                ", destroyStart='" + destroyStart + '\'' +
                ", init='" + init + '\'' +
                ", grab='" + grab + '\'' +
                ", reInit='" + reInit + '\'' +
                ", senserReset='" + senserReset + '\'' +
                ", sid='" + sid + '\'' +
                ", vcmHall='" + vcmHall + '\'' +
                ", vcmInit='" + vcmInit + '\'' +
                ", vcmHall2='" + vcmHall2 + '\'' +
                ", vcmPowerOff='" + vcmPowerOff + '\'' +
                ", vcmPowerOn='" + vcmPowerOn + '\'' +
                ", vcmTop='" + vcmTop + '\'' +
                ", vcmTopHall='" + vcmTopHall + '\'' +
                ", vcmZ='" + vcmZ + '\'' +
                ", vcmZHall='" + vcmZHall + '\'' +
                ", vcmOisInit='" + vcmOisInit + '\'' +
                ", chartAlignment1='" + chartAlignment1 + '\'' +
                ", AA1='" + AA1 + '\'' +
                ", AA2='" + AA2 + '\'' +
                ", mtfCheck='" + mtfCheck + '\'' +
                ", AA3='" + AA3 + '\'' +
                ", mtfCheck2='" + mtfCheck2 + '\'' +
                ", lpOn='" + lpOn + '\'' +
                ", lpOcCheck='" + lpOcCheck + '\'' +
                ", lpOc='" + lpOc + '\'' +
                ", lpOnBlemish='" + lpOnBlemish + '\'' +
                ", blemish='" + blemish + '\'' +
                ", lpOff='" + lpOff + '\'' +
                ", chartAlignment='" + chartAlignment + '\'' +
                ", vcmMoveToZ='" + vcmMoveToZ + '\'' +
                ", delay='" + delay + '\'' +
                ", vcmPowerOffCheck='" + vcmPowerOffCheck + '\'' +
                ", recordPosition='" + recordPosition + '\'' +
                ", dispense='" + dispense + '\'' +
                ", epoxyInspectionAuto='" + epoxyInspectionAuto + '\'' +
                ", epoxyInspection='" + epoxyInspection + '\'' +
                ", backToPosition='" + backToPosition + '\'' +
                ", uvon='" + uvon + '\'' +
                ", yLevel='" + yLevel + '\'' +
                ", uvoff='" + uvoff + '\'' +
                ", gripperOpen='" + gripperOpen + '\'' +
                ", saveOc='" + saveOc + '\'' +
                ", saveMtf='" + saveMtf + '\'' +
                ", destroy='" + destroy + '\'' +
                ", moveToBlemishPos='" + moveToBlemishPos + '\'' +
                ", mtfCheck3='" + mtfCheck3 + '\'' +
                ", mtfOffAxisCheck1='" + mtfOffAxisCheck1 + '\'' +
                ", mtfOffAxisCheck2='" + mtfOffAxisCheck2 + '\'' +
                ", mtfOffAxisCheck3='" + mtfOffAxisCheck3 + '\'' +
                ", lpBlemish='" + lpBlemish + '\'' +
                ", chartAlignment2='" + chartAlignment2 + '\'' +
                ", vcmMoveToZPos='" + vcmMoveToZPos + '\'' +
                ", zOffset='" + zOffset + '\'' +
                ", openCheck='" + openCheck + '\'' +
                ", roiCc='" + roiCc + '\'' +
                ", roiUl='" + roiUl + '\'' +
                ", roiUr='" + roiUr + '\'' +
                ", roiLl='" + roiLl + '\'' +
                ", roiLr='" + roiLr + '\'' +
                ", result1='" + result1 + '\'' +
                ", result2='" + result2 + '\'' +
                ", result3='" + result3 + '\'' +
                ", result4='" + result4 + '\'' +
                ", result5='" + result5 + '\'' +
                ", result6='" + result6 + '\'' +
                ", result7='" + result7 + '\'' +
                ", result8='" + result8 + '\'' +
                ", result9='" + result9 + '\'' +
                ", result10='" + result10 + '\'' +
                ", result11='" + result11 + '\'' +
                ", result12='" + result12 + '\'' +
                ", result13='" + result13 + '\'' +
                ", result14='" + result14 + '\'' +
                ", result15='" + result15 + '\'' +
                ", result16='" + result16 + '\'' +
                ", result17='" + result17 + '\'' +
                ", result18='" + result18 + '\'' +
                ", result19='" + result19 + '\'' +
                ", result20='" + result20 + '\'' +
                ", result21='" + result21 + '\'' +
                ", result22='" + result22 + '\'' +
                ", result23='" + result23 + '\'' +
                ", result24='" + result24 + '\'' +
                ", result25='" + result25 + '\'' +
                ", result26='" + result26 + '\'' +
                ", result27='" + result27 + '\'' +
                ", result28='" + result28 + '\'' +
                ", result29='" + result29 + '\'' +
                ", result30='" + result30 + '\'' +
                ", result31='" + result31 + '\'' +
                ", result32='" + result32 + '\'' +
                ", result33='" + result33 + '\'' +
                ", result34='" + result34 + '\'' +
                ", result35='" + result35 + '\'' +
                ", result36='" + result36 + '\'' +
                ", result37='" + result37 + '\'' +
                ", result38='" + result38 + '\'' +
                ", result39='" + result39 + '\'' +
                ", result40='" + result40 + '\'' +
                ", result41='" + result41 + '\'' +
                ", result42='" + result42 + '\'' +
                ", result43='" + result43 + '\'' +
                ", result44='" + result44 + '\'' +
                ", result45='" + result45 + '\'' +
                ", result46='" + result46 + '\'' +
                ", result47='" + result47 + '\'' +
                ", result48='" + result48 + '\'' +
                ", result49='" + result49 + '\'' +
                ", result50='" + result50 + '\'' +
                ", result51='" + result51 + '\'' +
                ", result52='" + result52 + '\'' +
                ", xResMin='" + xResMin + '\'' +
                ", xResMax='" + xResMax + '\'' +
                ", yResMin='" + yResMin + '\'' +
                ", yResMax='" + yResMax + '\'' +
                ", epoxyInspectionInterval='" + epoxyInspectionInterval + '\'' +
                ", resultCheckMin='" + resultCheckMin + '\'' +
                ", resultCheckMax='" + resultCheckMax + '\'' +
                '}';
    }

// 通常用于定义排序顺序，而不是这种复杂的属性比较。
    /*@Override
    public int compareTo(AaListParams o) {
        return 0;
    }*/
}
