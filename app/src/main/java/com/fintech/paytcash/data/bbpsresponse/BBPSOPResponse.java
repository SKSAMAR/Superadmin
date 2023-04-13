package com.fintech.paytcash.data.bbpsresponse;

import java.io.Serializable;
import java.lang.Boolean;
import java.lang.Integer;
import java.lang.String;
import java.util.ArrayList;

public class BBPSOPResponse implements Serializable {
  private Integer response_code;

  private ArrayList<BBPSOPData> data;

  private String message;

  private Boolean status;

  public Integer getResponse_code() {
    return this.response_code;
  }

  public void setResponse_code(Integer response_code) {
    this.response_code = response_code;
  }

  public ArrayList<BBPSOPData> getData() {
    return this.data;
  }

  public void setData(ArrayList<BBPSOPData> data) {
    this.data = data;
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

  @Override
  public String toString() {
    return "BBPSOPResponse{" +
            "response_code=" + response_code +
            ", data=" + data +
            ", message='" + message + '\'' +
            ", status=" + status +
            '}';
  }
}
