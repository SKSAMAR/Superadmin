package com.fintech.paytcash.listeners;

import com.fintech.paytcash.data.network.responses.PaytmTokenInformation;

public interface ResponseTypeListener {

    void onResponseStart();

    void onResponse(PaytmTokenInformation paytmTokenInformation);

    void onError(String message);
}
