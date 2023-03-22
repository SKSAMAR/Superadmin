package com.fintech.petoindia.data.network.responses;

import com.fintech.petoindia.data.db.entities.UserProfile;

public class DetailResponse {
    boolean status;
    public String message;
    public UserProfile data;

    public DetailResponse(boolean status, String message, UserProfile data) {
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

    public UserProfile getData() {
        return data;
    }

    public void setData(UserProfile data) {
        this.data = data;
    }

    @Override
    public String toString() {
        return "DetailResponse{" +
                "status=" + status +
                ", message='" + message + '\'' +
                ", data=" + data +
                '}';
    }
}
