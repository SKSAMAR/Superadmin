package com.fintech.petoindia.master_util;

import android.content.Context;

import com.fintech.petoindia.R;
import com.fintech.petoindia.util.MyAlertUtils;

public class MessageUtil {

    final Context context;
    public MessageUtil(Context context) {
        this.context = context.getApplicationContext();
    }


    public void success(String message){
        MyAlertUtils.showAlertDialog(context.getApplicationContext(), "Success", message, R.drawable.success);
    }

    public void error(String message){
        MyAlertUtils.showAlertDialog(context.getApplicationContext(), "Failure", message, R.drawable.warning);
    }

    public void loading(){
        MyAlertUtils.showProgressAlertDialog(context.getApplicationContext());
    }

    public void dismissDialog(){
        MyAlertUtils.dismissAlertDialog();
    }

    public void anotherDialog(String message){
        MyAlertUtils.anotherAlertDialog(context.getApplicationContext(), message, "Success", R.drawable.success);
    }

}
