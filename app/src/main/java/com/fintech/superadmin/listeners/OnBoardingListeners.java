package com.fintech.superadmin.listeners;

import com.fintech.superadmin.data.network.responses.PaySprintMerchant;

public interface OnBoardingListeners {
    void onBegin();
    void onBoardingResponse(String result, String purpose);
    void onComplete();
    void onFailure(String message);
    void onCheck(PaySprintMerchant merchant, String activity);
}
