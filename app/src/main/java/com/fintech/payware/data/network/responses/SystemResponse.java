package com.fintech.payware.data.network.responses;

public class SystemResponse<T> {
    String message;
    Integer response_code;
    Boolean status;
    T receivableData;

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

    public Boolean getStatus() {
        return status;
    }

    public void setStatus(Boolean status) {
        this.status = status;
    }

    public T getReceivableData() {
        return receivableData;
    }

    public void setReceivableData(T receivableData) {
        this.receivableData = receivableData;
    }

    @Override
    public String toString() {
        return "SystemResponse{" +
                "message='" + message + '\'' +
                ", response_code=" + response_code +
                ", status=" + status +
                ", receivableData=" + receivableData +
                '}';
    }
}
