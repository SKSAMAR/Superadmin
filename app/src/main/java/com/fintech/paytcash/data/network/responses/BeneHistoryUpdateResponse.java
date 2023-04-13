package com.fintech.paytcash.data.network.responses;

public class BeneHistoryUpdateResponse {
    public boolean status;
    public Integer response_code;
    public String message;

    public BeneHistoryUpdateResponse(boolean status, Integer response_code, String message) {
        this.status = status;
        this.response_code = response_code;
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

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
