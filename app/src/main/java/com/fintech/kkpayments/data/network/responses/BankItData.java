package com.fintech.kkpayments.data.network.responses;

import java.io.Serializable;

public class BankItData implements Serializable {
    String FIRST_NAME;
    String LAST_NAME;
    String SERIAL_NUMBER;
    String AADHAAR_NO;
    String PAN_NO;
    String MOBILE_NUMBER;
    String EMAIL;
    String STATUS;
    String AGENT_ID;
    String PARTNER_AGENT_ID;

    public BankItData(String FIRST_NAME, String LAST_NAME, String SERIAL_NUMBER, String AADHAAR_NO, String PAN_NO, String MOBILE_NUMBER, String EMAIL, String STATUS, String AGENT_ID, String PARTNER_AGENT_ID) {
        this.FIRST_NAME = FIRST_NAME;
        this.LAST_NAME = LAST_NAME;
        this.SERIAL_NUMBER = SERIAL_NUMBER;
        this.AADHAAR_NO = AADHAAR_NO;
        this.PAN_NO = PAN_NO;
        this.MOBILE_NUMBER = MOBILE_NUMBER;
        this.EMAIL = EMAIL;
        this.STATUS = STATUS;
        this.AGENT_ID = AGENT_ID;
        this.PARTNER_AGENT_ID = PARTNER_AGENT_ID;
    }

    public String getFIRST_NAME() {
        return FIRST_NAME;
    }

    public void setFIRST_NAME(String FIRST_NAME) {
        this.FIRST_NAME = FIRST_NAME;
    }

    public String getLAST_NAME() {
        return LAST_NAME;
    }

    public void setLAST_NAME(String LAST_NAME) {
        this.LAST_NAME = LAST_NAME;
    }

    public String getSERIAL_NUMBER() {
        return SERIAL_NUMBER;
    }

    public void setSERIAL_NUMBER(String SERIAL_NUMBER) {
        this.SERIAL_NUMBER = SERIAL_NUMBER;
    }

    public String getAADHAAR_NO() {
        return AADHAAR_NO;
    }

    public void setAADHAAR_NO(String AADHAAR_NO) {
        this.AADHAAR_NO = AADHAAR_NO;
    }

    public String getPAN_NO() {
        return PAN_NO;
    }

    public void setPAN_NO(String PAN_NO) {
        this.PAN_NO = PAN_NO;
    }

    public String getMOBILE_NUMBER() {
        return MOBILE_NUMBER;
    }

    public void setMOBILE_NUMBER(String MOBILE_NUMBER) {
        this.MOBILE_NUMBER = MOBILE_NUMBER;
    }

    public String getEMAIL() {
        return EMAIL;
    }

    public void setEMAIL(String EMAIL) {
        this.EMAIL = EMAIL;
    }

    public String getSTATUS() {
        return STATUS;
    }

    public void setSTATUS(String STATUS) {
        this.STATUS = STATUS;
    }

    public String getAGENT_ID() {
        return AGENT_ID;
    }

    public void setAGENT_ID(String AGENT_ID) {
        this.AGENT_ID = AGENT_ID;
    }

    public String getPARTNER_AGENT_ID() {
        return PARTNER_AGENT_ID;
    }

    public void setPARTNER_AGENT_ID(String PARTNER_AGENT_ID) {
        this.PARTNER_AGENT_ID = PARTNER_AGENT_ID;
    }
}
