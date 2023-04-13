package com.fintech.paytcash.listeners;

import com.fintech.paytcash.data.model.BankModel;

import java.util.ArrayList;

public interface ToBankListener {

    void onStarted(String message);

    void onCompleted(String message);

    void onError(String message);

    void setAllBanks(ArrayList<BankModel> list);


}
