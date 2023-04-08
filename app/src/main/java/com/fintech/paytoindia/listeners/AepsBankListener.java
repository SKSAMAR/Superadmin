package com.fintech.paytoindia.listeners;

import android.view.View;

import com.fintech.paytoindia.data.model.AEPSBanksModel;

public interface AepsBankListener{

    void selectedAepsBanks(View view, AEPSBanksModel model);
}
