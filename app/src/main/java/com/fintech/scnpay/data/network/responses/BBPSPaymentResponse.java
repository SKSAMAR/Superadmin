package com.fintech.scnpay.data.network.responses;

public class BBPSPaymentResponse {
    public boolean status;
    public Integer response_code;
    public String operatorid;
    public Long ackno;
    public String message;

    public BBPSPaymentResponse(boolean status, Integer response_code, String operatorid, Long ackno, String message) {
        this.status = status;
        this.response_code = response_code;
        this.operatorid = operatorid;
        this.ackno = ackno;
        this.message = message;
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

    public String getOperatorid() {
        return operatorid;
    }

    public void setOperatorid(String operatorid) {
        this.operatorid = operatorid;
    }

    public Long getAckno() {
        return ackno;
    }

    public void setAckno(Long ackno) {
        this.ackno = ackno;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
