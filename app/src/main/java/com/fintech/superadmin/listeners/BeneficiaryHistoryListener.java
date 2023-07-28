package com.fintech.superadmin.listeners;

import android.view.View;

import com.fintech.superadmin.data.network.responses.BeneficiaryHistoryResponse;

public interface BeneficiaryHistoryListener<T> {

    void clickOnMoreInfo(View view, T history);
    void clickOnUpdateInfo(View view, T history);
    void notifierScreen(boolean result);
    void clickOnRefund(View view, T history);
    void bringAllOverHistoryAgain(boolean status);

}
