package com.fintech.scnpay.util;

public class UtilHolder {

    static Double longitude = null;
    static Double latitude = null;

    public static String getLongitude() {
        if(longitude==null){
            return "88.363895";
        }
        return String.valueOf(longitude);
    }

    public static void setLongitude(Double longitude) {
        UtilHolder.longitude = longitude;
    }

    public static String getLatitude() {
        if(latitude == null){
            return "22.572646";
        }
        return String.valueOf(latitude);
    }

    public static void setLatitude(Double latitude) {
        UtilHolder.latitude = latitude;
    }
}
