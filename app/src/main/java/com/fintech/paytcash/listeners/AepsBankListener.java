package com.fintech.paytcash.listeners;

import android.view.View;

import com.fintech.paytcash.data.model.AEPSBanksModel;

public interface AepsBankListener{

    void selectedAepsBanks(View view, AEPSBanksModel model);
}
