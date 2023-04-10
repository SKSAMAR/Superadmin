package com.fintech.scnpay.listeners;

import android.view.View;

import com.fintech.scnpay.data.model.BankModel;

public interface BanksListener {

    public void selectedBanks(View view, BankModel model);
}
