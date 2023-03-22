package com.fintech.petoindia.data.model;

public class GraphModel {
    String date;
    Integer amount;

    public GraphModel(String date, Integer amount) {
        this.date = date;
        this.amount = amount;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public Integer getAmount() {
        return amount;
    }

    public void setAmount(Integer amount) {
        this.amount = amount;
    }
}
