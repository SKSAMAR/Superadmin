package com.fintech.prepe.data.deer_response;

import java.io.Serializable;
import java.lang.String;

public class CFPTransaction implements Serializable {
  private Data data;

  private String subCode;

  private String message;

  private String status;

  public Data getData() {
    return this.data;
  }

  public void setData(Data data) {
    this.data = data;
  }

  public String getSubCode() {
    return this.subCode;
  }

  public void setSubCode(String subCode) {
    this.subCode = subCode;
  }

  public String getMessage() {
    if(this.message == null){
       return "Resend OTP Again, Something has went wrong.";
    }
    return this.message;
  }

  public void setMessage(String message) {
    this.message = message;
  }

  public String getStatus() {
    return this.status;
  }

  public void setStatus(String status) {
    this.status = status;
  }

  public static class Data implements Serializable {
    private String referenceId;

    public String getReferenceId() {
      return this.referenceId;
    }

    public void setReferenceId(String referenceId) {
      this.referenceId = referenceId;
    }
  }
}
