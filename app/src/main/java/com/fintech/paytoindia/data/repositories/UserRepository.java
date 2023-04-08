package com.fintech.paytoindia.data.repositories;

import android.content.Context;
import android.content.Intent;

import androidx.lifecycle.LiveData;

import com.fintech.paytoindia.activities.HomeActivity;
import com.fintech.paytoindia.constructor.Construct;
import com.fintech.paytoindia.data.db.AppDatabase;
import com.fintech.paytoindia.data.db.entities.AuthData;
import com.fintech.paytoindia.data.db.entities.User;
import com.fintech.paytoindia.data.network.APIServices;
import com.fintech.paytoindia.data.network.responses.RegularResponse;
import com.fintech.paytoindia.listeners.AuthListener;
import com.fintech.paytoindia.listeners.AuthoriseListener;
import com.fintech.paytoindia.util.Accessable;
import com.fintech.paytoindia.util.DisplayMessageUtil;
import com.fintech.paytoindia.util.UtilHolder;

import javax.inject.Inject;

import dagger.hilt.android.qualifiers.ApplicationContext;
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers;
import io.reactivex.rxjava3.annotations.NonNull;
import io.reactivex.rxjava3.core.Observer;
import io.reactivex.rxjava3.disposables.Disposable;
import io.reactivex.rxjava3.schedulers.Schedulers;

public class UserRepository {
    AppDatabase appDatabase;
    public APIServices apiServices;

    public static String receivedOtp;

    @Inject
    public UserRepository(@ApplicationContext Context context, APIServices apiServices){
            appDatabase = AppDatabase.getAppDatabase(context);
            this.apiServices = apiServices;
    }

    public void userLogin(Context context, String mobile, String password, AuthListener authListener) {
        apiServices.getUserLogin(mobile, password, UtilHolder.getLongitude(), UtilHolder.getLatitude())
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<AuthData>() {
                    @Override
                    public void onSubscribe(@NonNull Disposable d) {

                    }

                    @Override
                    public void onNext(@NonNull AuthData authData) {

                        if(authData.getStatus() && authData.getRs_code().equals("200") && authData.getLogin_Auth().equals(1)){
                            Construct.installToken(context, authData, () -> {
                                Intent intent = new Intent(context, HomeActivity.class);
                                intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
                                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                                intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                                DisplayMessageUtil.dismissDialog();
                                context.startActivity(intent);
                            });
                        }else{
                            DisplayMessageUtil.error(context,authData.getUser_Exist());
                        }
                    }

                    @Override
                    public void onError(@NonNull Throwable e) {
                        authListener.onFailure("Failed due to: "+e.getMessage());
                    }

                    @Override
                    public void onComplete() {

                    }
                });

    }



    public void userSignup(String first_name, String last_name, String mobile, String email, String password, String editOtp, String referal_code, AuthListener authListener, AuthoriseListener<RegularResponse> authoriseListener){
        apiServices.userSignup(first_name, last_name, mobile, email, password, editOtp, referal_code)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(res->{
                    authListener.displayMessage(res.getMessage());
                    authoriseListener.onAuth(res);
                }, err-> authListener.onFailure(err.getMessage()));
    }

    public void sendSignUpOtp(String mobile, String email, AuthListener authListener){
        
        apiServices.sendSignupOTP(mobile, email)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<RegularResponse>() {
                    @Override
                    public void onSubscribe(@NonNull Disposable d) {

                    }

                    @Override
                    public void onNext(@NonNull RegularResponse response) {
                        if(response.status){
                            receivedOtp = response.getMessage().trim();
                            authListener.otp_verification(response);
                        }else{
                            authListener.onFailure(response.getMessage());
                        }
                    }

                    @Override
                    public void onError(@NonNull Throwable e) {
                        authListener.onFailure("Failed Bad: "+e.getMessage());
                    }

                    @Override
                    public void onComplete() {

                    }
                });
    }


    public LiveData<User> getSignedInUser(){
        return appDatabase.getUserDao().getUser();
    }

    public User getSignedInRegularUser(){
        return appDatabase.getUserDao().getRegularUser();
    }


    public void forgotMyPassword(AuthListener listener, String mobile){
        if(mobile==null){
            listener.onFailure("Provide a valid mobile");
        }
        else if(mobile.isEmpty()){
            listener.onFailure("Provide a valid mobile");
        }
        else if(mobile.length()<10){
            listener.onFailure("Provide a valid mobile");
        }
        else{
            listener.onStarted();

            apiServices.forgotMyPassword(mobile)
                    .subscribeOn(Schedulers.io())
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribe(new Observer<RegularResponse>() {
                        @Override
                        public void onSubscribe(@NonNull Disposable d) {

                        }

                        @Override
                        public void onNext(@NonNull RegularResponse response) {
                            listener.otp_verification(response);
                        }

                        @Override
                        public void onError(@NonNull Throwable e) {
                            listener.onFailure(e.getMessage());
                        }

                        @Override
                        public void onComplete() {

                        }
                    });
        }
    }


    public void changeEntirePassword(AuthListener listener, String mobile, String otp, String password){
        
        apiServices.changeMyPasswordStart(mobile, otp, password)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<RegularResponse>() {
                    @Override
                    public void onSubscribe(@NonNull Disposable d) {
                        listener.onStarted();
                    }

                    @Override
                    public void onNext(@NonNull RegularResponse response) {
                        listener.otp_verification(response);
                    }

                    @Override
                    public void onError(@NonNull Throwable e) {
                        listener.onFailure("Failed due to\n"+e.getMessage());
                    }

                    @Override
                    public void onComplete() {

                    }
                });
    }

    public void refreshUserData(Context context){
        if(Accessable.isAccessable()){
            apiServices.getAllCredentials("fetch_all")
                    .subscribeOn(Schedulers.io())
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribe(c->{
                        if(c.getStatus() && c.getResponse_code().equals(1)){
                            Construct.refreshData(context, c.getReceivableData());
                        }
                    },error->{

                    });
        }
    }

}
