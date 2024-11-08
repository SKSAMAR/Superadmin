package com.fintech.superadmin.data.repositories;

import android.content.Context;

import com.fintech.superadmin.data.db.AppDatabase;
import com.fintech.superadmin.data.network.APIServices;
import com.fintech.superadmin.data.network.responses.AuthResponse;
import com.fintech.superadmin.data.network.responses.RegularResponse;
import com.fintech.superadmin.listeners.NumberPayListener;

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


}
