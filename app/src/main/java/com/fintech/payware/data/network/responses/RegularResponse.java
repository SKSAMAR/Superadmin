package com.fintech.payware.data.network.responses;

import java.io.Serializable;

public class RegularResponse implements Serializable {

    public boolean status;
    public Integer response_code;
    public Integer responsecode;
    public String message;
    public Integer txnstatus;

    public RegularResponse(boolean status, Integer response_code, Integer responsecode, String message, Integer txnstatus) {
        this.status = status;
        this.response_code = response_code;
        this.responsecode = responsecode;
        this.message = message;
        this.txnstatus = txnstatus;
    }

    public Integer getResponsecode() {
        return responsecode;
    }

    public void setResponsecode(Integer responsecode) {
        this.responsecode = responsecode;
    }

    public Integer getTxnstatus() {
        return txnstatus;
    }

    public void setTxnstatus(Integer txnstatus) {
        this.txnstatus = txnstatus;
    }

    public boolean isStatus() {
        return status;
    }

    public void setStatus(boolean status) {
        this.status = status;
    }

    public Integer getResponse_code() {
        if(response_code == null){
            return responsecode;
        }

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
}
