package com.fintech.prepe.util;

import android.os.SystemClock;

public class Accessable {
    private static long mLastClickTime = 0;
    public static boolean isAccessable(){
        if (SystemClock.elapsedRealtime() - mLastClickTime < 1000){
            return false;
        }
        else{
            mLastClickTime = SystemClock.elapsedRealtime();
            return true;
        }
    }
}
