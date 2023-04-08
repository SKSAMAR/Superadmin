package com.fintech.paytoindia.constructor;

import android.content.Context;
import android.content.Intent;

import com.fintech.paytoindia.activities.HomeActivity;
//import webspidysoftwareysoftware.activities.MainActivity;
import com.fintech.paytoindia.auth.AuthActivity;
import com.fintech.paytoindia.data.db.AppDatabase;
import com.fintech.paytoindia.data.db.entities.AuthData;
import com.fintech.paytoindia.data.db.entities.Report;
import com.fintech.paytoindia.data_model.LoginModel;
import com.fintech.paytoindia.deer_listener.WaitingListener;
import com.fintech.paytoindia.util.DisplayMessageUtil;

import io.reactivex.rxjava3.annotations.NonNull;
import io.reactivex.rxjava3.core.Completable;
import io.reactivex.rxjava3.core.CompletableObserver;
import io.reactivex.rxjava3.disposables.Disposable;
import io.reactivex.rxjava3.schedulers.Schedulers;

public class Construct {

    public static void logoutExecute(Context context){
        AppDatabase appDatabase = AppDatabase.getAppDatabase(context);
        Completable.fromRunnable(() -> {
                    appDatabase.getUserDao().loggedOutUser();
                    appDatabase.getAuthDao().deleteAuthData();
                    appDatabase.getUserProfileDao().deleteUserProfile();
                }).subscribeOn(Schedulers.io())
                .subscribe(new CompletableObserver() {
                    @Override
                    public void onSubscribe(@NonNull Disposable d) {

                    }

                    @Override
                    public void onComplete() {
                        Intent intent = new Intent(context, AuthActivity.class);
                        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
                        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                        DisplayMessageUtil.dismissDialog();
                        context.startActivity(intent);
                    }

                    @Override
                    public void onError(@NonNull Throwable e) {

                    }
                });

    }

    public static void installToken(Context context, AuthData data, WaitingListener listener){
        AppDatabase appDatabase = AppDatabase.getAppDatabase(context);
        Completable.fromRunnable(() -> appDatabase.getAuthDao().insertAuthDao(data))
                .subscribeOn(Schedulers.io())
                .subscribe(new CompletableObserver() {
                    @Override
                    public void onSubscribe(@NonNull Disposable d) {

                    }

                    @Override
                    public void onComplete() {
                        listener.taskFinished();
                    }

                    @Override
                    public void onError(@NonNull Throwable e) {

                    }
                });
    }

    public static void saveUser(Context context, LoginModel model){
        AppDatabase appDatabase = AppDatabase.getAppDatabase(context);
        Completable.fromRunnable(() -> {
                    if(model.getUser()!=null){
                        appDatabase.getReportDao().insertReport(new Report(System.currentTimeMillis()));
                        appDatabase.getUserDao().insert(model.getUser());
                    }
                    if(model.getUserProfile()!=null){
                        appDatabase.getUserProfileDao().insertUserProfile(model.getUserProfile());
                    }
                }).subscribeOn(Schedulers.io())
                .subscribe(new CompletableObserver() {
                    @Override
                    public void onSubscribe(@NonNull Disposable d) {

                    }

                    @Override
                    public void onComplete() {
                        Intent intent = new Intent(context, HomeActivity.class);
                        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
                        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                        DisplayMessageUtil.dismissDialog();
                        context.startActivity(intent);
                    }

                    @Override
                    public void onError(@NonNull Throwable e) {

                    }
                });
    }
    public static void refreshData(Context context, LoginModel model){
        DisplayMessageUtil.dismissDialog();
        AppDatabase appDatabase = AppDatabase.getAppDatabase(context);
        Completable.fromRunnable(() -> {
                    if(model.getUser()!=null){
                        appDatabase.getUserDao().insert(model.getUser());
                    }
                    if(model.getUserProfile()!=null){
                        appDatabase.getUserProfileDao().insertUserProfile(model.getUserProfile());
                    }
                }).subscribeOn(Schedulers.io())
                .subscribe(new CompletableObserver() {
                    @Override
                    public void onSubscribe(@NonNull Disposable d) {

                    }

                    @Override
                    public void onComplete() {

                    }

                    @Override
                    public void onError(@NonNull Throwable e) {

                    }
                });
    }

}
