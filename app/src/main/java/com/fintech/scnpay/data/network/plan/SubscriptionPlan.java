package com.fintech.scnpay.data.network.plan;

public class SubscriptionPlan {
    public String id;
    public String name;
    public String price;
    public String description;
    public String validity;
    public String user;
    public String status;
    public String date;

    public SubscriptionPlan(String id, String name, String price, String description, String validity, String user, String status, String date) {
        this.id = id;
        this.name = name;
        this.price = price;
        this.description = description;
        this.validity = validity;
        this.user = user;
        this.status = status;
        this.date = date;
    }
}
