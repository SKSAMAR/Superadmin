package com.fintech.payware.data.network.responses;

import java.util.List;

public class MiniStatementResponse {
    public boolean status = false;
    public Long ackno;
    public String datetime;
    public String balanceamount;
    public String bankrrn;
    public String bankiin;
    public String message;
    public String errorcode;
    public List<MiniStatementData> ministatement;

    public MiniStatementResponse(boolean status, Long ackno, String datetime, String balanceamount, String bankrrn, String bankiin, String message, String errorcode, List<MiniStatementData> ministatement) {
        this.status = status;
        this.ackno = ackno;
        this.datetime = datetime;
        this.balanceamount = balanceamount;
        this.bankrrn = bankrrn;
        this.bankiin = bankiin;
        this.message = message;
        this.errorcode = errorcode;
        this.ministatement = ministatement;
    }

    public boolean isStatus() {
        return status;
    }

    public void setStatus(boolean status) {
        this.status = status;
    }

    public String getBalanceamount() {
        return balanceamount;
    }

    public void setBalanceamount(String balanceamount) {
        this.balanceamount = balanceamount;
    }

    public Long getAckno() {
        return ackno;
    }

    public void setAckno(Long ackno) {
        this.ackno = ackno;
    }

    public String getDatetime() {
        return datetime;
    }

    public void setDatetime(String datetime) {
        this.datetime = datetime;
    }

    public String getBankrrn() {
        return bankrrn;
    }

    public void setBankrrn(String bankrrn) {
        this.bankrrn = bankrrn;
    }

    public String getBankiin() {
        return bankiin;
    }

    public void setBankiin(String bankiin) {
        this.bankiin = bankiin;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getErrorcode() {
        return errorcode;
    }

    public void setErrorcode(String errorcode) {
        this.errorcode = errorcode;
    }

    public List<MiniStatementData> getMinistatement() {
        return ministatement;
    }

    public void setMinistatement(List<MiniStatementData> ministatement) {
        this.ministatement = ministatement;
    }
}
