package com.fintech.petoindia.data.network.responses;

import java.util.List;

public class PackageResponseData {
    boolean status;
    String message;
    int response_code;
    String selected;
    List<PackageData> data;


    public PackageResponseData(boolean status, String message, int response_code, String selected, List<PackageData> data) {
        this.status = status;
        this.message = message;
        this.response_code = response_code;
        this.selected = selected;
        this.data = data;
    }

    public String getSelected() {
        return selected;
    }

    public void setSelected(String selected) {
        this.selected = selected;
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

    public int getResponse_code() {
        return response_code;
    }

    public void setResponse_code(int response_code) {
        this.response_code = response_code;
    }

    public List<PackageData> getData() {
        return data;
    }

    public void setData(List<PackageData> data) {
        this.data = data;
    }

    public static class PackageData{
        public String id;
        public String name;
        public String price;
        public String type;


        public PackageData(String id, String name, String price, String type) {
            this.id = id;
            this.name = name;
            this.price = price;
            this.type = type;
        }

        public String getId() {
            return id;
        }

        public void setId(String id) {
            this.id = id;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public String getPrice() {
            return price;
        }

        public void setPrice(String price) {
            this.price = price;
        }

        public String getType() {
            return type;
        }

        public void setType(String type) {
            this.type = type;
        }

        @Override
        public String toString() {
            return name;
        }
    }
}
