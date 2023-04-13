package com.fintech.paytcash.data.network.dmt;


import com.fintech.paytcash.data.network.responses.BeneficiaryBank;

import java.io.Serializable;
import java.util.List;

public class FetchBeneficiaryResponse implements Serializable {
  private Integer response_code;

  private List<BeneficiaryBank> data;

  private String message;

  private Boolean status;

  public Integer getResponse_code() {
    return this.response_code;
  }

  public void setResponse_code(Integer response_code) {
    this.response_code = response_code;
  }

  public List<BeneficiaryBank> getData() {
    return this.data;
  }

  public void setData(List<BeneficiaryBank> data) {
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
}
