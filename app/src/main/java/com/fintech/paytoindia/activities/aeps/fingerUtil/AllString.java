package com.fintech.paytoindia.activities.aeps.fingerUtil;

import android.content.Context;
import android.content.SharedPreferences;

public class AllString {
    private static SharedPreferences sharedPreferences;
    public static final String PREFS_NAME = "MODULE_PREFS";
    public static final String Version = "1.1.1";
    public static final String baseUrl_aeps = "http://uat.dhansewa.com/AEPS/";
    public static final String baseUrl_update = "https://uat.dhansewa.com/aeps/";
    public static final String baseUrl_common = "http://uat.dhansewa.com/Common/";
    public static final String baseUrl_ekyc = "http://uat.dhansewa.com/aeps/";
    public static final String baseUrl_aepsNew = "https://partners.mahagram.in/aepsapi/api/";
    public static final String airtelAeps = "";
    public static final String CheckMorpho = "Please check your Morpho device or contact customer support!";
    public static final String CheckMantra = "Please check your Mantra device or contact customer support!";
    public static final String CheckSecugen = "Please check your Secugen device or contact customer support!";
    public static final String CheckTatvik = "Please check your Tatvik device or contact customer support!";
    public static final String CheckStartek = "Please check your Startek device or contact customer support!";
    public static final String CheckEvolute = "Please check your Evolute device or contact customer support!";
    public static final String ConnectMorpho = "Please connect your Morpho device!";
    public static final String ConnectMantra = "Please connect your Mantra device!";
    public static final String ConnectSecugen = "Please connect your Secugen device!";
    public static final String ConnectTatvik = "Please connect your Tatvik device!";
    public static final String ConnectStartek = "Please connect your Startek device!";
    public static final String ConnectEvolute = "Please connect your Evolute device!";
    public static final int PLAY_SERVICES_RESOLUTION_REQUEST = 9000;
    public static final int LongLocationInterval = 3000;
    public static final int FastestLocationInterval = 1000;
    public static final String Rupee = "₹";
    public static final String ServiceNotAvailableMsg = "Sorry, This service is not available right now!";
    public static final String InvalidAadhaarNumber = "Please enter valid Aadhar Number!";
    public static final String InvalidVIDNumber = "Please enter valid VID!";
    public static final String Permission_Denied = "Permission Denied";
    public static final String iciciNewMerchantMessage = "Re-initiate the transaction BC is now active at bank end!";
    public static final String MobileNumberVerify = "Please enter customer's 10 digit mobile number!";
    public static final String ImageSizeMsg = "Image size should be less than 500 KB!";
    public static final String ImageNotFoundError = "Selected Image Not Found!";
    public static final String RD_SERVICE_PACKAGE_NAME = "com.mantra.rdservice";
    public static final String MORPHO_RD_SERVICE_PACKAGE_NAME = "com.scl.rdservice";
    public static final String SECUGEN_RD_SERVICE_PACKAGE_NAME = "com.secugen.rdservice";
    public static final String TATVIK_RD_SERVICE_PACKAGE_NAME = "com.tatvik.bio.tmf20";
    public static final String STARTEK_RD_SERVICE_PACKAGE_NAME = "com.acpl.registersdk";
    public static final String EVOLUTE_RD_SERVICE_PACKAGE_NAME = "com.evolute.rdservice";
    public static final String RD_SERVICE_PLAY_STORE_ADDRESS = "https://play.google.com/store/apps/details?id=com.mantra.rdservice";
    public static final String MORPHO_RD_SERVICE_PLAY_STORE_ADDRESS = "https://play.google.com/store/apps/details?id=com.scl.rdservice";
    public static final String SECUGEN_RD_SERVICE_PLAY_STORE_ADDRESS = "https://play.google.com/store/apps/details?id=com.secugen.rdservice";
    public static final String TATVIK_RD_SERVICE_PLAY_STORE_ADDRESS = "https://play.google.com/store/apps/details?id=com.tatvik.bio.tmf20";
    public static final String STARTEK_RD_SERVICE_PLAY_STORE_ADDRESS = "https://play.google.com/store/apps/details?id=com.acpl.registersdk";
    public static final String EVOLUTE_RD_SERVICE_PLAY_STORE_ADDRESS = "https://play.google.com/store/apps/details?id=com.evolute.rdservice";
    public static final String DeviceSuccessCode = "919";
    public static final String DeviceFailedCode = "111";
    public static String InvalidParameterCode;
    public static String ServerErrorCode;
    public static String SomethingWentWrongCode;
    public static String ConnProblemCode;
    public static String BackPressErrorCode;
    public static String StatusCodeFromServer;
    public static String InvalidParameterMsg;
    public static final String ServerError = "Server Error.Please try again later";
    public static final String SomethingWentWrong = "Something went wrong.Please try again later.";
    public static final String ConnProblem = "Connection Problem.Please try again later.";
    public static String BackPressErrorMsg;
    public static String BackPressBankSelection;
    public static String DeviceNotRegister;
    public static String DeviceNotConnected;
    public static String SELECTED_DEVICE;
    public static String SELECTED_DEVICE_INDEX;

    public AllString() {
    }

    public static String getValue(Context context, String PREF_KEY) {
        sharedPreferences = context.getSharedPreferences("MODULE_PREFS", 0);
        return sharedPreferences.getString(PREF_KEY, (String)null);
    }

    public static void setValue(Context context, String PREF_KEY, String PREF_VALUE) {
        sharedPreferences = context.getSharedPreferences("MODULE_PREFS", 0);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString(PREF_KEY, PREF_VALUE);
        editor.commit();
    }

    static {
        //SnackBarBackGroundColor = color.snackBarColor1;
        //SnackBarBackGroundColorGreen = color.lightDarkGreen;
        InvalidParameterCode = "101";
        ServerErrorCode = "102";
        SomethingWentWrongCode = "103";
        ConnProblemCode = "104";
        BackPressErrorCode = "105";
        StatusCodeFromServer = "106";
        InvalidParameterMsg = "Invalid Parameter Value";
        BackPressErrorMsg = "Back Pressed. Transaction Cancelled";
        BackPressBankSelection = "Bank Not Selected";
        DeviceNotRegister = "Device not register against this BC";
        DeviceNotConnected = "Device not connect";
        SELECTED_DEVICE = "selected_device";
        SELECTED_DEVICE_INDEX = "selected_device_INDEX";
    }
}
