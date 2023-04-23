package com.fintech.superadmin.listeners;

import android.view.View;

import com.fintech.superadmin.data.model.BankModel;

public interface BanksListener {

    public void selectedBanks(View view, BankModel model);
}
