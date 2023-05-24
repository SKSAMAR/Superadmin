package com.fintech.superadmin.data.network.responses;

import java.io.Serializable;

public class AnalyticsResponseModel implements Serializable {

    public String amount_in_word;
    public String transactionType;
    public String cause;
    public String id;//taken
    public String status;//taken for recharge
    public String amount_earlier;//taken
    public String amount_left;//taken
    public String logo;//taken for recharges
    public String onMobile; //taken for recharges
    public String txn_id; //taken from reference id
    public String operator_name;//taken for recharge
    public String op_id;//taken for recharge
    public String user_mobile;//taken for all
    public String commission_amount;//taken for all
    public String date;//taken from timestamp
    public String payment_type;//taken for all
    public String json_response;//taken for mobile
    public String amount;//taken
    public String tds;
    public String gst;
    public String bankName;

    public String getTransactionType() {
        return transactionType;
    }

    public void setTransactionType(String transactionType) {
        this.transactionType = transactionType;
    }

    public String getAmount_in_word() {
        return amount_in_word;
    }

    public void setAmount_in_word(String amount_in_word) {
        this.amount_in_word = amount_in_word;
    }

    public String getCause() {
        return cause;
    }

    public void setCause(String cause) {
        this.cause = cause;
    }

    public String getTds() {
        return tds;
    }

    public void setTds(String tds) {
        this.tds = tds;
    }

    public String getGst() {
        return gst;
    }

    public void setGst(String gst) {
        this.gst = gst;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getAmount_earlier() {
        return amount_earlier;
    }

    public void setAmount_earlier(String amount_earlier) {
        this.amount_earlier = amount_earlier;
    }

    public String getAmount_left() {
        return amount_left;
    }

    public void setAmount_left(String amount_left) {
        this.amount_left = amount_left;
    }

    public String getLogo() {
        return logo;
    }

    public void setLogo(String logo) {
        this.logo = logo;
    }

    public String getOnMobile() {
        return onMobile;
    }

    public void setOnMobile(String onMobile) {
        this.onMobile = onMobile;
    }

    public String getTxn_id() {
        return txn_id;
    }

    public void setTxn_id(String txn_id) {
        this.txn_id = txn_id;
    }

    public String getOperator_name() {
        return operator_name;
    }

    public void setOperator_name(String operator_name) {
        this.operator_name = operator_name;
    }

    public String getOp_id() {
        return op_id;
    }

    public void setOp_id(String op_id) {
        this.op_id = op_id;
    }

    public String getUser_mobile() {
        return user_mobile;
    }

    public void setUser_mobile(String user_mobile) {
        this.user_mobile = user_mobile;
    }

    public String getCommission_amount() {
        return commission_amount;
    }

    public void setCommission_amount(String commission_amount) {
        this.commission_amount = commission_amount;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getPayment_type() {
        return payment_type;
    }

    public void setPayment_type(String payment_type) {
        this.payment_type = payment_type;
    }

    public String getJson_response() {
        return json_response;
    }

    public void setJson_response(String json_response) {
        this.json_response = json_response;
    }

    public String getAmount() {
        return amount;
    }

    public void setAmount(String amount) {
        this.amount = amount;
    }

    public String getBankName() {
        return bankName;
    }

    public void setBankName(String bankName) {
        this.bankName = bankName;
    }
}
