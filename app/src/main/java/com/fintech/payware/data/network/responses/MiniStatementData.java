package com.fintech.payware.data.network.responses;

public class MiniStatementData {
    public String date;
    public String txnType;
    public String amount;
    public String narration;

    public MiniStatementData(String date, String txnType, String amount, String narration) {
        this.date = date;
        this.txnType = txnType;
        this.amount = amount;
        this.narration = narration;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getTxnType() {
        return txnType;
    }

    public void setTxnType(String txnType) {
        this.txnType = txnType;
    }

    public String getAmount() {
        return amount;
    }

    public void setAmount(String amount) {
        this.amount = amount;
    }

    public String getNarration() {
        return narration;
    }

    public void setNarration(String narration) {
        this.narration = narration;
    }
}
