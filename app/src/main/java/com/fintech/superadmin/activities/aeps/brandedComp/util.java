package com.fintech.superadmin.activities.aeps.brandedComp;

import android.content.Context;
import android.content.pm.PackageManager;
import android.view.View;
import android.view.inputmethod.InputMethodManager;

import androidx.appcompat.app.AppCompatActivity;

import com.google.android.material.snackbar.Snackbar;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;


public class util {
    public void showDialog(Context context) {

    }


    public static boolean isPackageInstalled(String packagename, PackageManager packageManager) {
        try {
            packageManager.getPackageInfo(packagename, 0);
            return true;
        } catch (PackageManager.NameNotFoundException e) {
            return false;
        }
    }

    public void hideKeyBoard(View view, AppCompatActivity appCompatActivity) {
        InputMethodManager imm = (InputMethodManager) appCompatActivity.getSystemService(Context.INPUT_METHOD_SERVICE);
        imm.hideSoftInputFromWindow(view.getWindowToken(), 0);
    }

    public void snackBar(View parent, String msg, int backgroundColor) {
        Snackbar snackbar = Snackbar.make(parent, "" + msg, 0);
        snackbar.show();
    }


    public String getTimeStamp() {
        Date date = new Date();
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ssZZZ", Locale.ENGLISH);
        return sdf.format(date);
    }
}
