package com.fintech.superadmin.data_model;

public class AppType {
    private final String appType;

    public AppType(String appType) {
        this.appType = appType;
    }

    public String getAppType() {
        if (appType == null){
            return "B2B";
        }
        return appType;
    }
}
