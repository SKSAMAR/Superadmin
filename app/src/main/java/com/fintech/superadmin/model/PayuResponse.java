package com.fintech.superadmin.model;

import java.io.Serializable;

public class PayuResponse implements Serializable {
  public Integer response_code;
  public Boolean status;
  public String message;
  public String salt;
  public String key;

  public PayuResponse(Integer response_code, Boolean status, String message, String salt, String key) {
    this.response_code = response_code;
    this.status = status;
    this.message = message;
    this.salt = salt;
    this.key = key;
  }

  public Integer getResponse_code() {
    return response_code;
  }

  public void setResponse_code(Integer response_code) {
    this.response_code = response_code;
  }

  public Boolean getStatus() {
    return status;
  }

  public void setStatus(Boolean status) {
    this.status = status;
  }

  public String getMessage() {
    return message;
  }

  public void setMessage(String message) {
    this.message = message;
  }

  public String getSalt() {
    return salt;
  }

  public void setSalt(String salt) {
    this.salt = salt;
  }

  public String getKey() {
    return key;
  }

  public void setKey(String key) {
    this.key = key;
  }
}
