package com.fintech.paytcash.data.network.responses;

public class PayoutAddResponse {
    boolean status;
    int statuscode;
    String message;

    public PayoutAddResponse(boolean status, int statuscode, String message) {
        this.status = status;
        this.statuscode = statuscode;
        this.message = message;
    }

    public boolean isStatus() {
        return status;
    }

    public void setStatus(boolean status) {
        this.status = status;
    }

    public int getStatuscode() {
        return statuscode;
    }

    public void setStatuscode(int statuscode) {
        this.statuscode = statuscode;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
