package com.fintech.kkpayments.listeners;

import com.fintech.kkpayments.data.network.responses.PaytmTokenInformation;

public interface ResponseTypeListener {

    void onResponseStart();

    void onResponse(PaytmTokenInformation paytmTokenInformation);

    void onError(String message);
}
