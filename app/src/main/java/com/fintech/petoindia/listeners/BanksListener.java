package com.fintech.petoindia.listeners;

import android.view.View;

import com.fintech.petoindia.data.model.BankModel;

public interface BanksListener {

    public void selectedBanks(View view, BankModel model);
}
