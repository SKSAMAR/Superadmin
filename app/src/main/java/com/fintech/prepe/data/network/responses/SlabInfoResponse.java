package com.fintech.prepe.data.network.responses;

import java.io.Serializable;

public class SlabInfoResponse implements Serializable {
    String id;
    String comm_pack_id;
    String min_amount;
    String max_amount;
    String tds;
    String gst;
    String type;
    String amount_type;
    String amount;
    String status;
    String charge;
    String ms_com;
    String ds_com;

    public SlabInfoResponse(String id, String comm_pack_id, String min_amount, String max_amount, String tds, String gst, String type, String amount_type, String amount, String status, String charge, String ms_com, String ds_com) {
        this.id = id;
        this.comm_pack_id = comm_pack_id;
        this.min_amount = min_amount;
        this.max_amount = max_amount;
        this.tds = tds;
        this.gst = gst;
        this.type = type;
        this.amount_type = amount_type;
        this.amount = amount;
        this.status = status;
        this.charge = charge;
        this.ms_com = ms_com;
        this.ds_com = ds_com;
    }


    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getComm_pack_id() {
        return comm_pack_id;
    }

    public void setComm_pack_id(String comm_pack_id) {
        this.comm_pack_id = comm_pack_id;
    }

    public String getMin_amount() {
        return min_amount;
    }

    public void setMin_amount(String min_amount) {
        this.min_amount = min_amount;
    }

    public String getMax_amount() {
        return max_amount;
    }

    public void setMax_amount(String max_amount) {
        this.max_amount = max_amount;
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

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getAmount_type() {
        return amount_type;
    }

    public void setAmount_type(String amount_type) {
        this.amount_type = amount_type;
    }

    public String getAmount() {
        return amount;
    }

    public void setAmount(String amount) {
        this.amount = amount;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getCharge() {
        return charge;
    }

    public void setCharge(String charge) {
        this.charge = charge;
    }

    public String getMs_com() {
        return ms_com;
    }

    public void setMs_com(String ms_com) {
        this.ms_com = ms_com;
    }

    public String getDs_com() {
        return ds_com;
    }

    public void setDs_com(String ds_com) {
        this.ds_com = ds_com;
    }
}
