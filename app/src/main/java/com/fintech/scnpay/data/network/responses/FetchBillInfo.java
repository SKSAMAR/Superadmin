package com.fintech.scnpay.data.network.responses;

import java.io.Serializable;

public class FetchBillInfo implements Serializable {

    public Integer response_code;
    public boolean status;
    public String amount;
    public String name;
    public String duedate;
    public String billdate;
    public BillFetch bill_fetch;
    public String message;


    public FetchBillInfo(Integer response_code, boolean status, String amount, String name, String duedate, String billdate, BillFetch bill_fetch, String message) {
        this.response_code = response_code;
        this.status = status;
        this.amount = amount;
        this.name = name;
        this.duedate = duedate;
        this.billdate = billdate;
        this.bill_fetch = bill_fetch;
        this.message = message;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public Integer getResponse_code() {
        return response_code;
    }

    public void setResponse_code(Integer response_code) {
        this.response_code = response_code;
    }

    public boolean isStatus() {
        return status;
    }

    public void setStatus(boolean status) {
        this.status = status;
    }

    public String getAmount() {
        return amount;
    }

    public void setAmount(String amount) {
        this.amount = amount;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDuedate() {
        return duedate;
    }

    public void setDuedate(String duedate) {
        this.duedate = duedate;
    }

    public String getBilldate() {
        return billdate;
    }

    public void setBilldate(String billdate) {
        this.billdate = billdate;
    }

    public BillFetch getBill_fetch() {
        return bill_fetch;
    }

    public void setBill_fetch(BillFetch bill_fetch) {
        this.bill_fetch = bill_fetch;
    }

    @Override
    public String toString() {
        return "FetchBillInfo{" +
                "response_code=" + response_code +
                ", status=" + status +
                ", amount='" + amount + '\'' +
                ", name='" + name + '\'' +
                ", duedate='" + duedate + '\'' +
                ", billdate='" + billdate + '\'' +
                ", bill_fetch=" + bill_fetch +
                ", message='" + message + '\'' +
                '}';
    }
}
