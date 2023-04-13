package com.fintech.paytcash.listeners;

import android.view.View;

import com.fintech.paytcash.data.model.BankModel;

public interface BanksListener {

    public void selectedBanks(View view, BankModel model);
}
