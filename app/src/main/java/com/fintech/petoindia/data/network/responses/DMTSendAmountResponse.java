package com.fintech.petoindia.data.network.responses;

public class DMTSendAmountResponse {
    public boolean status;
    public Integer response_code;
    public String message;
    public String remarks;

    public DMTSendAmountResponse(boolean status, Integer response_code, String message, String remarks) {
        this.status = status;
        this.response_code = response_code;
        this.message = message;
        this.remarks = remarks;
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

    public String getRemarks() {
        return remarks;
    }

    public void setRemarks(String remarks) {
        this.remarks = remarks;
    }
}
