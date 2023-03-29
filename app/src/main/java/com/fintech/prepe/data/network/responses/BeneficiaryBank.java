package com.fintech.prepe.data.network.responses;

import java.io.Serializable;

public class BeneficiaryBank implements Serializable {
    public String bene_id;
    public String bankid;
    public String bankname;
    public String name;
    public String accno;
    public String ifsc;
    public String verified;
    public String banktype;
    public String paytm;


    public BeneficiaryBank(String bene_id, String bankid, String bankname, String name, String accno, String ifsc, String verified, String banktype, String paytm) {
        this.bene_id = bene_id;
        this.bankid = bankid;
        this.bankname = bankname;
        this.name = name;
        this.accno = accno;
        this.ifsc = ifsc;
        this.verified = verified;
        this.banktype = banktype;
        this.paytm = paytm;
    }

    public String getBene_id() {
        return bene_id;
    }

    public void setBene_id(String bene_id) {
        this.bene_id = bene_id;
    }

    public String getBankid() {
        return bankid;
    }

    public void setBankid(String bankid) {
        this.bankid = bankid;
    }

    public String getBankname() {
        return bankname;
    }

    public void setBankname(String bankname) {
        this.bankname = bankname;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getAccno() {
        return accno;
    }

    public void setAccno(String accno) {
        this.accno = accno;
    }

    public String getIfsc() {
        return ifsc;
    }

    public void setIfsc(String ifsc) {
        this.ifsc = ifsc;
    }

    public String getVerified() {
        return verified;
    }

    public void setVerified(String verified) {
        this.verified = verified;
    }

    public String getBanktype() {
        return banktype;
    }

    public void setBanktype(String banktype) {
        this.banktype = banktype;
    }

    public String getPaytm() {
        return paytm;
    }

    public void setPaytm(String paytm) {
        this.paytm = paytm;
    }
}
