package com.fintech.paytcash.util;

import android.Manifest;
import android.annotation.SuppressLint;

import java.text.SimpleDateFormat;
import java.util.Date;

public class Constant {

    public static final int CHROME_CUSTOM_TAB_REQUEST_CODE = 3423;
    public static final String UPI_CREDENTIALS = "UPI_CREDENTIALS";
    public static final int FAADHAAR = 101;
    public static final int BAADHAAR = 102;
    public static final int PAN = 103;
    public static final int SLIP = 104;
    public static final int STATEMENT = 105;
    public static final int ITR = 106;
    public static final int PROOF = 435324;


    public static final int PICKFILE_RESULT_CODE = 123213;
    public static final int GALLERY_IMAGE = 2;
    public static final int CAMERA_IMAGE = 3;
    public static final String IMAGE_PARAMETER = "IMAGE_PARAMETER";
    //public static final String BASE_URL_DATA = "paytcash.in";
    public static final String DHANSEWA = "uat.dhansewa.com";

    public static int OTP_TIMES = 0;
    //public static String BASE_URL = "https://paytoindia.in/";
    public static String REGISTRATION_TOKEN = "";


    public static final int PERMISSION = 786123;
    public static final String[] GIVEN_PERMISSION = new String[]{Manifest.permission.READ_CONTACTS,
                            Manifest.permission.ACCESS_FINE_LOCATION,
                            Manifest.permission.READ_EXTERNAL_STORAGE,
                            Manifest.permission.WRITE_EXTERNAL_STORAGE
    };


    public static String getCurrentTimeStamp(){
        try {
            @SuppressLint("SimpleDateFormat")
            SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            return dateFormat.format(new Date());
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }


    public static String getRegistrationToken(){
        if(REGISTRATION_TOKEN == null){
            return "";
        }
        return REGISTRATION_TOKEN;
    }
}
