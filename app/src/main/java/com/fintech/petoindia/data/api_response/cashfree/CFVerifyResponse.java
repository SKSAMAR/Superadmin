package com.fintech.petoindia.data.api_response.cashfree;

import java.io.Serializable;
import java.lang.Integer;
import java.lang.String;

public class CFVerifyResponse implements Serializable {
  private Result result;

  private String task;

  private String patronId;

  private Essentials essentials;

  private String id;

  public Result getResult() {
    return this.result;
  }

  public void setResult(Result result) {
    this.result = result;
  }

  public String getTask() {
    return this.task;
  }

  public void setTask(String task) {
    this.task = task;
  }

  public String getPatronId() {
    return this.patronId;
  }

  public void setPatronId(String patronId) {
    this.patronId = patronId;
  }

  public Essentials getEssentials() {
    return this.essentials;
  }

  public void setEssentials(Essentials essentials) {
    this.essentials = essentials;
  }

  public String getId() {
    return this.id;
  }

  public void setId(String id) {
    this.id = id;
  }

  public static class Result implements Serializable {
    private String reason;

    private BankTransfer bankTransfer;

    private Integer nameMatchScore;

    private String mobileMatch;

    private String active;

    private String signzyReferenceId;

    private AuditTrail auditTrail;

    private String nameMatch;

    public String getReason() {
      return this.reason;
    }

    public void setReason(String reason) {
      this.reason = reason;
    }

    public BankTransfer getBankTransfer() {
      return this.bankTransfer;
    }

    public void setBankTransfer(BankTransfer bankTransfer) {
      this.bankTransfer = bankTransfer;
    }

    public Integer getNameMatchScore() {
      return this.nameMatchScore;
    }

    public void setNameMatchScore(Integer nameMatchScore) {
      this.nameMatchScore = nameMatchScore;
    }

    public String getMobileMatch() {
      return this.mobileMatch;
    }

    public void setMobileMatch(String mobileMatch) {
      this.mobileMatch = mobileMatch;
    }

    public String getActive() {
      return this.active;
    }

    public void setActive(String active) {
      this.active = active;
    }

    public String getSignzyReferenceId() {
      return this.signzyReferenceId;
    }

    public void setSignzyReferenceId(String signzyReferenceId) {
      this.signzyReferenceId = signzyReferenceId;
    }

    public AuditTrail getAuditTrail() {
      return this.auditTrail;
    }

    public void setAuditTrail(AuditTrail auditTrail) {
      this.auditTrail = auditTrail;
    }

    public String getNameMatch() {
      return this.nameMatch;
    }

    public void setNameMatch(String nameMatch) {
      this.nameMatch = nameMatch;
    }

    public static class BankTransfer implements Serializable {
      private String beneName;

      private String beneMMID;

      private String response;

      private String beneIFSC;

      private String bankRRN;

      private String beneMobile;

      public String getBeneName() {
        return this.beneName;
      }

      public void setBeneName(String beneName) {
        this.beneName = beneName;
      }

      public String getBeneMMID() {
        return this.beneMMID;
      }

      public void setBeneMMID(String beneMMID) {
        this.beneMMID = beneMMID;
      }

      public String getResponse() {
        return this.response;
      }

      public void setResponse(String response) {
        this.response = response;
      }

      public String getBeneIFSC() {
        return this.beneIFSC;
      }

      public void setBeneIFSC(String beneIFSC) {
        this.beneIFSC = beneIFSC;
      }

      public String getBankRRN() {
        return this.bankRRN;
      }

      public void setBankRRN(String bankRRN) {
        this.bankRRN = bankRRN;
      }

      public String getBeneMobile() {
        return this.beneMobile;
      }

      public void setBeneMobile(String beneMobile) {
        this.beneMobile = beneMobile;
      }
    }

    public static class AuditTrail implements Serializable {
      private String nature;

      private String value;

      private String timestamp;

      public String getNature() {
        return this.nature;
      }

      public void setNature(String nature) {
        this.nature = nature;
      }

      public String getValue() {
        return this.value;
      }

      public void setValue(String value) {
        this.value = value;
      }

      public String getTimestamp() {
        return this.timestamp;
      }

      public void setTimestamp(String timestamp) {
        this.timestamp = timestamp;
      }
    }
  }

  public static class Essentials implements Serializable {
    private String beneficiaryIFSC;

    private String beneficiaryMobile;

    private String beneficiaryName;

    private String beneficiaryAccount;

    public String getBeneficiaryIFSC() {
      return this.beneficiaryIFSC;
    }

    public void setBeneficiaryIFSC(String beneficiaryIFSC) {
      this.beneficiaryIFSC = beneficiaryIFSC;
    }

    public String getBeneficiaryMobile() {
      return this.beneficiaryMobile;
    }

    public void setBeneficiaryMobile(String beneficiaryMobile) {
      this.beneficiaryMobile = beneficiaryMobile;
    }

    public String getBeneficiaryName() {
      return this.beneficiaryName;
    }

    public void setBeneficiaryName(String beneficiaryName) {
      this.beneficiaryName = beneficiaryName;
    }

    public String getBeneficiaryAccount() {
      return this.beneficiaryAccount;
    }

    public void setBeneficiaryAccount(String beneficiaryAccount) {
      this.beneficiaryAccount = beneficiaryAccount;
    }
  }
}
