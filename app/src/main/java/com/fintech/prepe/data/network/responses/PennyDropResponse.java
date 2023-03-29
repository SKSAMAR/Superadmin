package com.fintech.prepe.data.network.responses;

public class PennyDropResponse {
    public boolean status;
    public Integer response_code;
    public String message;
    public String utr;
    public String benename;
    public Integer txnstatus;

    public PennyDropResponse(boolean status, Integer response_code, String message, String utr, String benename, Integer txnstatus) {
        this.status = status;
        this.response_code = response_code;
        this.message = message;
        this.utr = utr;
        this.benename = benename;
        this.txnstatus = txnstatus;
    }

    public boolean isStatus() {
        return status;
    }

    public void setStatus(boolean status) {
        this.status = status;
    }

    public Integer getResponse_code() {
        return response_code;
    }

    public void setResponse_code(Integer response_code) {
        this.response_code = response_code;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getUtr() {
        return utr;
    }

    public void setUtr(String utr) {
        this.utr = utr;
    }

    public String getBenename() {
        return benename;
    }

    public void setBenename(String benename) {
        this.benename = benename;
    }

    public Integer getTxnstatus() {
        return txnstatus;
    }

    public void setTxnstatus(Integer txnstatus) {
        this.txnstatus = txnstatus;
    }
}
