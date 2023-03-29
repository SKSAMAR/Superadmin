package com.fintech.kkpayments.data.network;

import android.content.Context;
import android.net.ConnectivityManager;

import com.fintech.kkpayments.util.Exceptions;

import java.io.IOException;

import javax.inject.Inject;

import dagger.hilt.android.qualifiers.ApplicationContext;
import okhttp3.Interceptor;
import okhttp3.Response;

public class NetworkConnectionInterceptor implements Interceptor {
    Context context;

    @Inject
    public NetworkConnectionInterceptor(@ApplicationContext Context context){
        this.context = context;
    }

    @Override
    public Response intercept(Chain chain) throws IOException {

        if(!isInternetAvailable()){
            throw Exceptions.noInternetException("Make sure you are connected to internet");
        }
        return chain.proceed(chain.request());
    }


    public boolean isInternetAvailable(){

        ConnectivityManager connectivityManager = (ConnectivityManager)context.getSystemService(Context.CONNECTIVITY_SERVICE);
        if(connectivityManager.getActiveNetworkInfo()!=null && connectivityManager.getActiveNetworkInfo().isConnected()){
            return true;
        }
        else{
            return false;
        }
    }
}
