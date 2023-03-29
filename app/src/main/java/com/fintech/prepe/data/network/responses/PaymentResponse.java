package com.fintech.prepe.data.network.responses;

public class PaymentResponse {
    public Integer responsecode;
    public String message;
    public String response;

    public PaymentResponse(Integer responsecode, String message, String response) {
        this.responsecode = responsecode;
        this.message = message;
        this.response = response;
    }

    public Integer getResponsecode() {
        return responsecode;
    }

    public void setResponsecode(Integer responsecode) {
        this.responsecode = responsecode;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getResponse() {
        return response;
    }

    public void setResponse(String response) {
        this.response = response;
    }
}
