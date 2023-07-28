package com.fintech.superadmin.listeners;

import android.view.View;

import com.fintech.superadmin.data.model.BankModel;
import com.fintech.superadmin.data.model.EkoBankModel;

public interface EkoBanksListener {

    public void selectedBanks(View view, EkoBankModel model);
}
