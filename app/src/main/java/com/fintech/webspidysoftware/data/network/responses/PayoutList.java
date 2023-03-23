package com.fintech.webspidysoftware.data.network.responses;

public class PayoutList {

    String id;
    String beneid;
    String merchantcode;
    String bankname;
    String account;
    String ifsc;
    String name;
    String account_type;
    String verified;
    String status;

    public PayoutList(String id, String beneid, String merchantcode, String bankname, String account, String ifsc, String name, String account_type, String verified, String status) {
        this.id = id;
        this.beneid = beneid;
        this.merchantcode = merchantcode;
        this.bankname = bankname;
        this.account = account;
        this.ifsc = ifsc;
        this.name = name;
        this.account_type = account_type;
        this.verified = verified;
        this.status = status;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getBeneid() {
        return beneid;
    }

    public void setBeneid(String beneid) {
        this.beneid = beneid;
    }

    public String getMerchantcode() {
        return merchantcode;
    }

    public void setMerchantcode(String merchantcode) {
        this.merchantcode = merchantcode;
    }

    public String getBankname() {
        return bankname;
    }

    public void setBankname(String bankname) {
        this.bankname = bankname;
    }

    public String getAccount() {
        return account;
    }

    public void setAccount(String account) {
        this.account = account;
    }

    public String getIfsc() {
        return ifsc;
    }

    public void setIfsc(String ifsc) {
        this.ifsc = ifsc;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getAccount_type() {
        return account_type;
    }

    public void setAccount_type(String account_type) {
        this.account_type = account_type;
    }

    public String getVerified() {
        return verified;
    }

    public void setVerified(String verified) {
        this.verified = verified;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }
}
