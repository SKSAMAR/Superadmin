package com.fintech.petoindia.data.model;

import java.io.Serializable;

public class MicroAtmModel implements Serializable {
    String message;
    String Response;
    String TransAmount;
    String BalAmount;
    String BalRrn;
    String TxnId;
    String TransType;
    String Type;
    String CardNumber;
    String CardType;
    String TerminalId;
    String BankName;

    public MicroAtmModel(String message, String response, String transAmount, String balAmount, String balRrn, String txnId, String transType, String type, String cardNumber, String cardType, String terminalId, String bankName) {
        this.message = message;
        Response = response;
        TransAmount = transAmount;
        BalAmount = balAmount;
        BalRrn = balRrn;
        TxnId = txnId;
        TransType = transType;
        Type = type;
        CardNumber = cardNumber;
        CardType = cardType;
        TerminalId = terminalId;
        BankName = bankName;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getResponse() {
        return Response;
    }

    public void setResponse(String response) {
        Response = response;
    }

    public String getTransAmount() {
        return TransAmount;
    }

    public void setTransAmount(String transAmount) {
        TransAmount = transAmount;
    }

    public String getBalAmount() {
        return BalAmount;
    }

    public void setBalAmount(String balAmount) {
        BalAmount = balAmount;
    }

    public String getBalRrn() {
        return BalRrn;
    }

    public void setBalRrn(String balRrn) {
        BalRrn = balRrn;
    }

    public String getTxnId() {
        return TxnId;
    }

    public void setTxnId(String txnId) {
        TxnId = txnId;
    }

    public String getTransType() {
        return TransType;
    }

    public void setTransType(String transType) {
        TransType = transType;
    }

    public String getType() {
        return Type;
    }

    public void setType(String type) {
        Type = type;
    }

    public String getCardNumber() {
        return CardNumber;
    }

    public void setCardNumber(String cardNumber) {
        CardNumber = cardNumber;
    }

    public String getCardType() {
        return CardType;
    }

    public void setCardType(String cardType) {
        CardType = cardType;
    }

    public String getTerminalId() {
        return TerminalId;
    }

    public void setTerminalId(String terminalId) {
        TerminalId = terminalId;
    }

    public String getBankName() {
        return BankName;
    }

    public void setBankName(String bankName) {
        BankName = bankName;
    }

}
