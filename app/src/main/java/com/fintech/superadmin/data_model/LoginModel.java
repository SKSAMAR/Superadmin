package com.fintech.superadmin.data_model;

import com.fintech.superadmin.data.db.entities.User;
import com.fintech.superadmin.data.db.entities.UserProfile;

public class LoginModel {
    User user;
    UserProfile userProfile;

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public UserProfile getUserProfile() {
        return userProfile;
    }

    public void setUserProfile(UserProfile userProfile) {
        this.userProfile = userProfile;
    }
}