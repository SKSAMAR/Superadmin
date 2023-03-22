package com.fintech.petoindia.listeners;

import com.fintech.petoindia.data.model.BankModel;

import java.util.ArrayList;

public interface ToBankListener {

    void onStarted(String message);

    void onCompleted(String message);

    void onError(String message);

    void setAllBanks(ArrayList<BankModel> list);


}
