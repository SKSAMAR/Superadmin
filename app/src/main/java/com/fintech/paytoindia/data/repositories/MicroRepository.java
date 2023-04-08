package com.fintech.paytoindia.data.repositories;

import android.content.Context;

import com.fintech.paytoindia.data.db.AppDatabase;
import com.fintech.paytoindia.data.db.entities.User;
import com.fintech.paytoindia.data.network.APIServices;
import com.fintech.paytoindia.util.ViewUtils;

import dagger.hilt.android.qualifiers.ApplicationContext;
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers;
import io.reactivex.rxjava3.annotations.NonNull;
import io.reactivex.rxjava3.core.Observer;
import io.reactivex.rxjava3.disposables.Disposable;
import io.reactivex.rxjava3.schedulers.Schedulers;

public class MicroRepository {

    AppDatabase appDatabase;
    APIServices  apiServices;

    public MicroRepository(@ApplicationContext Context context, APIServices  apiServices){
        appDatabase = AppDatabase.getAppDatabase(context);
        this.apiServices = apiServices;
    }

    public void updateTheDatabase(Context context, String message, String response, String transAmount, String balAmount, String balRrn, String txnId, String transType, String type, String cardNumber, String cardType, String terminalId, String bankName, String reference, String boolstatus){
        User user = appDatabase.getUserDao().getRegularUser();
        
        apiServices.insertReportForMicroAtm(message,response,transAmount,balAmount,balRrn,txnId,transType,type,cardNumber,cardType,terminalId,bankName,user.getId(),user.getUserstatus(),reference, boolstatus)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<String>() {
                    @Override
                    public void onSubscribe(@NonNull Disposable d) {

                    }

                    @Override
                    public void onNext(@NonNull String s) {
                        ViewUtils.showToast(context, s);
                    }

                    @Override
                    public void onError(@NonNull Throwable e) {
                        ViewUtils.showToast(context, "Failed due to\n"+e.getMessage());
                    }

                    @Override
                    public void onComplete() {

                    }
                });
    }
}
