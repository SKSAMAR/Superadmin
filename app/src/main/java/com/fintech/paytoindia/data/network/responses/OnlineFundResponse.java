package com.fintech.paytoindia.data.network.responses;

public class OnlineFundResponse {
    public boolean status;
    public String message;
    public int code;

    public OnlineFundResponse(boolean status, String message, int code) {
        this.status = status;
        this.message = message;
        this.code = code;
    }

    public boolean isStatus() {
        return status;
    }

    public void setStatus(boolean status) {
        this.status = status;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }
}
