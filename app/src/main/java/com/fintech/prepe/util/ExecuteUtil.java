package com.fintech.prepe.util;

import android.content.Context;
import android.content.Intent;

import com.fintech.prepe.activities.HomeActivity;


public class ExecuteUtil {
    public static void ThrowOut(Context context){
        DisplayMessageUtil.dismissDialog();
        Intent intent = new Intent(context, HomeActivity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        context.startActivity(intent);
    }
}
