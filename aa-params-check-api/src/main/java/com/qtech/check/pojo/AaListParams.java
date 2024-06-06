package com.qtech.check.pojo;

import com.fasterxml.jackson.annotation.JsonFormat;

import java.util.Date;
import java.util.List;

/**
 * author :  gaozhilin
 * email  :  gaoolin@gmail.com
 * date   :  2024/05/14 11:50:36
 * desc   :
 */


public class AaListParams extends AaListParamsBaseEntity {

    private String simId;
    private String prodType;
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date receivedTime; // 新增字段


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

    @Override
    public String getClampOnOff() {
        return super.getClampOnOff();
    }

    @Override
    public String getDestroyStart() {
        return super.getDestroyStart();
    }

    @Override
    public String getInit() {
        return super.getInit();
    }

    @Override
    public String getGrab() {
        return super.getGrab();
    }

    @Override
    public String getReInit() {
        return super.getReInit();
    }

    @Override
    public String getSenserReset() {
        return super.getSenserReset();
    }

    @Override
    public String getSid() {
        return super.getSid();
    }

    @Override
    public String getVcmHall() {
        return super.getVcmHall();
    }

    @Override
    public String getVcmInit() {
        return super.getVcmInit();
    }

    @Override
    public String getVcmHall2() {
        return super.getVcmHall2();
    }

    @Override
    public String getVcmPowerOff() {
        return super.getVcmPowerOff();
    }

    @Override
    public String getVcmPowerOn() {
        return super.getVcmPowerOn();
    }

    @Override
    public String getVcmTop() {
        return super.getVcmTop();
    }

    @Override
    public String getVcmTopHall() {
        return super.getVcmTopHall();
    }

    @Override
    public String getVcmZ() {
        return super.getVcmZ();
    }

    @Override
    public String getVcmZHall() {
        return super.getVcmZHall();
    }

    @Override
    public String getVcmOisInit() {
        return super.getVcmOisInit();
    }

    @Override
    public String getChartAlignment1() {
        return super.getChartAlignment1();
    }

    @Override
    public String getAA1() {
        return super.getAA1();
    }

    @Override
    public String getAA2() {
        return super.getAA2();
    }

    @Override
    public String getMtfCheck() {
        return super.getMtfCheck();
    }

    @Override
    public String getAA3() {
        return super.getAA3();
    }

    @Override
    public String getMtfCheck2() {
        return super.getMtfCheck2();
    }

    @Override
    public String getLpOn() {
        return super.getLpOn();
    }

    @Override
    public String getLpOcCheck() {
        return super.getLpOcCheck();
    }

    @Override
    public String getLpOc() {
        return super.getLpOc();
    }

    @Override
    public String getLpOnBlemish() {
        return super.getLpOnBlemish();
    }

    @Override
    public String getBlemish() {
        return super.getBlemish();
    }

    @Override
    public String getLpOff() {
        return super.getLpOff();
    }

    @Override
    public String getChartAlignment() {
        return super.getChartAlignment();
    }

    @Override
    public String getVcmMoveToZ() {
        return super.getVcmMoveToZ();
    }

    @Override
    public String getDelay() {
        return super.getDelay();
    }

    @Override
    public String getVcmPowerOffCheck() {
        return super.getVcmPowerOffCheck();
    }

    @Override
    public String getRecordPosition() {
        return super.getRecordPosition();
    }

    @Override
    public String getDispense() {
        return super.getDispense();
    }

    @Override
    public String getEpoxyInspectionAuto() {
        return super.getEpoxyInspectionAuto();
    }

    @Override
    public String getEpoxyInspection() {
        return super.getEpoxyInspection();
    }

    @Override
    public String getBackToPosition() {
        return super.getBackToPosition();
    }

    @Override
    public String getUvon() {
        return super.getUvon();
    }

    @Override
    public String getYLevel() {
        return super.getYLevel();
    }

    @Override
    public String getUvoff() {
        return super.getUvoff();
    }

    @Override
    public String getGripperOpen() {
        return super.getGripperOpen();
    }

    @Override
    public String getSaveOc() {
        return super.getSaveOc();
    }

    @Override
    public String getSaveMtf() {
        return super.getSaveMtf();
    }

    @Override
    public String getDestroy() {
        return super.getDestroy();
    }

    @Override
    public String getMoveToBlemishPos() {
        return super.getMoveToBlemishPos();
    }

    @Override
    public String getMtfCheck3() {
        return super.getMtfCheck3();
    }

    @Override
    public String getMtfOffAxisCheck1() {
        return super.getMtfOffAxisCheck1();
    }

    @Override
    public String getMtfOffAxisCheck2() {
        return super.getMtfOffAxisCheck2();
    }

    @Override
    public String getMtfOffAxisCheck3() {
        return super.getMtfOffAxisCheck3();
    }

    @Override
    public String getLpBlemish() {
        return super.getLpBlemish();
    }

    @Override
    public String getChartAlignment2() {
        return super.getChartAlignment2();
    }

    @Override
    public String getVcmMoveToZPos() {
        return super.getVcmMoveToZPos();
    }

    @Override
    public String getZOffset() {
        return super.getZOffset();
    }

    @Override
    public String getOpenCheck() {
        return super.getOpenCheck();
    }

    @Override
    public String getRoiCc() {
        return super.getRoiCc();
    }

    @Override
    public String getRoiUl() {
        return super.getRoiUl();
    }

    @Override
    public String getRoiUr() {
        return super.getRoiUr();
    }

    @Override
    public String getRoiLl() {
        return super.getRoiLl();
    }

    @Override
    public String getRoiLr() {
        return super.getRoiLr();
    }

    @Override
    public String getResult1() {
        return super.getResult1();
    }

    @Override
    public String getResult2() {
        return super.getResult2();
    }

    @Override
    public String getResult3() {
        return super.getResult3();
    }

    @Override
    public String getResult4() {
        return super.getResult4();
    }

    @Override
    public String getResult5() {
        return super.getResult5();
    }

    @Override
    public String getResult6() {
        return super.getResult6();
    }

    @Override
    public String getResult7() {
        return super.getResult7();
    }

    @Override
    public String getResult8() {
        return super.getResult8();
    }

    @Override
    public String getResult9() {
        return super.getResult9();
    }

    @Override
    public String getResult10() {
        return super.getResult10();
    }

    @Override
    public String getResult11() {
        return super.getResult11();
    }

    @Override
    public String getResult12() {
        return super.getResult12();
    }

    @Override
    public String getResult13() {
        return super.getResult13();
    }

    @Override
    public String getResult14() {
        return super.getResult14();
    }

    @Override
    public String getResult15() {
        return super.getResult15();
    }

    @Override
    public String getResult16() {
        return super.getResult16();
    }

    @Override
    public String getResult17() {
        return super.getResult17();
    }

    @Override
    public String getResult18() {
        return super.getResult18();
    }

    @Override
    public String getResult19() {
        return super.getResult19();
    }

    @Override
    public String getResult20() {
        return super.getResult20();
    }

    @Override
    public String getResult21() {
        return super.getResult21();
    }

    @Override
    public String getResult22() {
        return super.getResult22();
    }

    @Override
    public String getResult23() {
        return super.getResult23();
    }

    @Override
    public String getResult24() {
        return super.getResult24();
    }

    @Override
    public String getResult25() {
        return super.getResult25();
    }

    @Override
    public String getResult26() {
        return super.getResult26();
    }

    @Override
    public String getResult27() {
        return super.getResult27();
    }

    @Override
    public String getResult28() {
        return super.getResult28();
    }

    @Override
    public String getResult29() {
        return super.getResult29();
    }

    @Override
    public String getResult30() {
        return super.getResult30();
    }

    @Override
    public String getResult31() {
        return super.getResult31();
    }

    @Override
    public String getResult32() {
        return super.getResult32();
    }

    @Override
    public String getResult33() {
        return super.getResult33();
    }

    @Override
    public String getResult34() {
        return super.getResult34();
    }

    @Override
    public String getResult35() {
        return super.getResult35();
    }

    @Override
    public String getResult36() {
        return super.getResult36();
    }

    @Override
    public String getResult37() {
        return super.getResult37();
    }

    @Override
    public String getResult38() {
        return super.getResult38();
    }

    @Override
    public String getResult39() {
        return super.getResult39();
    }

    @Override
    public String getResult40() {
        return super.getResult40();
    }

    @Override
    public String getResult41() {
        return super.getResult41();
    }

    @Override
    public String getResult42() {
        return super.getResult42();
    }

    @Override
    public String getResult43() {
        return super.getResult43();
    }

    @Override
    public String getResult44() {
        return super.getResult44();
    }

    @Override
    public String getResult45() {
        return super.getResult45();
    }

    @Override
    public String getResult46() {
        return super.getResult46();
    }

    @Override
    public String getResult47() {
        return super.getResult47();
    }

    @Override
    public String getResult48() {
        return super.getResult48();
    }

    @Override
    public String getResult49() {
        return super.getResult49();
    }

    @Override
    public String getResult50() {
        return super.getResult50();
    }

    @Override
    public String getResult51() {
        return super.getResult51();
    }

    @Override
    public String getResult52() {
        return super.getResult52();
    }

    @Override
    public String getXResMin() {
        return super.getXResMin();
    }

    @Override
    public String getXResMax() {
        return super.getXResMax();
    }

    @Override
    public String getYResMin() {
        return super.getYResMin();
    }

    @Override
    public String getYResMax() {
        return super.getYResMax();
    }

    @Override
    public String getEpoxyInspectionInterval() {
        return super.getEpoxyInspectionInterval();
    }

    @Override
    public String getResultCheckMin() {
        return super.getResultCheckMin();
    }

    @Override
    public String getResultCheckMax() {
        return super.getResultCheckMax();
    }

    @Override
    public void setClampOnOff(String clampOnOff) {
        super.setClampOnOff(clampOnOff);
    }

    @Override
    public void setDestroyStart(String destroyStart) {
        super.setDestroyStart(destroyStart);
    }

    @Override
    public void setInit(String init) {
        super.setInit(init);
    }

    @Override
    public void setGrab(String grab) {
        super.setGrab(grab);
    }

    @Override
    public void setReInit(String reInit) {
        super.setReInit(reInit);
    }

    @Override
    public void setSenserReset(String senserReset) {
        super.setSenserReset(senserReset);
    }

    @Override
    public void setSid(String sid) {
        super.setSid(sid);
    }

    @Override
    public void setVcmHall(String vcmHall) {
        super.setVcmHall(vcmHall);
    }

    @Override
    public void setVcmInit(String vcmInit) {
        super.setVcmInit(vcmInit);
    }

    @Override
    public void setVcmHall2(String vcmHall2) {
        super.setVcmHall2(vcmHall2);
    }

    @Override
    public void setVcmPowerOff(String vcmPowerOff) {
        super.setVcmPowerOff(vcmPowerOff);
    }

    @Override
    public void setVcmPowerOn(String vcmPowerOn) {
        super.setVcmPowerOn(vcmPowerOn);
    }

    @Override
    public void setVcmTop(String vcmTop) {
        super.setVcmTop(vcmTop);
    }

    @Override
    public void setVcmTopHall(String vcmTopHall) {
        super.setVcmTopHall(vcmTopHall);
    }

    @Override
    public void setVcmZ(String vcmZ) {
        super.setVcmZ(vcmZ);
    }

    @Override
    public void setVcmZHall(String vcmZHall) {
        super.setVcmZHall(vcmZHall);
    }

    @Override
    public void setVcmOisInit(String vcmOisInit) {
        super.setVcmOisInit(vcmOisInit);
    }

    @Override
    public void setChartAlignment1(String chartAlignment1) {
        super.setChartAlignment1(chartAlignment1);
    }

    @Override
    public void setAA1(String AA1) {
        super.setAA1(AA1);
    }

    @Override
    public void setAA2(String AA2) {
        super.setAA2(AA2);
    }

    @Override
    public void setMtfCheck(String mtfCheck) {
        super.setMtfCheck(mtfCheck);
    }

    @Override
    public void setAA3(String AA3) {
        super.setAA3(AA3);
    }

    @Override
    public void setMtfCheck2(String mtfCheck2) {
        super.setMtfCheck2(mtfCheck2);
    }

    @Override
    public void setLpOn(String lpOn) {
        super.setLpOn(lpOn);
    }

    @Override
    public void setLpOcCheck(String lpOcCheck) {
        super.setLpOcCheck(lpOcCheck);
    }

    @Override
    public void setLpOc(String lpOc) {
        super.setLpOc(lpOc);
    }

    @Override
    public void setLpOnBlemish(String lpOnBlemish) {
        super.setLpOnBlemish(lpOnBlemish);
    }

    @Override
    public void setBlemish(String blemish) {
        super.setBlemish(blemish);
    }

    @Override
    public void setLpOff(String lpOff) {
        super.setLpOff(lpOff);
    }

    @Override
    public void setChartAlignment(String chartAlignment) {
        super.setChartAlignment(chartAlignment);
    }

    @Override
    public void setVcmMoveToZ(String vcmMoveToZ) {
        super.setVcmMoveToZ(vcmMoveToZ);
    }

    @Override
    public void setDelay(String delay) {
        super.setDelay(delay);
    }

    @Override
    public void setVcmPowerOffCheck(String vcmPowerOffCheck) {
        super.setVcmPowerOffCheck(vcmPowerOffCheck);
    }

    @Override
    public void setRecordPosition(String recordPosition) {
        super.setRecordPosition(recordPosition);
    }

    @Override
    public void setDispense(String dispense) {
        super.setDispense(dispense);
    }

    @Override
    public void setEpoxyInspectionAuto(String epoxyInspectionAuto) {
        super.setEpoxyInspectionAuto(epoxyInspectionAuto);
    }

    @Override
    public void setEpoxyInspection(String epoxyInspection) {
        super.setEpoxyInspection(epoxyInspection);
    }

    @Override
    public void setBackToPosition(String backToPosition) {
        super.setBackToPosition(backToPosition);
    }

    @Override
    public void setUvon(String uvon) {
        super.setUvon(uvon);
    }

    @Override
    public void setYLevel(String yLevel) {
        super.setYLevel(yLevel);
    }

    @Override
    public void setUvoff(String uvoff) {
        super.setUvoff(uvoff);
    }

    @Override
    public void setGripperOpen(String gripperOpen) {
        super.setGripperOpen(gripperOpen);
    }

    @Override
    public void setSaveOc(String saveOc) {
        super.setSaveOc(saveOc);
    }

    @Override
    public void setSaveMtf(String saveMtf) {
        super.setSaveMtf(saveMtf);
    }

    @Override
    public void setDestroy(String destroy) {
        super.setDestroy(destroy);
    }

    @Override
    public void setMoveToBlemishPos(String moveToBlemishPos) {
        super.setMoveToBlemishPos(moveToBlemishPos);
    }

    @Override
    public void setMtfCheck3(String mtfCheck3) {
        super.setMtfCheck3(mtfCheck3);
    }

    @Override
    public void setMtfOffAxisCheck1(String mtfOffAxisCheck1) {
        super.setMtfOffAxisCheck1(mtfOffAxisCheck1);
    }

    @Override
    public void setMtfOffAxisCheck2(String mtfOffAxisCheck2) {
        super.setMtfOffAxisCheck2(mtfOffAxisCheck2);
    }

    @Override
    public void setMtfOffAxisCheck3(String mtfOffAxisCheck3) {
        super.setMtfOffAxisCheck3(mtfOffAxisCheck3);
    }

    @Override
    public void setLpBlemish(String lpBlemish) {
        super.setLpBlemish(lpBlemish);
    }

    @Override
    public void setChartAlignment2(String chartAlignment2) {
        super.setChartAlignment2(chartAlignment2);
    }

    @Override
    public void setVcmMoveToZPos(String vcmMoveToZPos) {
        super.setVcmMoveToZPos(vcmMoveToZPos);
    }

    @Override
    public void setZOffset(String zOffset) {
        super.setZOffset(zOffset);
    }

    @Override
    public void setOpenCheck(String openCheck) {
        super.setOpenCheck(openCheck);
    }

    @Override
    public void setRoiCc(String roiCc) {
        super.setRoiCc(roiCc);
    }

    @Override
    public void setRoiUl(String roiUl) {
        super.setRoiUl(roiUl);
    }

    @Override
    public void setRoiUr(String roiUr) {
        super.setRoiUr(roiUr);
    }

    @Override
    public void setRoiLl(String roiLl) {
        super.setRoiLl(roiLl);
    }

    @Override
    public void setRoiLr(String roiLr) {
        super.setRoiLr(roiLr);
    }

    @Override
    public void setResult1(String result1) {
        super.setResult1(result1);
    }

    @Override
    public void setResult2(String result2) {
        super.setResult2(result2);
    }

    @Override
    public void setResult3(String result3) {
        super.setResult3(result3);
    }

    @Override
    public void setResult4(String result4) {
        super.setResult4(result4);
    }

    @Override
    public void setResult5(String result5) {
        super.setResult5(result5);
    }

    @Override
    public void setResult6(String result6) {
        super.setResult6(result6);
    }

    @Override
    public void setResult7(String result7) {
        super.setResult7(result7);
    }

    @Override
    public void setResult8(String result8) {
        super.setResult8(result8);
    }

    @Override
    public void setResult9(String result9) {
        super.setResult9(result9);
    }

    @Override
    public void setResult10(String result10) {
        super.setResult10(result10);
    }

    @Override
    public void setResult11(String result11) {
        super.setResult11(result11);
    }

    @Override
    public void setResult12(String result12) {
        super.setResult12(result12);
    }

    @Override
    public void setResult13(String result13) {
        super.setResult13(result13);
    }

    @Override
    public void setResult14(String result14) {
        super.setResult14(result14);
    }

    @Override
    public void setResult15(String result15) {
        super.setResult15(result15);
    }

    @Override
    public void setResult16(String result16) {
        super.setResult16(result16);
    }

    @Override
    public void setResult17(String result17) {
        super.setResult17(result17);
    }

    @Override
    public void setResult18(String result18) {
        super.setResult18(result18);
    }

    @Override
    public void setResult19(String result19) {
        super.setResult19(result19);
    }

    @Override
    public void setResult20(String result20) {
        super.setResult20(result20);
    }

    @Override
    public void setResult21(String result21) {
        super.setResult21(result21);
    }

    @Override
    public void setResult22(String result22) {
        super.setResult22(result22);
    }

    @Override
    public void setResult23(String result23) {
        super.setResult23(result23);
    }

    @Override
    public void setResult24(String result24) {
        super.setResult24(result24);
    }

    @Override
    public void setResult25(String result25) {
        super.setResult25(result25);
    }

    @Override
    public void setResult26(String result26) {
        super.setResult26(result26);
    }

    @Override
    public void setResult27(String result27) {
        super.setResult27(result27);
    }

    @Override
    public void setResult28(String result28) {
        super.setResult28(result28);
    }

    @Override
    public void setResult29(String result29) {
        super.setResult29(result29);
    }

    @Override
    public void setResult30(String result30) {
        super.setResult30(result30);
    }

    @Override
    public void setResult31(String result31) {
        super.setResult31(result31);
    }

    @Override
    public void setResult32(String result32) {
        super.setResult32(result32);
    }

    @Override
    public void setResult33(String result33) {
        super.setResult33(result33);
    }

    @Override
    public void setResult34(String result34) {
        super.setResult34(result34);
    }

    @Override
    public void setResult35(String result35) {
        super.setResult35(result35);
    }

    @Override
    public void setResult36(String result36) {
        super.setResult36(result36);
    }

    @Override
    public void setResult37(String result37) {
        super.setResult37(result37);
    }

    @Override
    public void setResult38(String result38) {
        super.setResult38(result38);
    }

    @Override
    public void setResult39(String result39) {
        super.setResult39(result39);
    }

    @Override
    public void setResult40(String result40) {
        super.setResult40(result40);
    }

    @Override
    public void setResult41(String result41) {
        super.setResult41(result41);
    }

    @Override
    public void setResult42(String result42) {
        super.setResult42(result42);
    }

    @Override
    public void setResult43(String result43) {
        super.setResult43(result43);
    }

    @Override
    public void setResult44(String result44) {
        super.setResult44(result44);
    }

    @Override
    public void setResult45(String result45) {
        super.setResult45(result45);
    }

    @Override
    public void setResult46(String result46) {
        super.setResult46(result46);
    }

    @Override
    public void setResult47(String result47) {
        super.setResult47(result47);
    }

    @Override
    public void setResult48(String result48) {
        super.setResult48(result48);
    }

    @Override
    public void setResult49(String result49) {
        super.setResult49(result49);
    }

    @Override
    public void setResult50(String result50) {
        super.setResult50(result50);
    }

    @Override
    public void setResult51(String result51) {
        super.setResult51(result51);
    }

    @Override
    public void setResult52(String result52) {
        super.setResult52(result52);
    }

    @Override
    public void setXResMin(String xResMin) {
        super.setXResMin(xResMin);
    }

    @Override
    public void setXResMax(String xResMax) {
        super.setXResMax(xResMax);
    }

    @Override
    public void setYResMin(String yResMin) {
        super.setYResMin(yResMin);
    }

    @Override
    public void setYResMax(String yResMax) {
        super.setYResMax(yResMax);
    }

    @Override
    public void setEpoxyInspectionInterval(String epoxyInspectionInterval) {
        super.setEpoxyInspectionInterval(epoxyInspectionInterval);
    }

    @Override
    public void setResultCheckMin(String resultCheckMin) {
        super.setResultCheckMin(resultCheckMin);
    }

    @Override
    public void setResultCheckMax(String resultCheckMax) {
        super.setResultCheckMax(resultCheckMax);
    }

    public AaListParams() {
        super();
        this.simId = null;
        this.prodType = null;
        this.receivedTime = null;
    }

    @Override
    public void reset() {
        super.reset();
    }

    @Override
    public void fillWithData(List<AaListCommand> aaListCommands) {
        super.fillWithData(aaListCommands);
    }

    @Override
    public String toString() {
        return super.toString();
    }

    @Override
    public boolean equals(Object o) {
        return super.equals(o);
    }

    @Override
    public int hashCode() {
        return super.hashCode();
    }

    // 通常用于定义排序顺序，而不是这种复杂的属性比较。
    /*@Override
    public int compareTo(AaListParams o) {
        return 0;
    }*/
}
