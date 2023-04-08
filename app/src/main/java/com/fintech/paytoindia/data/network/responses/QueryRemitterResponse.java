package com.fintech.paytoindia.data.network.responses;

public class QueryRemitterResponse {
    public boolean status;
    public Integer response_code;
    public String stateresp;
    public String message;


    public QueryRemitterResponse(boolean status, Integer response_code, String stateresp, String message) {
        this.status = status;
        this.response_code = response_code;
        this.stateresp = stateresp;
        this.message = message;
    }

    public String getStateresp() {
        return stateresp;
    }

    public void setStateresp(String stateresp) {
        this.stateresp = stateresp;
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


    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
