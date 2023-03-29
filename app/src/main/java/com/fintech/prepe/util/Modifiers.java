package com.fintech.prepe.util;

public class Modifiers {

    public static String checkValidation(String value, String defaultValue){
        if(value==null){
            return defaultValue;
        }
        return value;
    }
}
