package com.fintech.payware.util;

import android.os.StrictMode;
import android.util.Log;

import com.fintech.payware.deer_listener.DownloadListener;

import java.io.InputStream;
import java.net.URL;

import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers;
import io.reactivex.rxjava3.annotations.NonNull;
import io.reactivex.rxjava3.core.Observable;
import io.reactivex.rxjava3.core.Observer;
import io.reactivex.rxjava3.disposables.Disposable;
import io.reactivex.rxjava3.schedulers.Schedulers;

public class Converter {

    public static InputStream convertUrlToBase64(String url, DownloadListener listener) {
        URL newurl;
        try {
            Observable.just(1).subscribeOn(Schedulers.io())
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribe(new Observer<Integer>() {
                        @Override
                        public void onSubscribe(@NonNull Disposable d) {

                        }

                        @Override
                        public void onNext(@NonNull Integer integer) {

                        }

                        @Override
                        public void onError(@NonNull Throwable e) {

                        }

                        @Override
                        public void onComplete() {

                        }
                    });

            StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
            StrictMode.setThreadPolicy(policy);
            newurl = new URL(url);
            InputStream inputStream =  newurl.openConnection().getInputStream();
            Log.d("DownloadingProcess","Succeeded");
            return inputStream;
        } catch (Exception e) {
            e.printStackTrace();
            Log.d("DownloadingProcess","Failed "+e.getLocalizedMessage());
        }
        return null;
    }
}
