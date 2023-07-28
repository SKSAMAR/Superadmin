package com.fintech.superadmin.listeners;

import com.fintech.superadmin.data.model.EkoBankModel;

import java.util.ArrayList;

public interface ToEkoBankListener {

    void onStarted(String message);

    void onCompleted(String message);

    void onError(String message);

    void setAllBanks(ArrayList<EkoBankModel> list);


}
