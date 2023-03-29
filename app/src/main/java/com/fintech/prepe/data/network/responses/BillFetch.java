package com.fintech.prepe.data.network.responses;

import java.io.Serializable;

public class BillFetch implements Serializable {

    public String billAmount;
    public String billnetamount;
    public String billdate;
    public String dueDate;
    public boolean acceptPayment;
    public boolean acceptPartPay;
    public String cellNumber;
    public String userName;


    public BillFetch(String billAmount, String billnetamount, String billdate, String dueDate, boolean acceptPayment, boolean acceptPartPay, String cellNumber, String userName) {
        this.billAmount = billAmount;
        this.billnetamount = billnetamount;
        this.billdate = billdate;
        this.dueDate = dueDate;
        this.acceptPayment = acceptPayment;
        this.acceptPartPay = acceptPartPay;
        this.cellNumber = cellNumber;
        this.userName = userName;
    }

    public String getCellNumber() {
        return cellNumber;
    }

    public void setCellNumber(String cellNumber) {
        this.cellNumber = cellNumber;
    }

    public String getBillAmount() {
        return billAmount;
    }

    public void setBillAmount(String billAmount) {
        this.billAmount = billAmount;
    }

    public String getBillnetamount() {
        if(billnetamount==null || billnetamount.trim().isEmpty()){
            return billAmount;
        }
        return billnetamount;
    }

    public void setBillnetamount(String billnetamount) {
        this.billnetamount = billnetamount;
    }

    public String getBilldate() {
        return billdate;
    }

    public void setBilldate(String billdate) {
        this.billdate = billdate;
    }

    public String getDueDate() {
        return dueDate;
    }

    public void setDueDate(String dueDate) {
        this.dueDate = dueDate;
    }

    public boolean isAcceptPayment() {
        return acceptPayment;
    }

    public void setAcceptPayment(boolean acceptPayment) {
        this.acceptPayment = acceptPayment;
    }

    public boolean isAcceptPartPay() {
        return acceptPartPay;
    }

    public void setAcceptPartPay(boolean acceptPartPay) {
        this.acceptPartPay = acceptPartPay;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    @Override
    public String toString() {
        return "BillFetch{" +
                "billAmount='" + billAmount + '\'' +
                ", billnetamount='" + billnetamount + '\'' +
                ", billdate='" + billdate + '\'' +
                ", dueDate='" + dueDate + '\'' +
                ", acceptPayment=" + acceptPayment +
                ", acceptPartPay=" + acceptPartPay +
                ", cellNumber='" + cellNumber + '\'' +
                ", userName='" + userName + '\'' +
                '}';
    }
}
