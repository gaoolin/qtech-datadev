package com.qtech.mq.serializer;

/**
 * author :  gaozhilin
 * email  :  gaoolin@gmail.com
 * date   :  2024/08/24 11:07:37
 * desc   :
 */

public class CompositeKey {
    private final String simId;
    private final String prodType;
    private final String chkDt;

    public CompositeKey(String simId, String prodType, String chkDt) {
        this.simId = simId;
        this.prodType = prodType;
        this.chkDt = chkDt;
    }

    public String getSimId() {
        return simId;
    }

    public String getProdType() {
        return prodType;
    }

    public String getChkDt() {
        return chkDt;
    }



    // Getters, equals, hashCode, toString 方法

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        CompositeKey that = (CompositeKey) o;
        return simId.equals(that.simId) && prodType.equals(that.prodType) && chkDt.equals(that.chkDt);
    }

    @Override
    public int hashCode() {
        return 31 * simId.hashCode() + prodType.hashCode();
    }

    @Override
    public String toString() {
        return "CompositeKey{" +
                "simId='" + simId + '\'' +
                ", prodType='" + prodType + '\'' +
                ", chkDt='" + chkDt + '\'' +
                '}';
    }
}
