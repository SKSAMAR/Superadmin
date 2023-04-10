package com.fintech.scnpay.data.network.responses;

import java.io.Serializable;

public class BeneficiaryHistory implements Serializable {

    public boolean status;
    public Integer response_code;
    public String ackno;
    public String utr;
    public String txn_status;
    public String benename;
    public String remarks;
    public String message;
    public String remitter;
    public String account_number;
    public String bc_share;
    public String txn_amount;
    public String NPCI_response_code;
    public Double balance;
    public String customercharge;
    public String gst;
    public String tds;
    public String netcommission;


    public BeneficiaryHistory(boolean status, Integer response_code, String ackno, String utr, String txn_status, String benename, String remarks, String message, String remitter, String account_number, String bc_share, String txn_amount, String NPCI_response_code, Double balance, String customercharge, String gst, String tds, String netcommission) {
        this.status = status;
        this.response_code = response_code;
        this.ackno = ackno;
        this.utr = utr;
        this.txn_status = txn_status;
        this.benename = benename;
        this.remarks = remarks;
        this.message = message;
        this.remitter = remitter;
        this.account_number = account_number;
        this.bc_share = bc_share;
        this.txn_amount = txn_amount;
        this.NPCI_response_code = NPCI_response_code;
        this.balance = balance;
        this.customercharge = customercharge;
        this.gst = gst;
        this.tds = tds;
        this.netcommission = netcommission;
    }

    public boolean isStatus() {
        return status;
    }

    public void setStatus(boolean status) {
        this.status = status;
    }

    public Integer getResponse_code() {
        return response_code;
    }

    public void setResponse_code(Integer response_code) {
        this.response_code = response_code;
    }

    public String getAckno() {
        return ackno;
    }

    public void setAckno(String ackno) {
        this.ackno = ackno;
    }

    public String getUtr() {
        return utr;
    }

    public void setUtr(String utr) {
        this.utr = utr;
    }

    public String getTxn_status() {
        return txn_status;
    }

    public void setTxn_status(String txn_status) {
        this.txn_status = txn_status;
    }

    public String getBenename() {
        return benename;
    }

    public void setBenename(String benename) {
        this.benename = benename;
    }

    public String getRemarks() {
        return remarks;
    }

    public void setRemarks(String remarks) {
        this.remarks = remarks;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getRemitter() {
        return remitter;
    }

    public void setRemitter(String remitter) {
        this.remitter = remitter;
    }

    public String getAccount_number() {
        return account_number;
    }

    public void setAccount_number(String account_number) {
        this.account_number = account_number;
    }

    public String getBc_share() {
        return bc_share;
    }

    public void setBc_share(String bc_share) {
        this.bc_share = bc_share;
    }

    public String getTxn_amount() {
        return txn_amount;
    }

    public void setTxn_amount(String txn_amount) {
        this.txn_amount = txn_amount;
    }

    public String getNPCI_response_code() {
        return NPCI_response_code;
    }

    public void setNPCI_response_code(String NPCI_response_code) {
        this.NPCI_response_code = NPCI_response_code;
    }

    public Double getBalance() {
        return balance;
    }

    public void setBalance(Double balance) {
        this.balance = balance;
    }

    public String getCustomercharge() {
        return customercharge;
    }

    public void setCustomercharge(String customercharge) {
        this.customercharge = customercharge;
    }

    public String getGst() {
        return gst;
    }

    public void setGst(String gst) {
        this.gst = gst;
    }

    public String getTds() {
        return tds;
    }

    public void setTds(String tds) {
        this.tds = tds;
    }

    public String getNetcommission() {
        return netcommission;
    }

    public void setNetcommission(String netcommission) {
        this.netcommission = netcommission;
    }
}
