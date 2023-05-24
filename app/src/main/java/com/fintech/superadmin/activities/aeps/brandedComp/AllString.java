package com.fintech.superadmin.activities.aeps.brandedComp;

import android.content.Context;
import android.content.SharedPreferences;

import com.fintech.superadmin.R;

public class AllString {
    private static SharedPreferences sharedPreferences;



    public static final String PREFS_NAME = "MODULE_PREFS";
    public static final String CheckMorpho = "Please check your Morpho device or contact customer support!";

    public static final String CheckMantra = "Please check your Mantra device or contact customer support!";
    public static final String CheckSecugen = "Please check your Secugen device or contact customer support!";
    public static final String CheckTatvik = "Please check your Tatvik device or contact customer support!";
    public static final String CheckStartek = "Please check your Startek device or contact customer support!";
    public static final String CheckEvolute = "Please check your Evolute device or contact customer support!";
    public static final String CheckNextBio = "Please check your NextBiometric device or contact customer support!";
    public static final String ConnectMorpho = "Please connect your Morpho device!";
    public static final String ConnectMantra = "Please connect your Mantra device!";
    public static final String ConnectSecugen = "Please connect your Secugen device!";
    public static final String ConnectTatvik = "Please connect your Tatvik device!";
    public static final String ConnectStartek = "Please connect your Startek device!";
    public static final String ConnectEvolute = "Please connect your Evolute device!";
    public static final String ConnectNextBio = "Please connect your NextBioMetric device!";
    public static final int SnackBarBackGroundColor = android.R.color.holo_red_dark;
    public static final int SnackBarBackGroundColorGreen = android.R.color.holo_green_dark;
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
    public static final String NEXT_RD_SERVICE_PACKAGE_NAME = "com.nextbiometrics.onetouchrdservice";
    public static final String NEXT_RD_SERVICE_PLAY_STORE_ADDRESS = "https://play.google.com/store/apps/details?id=com.nextbiometrics.onetouchrdservice&hl=en_IN&gl=US";
    public static final String RD_SERVICE_PLAY_STORE_ADDRESS = "https://play.google.com/store/apps/details?id=com.mantra.rdservice";
    public static final String MORPHO_RD_SERVICE_PLAY_STORE_ADDRESS = "https://play.google.com/store/apps/details?id=com.scl.rdservice";
    public static final String SECUGEN_RD_SERVICE_PLAY_STORE_ADDRESS = "https://play.google.com/store/apps/details?id=com.secugen.rdservice";
    public static final String TATVIK_RD_SERVICE_PLAY_STORE_ADDRESS = "https://play.google.com/store/apps/details?id=com.tatvik.bio.tmf20";
    public static final String STARTEK_RD_SERVICE_PLAY_STORE_ADDRESS = "https://play.google.com/store/apps/details?id=com.acpl.registersdk";
    public static final String EVOLUTE_RD_SERVICE_PLAY_STORE_ADDRESS = "https://play.google.com/store/apps/details?id=com.evolute.rdservice";
    public static final String DeviceSuccessCode = "919";
    public static final String DeviceFailedCode = "111";
    public static final String ServerError = "Server Error.Please try again later";
    public static final String SomethingWentWrong = "Something went wrong.Please try again later.";
    public static final String ConnProblem = "Connection Problem.Please try again later.";
    /*  80 */   public static String InvalidParameterCode = "101";
    /*  81 */   public static String ServerErrorCode = "102";
    /*  82 */   public static String SomethingWentWrongCode = "103";
    /*  83 */   public static String ConnProblemCode = "104";
    /*  84 */   public static String BackPressErrorCode = "105";
    /*  85 */   public static String StatusCodeFromServer = "106";
    /*  86 */   public static String StatusCodeFromService = "107";
    /*  87 */   public static String InvalidParameterMsg = "Invalid Parameter Value";
    /*  88 */   public static String BackPressErrorMsg = "Back Pressed. Transaction Cancelled";
    /*  89 */   public static String BackPressBankSelection = "Bank Not Selected";
    /*  90 */   public static String DeviceNotRegister = "Device not Connected";


    /*  94 */   public static String SELECTED_DEVICEONE = "selected_device";
    /*  95 */   public static String SELECTED_DEVICE_INDEX = "selected_device_INDEX";

    public static String getValue(Context context, String PREF_KEY) {
        /*  98 */
        sharedPreferences = context.getSharedPreferences("MODULE_PREFS", 0);
        /*  99 */
        return sharedPreferences.getString(PREF_KEY, null);
    }


    public static void setValue(Context context, String PREF_KEY, String PREF_VALUE) {
        /* 105 */
        sharedPreferences = context.getSharedPreferences("MODULE_PREFS", 0);
        /* 106 */
        SharedPreferences.Editor editor = sharedPreferences.edit();
        /* 107 */
        editor.putString(PREF_KEY, PREF_VALUE);
        /* 108 */
        editor.commit();
    }

    public static native String baseUrlEKYC();

    public static native String baseUrlCommon();

    public static native String baseUrlAeps();

    public static native String baseUrlUpdate();
}
