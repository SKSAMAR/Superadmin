package com.fintech.superadmin.data.network.responses;


import com.fintech.superadmin.data.db.entities.RazorpayData;

public class GatewayResponse {

    public boolean status;
    public String message;
    public RazorpayData razor_pay;

    public GatewayResponse(boolean status, String message, RazorpayData razor_pay) {
        this.status = status;
        this.message = message;
        this.razor_pay = razor_pay;
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

    public RazorpayData getRazor_pay() {
        return razor_pay;
    }

    public void setRazor_pay(RazorpayData razor_pay) {
        this.razor_pay = razor_pay;
    }
}
