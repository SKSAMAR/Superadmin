package com.fintech.webspidysoftware.data.network.responses;

import java.util.List;

public class BeneficiaryResponse {
    public boolean status;
    public Integer response_code;
    public List<BeneficiaryBank> data;
    public String message;

    public BeneficiaryResponse(boolean status, Integer response_code, List<BeneficiaryBank> data, String message) {
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

    public List<BeneficiaryBank> getData() {
        return data;
    }

    public void setData(List<BeneficiaryBank> data) {
        this.data = data;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
