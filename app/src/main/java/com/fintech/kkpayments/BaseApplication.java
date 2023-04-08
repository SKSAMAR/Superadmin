package com.fintech.kkpayments;


import android.app.Application;
import android.content.Context;

//import com.finopaytech.finosdk.helpers.FinoApplication;

import dagger.hilt.android.HiltAndroidApp;

@HiltAndroidApp
public class BaseApplication extends Application {
    @Override
    public void onCreate() {
        super.onCreate();
        //FinoApplication.init(this);
    }

    @Override
    protected void attachBaseContext(Context context)
    {
        super.attachBaseContext(context);
    }

}
