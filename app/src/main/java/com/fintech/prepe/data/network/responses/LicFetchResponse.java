package com.fintech.prepe.data.network.responses;

import java.io.Serializable;

public class LicFetchResponse implements Serializable {
    int response_code;
    boolean status;
    String amount;
    String name;
    String duedate;
    BillData bill_fetch;
    String ad2;
    String ad3;
    String message;

    public LicFetchResponse(){

    }

    public LicFetchResponse(int response_code, boolean status, String amount, String name, String duedate, BillData bill_fetch, String ad2, String ad3, String message) {
        this.response_code = response_code;
        this.status = status;
        this.amount = amount;
        this.name = name;
        this.duedate = duedate;
        this.bill_fetch = bill_fetch;
        this.ad2 = ad2;
        this.ad3 = ad3;
        this.message = message;
    }

    public int getResponse_code() {
        return response_code;
    }

    public void setResponse_code(int response_code) {
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

    public BillData getBill_fetch() {
        return bill_fetch;
    }

    public void setBill_fetch(BillData bill_fetch) {
        this.bill_fetch = bill_fetch;
    }

    public String getAd2() {
        return ad2;
    }

    public void setAd2(String ad2) {
        this.ad2 = ad2;
    }

    public String getAd3() {
        return ad3;
    }

    public void setAd3(String ad3) {
        this.ad3 = ad3;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }



    public static class BillData implements Serializable{
       public String billAmount;
       public String billnetamount;
       public String dueDate;
       public int maxBillAmount;
       public boolean acceptPayment;
       public boolean acceptPartPay;
       public String cellNumber;
       public String userName;



        public BillData(String billAmount, String billnetamount, String dueDate, int maxBillAmount, boolean acceptPayment, boolean acceptPartPay, String cellNumber, String userName) {
            this.billAmount = billAmount;
            this.billnetamount = billnetamount;
            this.dueDate = dueDate;
            this.maxBillAmount = maxBillAmount;
            this.acceptPayment = acceptPayment;
            this.acceptPartPay = acceptPartPay;
            this.cellNumber = cellNumber;
            this.userName = userName;
        }

    }

    @Override
    public String toString() {
        return "LicFetchResponse{" +
                "response_code=" + response_code +
                ", status=" + status +
                ", amount='" + amount + '\'' +
                ", name='" + name + '\'' +
                ", duedate='" + duedate + '\'' +
                ", bill_fetch=" + bill_fetch +
                ", ad2='" + ad2 + '\'' +
                ", ad3='" + ad3 + '\'' +
                ", message='" + message + '\'' +
                '}';
    }
}
