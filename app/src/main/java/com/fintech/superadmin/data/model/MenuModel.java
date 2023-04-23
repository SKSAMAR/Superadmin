package com.fintech.superadmin.data.model;

import java.io.Serializable;

public class MenuModel implements Serializable {
    public Integer icon;
    public String title;
    public String passed_name;

    public MenuModel(Integer icon, String title) {
        this.icon = icon;
        this.title = title;
    }

    public MenuModel(Integer icon, String title, String passed_name) {
        this.icon = icon;
        this.title = title;
        this.passed_name = passed_name;
    }

    public String getPassed_name() {
        if(passed_name == null){
            return title;
        }
        return passed_name;
    }

    public void setPassed_name(String passed_name) {
        this.passed_name = passed_name;
    }

    public Integer getIcon() {
        return icon;
    }

    public void setIcon(Integer icon) {
        this.icon = icon;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }
}
