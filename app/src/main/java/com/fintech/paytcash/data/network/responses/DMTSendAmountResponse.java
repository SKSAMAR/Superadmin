package com.fintech.paytcash.data.network.responses;

import java.util.List;

public class DMTSendAmountResponse {
    public boolean status;
    public Integer response_code;
    public String message;
    public String remarks;
    public String txn_id;
    public List<DmtTransaction> transactionsRow;

    public DMTSendAmountResponse(){

    }


    public DMTSendAmountResponse(boolean status, Integer response_code, String message, String remarks, List<DmtTransaction> transactionsRow) {
        this.status = status;
        this.response_code = response_code;
        this.message = message;
        this.remarks = remarks;
        this.transactionsRow = transactionsRow;
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

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getRemarks() {
        return remarks;
    }

    public void setRemarks(String remarks) {
        this.remarks = remarks;
    }

    public String getDmtTransactions(){
        StringBuilder info = new StringBuilder();
        try {
            for (int i = 0; i < transactionsRow.size(); i++) {
                info.append("\n").append(transactionsRow.get(i).AMOUNT).append("\t").append(transactionsRow.get(i).STATUS);
            }
            return info.toString();
        }catch (Exception e){
            e.printStackTrace();
        }
        return message;
    }
}

class DmtTransaction{

    public String AMOUNT;
    public String STATUS;

    public DmtTransaction(String AMOUNT, String STATUS) {
        this.AMOUNT = AMOUNT;
        this.STATUS = STATUS;
    }
}

