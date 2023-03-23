package com.fintech.webspidysoftware.listeners;

import android.view.View;

import com.fintech.webspidysoftware.data.model.BankModel;

public interface BanksListener {

    public void selectedBanks(View view, BankModel model);
}
