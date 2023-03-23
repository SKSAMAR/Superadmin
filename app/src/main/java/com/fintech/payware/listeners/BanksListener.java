package com.fintech.payware.listeners;

import android.view.View;

import com.fintech.payware.data.model.BankModel;

public interface BanksListener {

    public void selectedBanks(View view, BankModel model);
}
