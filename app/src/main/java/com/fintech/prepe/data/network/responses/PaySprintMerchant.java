package com.fintech.prepe.data.network.responses;

import java.io.Serializable;

public class PaySprintMerchant implements Serializable {
    public boolean result;
    public String message;
    public String ref_no;
    public String txn_id;
    public String partnerid;
    public String merchantcode;
    public String isicicikyc;
    public String timestamp;
    public String owner;
    public String ownerid;
    public String api_firm;
    public String api_jwt;
    public String api_mechant_code;
    public String api_partner_id;

    public PaySprintMerchant(boolean result, String message, String ref_no, String txn_id, String partnerid, String merchantcode, String isicicikyc, String timestamp, String owner, String ownerid, String api_firm, String api_jwt, String api_mechant_code, String api_partner_id) {
        this.result = result;
        this.message = message;
        this.ref_no = ref_no;
        this.txn_id = txn_id;
        this.partnerid = partnerid;
        this.merchantcode = merchantcode;
        this.isicicikyc = isicicikyc;
        this.timestamp = timestamp;
        this.owner = owner;
        this.ownerid = ownerid;
        this.api_firm = api_firm;
        this.api_jwt = api_jwt;
        this.api_mechant_code = api_mechant_code;
        this.api_partner_id = api_partner_id;
    }

    public boolean isResult() {
        return result;
    }

    public void setResult(boolean result) {
        this.result = result;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getRef_no() {
        return ref_no;
    }

    public void setRef_no(String ref_no) {
        this.ref_no = ref_no;
    }

    public String getTxn_id() {
        return txn_id;
    }

    public void setTxn_id(String txn_id) {
        this.txn_id = txn_id;
    }

    public String getPartnerid() {
        return partnerid;
    }

    public void setPartnerid(String partnerid) {
        this.partnerid = partnerid;
    }

    public String getMerchantcode() {
        return merchantcode;
    }

    public void setMerchantcode(String merchantcode) {
        this.merchantcode = merchantcode;
    }

    public String getIsicicikyc() {
        return isicicikyc;
    }

    public void setIsicicikyc(String isicicikyc) {
        this.isicicikyc = isicicikyc;
    }

    public String getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(String timestamp) {
        this.timestamp = timestamp;
    }

    public String getOwner() {
        return owner;
    }

    public void setOwner(String owner) {
        this.owner = owner;
    }

    public String getOwnerid() {
        return ownerid;
    }

    public void setOwnerid(String ownerid) {
        this.ownerid = ownerid;
    }

    public String getApi_firm() {
        return api_firm;
    }

    public void setApi_firm(String api_firm) {
        this.api_firm = api_firm;
    }

    public String getApi_jwt() {
        return api_jwt;
    }

    public void setApi_jwt(String api_jwt) {
        this.api_jwt = api_jwt;
    }

    public String getApi_mechant_code() {
        return api_mechant_code;
    }

    public void setApi_mechant_code(String api_mechant_code) {
        this.api_mechant_code = api_mechant_code;
    }

    public String getApi_partner_id() {
        return api_partner_id;
    }

    public void setApi_partner_id(String api_partner_id) {
        this.api_partner_id = api_partner_id;
    }

    @Override
    public String toString() {
        return "PaySprintMerchant{" +
                "result=" + result +
                ", message='" + message + '\'' +
                ", ref_no='" + ref_no + '\'' +
                ", txn_id='" + txn_id + '\'' +
                ", partnerid='" + partnerid + '\'' +
                ", merchantcode='" + merchantcode + '\'' +
                ", isicicikyc='" + isicicikyc + '\'' +
                ", timestamp='" + timestamp + '\'' +
                ", owner='" + owner + '\'' +
                ", ownerid='" + ownerid + '\'' +
                ", api_firm='" + api_firm + '\'' +
                ", api_jwt='" + api_jwt + '\'' +
                ", api_mechant_code='" + api_mechant_code + '\'' +
                ", api_partner_id='" + api_partner_id + '\'' +
                '}';
    }
}
