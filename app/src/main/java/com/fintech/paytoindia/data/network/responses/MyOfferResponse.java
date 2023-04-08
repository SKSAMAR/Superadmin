package com.fintech.paytoindia.data.network.responses;

import java.util.List;

public class MyOfferResponse {
    int code;
    List<ROfferModel> offers;
    String message;

    public MyOfferResponse(int code, List<ROfferModel> offers, String message) {
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

    public List<ROfferModel> getOffers() {
        return offers;
    }

    public void setOffers(List<ROfferModel> offers) {
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
        return "MyOfferResponse{" +
                "code=" + code +
                ", offers=" + offers +
                ", message='" + message + '\'' +
                '}';
    }
}
