package com.fintech.petoindia.data.network.responses;

import com.fintech.petoindia.data.model.UserTypeModel;

import java.util.List;

public class UserTypeResponse {

    public boolean status;
    public String message;
    public List<UserTypeModel> data;

    public UserTypeResponse(boolean status, String message, List<UserTypeModel> data) {
        this.status = status;
        this.message = message;
        this.data = data;
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

    public List<UserTypeModel> getData() {
        return data;
    }

    public void setData(List<UserTypeModel> data) {
        this.data = data;
    }
}
