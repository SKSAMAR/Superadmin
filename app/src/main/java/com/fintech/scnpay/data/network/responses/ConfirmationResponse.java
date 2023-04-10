package com.fintech.scnpay.data.network.responses;

public class ConfirmationResponse {
    public boolean status;
    public String message;
    public int code;

    public ConfirmationResponse(boolean status, String message, int code) {
        this.status = status;
        this.message = message;
        this.code = code;
    }

    public boolean isstatus() {
        return status;
    }

    public void setstatus(boolean status) {
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
