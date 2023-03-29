package com.fintech.prepe.listeners;

import android.view.View;

import com.fintech.prepe.data.network.responses.BeneficiaryHistoryResponse;

public interface BeneficiaryHistoryListener {

    void clickOnMoreInfo(View view, BeneficiaryHistoryResponse history);
    void clickOnUpdateInfo(View view, BeneficiaryHistoryResponse history);
    void notifierScreen(boolean result);
    void clickOnRefund(View view, BeneficiaryHistoryResponse history);
    void bringAllOverHistoryAgain(boolean status);

}
