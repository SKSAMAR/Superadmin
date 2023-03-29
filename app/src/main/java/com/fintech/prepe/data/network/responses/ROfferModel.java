package com.fintech.prepe.data.network.responses;

public class ROfferModel {
    String rs;
    String desc;

    public ROfferModel(String rs, String desc) {
        this.rs = rs;
        this.desc = desc;
    }

    public String getRs() {
        return rs;
    }

    public void setRs(String rs) {
        this.rs = rs;
    }

    public String getDesc() {
        return desc;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }

    @Override
    public String toString() {
        return "ROfferModel{" +
                "rs='" + rs + '\'' +
                ", desc='" + desc + '\'' +
                '}';
    }
}
