package com.fintech.paytcash.data.repositories;

import android.content.Context;

import com.fintech.paytcash.data.db.AppDatabase;
import com.fintech.paytcash.data.network.APIServices;
import com.fintech.paytcash.data.network.responses.AuthResponse;
import com.fintech.paytcash.listeners.NumberPayListener;

import javax.inject.Inject;

import dagger.hilt.android.qualifiers.ApplicationContext;
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers;
import io.reactivex.rxjava3.annotations.NonNull;
import io.reactivex.rxjava3.core.Observer;
import io.reactivex.rxjava3.disposables.Disposable;
import io.reactivex.rxjava3.schedulers.Schedulers;

public class MobileNumberPayRepository {

    APIServices  apiServices;
    AppDatabase appDatabase;

    @Inject
    public MobileNumberPayRepository(@ApplicationContext Context context, APIServices  apiServices){
        appDatabase = AppDatabase.getAppDatabase(context);
        this.apiServices = apiServices;
    }


    public void isNumberValidCheck(String mobile, NumberPayListener listener){
        
        apiServices.numberAuthenticate(mobile)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<AuthResponse>() {
                    @Override
                    public void onSubscribe(@NonNull Disposable d) {
                        listener.showProgress("Please Wait, Loading..");
                    }

                    @Override
                    public void onNext(@NonNull AuthResponse authResponse) {
                        listener.isNumberValid(authResponse);
                    }

                    @Override
                    public void onError(@NonNull Throwable e) {
                        listener.showMessage(e.getMessage());
                    }

                    @Override
                    public void onComplete() {

                    }
                });


    }
}
