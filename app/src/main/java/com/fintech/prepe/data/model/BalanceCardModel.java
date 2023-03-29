package com.fintech.prepe.data.model;

public class BalanceCardModel {
    public String name;
    public String rNumber;
    public String balance;

    public BalanceCardModel(String name, String rNumber, String balance) {
        this.name = name;
        this.rNumber = rNumber;
        this.balance = balance;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getrNumber() {
        return rNumber;
    }

    public void setrNumber(String rNumber) {
        this.rNumber = rNumber;
    }

    public String getBalance() {
        return balance;
    }

    public void setBalance(String balance) {
        this.balance = balance;
    }
}
