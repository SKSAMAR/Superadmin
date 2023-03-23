package com.fintech.payware.data.network.responses;

import java.io.Serializable;

public class AnalyticsResponseModel implements Serializable {

    String amount_in_word;
    String transactionType;
    String cause;
    String id;//taken
    String status;//taken for recharge
    String amount_earlier;//taken
    String amount_left;//taken
    String logo;//taken for recharges
    String onMobile; //taken for recharges
    String txn_id; //taken from reference id
    String operator_name;//taken for recharge
    String op_id;//taken for recharge
    String user_mobile;//taken for all
    String commission_amount;//taken for all
    String date;//taken from timestamp
    String payment_type;//taken for all
    String json_response;//taken for mobile
    String amount;//taken
    String tds;
    String gst;

    public AnalyticsResponseModel(String amount_in_word, String transactionType, String cause, String id, String status, String amount_earlier, String amount_left, String logo, String onMobile, String txn_id, String operator_name, String op_id, String user_mobile, String commission_amount, String date, String payment_type, String json_response, String amount, String tds, String gst) {
        this.amount_in_word = amount_in_word;
        this.transactionType = transactionType;
        this.cause = cause;
        this.id = id;
        this.status = status;
        this.amount_earlier = amount_earlier;
        this.amount_left = amount_left;
        this.logo = logo;
        this.onMobile = onMobile;
        this.txn_id = txn_id;
        this.operator_name = operator_name;
        this.op_id = op_id;
        this.user_mobile = user_mobile;
        this.commission_amount = commission_amount;
        this.date = date;
        this.payment_type = payment_type;
        this.json_response = json_response;
        this.amount = amount;
        this.tds = tds;
        this.gst = gst;
    }

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
}
