package com.fintech.webspidysoftware.data.model;

import java.io.Serializable;

public class PDMTHistoryModel implements Serializable {
    private String ID;
    private String OWNER;
    private String OWNER_ID;
    private String USER_ID;
    private String USER_TYPE;
    private String MOBILE;
    private String BENE_ID;
    private String ACCOUNT;
    private String TIMESTAMP;
    private String RESPONSE;
    private String AMOUNT;
    private String TRANS_TYPE;
    private String STATUS;
    private String REFFRENCE_ID;
    private String COMM_REFID;
    private String RES_REFID;
    private String CHECK_RESPONSE;
    private String FILTER_DATE;
    private String REFUND_RESPONSE;




    public String getID() {
        return ID;
    }

    public String getOWNER() {
        return OWNER;
    }

    public String getOWNER_ID() {
        return OWNER_ID;
    }

    public String getUSER_ID() {
        return USER_ID;
    }

    public String getUSER_TYPE() {
        return USER_TYPE;
    }

    public String getMOBILE() {
        return MOBILE;
    }

    public String getBENE_ID() {
        return BENE_ID;
    }

    public String getACCOUNT() {
        return ACCOUNT;
    }

    public String getTIMESTAMP() {
        return TIMESTAMP;
    }

    public String getRESPONSE() {
        return RESPONSE;
    }

    public String getAMOUNT() {
        return AMOUNT;
    }

    public String getTRANS_TYPE() {
        return TRANS_TYPE;
    }

    public String getSTATUS() {
        return STATUS;
    }

    public String getREFFRENCE_ID() {
        return REFFRENCE_ID;
    }

    public String getCOMM_REFID() {
        return COMM_REFID;
    }

    public String getRES_REFID() {
        return RES_REFID;
    }

    public String getCHECK_RESPONSE() {
        return CHECK_RESPONSE;
    }

    public String getFILTER_DATE() {
        return FILTER_DATE;
    }

    public String getREFUND_RESPONSE() {
        return REFUND_RESPONSE;
    }


    public void setID(String ID) {
        this.ID = ID;
    }

    public void setOWNER(String OWNER) {
        this.OWNER = OWNER;
    }

    public void setOWNER_ID(String OWNER_ID) {
        this.OWNER_ID = OWNER_ID;
    }

    public void setUSER_ID(String USER_ID) {
        this.USER_ID = USER_ID;
    }

    public void setUSER_TYPE(String USER_TYPE) {
        this.USER_TYPE = USER_TYPE;
    }

    public void setMOBILE(String MOBILE) {
        this.MOBILE = MOBILE;
    }

    public void setBENE_ID(String BENE_ID) {
        this.BENE_ID = BENE_ID;
    }

    public void setACCOUNT(String ACCOUNT) {
        this.ACCOUNT = ACCOUNT;
    }

    public void setTIMESTAMP(String TIMESTAMP) {
        this.TIMESTAMP = TIMESTAMP;
    }

    public void setRESPONSE(String RESPONSE) {
        this.RESPONSE = RESPONSE;
    }

    public void setAMOUNT(String AMOUNT) {
        this.AMOUNT = AMOUNT;
    }

    public void setTRANS_TYPE(String TRANS_TYPE) {
        this.TRANS_TYPE = TRANS_TYPE;
    }

    public void setSTATUS(String STATUS) {
        this.STATUS = STATUS;
    }

    public void setREFFRENCE_ID(String REFFRENCE_ID) {
        this.REFFRENCE_ID = REFFRENCE_ID;
    }

    public void setCOMM_REFID(String COMM_REFID) {
        this.COMM_REFID = COMM_REFID;
    }

    public void setRES_REFID(String RES_REFID) {
        this.RES_REFID = RES_REFID;
    }

    public void setCHECK_RESPONSE(String CHECK_RESPONSE) {
        this.CHECK_RESPONSE = CHECK_RESPONSE;
    }

    public void setFILTER_DATE(String FILTER_DATE) {
        this.FILTER_DATE = FILTER_DATE;
    }

    public void setREFUND_RESPONSE(String REFUND_RESPONSE) {
        this.REFUND_RESPONSE = REFUND_RESPONSE;
    }

    @Override
    public String toString() {
        return "PDMTHistoryModel{" +
                "ID='" + ID + '\'' +
                ", OWNER='" + OWNER + '\'' +
                ", OWNER_ID='" + OWNER_ID + '\'' +
                ", USER_ID='" + USER_ID + '\'' +
                ", USER_TYPE='" + USER_TYPE + '\'' +
                ", MOBILE='" + MOBILE + '\'' +
                ", BENE_ID='" + BENE_ID + '\'' +
                ", ACCOUNT='" + ACCOUNT + '\'' +
                ", TIMESTAMP='" + TIMESTAMP + '\'' +
                ", RESPONSE='" + RESPONSE + '\'' +
                ", AMOUNT='" + AMOUNT + '\'' +
                ", TRANS_TYPE='" + TRANS_TYPE + '\'' +
                ", STATUS='" + STATUS + '\'' +
                ", REFFRENCE_ID='" + REFFRENCE_ID + '\'' +
                ", COMM_REFID='" + COMM_REFID + '\'' +
                ", RES_REFID='" + RES_REFID + '\'' +
                ", CHECK_RESPONSE='" + CHECK_RESPONSE + '\'' +
                ", FILTER_DATE='" + FILTER_DATE + '\'' +
                ", REFUND_RESPONSE='" + REFUND_RESPONSE + '\'' +
                '}';
    }
}
