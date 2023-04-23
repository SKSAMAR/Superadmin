package com.fintech.superadmin.data.network.responses;

public class BankItResponse {
    boolean status;
    int response_code;
    String message;
    BankItData data;
    BankItCred cred;

    public BankItResponse(boolean status, int response_code, String message, BankItData data, BankItCred cred) {
        this.status = status;
        this.response_code = response_code;
        this.message = message;
        this.data = data;
        this.cred = cred;
    }

    public boolean isStatus() {
        return status;
    }

    public void setStatus(boolean status) {
        this.status = status;
    }

    public int getResponse_code() {
        return response_code;
    }

    public void setResponse_code(int response_code) {
        this.response_code = response_code;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public BankItData getData() {
        return data;
    }

    public void setData(BankItData data) {
        this.data = data;
    }

    public BankItCred getCred() {
        return cred;
    }

    public void setCred(BankItCred cred) {
        this.cred = cred;
    }
}
