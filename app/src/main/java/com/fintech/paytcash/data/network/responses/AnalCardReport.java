package com.fintech.paytcash.data.network.responses;

public class AnalCardReport {
    int response_code;
    boolean status;
    String message;
    String current_transaction;
    String last_transaction;
    String current_business;
    String last_business;
    String current_earning;
    String last_earning;


    public AnalCardReport(int response_code, boolean status, String message, String current_transaction, String last_transaction, String current_business, String last_business, String current_earning, String last_earning) {
        this.response_code = response_code;
        this.status = status;
        this.message = message;
        this.current_transaction = current_transaction;
        this.last_transaction = last_transaction;
        this.current_business = current_business;
        this.last_business = last_business;
        this.current_earning = current_earning;
        this.last_earning = last_earning;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

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

    public String getCurrent_transaction() {
        return current_transaction;
    }

    public void setCurrent_transaction(String current_transaction) {
        this.current_transaction = current_transaction;
    }

    public String getLast_transaction() {
        return last_transaction;
    }

    public void setLast_transaction(String last_transaction) {
        this.last_transaction = last_transaction;
    }

    public String getCurrent_business() {
        return current_business;
    }

    public void setCurrent_business(String current_business) {
        this.current_business = current_business;
    }

    public String getLast_business() {
        return last_business;
    }

    public void setLast_business(String last_business) {
        this.last_business = last_business;
    }

    public String getCurrent_earning() {
        return current_earning;
    }

    public void setCurrent_earning(String current_earning) {
        this.current_earning = current_earning;
    }

    public String getLast_earning() {
        return last_earning;
    }

    public void setLast_earning(String last_earning) {
        this.last_earning = last_earning;
    }
}
