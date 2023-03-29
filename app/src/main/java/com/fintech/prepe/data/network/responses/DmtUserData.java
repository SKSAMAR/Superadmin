package com.fintech.prepe.data.network.responses;

import java.io.Serializable;

public class DmtUserData implements Serializable {

    public boolean status;
    public long response_code;
    public String message;
    public String fname;
    public String lname;
    public String mobile;
    public String my_status;
    public String bank3_limit;
    public String bank2_limit;
    public String bank1_limit;
    public String id;


    public DmtUserData(boolean status, long response_code, String message, String fname, String lname, String mobile, String my_status, String bank3_limit, String bank2_limit, String bank1_limit, String id) {
        this.status = status;
        this.response_code = response_code;
        this.message = message;
        this.fname = fname;
        this.lname = lname;
        this.mobile = mobile;
        this.my_status = my_status;
        this.bank3_limit = bank3_limit;
        this.bank2_limit = bank2_limit;
        this.bank1_limit = bank1_limit;
        this.id = id;
    }

    public String getFname() {
        return fname;
    }

    public void setFname(String fname) {
        this.fname = fname;
    }

    public String getLname() {
        return lname;
    }

    public void setLname(String lname) {
        this.lname = lname;
    }

    public String getMobile() {
        return mobile;
    }

    public void setMobile(String mobile) {
        this.mobile = mobile;
    }

    public boolean isStatus() {
        return status;
    }

    public void setStatus(boolean status) {
        this.status = status;
    }

    public long getResponse_code() {
        return response_code;
    }

    public void setResponse_code(long response_code) {
        this.response_code = response_code;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getMy_status() {
        return my_status;
    }

    public void setMy_status(String my_status) {
        this.my_status = my_status;
    }

    public String getBank3_limit() {
        return bank3_limit;
    }

    public void setBank3_limit(String bank3_limit) {
        this.bank3_limit = bank3_limit;
    }

    public String getBank2_limit() {
        return bank2_limit;
    }

    public void setBank2_limit(String bank2_limit) {
        this.bank2_limit = bank2_limit;
    }

    public String getBank1_limit() {
        return bank1_limit;
    }

    public void setBank1_limit(String bank1_limit) {
        this.bank1_limit = bank1_limit;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    @Override
    public String toString() {
        return "DmtUserData{" +
                "status=" + status +
                ", response_code=" + response_code +
                ", message='" + message + '\'' +
                ", fname='" + fname + '\'' +
                ", lname='" + lname + '\'' +
                ", mobile='" + mobile + '\'' +
                ", my_status='" + my_status + '\'' +
                ", bank3_limit='" + bank3_limit + '\'' +
                ", bank2_limit='" + bank2_limit + '\'' +
                ", bank1_limit='" + bank1_limit + '\'' +
                ", id='" + id + '\'' +
                '}';
    }
}
