package com.fintech.webspidysoftware.data.deer_response;

import java.io.Serializable;
import java.lang.Integer;
import java.lang.String;

public class CFPStatusCheck implements Serializable {
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
    private Transfer transfer;

    public Transfer getTransfer() {
      return this.transfer;
    }

    public void setTransfer(Transfer transfer) {
      this.transfer = transfer;
    }

    public static class Transfer implements Serializable {
      private String bankAccount;

      private String amount;

      private String utr;

      private String processedOn;

      private Integer acknowledged;

      private String phone;

      private String transferMode;

      private String beneId;

      private String transferId;

      private String ifsc;

      private String addedOn;

      private String status;

      public String getBankAccount() {
        return this.bankAccount;
      }

      public void setBankAccount(String bankAccount) {
        this.bankAccount = bankAccount;
      }

      public String getAmount() {
        return this.amount;
      }

      public void setAmount(String amount) {
        this.amount = amount;
      }

      public String getUtr() {
        return this.utr;
      }

      public void setUtr(String utr) {
        this.utr = utr;
      }

      public String getProcessedOn() {
        return this.processedOn;
      }

      public void setProcessedOn(String processedOn) {
        this.processedOn = processedOn;
      }

      public Integer getAcknowledged() {
        return this.acknowledged;
      }

      public void setAcknowledged(Integer acknowledged) {
        this.acknowledged = acknowledged;
      }

      public String getPhone() {
        return this.phone;
      }

      public void setPhone(String phone) {
        this.phone = phone;
      }

      public String getTransferMode() {
        return this.transferMode;
      }

      public void setTransferMode(String transferMode) {
        this.transferMode = transferMode;
      }

      public String getBeneId() {
        return this.beneId;
      }

      public void setBeneId(String beneId) {
        this.beneId = beneId;
      }

      public String getTransferId() {
        return this.transferId;
      }

      public void setTransferId(String transferId) {
        this.transferId = transferId;
      }

      public String getIfsc() {
        return this.ifsc;
      }

      public void setIfsc(String ifsc) {
        this.ifsc = ifsc;
      }

      public String getAddedOn() {
        return this.addedOn;
      }

      public void setAddedOn(String addedOn) {
        this.addedOn = addedOn;
      }

      public String getStatus() {
        return this.status;
      }

      public void setStatus(String status) {
        this.status = status;
      }
    }
  }
}
