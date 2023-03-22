package com.fintech.petoindia.data.repositories;

import android.content.Context;
import android.content.Intent;
import android.view.View;

import com.fintech.petoindia.R;
import com.fintech.petoindia.activities.HomeActivity;
import com.fintech.petoindia.data.db.AppDatabase;
import com.fintech.petoindia.data.db.entities.User;
import com.fintech.petoindia.data.network.APIServices;
import com.fintech.petoindia.data.network.responses.RegularResponse;
import com.fintech.petoindia.util.MyAlertUtils;

import javax.inject.Inject;

import dagger.hilt.android.qualifiers.ApplicationContext;
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers;
import io.reactivex.rxjava3.annotations.NonNull;
import io.reactivex.rxjava3.core.Observer;
import io.reactivex.rxjava3.disposables.Disposable;
import io.reactivex.rxjava3.schedulers.Schedulers;

public class OnBoardingRepository {
    public APIServices  apiServices;
    public AppDatabase appDatabase;

    @Inject
    public OnBoardingRepository(@ApplicationContext Context context, APIServices  apiServices){
        appDatabase = AppDatabase.getAppDatabase(context);
        this.apiServices = apiServices;
    }


    public void startOnBoarding(View view, String first_name, String last_name, String email, String mobile, String aadhaar_no, String pan_no, String serial_no, String front_aadhaar, String back_aadhaar, String front_pan, String back_pan, String serial_image){
        MyAlertUtils.showProgressAlertDialog(view.getContext());
        User user = appDatabase.getUserDao().getRegularUser();
        
        apiServices.startOnBoarding(user.getId(),first_name, last_name, email, mobile, aadhaar_no, pan_no, serial_no, front_aadhaar, back_aadhaar, front_pan, back_pan, serial_image)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<RegularResponse>() {
                    @Override
                    public void onSubscribe(@NonNull Disposable d) {

                    }

                    @Override
                    public void onNext(@NonNull RegularResponse response) {
                        MyAlertUtils.dismissAlertDialog();
                        if(response.isStatus()){
                            Intent intent = new Intent(view.getContext(), HomeActivity.class);
                            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                            intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                            view.getContext().startActivity(intent);
                            MyAlertUtils.anotherAlertDialog(view.getContext(), "Result", response.getMessage(), R.drawable.success);
                        }else{
                            MyAlertUtils.anotherAlertDialog(view.getContext(), "Failed", response.getMessage(), R.drawable.failed);
                        }
                    }

                    @Override
                    public void onError(@NonNull Throwable e) {
                        MyAlertUtils.showServerAlertDialog(view.getContext(), e.getMessage());
                    }

                    @Override
                    public void onComplete() {

                    }
                });
    }

}
