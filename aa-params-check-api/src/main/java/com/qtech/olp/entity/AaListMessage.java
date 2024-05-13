package com.qtech.olp.entity;

import com.fasterxml.jackson.annotation.JsonFormat;

import java.lang.reflect.Field;
import java.util.Date;
import java.util.Map;
import java.util.stream.Collectors;

public class AaListMessage {

    private String mcId;
    private String prodType;
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date eventTime;
    private String list1DestroyStart;
    private String list2Init;
    private String list3Aa1;
    private String list4MtfCheck;
    private String list5Aa2;
    private String list6MtfCheck2;
    private String list7Aa3;
    private String list8MtfCheck3;
    private String list9Chartalignment2;
    private String list10Recordposition;
    private String list11Dispense;
    private String list12Epoxyinspection;
    private String list13Epoxyinspection; // Duplicate name, conSIDer renaming
    private String list14Backtoposition;
    private String list15Uvon;
    private String list16Sid;
    private String list17Uvoff;
    private String list18GripperOpen;
    private String list19Gripperopencheck;
    private String item3RoiCc;
    private String item3RoiUl;
    private String item3RoiUr;
    private String item3RoiLl;
    private String item3RoiLr;
    private String item3Target;
    private String item3CcToCornerLimit;

    public String getMcId() {
        return mcId;
    }

    public void setMcId(String mcId) {
        this.mcId = mcId;
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

    public String getList1DestroyStart() {
        return list1DestroyStart;
    }

    public void setList1DestroyStart(String list1DestroyStart) {
        this.list1DestroyStart = list1DestroyStart;
    }

    public String getList2Init() {
        return list2Init;
    }

    public void setList2Init(String list2Init) {
        this.list2Init = list2Init;
    }

    public String getList3Aa1() {
        return list3Aa1;
    }

    public void setList3Aa1(String list3Aa1) {
        this.list3Aa1 = list3Aa1;
    }

    public String getList4MtfCheck() {
        return list4MtfCheck;
    }

    public void setList4MtfCheck(String list4MtfCheck) {
        this.list4MtfCheck = list4MtfCheck;
    }

    public String getList5Aa2() {
        return list5Aa2;
    }

    public void setList5Aa2(String list5Aa2) {
        this.list5Aa2 = list5Aa2;
    }

    public String getList6MtfCheck2() {
        return list6MtfCheck2;
    }

    public void setList6MtfCheck2(String list6MtfCheck2) {
        this.list6MtfCheck2 = list6MtfCheck2;
    }

    public String getList7Aa3() {
        return list7Aa3;
    }

    public void setList7Aa3(String list7Aa3) {
        this.list7Aa3 = list7Aa3;
    }

    public String getList8MtfCheck3() {
        return list8MtfCheck3;
    }

    public void setList8MtfCheck3(String list8MtfCheck3) {
        this.list8MtfCheck3 = list8MtfCheck3;
    }

    public String getList9Chartalignment2() {
        return list9Chartalignment2;
    }

    public void setList9Chartalignment2(String list9Chartalignment2) {
        this.list9Chartalignment2 = list9Chartalignment2;
    }

    public String getList10Recordposition() {
        return list10Recordposition;
    }

    public void setList10Recordposition(String list10Recordposition) {
        this.list10Recordposition = list10Recordposition;
    }

    public String getList11Dispense() {
        return list11Dispense;
    }

    public void setList11Dispense(String list11Dispense) {
        this.list11Dispense = list11Dispense;
    }

    public String getList12Epoxyinspection() {
        return list12Epoxyinspection;
    }

    public void setList12Epoxyinspection(String list12Epoxyinspection) {
        this.list12Epoxyinspection = list12Epoxyinspection;
    }

    public String getList13Epoxyinspection() {
        return list13Epoxyinspection;
    }

    public void setList13Epoxyinspection(String list13Epoxyinspection) {
        this.list13Epoxyinspection = list13Epoxyinspection;
    }

    public String getList14Backtoposition() {
        return list14Backtoposition;
    }

    public void setList14Backtoposition(String list14Backtoposition) {
        this.list14Backtoposition = list14Backtoposition;
    }

    public String getList15Uvon() {
        return list15Uvon;
    }

    public void setList15Uvon(String list15Uvon) {
        this.list15Uvon = list15Uvon;
    }

    public String getList16Sid() {
        return list16Sid;
    }

    public void setList16Sid(String list16Sid) {
        this.list16Sid = list16Sid;
    }

    public String getList17Uvoff() {
        return list17Uvoff;
    }

    public void setList17Uvoff(String list17Uvoff) {
        this.list17Uvoff = list17Uvoff;
    }

    public String getList18GripperOpen() {
        return list18GripperOpen;
    }

    public void setList18GripperOpen(String list18GripperOpen) {
        this.list18GripperOpen = list18GripperOpen;
    }

    public String getList19Gripperopencheck() {
        return list19Gripperopencheck;
    }

    public void setList19Gripperopencheck(String list19Gripperopencheck) {
        this.list19Gripperopencheck = list19Gripperopencheck;
    }

    public String getItem3RoiCc() {
        return item3RoiCc;
    }

    public void setItem3RoiCc(String item3RoiCc) {
        this.item3RoiCc = item3RoiCc;
    }

    public String getItem3RoiUl() {
        return item3RoiUl;
    }

    public void setItem3RoiUl(String item3RoiUl) {
        this.item3RoiUl = item3RoiUl;
    }

    public String getItem3RoiUr() {
        return item3RoiUr;
    }

    public void setItem3RoiUr(String item3RoiUr) {
        this.item3RoiUr = item3RoiUr;
    }

    public String getItem3RoiLl() {
        return item3RoiLl;
    }

    public void setItem3RoiLl(String item3RoiLl) {
        this.item3RoiLl = item3RoiLl;
    }

    public String getItem3RoiLr() {
        return item3RoiLr;
    }

    public void setItem3RoiLr(String item3RoiLr) {
        this.item3RoiLr = item3RoiLr;
    }

    public String getItem3Target() {
        return item3Target;
    }

    public void setItem3Target(String item3Target) {
        this.item3Target = item3Target;
    }

    public String getItem3CcToCornerLimit() {
        return item3CcToCornerLimit;
    }

    public void setItem3CcToCornerLimit(String item3CcToCornerLimit) {
        this.item3CcToCornerLimit = item3CcToCornerLimit;
    }

    public static String underscoreToCamelCase(String input) {
        if (input == null || input.isEmpty()) {
            return input;
        }

        // 将输入按 _ 分割，然后流式处理每个部分
        String[] parts = input.split("_");
        StringBuilder camelCaseStr = new StringBuilder(parts[0].toLowerCase()); // 首个部分统一转为小写

        // 对于后续部分，除了第一个字符外，其他字符转为大写
        for (int i = 1; i < parts.length; i++) {
            if (!parts[i].isEmpty()) { // 跳过空字符串
                camelCaseStr.append(Character.toUpperCase(parts[i].charAt(0)));
                camelCaseStr.append(parts[i].substring(1).toLowerCase()); // 其余字符转小写
            }
        }

        return camelCaseStr.toString();
    }

    /**
     * 填充Map中的数据到Params对象，使用反射来动态设置属性值
     *
     * @param data 字典数据，键应与Params类的属性名匹配
     */
    public void fillWithData(Map<String, String> data) {
        if (data == null) {
            return;
        }

        Map<String, String> camelCaseData = data.entrySet().stream()
                .collect(Collectors.toMap(entry -> underscoreToCamelCase(entry.getKey()), Map.Entry::getValue));

        try {
            for (Field field : AaListMessage.class.getDeclaredFields()) {
                String camelCaseKey = field.getName();
                // System.out.println(camelCaseData);
                // System.out.println(camelCaseKey);
                if (camelCaseData.containsKey(camelCaseKey)) {
                    field.setAccessible(true);
                    field.set(this, camelCaseData.get(camelCaseKey));
                }
            }
        } catch (IllegalAccessException e) {
            throw new IllegalStateException("Failed to set properties due to reflection error", e);
        }
    }

    @Override
    public String toString() {
        return "AaParams{" +
                "mcId='" + mcId + '\'' +
                ", prodType='" + prodType + '\'' +
                ", eventTime=" + eventTime +
                ", list1DestroyStart='" + list1DestroyStart + '\'' +
                ", list2Init='" + list2Init + '\'' +
                ", list3Aa1='" + list3Aa1 + '\'' +
                ", list4MtfCheck='" + list4MtfCheck + '\'' +
                ", list5Aa2='" + list5Aa2 + '\'' +
                ", list6MtfCheck2='" + list6MtfCheck2 + '\'' +
                ", list7Aa3='" + list7Aa3 + '\'' +
                ", list8MtfCheck3='" + list8MtfCheck3 + '\'' +
                ", list9Chartalignment2='" + list9Chartalignment2 + '\'' +
                ", list10Recordposition='" + list10Recordposition + '\'' +
                ", list11Dispense='" + list11Dispense + '\'' +
                ", list12Epoxyinspection='" + list12Epoxyinspection + '\'' +
                ", list13Epoxyinspection='" + list13Epoxyinspection + '\'' +
                ", list14Backtoposition='" + list14Backtoposition + '\'' +
                ", list15Uvon='" + list15Uvon + '\'' +
                ", list16Sid='" + list16Sid + '\'' +
                ", list17Uvoff='" + list17Uvoff + '\'' +
                ", list18GripperOpen='" + list18GripperOpen + '\'' +
                ", list19Gripperopencheck='" + list19Gripperopencheck + '\'' +
                ", item3RoiCc='" + item3RoiCc + '\'' +
                ", item3RoiUl='" + item3RoiUl + '\'' +
                ", item3RoiUr='" + item3RoiUr + '\'' +
                ", item3RoiLl='" + item3RoiLl + '\'' +
                ", item3RoiLr='" + item3RoiLr + '\'' +
                ", item3Target='" + item3Target + '\'' +
                ", item3CcToCornerLimit='" + item3CcToCornerLimit + '\'' +
                '}';
    }
}
