package com.fintech.paytoindia.data.network.responses;

import java.io.Serializable;

public class DetailedHistoryResponse implements Serializable {

    boolean status;
    String message;
    Integer response_code;
    String type_response;
    String trans_type;
    String data_response;
    String data_check_response;
    String additional_info;

    public DetailedHistoryResponse(boolean status, String message, Integer response_code, String type_response, String trans_type, String data_response, String data_check_response, String additional_info) {
        this.status = status;
        this.message = message;
        this.response_code = response_code;
        this.type_response = type_response;
        this.trans_type = trans_type;
        this.data_response = data_response;
        this.data_check_response = data_check_response;
        this.additional_info = additional_info;
    }

    public String getAdditional_info() {
        return additional_info;
    }

    public void setAdditional_info(String additional_info) {
        this.additional_info = additional_info;
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

    public Integer getResponse_code() {
        return response_code;
    }

    public void setResponse_code(Integer response_code) {
        this.response_code = response_code;
    }

    public String getType_response() {
        return type_response;
    }

    public void setType_response(String type_response) {
        this.type_response = type_response;
    }

    public String getTrans_type() {
        return trans_type;
    }

    public void setTrans_type(String trans_type) {
        this.trans_type = trans_type;
    }

    public String getData_response() {
        return data_response;
    }

    public void setData_response(String data_response) {
        this.data_response = data_response;
    }

    public String getData_check_response() {
        return data_check_response;
    }

    public void setData_check_response(String data_check_response) {
        this.data_check_response = data_check_response;
    }

    @Override
    public String toString() {
        return "DetailedHistoryResponse{" +
                "status=" + status +
                ", message='" + message + '\'' +
                ", response_code=" + response_code +
                ", type_response='" + type_response + '\'' +
                ", trans_type='" + trans_type + '\'' +
                ", data_response='" + data_response + '\'' +
                ", data_check_response='" + data_check_response + '\'' +
                '}';
    }
}
