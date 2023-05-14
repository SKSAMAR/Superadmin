package com.fintech.superadmin.data_model;

public class DynamicData {
    public String key = "";
    public Object value = "";

    public DynamicData(String key, Object value) {
        this.key = key;
        this.value = value;
    }

    public String getKey() {
        if (key!=null){
            return key.replace("_", " ");
        }

        return "";
    }

    public void setKey(String key) {
        this.key = key;
    }

    public String getValue() {
        if (value!=null){
            return value.toString().replace("_", " ");
        }
        return "";
    }

    public void setValue(String value) {
        this.value = value;
    }
}
