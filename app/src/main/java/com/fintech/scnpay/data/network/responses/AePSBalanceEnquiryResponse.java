package com.fintech.scnpay.data.network.responses;

public class AePSBalanceEnquiryResponse {

    public boolean status;
    public String message;
    public Long ackno;
    public Long amount;
    public String balanceamount;
    public String bankrrn;
    public String bankiin;
    public Integer response_code;
    public String errorcode;
    public String clientrefno;
    public String last_aadhar;
    public String name;

    public AePSBalanceEnquiryResponse(boolean status, String message, Long ackno, Long amount, String balanceamount, String bankrrn, String bankiin, Integer response_code, String errorcode, String clientrefno, String last_aadhar, String name) {
        this.status = status;
        this.message = message;
        this.ackno = ackno;
        this.amount = amount;
        this.balanceamount = balanceamount;
        this.bankrrn = bankrrn;
        this.bankiin = bankiin;
        this.response_code = response_code;
        this.errorcode = errorcode;
        this.clientrefno = clientrefno;
        this.last_aadhar = last_aadhar;
        this.name = name;
    }

    public boolean isStatus() {
        return status;
    }

    public void setStatus(boolean status) {
        this.status = status;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public Long getAckno() {
        return ackno;
    }

    public void setAckno(Long ackno) {
        this.ackno = ackno;
    }

    public Long getAmount() {
        return amount;
    }

    public void setAmount(Long amount) {
        this.amount = amount;
    }

    public String getBalanceamount() {
        return balanceamount;
    }

    public void setBalanceamount(String balanceamount) {
        this.balanceamount = balanceamount;
    }

    public String getBankrrn() {
        return bankrrn;
    }

    public void setBankrrn(String bankrrn) {
        this.bankrrn = bankrrn;
    }

    public String getBankiin() {
        return bankiin;
    }

    public void setBankiin(String bankiin) {
        this.bankiin = bankiin;
    }

    public Integer getResponse_code() {
        return response_code;
    }

    public void setResponse_code(Integer response_code) {
        this.response_code = response_code;
    }

    public String getErrorcode() {
        return errorcode;
    }

    public void setErrorcode(String errorcode) {
        this.errorcode = errorcode;
    }

    public String getClientrefno() {
        return clientrefno;
    }

    public void setClientrefno(String clientrefno) {
        this.clientrefno = clientrefno;
    }

    public String getLast_aadhar() {
        return last_aadhar;
    }

    public void setLast_aadhar(String last_aadhar) {
        this.last_aadhar = last_aadhar;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

}
