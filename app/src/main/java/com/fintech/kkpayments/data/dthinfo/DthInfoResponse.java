package com.fintech.kkpayments.data.dthinfo;

import java.io.Serializable;
import java.lang.Boolean;
import java.lang.Integer;
import java.lang.String;
import java.util.List;

public class DthInfoResponse implements Serializable {
  private Integer response_code;

  private String message;

  private Boolean status;

  private List<? extends Info> info;

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

  public Boolean getStatus() {
    return this.status;
  }

  public void setStatus(Boolean status) {
    this.status = status;
  }

  public List<? extends Info> getInfo() {
    return this.info;
  }

  public void setInfo(List<? extends Info> info) {
    this.info = info;
  }

  public static class Info implements Serializable {
    private String NextRechargeDate;

    private Integer lastrechargeamount;

    private String planname;

    private String MonthlyRecharge;

    private String lastrechargedate;

    private String Balance;

    private String customerName;

    private String status;

    public String getNextRechargeDate() {
      return this.NextRechargeDate;
    }

    public void setNextRechargeDate(String NextRechargeDate) {
      this.NextRechargeDate = NextRechargeDate;
    }

    public Integer getLastrechargeamount() {
      return this.lastrechargeamount;
    }

    public void setLastrechargeamount(Integer lastrechargeamount) {
      this.lastrechargeamount = lastrechargeamount;
    }

    public String getPlanname() {
      return this.planname;
    }

    public void setPlanname(String planname) {
      this.planname = planname;
    }

    public String getMonthlyRecharge() {
      return this.MonthlyRecharge;
    }

    public void setMonthlyRecharge(String MonthlyRecharge) {
      this.MonthlyRecharge = MonthlyRecharge;
    }

    public String getLastrechargedate() {
      return this.lastrechargedate;
    }

    public void setLastrechargedate(String lastrechargedate) {
      this.lastrechargedate = lastrechargedate;
    }

    public String getBalance() {
      return this.Balance;
    }

    public void setBalance(String Balance) {
      this.Balance = Balance;
    }

    public String getCustomerName() {
      return this.customerName;
    }

    public void setCustomerName(String customerName) {
      this.customerName = customerName;
    }

    public String getStatus() {
      return this.status;
    }

    public void setStatus(String status) {
      this.status = status;
    }

    @Override
    public String toString() {
      return "Info{" +
              "NextRechargeDate='" + NextRechargeDate + '\'' +
              ", lastrechargeamount=" + lastrechargeamount +
              ", planname='" + planname + '\'' +
              ", MonthlyRecharge='" + MonthlyRecharge + '\'' +
              ", lastrechargedate='" + lastrechargedate + '\'' +
              ", Balance='" + Balance + '\'' +
              ", customerName='" + customerName + '\'' +
              ", status='" + status + '\'' +
              '}';
    }
  }

  @Override
  public String toString() {
    return "DthInfoResponse{" +
            "response_code=" + response_code +
            ", message='" + message + '\'' +
            ", status=" + status +
            ", info=" + info +
            '}';
  }
}
