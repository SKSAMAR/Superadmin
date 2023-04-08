package com.fintech.paytoindia.listeners;

import android.view.View;

import com.fintech.paytoindia.data.model.BankModel;

public interface BanksListener {

    public void selectedBanks(View view, BankModel model);
}
