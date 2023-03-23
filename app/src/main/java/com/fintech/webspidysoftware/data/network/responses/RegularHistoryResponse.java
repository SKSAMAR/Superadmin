package com.fintech.webspidysoftware.data.network.responses;

import java.util.List;

public class RegularHistoryResponse {

    int response_code;
    boolean status;
    String message;
    List<EveryDayData> data;

    public RegularHistoryResponse(int response_code, boolean status, String message, List<EveryDayData> data) {
        this.response_code = response_code;
        this.status = status;
        this.message = message;
        this.data = data;
    }

    public int getResponse_code() {
        return response_code;
    }

    public void setResponse_code(int response_code) {
        this.response_code = response_code;
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

    public List<EveryDayData> getData() {
        return data;
    }

    public void setData(List<EveryDayData> data) {
        this.data = data;
    }

    public static class EveryDayData{
        public String id;
        public String number;
        public String amount;
        public String reference;
        public String status;
        public String response;
        public String date_time;

        public EveryDayData(String id, String number, String amount, String reference, String status, String response, String date_time) {
            this.id = id;
            this.number = number;
            this.amount = amount;
            this.reference = reference;
            this.status = status;
            this.response = response;
            this.date_time = date_time;
        }

        public String getDate_time() {
            return date_time;
        }

        public void setDate_time(String date_time) {
            this.date_time = date_time;
        }

        public String getId() {
            return id;
        }

        public void setId(String id) {
            this.id = id;
        }

        public String getNumber() {
            return number;
        }

        public void setNumber(String number) {
            this.number = number;
        }

        public String getAmount() {
            return amount;
        }

        public void setAmount(String amount) {
            this.amount = amount;
        }

        public String getReference() {
            return reference;
        }

        public void setReference(String reference) {
            this.reference = reference;
        }

        public String getStatus() {
            return status;
        }

        public void setStatus(String status) {
            this.status = status;
        }

        public String getResponse() {
            return response;
        }

        public void setResponse(String response) {
            this.response = response;
        }
    }
}
