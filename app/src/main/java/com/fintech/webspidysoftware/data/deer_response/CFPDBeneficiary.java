package com.fintech.webspidysoftware.data.deer_response;

import java.io.Serializable;
import java.lang.String;

public class CFPDBeneficiary implements Serializable {
  private String DATE;

  private String ACCOUNT;

  private String BENEID;

  private String ADDRESS;

  private String ID;

  private String EMAIL;

  private String NAME;

  private String MOBILE;

  private String IFSC;

  private Boolean isVerified = false;

  public Boolean getVerified() {
    return isVerified;
  }

  public void setVerified(Boolean verified) {
    isVerified = verified;
  }

  public String getDATE() {
    return this.DATE;
  }

  public void setDATE(String DATE) {
    this.DATE = DATE;
  }

  public String getACCOUNT() {
    return this.ACCOUNT;
  }

  public void setACCOUNT(String ACCOUNT) {
    this.ACCOUNT = ACCOUNT;
  }

  public String getBENEID() {
    return this.BENEID;
  }

  public void setBENEID(String BENEID) {
    this.BENEID = BENEID;
  }

  public String getADDRESS() {
    return this.ADDRESS;
  }

  public void setADDRESS(String ADDRESS) {
    this.ADDRESS = ADDRESS;
  }

  public String getID() {
    return this.ID;
  }

  public void setID(String ID) {
    this.ID = ID;
  }

  public String getEMAIL() {
    return this.EMAIL;
  }

  public void setEMAIL(String EMAIL) {
    this.EMAIL = EMAIL;
  }

  public String getNAME() {
    return this.NAME;
  }

  public void setNAME(String NAME) {
    this.NAME = NAME;
  }

  public String getMOBILE() {
    return this.MOBILE;
  }

  public void setMOBILE(String MOBILE) {
    this.MOBILE = MOBILE;
  }

  public String getIFSC() {
    return this.IFSC;
  }

  public void setIFSC(String IFSC) {
    this.IFSC = IFSC;
  }

  @Override
  public String toString() {
    return "CFPDBeneficiary{" +
            "DATE='" + DATE + '\'' +
            ", ACCOUNT='" + ACCOUNT + '\'' +
            ", BENEID='" + BENEID + '\'' +
            ", ADDRESS='" + ADDRESS + '\'' +
            ", ID='" + ID + '\'' +
            ", EMAIL='" + EMAIL + '\'' +
            ", NAME='" + NAME + '\'' +
            ", MOBILE='" + MOBILE + '\'' +
            ", IFSC='" + IFSC + '\'' +
            '}';
  }
}
