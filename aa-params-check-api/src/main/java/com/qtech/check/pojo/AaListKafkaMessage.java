package com.qtech.check.pojo;

/**
 * author :  gaozhilin
 * email  :  gaoolin@gmail.com
 * date   :  2024/05/13 08:50:06
 * desc   :
 */


public class AaListKafkaMessage {

    private String OpCode;
    private String WoCode;
    private String FactoryName;

    public String getOpCode() {
        return OpCode;
    }

    public void setOpCode(String opCode) {
        OpCode = opCode;
    }

    public String getWoCode() {
        return WoCode;
    }

    public void setWoCode(String woCode) {
        WoCode = woCode;
    }

    public String getFactoryName() {
        return FactoryName;
    }

    public void setFactoryName(String factoryName) {
        FactoryName = factoryName;
    }

    @Override
    public String toString() {
        return "AaListKafkaMessage{" +
                "OpCode='" + OpCode + '\'' +
                ", WoCode='" + WoCode + '\'' +
                ", FactoryName='" + FactoryName + '\'' +
                '}';
    }
}
