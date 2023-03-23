package com.fintech.webspidysoftware.data.network.responses;

import java.io.Serializable;

public class BeneficiaryHistoryResponse implements Serializable {
    public String time;
    public String amount;
    public String trans_type;
    public BeneficiaryHistory data;
    public String status;
    public String reference_id;
    public boolean refundable;

    public BeneficiaryHistoryResponse(String time, String amount, String trans_type, BeneficiaryHistory data, String status, String reference_id, boolean refundable) {
        this.time = time;
        this.amount = amount;
        this.trans_type = trans_type;
        this.data = data;
        this.status = status;
        this.reference_id = reference_id;
        this.refundable = refundable;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public String getAmount() {
        return amount;
    }

    public void setAmount(String amount) {
        this.amount = amount;
    }

    public String getTrans_type() {
        return trans_type;
    }

    public void setTrans_type(String trans_type) {
        this.trans_type = trans_type;
    }

    public BeneficiaryHistory getData() {
        return data;
    }

    public void setData(BeneficiaryHistory data) {
        this.data = data;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getReference_id() {
        return reference_id;
    }

    public void setReference_id(String reference_id) {
        this.reference_id = reference_id;
    }

    public boolean isRefundable() {
        return refundable;
    }

    public void setRefundable(boolean refundable) {
        this.refundable = refundable;
    }
}
