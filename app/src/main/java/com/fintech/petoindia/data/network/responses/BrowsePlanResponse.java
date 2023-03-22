package com.fintech.petoindia.data.network.responses;

public class BrowsePlanResponse {
    String rs;
    String desc;
    String validity;
    String last_update;

    public BrowsePlanResponse(String rs, String desc, String validity, String last_update) {
        this.rs = rs;
        this.desc = desc;
        this.validity = validity;
        this.last_update = last_update;
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

    public String getValidity() {
        return validity;
    }

    public void setValidity(String validity) {
        this.validity = validity;
    }

    public String getLast_update() {
        return last_update;
    }

    public void setLast_update(String last_update) {
        this.last_update = last_update;
    }

    @Override
    public String toString() {
        return "BrowsePlanResponse{" +
                "rs='" + rs + '\'' +
                ", desc='" + desc + '\'' +
                ", validity='" + validity + '\'' +
                ", last_update='" + last_update + '\'' +
                '}';
    }
}
