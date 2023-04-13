package com.fintech.paytcash.data.model;

import java.io.Serializable;

public class BankModel implements Serializable {

    public String bankid;
    public String bankname;
    public String ifsccode;
    public String pennny;
    public String column;
    public String logo;


    public BankModel(String bankid, String bankname, String ifsccode, String pennny, String column, String logo) {
        this.bankid = bankid;
        this.bankname = bankname;
        this.ifsccode = ifsccode;
        this.pennny = pennny;
        this.column = column;
        this.logo = logo;
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

    public String getIfsccode() {
        return ifsccode;
    }

    public void setIfsccode(String ifsccode) {
        this.ifsccode = ifsccode;
    }

    public String getPennny() {
        return pennny;
    }

    public void setPennny(String pennny) {
        this.pennny = pennny;
    }

    public String getColumn() {
        return column;
    }

    public void setColumn(String column) {
        this.column = column;
    }

    public String getLogo() {
        return logo;
    }

    public void setLogo(String logo) {
        this.logo = logo;
    }


    @Override
    public String toString() {
        return bankname;
    }
}
