package com.fintech.kkpayments.data.network.responses;

import java.util.List;

public class CustomerInfoResponse {
    int code;
    List<CustomerInfoModel> offers;
    String message;

    public CustomerInfoResponse(int code, List<CustomerInfoModel> offers, String message) {
        this.code = code;
        this.offers = offers;
        this.message = message;
    }

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public List<CustomerInfoModel> getoffers() {
        return offers;
    }

    public void setoffers(List<CustomerInfoModel> offers) {
        this.offers = offers;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    @Override
    public String toString() {
        return "CustomerInfoResponse{" +
                "code=" + code +
                ", offers=" + offers +
                ", message='" + message + '\'' +
                '}';
    }
}
