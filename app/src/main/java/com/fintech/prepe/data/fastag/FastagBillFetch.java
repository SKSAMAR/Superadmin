package com.fintech.prepe.data.fastag;

import java.io.Serializable;
import java.lang.Boolean;
import java.lang.Integer;
import java.lang.String;

public class FastagBillFetch implements Serializable {
  private Integer response_code;

  private String amount;

  private String duedate;

  private String name;

  private Bill_fetch bill_fetch;

  private String message;

  private Boolean status;

  public Integer getResponse_code() {
    return this.response_code;
  }

  public void setResponse_code(Integer response_code) {
    this.response_code = response_code;
  }

  public String getAmount() {
    return this.amount;
  }

  public void setAmount(String amount) {
    this.amount = amount;
  }

  public String getDuedate() {
    return this.duedate;
  }

  public void setDuedate(String duedate) {
    this.duedate = duedate;
  }

  public String getName() {
    return this.name;
  }

  public void setName(String name) {
    this.name = name;
  }

  public Bill_fetch getBill_fetch() {
    return this.bill_fetch;
  }

  public void setBill_fetch(Bill_fetch bill_fetch) {
    this.bill_fetch = bill_fetch;
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

  public static class Bill_fetch implements Serializable {
    private String billAmount;

    private String billnetamount;

    private Integer minBillAmount;

    private Boolean acceptPartPay;

    private String userName;

    private Boolean acceptPayment;

    private String cellNumber;

    public String getBillAmount() {
      return this.billAmount;
    }

    public void setBillAmount(String billAmount) {
      this.billAmount = billAmount;
    }

    public String getBillnetamount() {
      return this.billnetamount;
    }

    public void setBillnetamount(String billnetamount) {
      this.billnetamount = billnetamount;
    }

    public Integer getMinBillAmount() {
      return this.minBillAmount;
    }

    public void setMinBillAmount(Integer minBillAmount) {
      this.minBillAmount = minBillAmount;
    }

    public Boolean getAcceptPartPay() {
      return this.acceptPartPay;
    }

    public void setAcceptPartPay(Boolean acceptPartPay) {
      this.acceptPartPay = acceptPartPay;
    }

    public String getUserName() {
      return this.userName;
    }

    public void setUserName(String userName) {
      this.userName = userName;
    }

    public Boolean getAcceptPayment() {
      return this.acceptPayment;
    }

    public void setAcceptPayment(Boolean acceptPayment) {
      this.acceptPayment = acceptPayment;
    }

    public String getCellNumber() {
      return this.cellNumber;
    }

    public void setCellNumber(String cellNumber) {
      this.cellNumber = cellNumber;
    }
  }
}
