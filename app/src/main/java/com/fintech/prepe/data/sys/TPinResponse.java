package com.fintech.prepe.data.sys;

import java.io.Serializable;
import java.lang.String;

public class TPinResponse implements Serializable {
  private String DATE;

  private String STATUS;

  private String TPIN;

  private String USER_ID;

  private String TOKEN_ID;

  private String ID;

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

  public String getTPIN() {
    return this.TPIN;
  }

  public void setTPIN(String TPIN) {
    this.TPIN = TPIN;
  }

  public String getUSER_ID() {
    return this.USER_ID;
  }

  public void setUSER_ID(String USER_ID) {
    this.USER_ID = USER_ID;
  }

  public String getTOKEN_ID() {
    return this.TOKEN_ID;
  }

  public void setTOKEN_ID(String TOKEN_ID) {
    this.TOKEN_ID = TOKEN_ID;
  }

  public String getID() {
    return this.ID;
  }

  public void setID(String ID) {
    this.ID = ID;
  }
}
