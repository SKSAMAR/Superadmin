package com.fintech.paytcash.data.network.responses;

import com.fintech.paytcash.data.db.entities.User;

public class AuthResponse {
    boolean status;
    public String message;
    public User user;


    public AuthResponse(boolean status, String message, User user) {
        this.status = status;
        this.message = message;
        this.user = user;
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

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    @Override
    public String toString() {
        return "AuthResponse{" +
                "status=" + status +
                ", message='" + message + '\'' +
                ", user=" + user +
                '}';
    }
}
