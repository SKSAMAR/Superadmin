package com.fintech.paytcash.data.network.responses;

import java.util.List;

public class PayoutListResponse {

    int response_code;
    boolean status;
    List<PayoutList> data;
    String message;

    public int getResponse_code() {
        return response_code;
    }

    public void setResponse_code(int response_code) {
        this.response_code = response_code;
    }

    public boolean isStatus() {
        return status;
    }

    public void setStatus(boolean status) {
        this.status = status;
    }

    public List<PayoutList> getData() {
        return data;
    }

    public void setData(List<PayoutList> data) {
        this.data = data;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
