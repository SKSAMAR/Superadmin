package com.fintech.kkpayments.util;

import android.content.Context;

import com.fintech.kkpayments.R;

public class DisplayMessageUtil {

    public static void success(Context context, String message){
        MyAlertUtils.showAlertDialog(context, "Success", message, R.drawable.success);
    }

    public static void dmtShow(Context context, String message) {

        MyAlertUtils.showDMTDialog(context, message, R.drawable.success);
    }

    public static void error(Context context, String message){
        MyAlertUtils.showAlertDialog(context, "Failure", message, R.drawable.warning);
    }

    public static void loading(Context context){
        MyAlertUtils.showProgressAlertDialog(context);
    }

    public static void dismissDialog(){
        MyAlertUtils.dismissAlertDialog();
    }

    public static void anotherDialogSuccess(Context context, String message){
        MyAlertUtils.anotherAlertDialog(context, message, "Success", R.drawable.success);
    }
    public static void anotherDialogFailure(Context context, String message){
        MyAlertUtils.anotherAlertDialog(context, message, "Failed", R.drawable.warning);
    }
}
