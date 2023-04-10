package com.fintech.scnpay.data.model;

public class AEPSBanksModel {

    public String id;

    public String bankname;

    public String iinno;

    public String activeflag;


    public AEPSBanksModel(String id, String bankname, String iinno, String activeflag) {
        this.id = id;
        this.bankname = bankname;
        this.iinno = iinno;
        this.activeflag = activeflag;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getBankname() {
        return bankname;
    }

    public void setBankname(String bankname) {
        this.bankname = bankname;
    }

    public String getIinno() {
        return iinno;
    }

    public void setIinno(String iinno) {
        this.iinno = iinno;
    }

    public String getActiveflag() {
        return activeflag;
    }

    public void setActiveflag(String activeflag) {
        this.activeflag = activeflag;
    }
}
