package com.fintech.prepe.data.model;

public class PersonUserModel {
    public String id;
    public String first_name;
    public String last_name;
    public String mobile;
    public String email;
    public String user_status;
    public String owner_status;
    public String owner_id;


    public PersonUserModel(String id, String first_name, String last_name, String mobile, String email, String user_status, String owner_status, String owner_id) {
        this.id = id;
        this.first_name = first_name;
        this.last_name = last_name;
        this.mobile = mobile;
        this.email = email;
        this.user_status = user_status;
        this.owner_status = owner_status;
        this.owner_id = owner_id;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getFirst_name() {
        return first_name;
    }

    public void setFirst_name(String first_name) {
        this.first_name = first_name;
    }

    public String getLast_name() {
        return last_name;
    }

    public void setLast_name(String last_name) {
        this.last_name = last_name;
    }

    public String getMobile() {
        return mobile;
    }

    public void setMobile(String mobile) {
        this.mobile = mobile;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getUser_status() {
        return user_status;
    }

    public void setUser_status(String user_status) {
        this.user_status = user_status;
    }

    public String getOwner_status() {
        return owner_status;
    }

    public void setOwner_status(String owner_status) {
        this.owner_status = owner_status;
    }

    public String getOwner_id() {
        return owner_id;
    }

    public void setOwner_id(String owner_id) {
        this.owner_id = owner_id;
    }

    @Override
    public String toString() {
        return first_name+" "+last_name;
    }
}
