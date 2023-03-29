package com.fintech.prepe.data.network.responses;

import java.io.Serializable;

public class AddBeneficiaryResponse implements Serializable {
    public boolean status;
    public Integer response_code;
    public BeneficiaryResponseData data;
    public String message;

    public AddBeneficiaryResponse(boolean status, Integer response_code, BeneficiaryResponseData data, String message) {
        this.status = status;
        this.response_code = response_code;
        this.data = data;
        this.message = message;
    }

    public boolean isStatus() {
        return status;
    }

    public void setStatus(boolean status) {
        this.status = status;
    }

    public Integer getResponse_code() {
        return response_code;
    }

    public void setResponse_code(Integer response_code) {
        this.response_code = response_code;
    }

    public BeneficiaryResponseData getData() {
        return data;
    }

    public void setData(BeneficiaryResponseData data) {
        this.data = data;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
