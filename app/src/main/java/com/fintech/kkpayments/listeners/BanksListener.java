package com.fintech.kkpayments.listeners;

import android.view.View;

import com.fintech.kkpayments.data.model.BankModel;

public interface BanksListener {

    public void selectedBanks(View view, BankModel model);
}
