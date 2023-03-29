package com.fintech.kkpayments.data.repositories;

import android.content.Context;
import android.view.View;

import com.fintech.kkpayments.data.db.AppDatabase;
import com.fintech.kkpayments.data.db.entities.User;
import com.fintech.kkpayments.data.network.APIServices;
import com.fintech.kkpayments.data.network.responses.UserTypeResponse;
import com.fintech.kkpayments.listeners.CreationListener;
import com.fintech.kkpayments.util.MyAlertUtils;

import javax.inject.Inject;

import dagger.hilt.android.qualifiers.ApplicationContext;
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers;
import io.reactivex.rxjava3.annotations.NonNull;
import io.reactivex.rxjava3.core.Observer;
import io.reactivex.rxjava3.disposables.Disposable;
import io.reactivex.rxjava3.schedulers.Schedulers;

public class CreationRepository {
    AppDatabase appDatabase;
    APIServices  apiServices;
    @Inject
    public CreationRepository(@ApplicationContext Context context, APIServices  apiServices){
        appDatabase = AppDatabase.getAppDatabase(context);
        this.apiServices = apiServices;
    }

    public void bringUserType(CreationListener listener, View view){
        MyAlertUtils.showProgressAlertDialog(view.getContext());
        User user = appDatabase.getUserDao().getRegularUser();

        apiServices.bringAllSuitableUserType(user.getId(), user.getUserstatus())
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<UserTypeResponse>() {
                    @Override
                    public void onSubscribe(@NonNull Disposable d) {

                    }

                    @Override
                    public void onNext(@NonNull UserTypeResponse userTypeResponse) {
                        MyAlertUtils.dismissAlertDialog();
                        if(userTypeResponse.isStatus()){
                            listener.achieveUserType(userTypeResponse.getData());
                        }else{
                            MyAlertUtils.showServerAlertDialog(view.getContext(), userTypeResponse.getMessage());
                        }
                    }

                    @Override
                    public void onError(@NonNull Throwable e) {
                        MyAlertUtils.showServerAlertDialog(view.getContext(), "Failed due to\n"+e.getMessage());
                    }

                    @Override
                    public void onComplete() {

                    }
                });
    }

}
