package com.fintech.kkpayments.listeners;

import android.view.View;

import com.fintech.kkpayments.data.network.responses.PayoutList;

import java.util.List;

public interface PayoutHomeListener {

    void setWholeRecycler(List<PayoutList> list);
    void setErrorInFetch(String value);
    void initiateStart();

    void openBeneficiary(View view, PayoutList data);
    void verifyPayoutBeneficiary(View view, PayoutList data);
    void checkStatusPayoutBeneficiary(View view, PayoutList data);
    void deleteBeneficiary(View view, PayoutList data);

}
