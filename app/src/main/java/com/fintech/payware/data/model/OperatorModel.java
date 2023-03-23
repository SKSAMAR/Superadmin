package com.fintech.payware.data.model;

import java.io.Serializable;

public class OperatorModel implements Serializable {
    private String id,operatorcode, name, service , com , logo;


    public OperatorModel(String id, String operatorcode, String name, String service, String com, String logo) {
        this.id = id;
        this.operatorcode = operatorcode;
        this.name = name;
        this.service = service;
        this.com = com;
        this.logo = logo;
    }


    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getOperatorcode() {
        return operatorcode;
    }

    public void setOperatorcode(String operatorcode) {
        this.operatorcode = operatorcode;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getService() {
        return service;
    }

    public void setService(String service) {
        this.service = service;
    }

    public String getCom() {
        return com;
    }

    public void setCom(String com) {
        this.com = com;
    }

    public String getLogo() {
        return logo;
    }

    public void setLogo(String logo) {
        this.logo = logo;
    }

    @Override
    public String toString() {
        return "OperatorModel{" +
                "id='" + id + '\'' +
                ", operatorcode='" + operatorcode + '\'' +
                ", name='" + name + '\'' +
                ", service='" + service + '\'' +
                ", com='" + com + '\'' +
                ", logo='" + logo + '\'' +
                '}';
    }
}
