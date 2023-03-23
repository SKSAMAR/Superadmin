package com.fintech.payware.data.network.responses;

import java.io.Serializable;
import java.util.List;

public class OperatorResponse implements Serializable {
    int response_code;
    boolean status;
    List<OperatorData> data;
    String message;

    public OperatorResponse(int response_code, boolean status, List<OperatorData> data, String message) {
        this.response_code = response_code;
        this.status = status;
        this.data = data;
        this.message = message;
    }

    public List<OperatorData> getData() {
        return data;
    }

    public void setData(List<OperatorData> data) {
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

    public static class OperatorData implements Serializable {
        String id;
        String name;
        String category;
        String viewbill;
        String regex;
        String displayname;
        String ad1_regex;

        public OperatorData(String id, String name, String category, String viewbill, String regex, String displayname, String ad1_regex) {
            this.id = id;
            this.name = name;
            this.category = category;
            this.viewbill = viewbill;
            this.regex = regex;
            this.displayname = displayname;
            this.ad1_regex = ad1_regex;
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

        public String getCategory() {
            return category;
        }

        public void setCategory(String category) {
            this.category = category;
        }

        public String getViewbill() {
            return viewbill;
        }

        public void setViewbill(String viewbill) {
            this.viewbill = viewbill;
        }

        public String getRegex() {
            return regex;
        }

        public void setRegex(String regex) {
            this.regex = regex;
        }

        public String getDisplayname() {
            return displayname;
        }

        public void setDisplayname(String displayname) {
            this.displayname = displayname;
        }

        public String getAd1_regex() {
            return ad1_regex;
        }

        public void setAd1_regex(String ad1_regex) {
            this.ad1_regex = ad1_regex;
        }

        @Override
        public String toString() {
            return "OperatorData{" +
                    "id='" + id + '\'' +
                    ", name='" + name + '\'' +
                    ", category='" + category + '\'' +
                    ", viewbill='" + viewbill + '\'' +
                    ", regex='" + regex + '\'' +
                    ", displayname='" + displayname + '\'' +
                    ", ad1_regex='" + ad1_regex + '\'' +
                    '}';
        }

      /*  @Override
        public String toString() {
            return "OperatorData{" +
                    ", name='" + name + '\'' +
                    ", category='" + category + '\'' +
                    ", displayname='" + displayname + '\'' +
                    '}';
        }
        */
    }
}
