package com.fintech.prepe.data_model.plan;

import java.io.Serializable;
import java.lang.String;

public class CFPlanModel implements Serializable {
  private String DATE;

  private String STATUS;

  private String AMOUNT;

  private String DESCRIPTION;

  private String PLAN_ID;

  private String INTERVALS;

  private String ID;

  private String PLAN_NAME;

  private String PLAN_TYPE;

  private String INTERVAL_TYPE;

  public String getDATE() {
    return this.DATE;
  }

  public void setDATE(String DATE) {
    this.DATE = DATE;
  }

  public String getSTATUS() {
    return this.STATUS;
  }

  public void setSTATUS(String STATUS) {
    this.STATUS = STATUS;
  }

  public String getAMOUNT() {
    return this.AMOUNT;
  }

  public void setAMOUNT(String AMOUNT) {
    this.AMOUNT = AMOUNT;
  }

  public String getDESCRIPTION() {
    return this.DESCRIPTION;
  }

  public void setDESCRIPTION(String DESCRIPTION) {
    this.DESCRIPTION = DESCRIPTION;
  }

  public String getPLAN_ID() {
    return this.PLAN_ID;
  }

  public void setPLAN_ID(String PLAN_ID) {
    this.PLAN_ID = PLAN_ID;
  }

  public String getINTERVALS() {
    return this.INTERVALS;
  }

  public void setINTERVALS(String INTERVALS) {
    this.INTERVALS = INTERVALS;
  }

  public String getID() {
    return this.ID;
  }

  public void setID(String ID) {
    this.ID = ID;
  }

  public String getPLAN_NAME() {
    return this.PLAN_NAME;
  }

  public void setPLAN_NAME(String PLAN_NAME) {
    this.PLAN_NAME = PLAN_NAME;
  }

  public String getPLAN_TYPE() {
    return this.PLAN_TYPE;
  }

  public void setPLAN_TYPE(String PLAN_TYPE) {
    this.PLAN_TYPE = PLAN_TYPE;
  }

  public String getINTERVAL_TYPE() {
    return this.INTERVAL_TYPE;
  }

  public void setINTERVAL_TYPE(String INTERVAL_TYPE) {
    this.INTERVAL_TYPE = INTERVAL_TYPE;
  }
}
