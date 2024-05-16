package com.qtech.olp.entity;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.qtech.olp.utils.Command;
import com.qtech.olp.utils.ToCamelCaseConverter;

import java.lang.reflect.Field;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * author :  gaozhilin
 * email  :  gaoolin@gmail.com
 * date   :  2024/05/14 11:50:36
 * desc   :
 */


public class AaListParamsMessage {

    private String simId;
    private String prodType;
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date eventTime;
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

    public Date getEventTime() {
        return eventTime;
    }

    public void setEventTime(Date eventTime) {
        this.eventTime = eventTime;
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

    public void fillWithData(List<Command> commands) {
        if (commands.isEmpty()) {
            return;
        }

        List<Map<String, String>> camelCaseData = commands.stream()
                .map(command -> {
                    Map<String, String> map = new HashMap<>();
                    map.put(ToCamelCaseConverter.doConvert(command.getCommand()), command.getEnable());
                    return map;
                })
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

    @Override
    public String toString() {
        return "AaListParamsMessage{" +
                "simId='" + simId + '\'' +
                ", prodType='" + prodType + '\'' +
                ", eventTime=" + eventTime +
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
                '}';
    }
}
