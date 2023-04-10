package com.fintech.scnpay.data.accountop;

import java.io.Serializable;
import java.lang.Boolean;
import java.lang.Integer;
import java.lang.String;

public class AccountOPeningResponse implements Serializable {
  private Integer response_code;

  private String data;

  private String type;

  private String message;

  private Boolean status;

  public Integer getResponse_code() {
    return this.response_code;
  }

  public void setResponse_code(Integer response_code) {
    this.response_code = response_code;
  }

  public String getData() {
    return this.data;
  }

  public void setData(String data) {
    this.data = data;
  }

  public String getType() {
    return this.type;
  }

  public void setType(String type) {
    this.type = type;
  }

  public String getMessage() {
    return this.message;
  }

  public void setMessage(String message) {
    this.message = message;
  }

  public Boolean getStatus() {
    return this.status;
  }

  public void setStatus(Boolean status) {
    this.status = status;
  }
}
