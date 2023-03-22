package com.fintech.petoindia.data.model;

import java.io.Serializable;

public class VirtualAccountModel implements Serializable {

    private String ID;
    private String USER_ID;
    private String ACCOUNT_NUM;
    private String IFSC;
    private String notifGroup;
    private String RESPONSE;
    private String DATE;
    private String VA_ID;
    private String UPI;

    public String getUPI() {
        return UPI;
    }

    public void setUPI(String UPI) {
        this.UPI = UPI;
    }

    public String getID() {
        return ID;
    }

    public String getUSER_ID() {
        return USER_ID;
    }

    public String getACCOUNT_NUM() {
        return ACCOUNT_NUM;
    }

    public String getIFSC() {
        return IFSC;
    }

    public String getNotifGroup() {
        return notifGroup;
    }

    public String getRESPONSE() {
        return RESPONSE;
    }

    public String getDATE() {
        return DATE;
    }

    public String getVA_ID() {
        return VA_ID;
    }


    public void setID(String ID) {
        this.ID = ID;
    }

    public void setUSER_ID(String USER_ID) {
        this.USER_ID = USER_ID;
    }

    public void setACCOUNT_NUM(String ACCOUNT_NUM) {
        this.ACCOUNT_NUM = ACCOUNT_NUM;
    }

    public void setIFSC(String IFSC) {
        this.IFSC = IFSC;
    }

    public void setNotifGroup(String notifGroup) {
        this.notifGroup = notifGroup;
    }

    public void setRESPONSE(String RESPONSE) {
        this.RESPONSE = RESPONSE;
    }

    public void setDATE(String DATE) {
        this.DATE = DATE;
    }

    public void setVA_ID(String VA_ID) {
        this.VA_ID = VA_ID;
    }

    @Override
    public String toString() {
        return "VirtualAccountModel{" +
                "ID='" + ID + '\'' +
                ", USER_ID='" + USER_ID + '\'' +
                ", ACCOUNT_NUM='" + ACCOUNT_NUM + '\'' +
                ", IFSC='" + IFSC + '\'' +
                ", notifGroup='" + notifGroup + '\'' +
                ", RESPONSE='" + RESPONSE + '\'' +
                ", DATE='" + DATE + '\'' +
                ", VA_ID='" + VA_ID + '\'' +
                '}';
    }
}

