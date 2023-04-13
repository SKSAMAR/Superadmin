package com.fintech.paytcash.data.hlr;

import com.fintech.paytcash.data.model.OperatorModel;

import java.io.Serializable;
import java.lang.Boolean;
import java.lang.Integer;
import java.lang.String;

public class HLRResponse implements Serializable {

  public HLRResponse() {

  }

  public HLRResponse(Integer response_code, String message, OperatorModel operator, Boolean status, Info info) {
    this.response_code = response_code;
    this.message = message;
    this.operator = operator;
    this.status = status;
    this.info = info;
  }

  private Integer response_code;

  private String message;

  private OperatorModel operator;

  private Boolean status;

  private Info info;

  public Integer getResponse_code() {
    return this.response_code;
  }

  public void setResponse_code(Integer response_code) {
    this.response_code = response_code;
  }

  public String getMessage() {
    return this.message;
  }

  public void setMessage(String message) {
    this.message = message;
  }

  public OperatorModel getOperator() {
    return this.operator;
  }

  public void setOperator(OperatorModel operator) {
    this.operator = operator;
  }

  public Boolean getStatus() {
    return this.status;
  }

  public void setStatus(Boolean status) {
    this.status = status;
  }

  public Info getInfo() {
    return this.info;
  }

  public void setInfo(Info info) {
    this.info = info;
  }


  public static class Info implements Serializable {

    public Info() {

    }

    public Info(String circle, String operator) {
      this.circle = circle;
      this.operator = operator;
    }

    private String circle;

    private String operator;

    public String getCircle() {
      return this.circle;
    }

    public void setCircle(String circle) {
      this.circle = circle;
    }

    public String getOperator() {
      return this.operator;
    }

    public void setOperator(String operator) {
      this.operator = operator;
    }
  }
}
