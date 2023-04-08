package com.fintech.paytoindia.data.model;

public class HistoryModel {

    public String history_logo;
    public String paid_to_receive_from;
    public String to_person;
    public String date;
    public String amount;
    public String debited_from_credit_to;
    public String trans_type;
    public String id;


    public HistoryModel(String history_logo, String paid_to_receive_from, String to_person, String date, String amount, String debited_from_credit_to, String trans_type, String id) {
        this.history_logo = history_logo;
        this.paid_to_receive_from = paid_to_receive_from;
        this.to_person = to_person;
        this.date = date;
        this.amount = amount;
        this.debited_from_credit_to = debited_from_credit_to;
        this.trans_type = trans_type;
        this.id = id;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getHistory_logo() {
        return history_logo;
    }

    public void setHistory_logo(String history_logo) {
        this.history_logo = history_logo;
    }

    public String getPaid_to_receive_from() {
        return paid_to_receive_from;
    }

    public void setPaid_to_receive_from(String paid_to_receive_from) {
        this.paid_to_receive_from = paid_to_receive_from;
    }

    public String getTo_person() {
        return to_person;
    }

    public void setTo_person(String to_person) {
        this.to_person = to_person;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getAmount() {
        return amount;
    }

    public void setAmount(String amount) {
        this.amount = amount;
    }

    public String getDebited_from_credit_to() {
        return debited_from_credit_to;
    }

    public void setDebited_from_credit_to(String debited_from_credit_to) {
        this.debited_from_credit_to = debited_from_credit_to;
    }

    public String getTrans_type() {
        return trans_type;
    }

    public void setTrans_type(String trans_type) {
        this.trans_type = trans_type;
    }
}
