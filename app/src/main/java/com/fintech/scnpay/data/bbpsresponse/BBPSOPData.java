package com.fintech.scnpay.data.bbpsresponse;

import java.io.Serializable;

public class BBPSOPData implements Serializable {
    private String viewbill;

    private String ad3_regex;

    private String ad3_name;

    private String ad1_name;

    private String ad1_regex;

    private String regex;

    private String displayname;

    private String name;

    private String ad3_d_name;

    private String ad2_d_name;

    private String ad2_name;

    private String ad2_regex;

    private String id;

    private String category;

    private String ad1_d_name;

    public String getViewbill() {
        return this.viewbill;
    }

    public void setViewbill(String viewbill) {
        this.viewbill = viewbill;
    }

    public String getAd3_regex() {
        return this.ad3_regex;
    }

    public void setAd3_regex(String ad3_regex) {
        this.ad3_regex = ad3_regex;
    }

    public String getAd3_name() {
        if(ad3_name == null){
            return "";
        }
        return this.ad3_name;
    }

    public void setAd3_name(String ad3_name) {
        this.ad3_name = ad3_name;
    }

    public String getAd1_name() {
        if(ad1_name == null){
            return "";
        }
        return this.ad1_name;
    }

    public void setAd1_name(String ad1_name) {
        this.ad1_name = ad1_name;
    }

    public String getAd1_regex() {
        return this.ad1_regex;
    }

    public void setAd1_regex(String ad1_regex) {
        this.ad1_regex = ad1_regex;
    }

    public String getRegex() {
        return this.regex;
    }

    public void setRegex(String regex) {
        this.regex = regex;
    }

    public String getDisplayname() {
        return this.displayname;
    }

    public void setDisplayname(String displayname) {
        this.displayname = displayname;
    }

    public String getName() {
        return this.name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getAd3_d_name() {
        return this.ad3_d_name;
    }

    public void setAd3_d_name(String ad3_d_name) {
        this.ad3_d_name = ad3_d_name;
    }

    public String getAd2_d_name() {
        return this.ad2_d_name;
    }

    public void setAd2_d_name(String ad2_d_name) {
        this.ad2_d_name = ad2_d_name;
    }

    public String getAd2_name() {
        if(ad2_name == null){
            return "";
        }
        return this.ad2_name;
    }

    public void setAd2_name(String ad2_name) {
        this.ad2_name = ad2_name;
    }

    public String getAd2_regex() {
        return this.ad2_regex;
    }

    public void setAd2_regex(String ad2_regex) {
        this.ad2_regex = ad2_regex;
    }

    public String getId() {
        return this.id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getCategory() {
        return this.category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public String getAd1_d_name() {
        return this.ad1_d_name;
    }

    public void setAd1_d_name(String ad1_d_name) {
        this.ad1_d_name = ad1_d_name;
    }

    @Override
    public String toString() {
        return name;
    }


    public String toStringWholeData() {
        return "BBPSOPData{" +
                "viewbill='" + viewbill + '\'' +
                ", ad3_regex='" + ad3_regex + '\'' +
                ", ad3_name='" + ad3_name + '\'' +
                ", ad1_name='" + ad1_name + '\'' +
                ", ad1_regex='" + ad1_regex + '\'' +
                ", regex='" + regex + '\'' +
                ", displayname='" + displayname + '\'' +
                ", name='" + name + '\'' +
                ", ad3_d_name='" + ad3_d_name + '\'' +
                ", ad2_d_name='" + ad2_d_name + '\'' +
                ", ad2_name='" + ad2_name + '\'' +
                ", ad2_regex='" + ad2_regex + '\'' +
                ", id='" + id + '\'' +
                ", category='" + category + '\'' +
                ", ad1_d_name='" + ad1_d_name + '\'' +
                '}';
    }
}
