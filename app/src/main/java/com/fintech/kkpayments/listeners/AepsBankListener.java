package com.fintech.kkpayments.listeners;

import android.view.View;

import com.fintech.kkpayments.data.model.AEPSBanksModel;

public interface AepsBankListener{

    void selectedAepsBanks(View view, AEPSBanksModel model);
}
