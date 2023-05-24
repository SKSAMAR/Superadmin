package com.fintech.superadmin.data.apiResponse.merchant;


import com.fintech.superadmin.data.network.responses.PaySprintMerchant;

public interface OnBoardListenersPaysprint {
        void onBegin();
        void onBoardingResponse(String result, String purpose);
        void onComplete();
        void onFailure(String message);
        void onCheck(PaySprintMerchant merchant, String activity);
    }


