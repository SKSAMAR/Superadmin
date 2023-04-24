package com.fintech.superadmin.viewmodel;

import android.content.Context;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.fintech.superadmin.data.dto.AtmProceedableDto;
import com.fintech.superadmin.data.network.APIServices;
import com.fintech.superadmin.deer_listener.master_listener.BetterListener;
import com.fintech.superadmin.util.MyAlertUtils;
import com.fintech.superadmin.util.NetworkUtil;

import javax.inject.Inject;

import dagger.hilt.android.lifecycle.HiltViewModel;
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers;
import io.reactivex.rxjava3.schedulers.Schedulers;

@HiltViewModel
public class AtmViewModel extends ViewModel {

    public APIServices apiServices;
    public BetterListener<AtmProceedableDto> listener;
    public MutableLiveData<String> atmType = new MutableLiveData<>(null);

    @Inject
    public AtmViewModel(APIServices apiServices) {
        this.apiServices = apiServices;
        getATMType();
    }

    public void getATMType() {
        apiServices.getATMType("getATMType")
                .subscribeOn(AndroidSchedulers.mainThread())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(result -> atmType.setValue(result.getMessage()));
    }


    public void recordTransaction(Context context, String amount, String txnType) {
        MyAlertUtils.showProgressAlertDialog(context);
        apiServices.reportMicroAtmTxn(amount, txnType, "initiate_atm_txn")
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(c -> {
                    MyAlertUtils.dismissAlertDialog();
                    if (c.getResponseCode() != null && c.getResponseCode().equals(1)) {
                        listener.onAchieved(c);
                    } else {
                        listener.onFailure(c.getMessage());
                    }
                }, e -> {
                    listener.onFailure(e.getLocalizedMessage());
                });
    }
}
