package com.fintech.superadmin;


import android.app.Application;
import android.content.Context;
import android.util.Log;

import dagger.hilt.android.HiltAndroidApp;
import java.lang.reflect.Method;

@HiltAndroidApp
public class BaseApplication extends Application {

    @Override
    public void onCreate() {
        super.onCreate();
        //com.finopaytech.finosdk.helpers.FinoApplication.init(this);
        executeFinoApplicationInit(this);
    }

    @Override
    protected void attachBaseContext(Context context) {
        super.attachBaseContext(context);
    }

    public static void executeFinoApplicationInit(Context context) {
        try {
            Class<?> finoApplicationClass = Class.forName("com.finopaytech.finosdk.helpers.FinoApplication");

            Method[] methods = finoApplicationClass.getMethods();
            for (Method method : methods) {
                if(method.getName().trim().equalsIgnoreCase("init")){
                    method.setAccessible(true);
                    try {
                        method.invoke(null, context.getApplicationContext());
                    }catch (Exception e){
                        Log.d("EXCEPTION_MESSAGE", method.getName());
                    }
                }
            }

        } catch (ClassNotFoundException e) {
            Log.d("EXCEPTION_MESSAGE", e.getMessage());
            // FinoApplication class not found, handle the scenario accordingly
        } catch (Exception e) {
            Log.d("EXCEPTION_MESSAGE", e.getMessage());
            // Other exceptions occurred, handle them accordingly
        }
    }

}
