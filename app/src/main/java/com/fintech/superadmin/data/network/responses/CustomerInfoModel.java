package com.fintech.superadmin.data.network.responses;

import java.io.Serializable;

public class CustomerInfoModel implements Serializable {
    String MonthlyRecharge;
    String Balance;
    String customerName;
    String status;
    String NextRechargeDate;
    String lastrechargeamount;
    String lastrechargedate;
    String planname;

    public CustomerInfoModel(String monthlyRecharge, String balance, String customerName, String status, String nextRechargeDate, String lastrechargeamount, String lastrechargedate, String planname) {
        MonthlyRecharge = monthlyRecharge;
        Balance = balance;
        this.customerName = customerName;
        this.status = status;
        NextRechargeDate = nextRechargeDate;
        this.lastrechargeamount = lastrechargeamount;
        this.lastrechargedate = lastrechargedate;
        this.planname = planname;
    }

    public String getMonthlyRecharge() {
        return MonthlyRecharge;
    }

    public void setMonthlyRecharge(String monthlyRecharge) {
        MonthlyRecharge = monthlyRecharge;
    }

    public String getBalance() {
        return Balance;
    }

    public void setBalance(String balance) {
        Balance = balance;
    }

    public String getCustomerName() {
        return customerName;
    }

    public void setCustomerName(String customerName) {
        this.customerName = customerName;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getNextRechargeDate() {
        return NextRechargeDate;
    }

    public void setNextRechargeDate(String nextRechargeDate) {
        NextRechargeDate = nextRechargeDate;
    }

    public String getLastrechargeamount() {
        return lastrechargeamount;
    }

    public void setLastrechargeamount(String lastrechargeamount) {
        this.lastrechargeamount = lastrechargeamount;
    }

    public String getLastrechargedate() {
        return lastrechargedate;
    }

    public void setLastrechargedate(String lastrechargedate) {
        this.lastrechargedate = lastrechargedate;
    }

    public String getPlanname() {
        return planname;
    }

    public void setPlanname(String planname) {
        this.planname = planname;
    }

    @Override
    public String toString() {
        return "CustomerInfoModel{" +
                "MonthlyRecharge='" + MonthlyRecharge + '\'' +
                ", Balance='" + Balance + '\'' +
                ", customerName='" + customerName + '\'' +
                ", status='" + status + '\'' +
                ", NextRechargeDate='" + NextRechargeDate + '\'' +
                ", lastrechargeamount='" + lastrechargeamount + '\'' +
                ", lastrechargedate='" + lastrechargedate + '\'' +
                ", planname='" + planname + '\'' +
                '}';
    }
}
