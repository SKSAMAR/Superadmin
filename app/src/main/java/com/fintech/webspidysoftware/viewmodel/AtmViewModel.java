package com.fintech.webspidysoftware.viewmodel;

import android.content.Context;

import androidx.lifecycle.ViewModel;

import com.fintech.webspidysoftware.data.network.APIServices;
import com.fintech.webspidysoftware.deer_listener.master_listener.BetterListener;
import com.fintech.webspidysoftware.util.MyAlertUtils;

import javax.inject.Inject;

import dagger.hilt.android.lifecycle.HiltViewModel;
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers;
import io.reactivex.rxjava3.schedulers.Schedulers;

@HiltViewModel
public class AtmViewModel extends ViewModel {

    public APIServices apiServices;
    public BetterListener listener;

    @Inject
    public AtmViewModel(APIServices apiServices){
        this.apiServices = apiServices;

    }

    public void recordTransaction(Context context, String amount, String txnType, String reference){
        MyAlertUtils.showProgressAlertDialog(context);
        apiServices.reportMicroAtmTxn(amount, txnType, reference,  "initiate_atm_txn")
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(c->{
                    MyAlertUtils.dismissAlertDialog();
                    if(!c.isStatus()){
                        listener.onFailure(c.getMessage());
                    }
                    else{
                        listener.onAchieved(c);
                    }
                }, e->{
                    listener.onFailure(e.getLocalizedMessage());
                });
    }
}
