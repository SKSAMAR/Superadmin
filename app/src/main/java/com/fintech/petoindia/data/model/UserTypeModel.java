package com.fintech.petoindia.data.model;

public class UserTypeModel {
    public String id;
    public String name;
    public String owner;
    public String owner_id;
    public String authority;


    public UserTypeModel(String id, String name, String owner, String owner_id, String authority) {
        this.id = id;
        this.name = name;
        this.owner = owner;
        this.owner_id = owner_id;
        this.authority = authority;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getOwner() {
        return owner;
    }

    public void setOwner(String owner) {
        this.owner = owner;
    }

    public String getOwner_id() {
        return owner_id;
    }

    public void setOwner_id(String owner_id) {
        this.owner_id = owner_id;
    }

    public String getAuthority() {
        return authority;
    }

    public void setAuthority(String authority) {
        this.authority = authority;
    }

    @Override
    public String toString() {
        return name;
    }
}
