package com.fintech.petoindia.listeners;

import com.fintech.petoindia.data.network.responses.PaytmTokenInformation;

public interface ResponseTypeListener {

    void onResponseStart();

    void onResponse(PaytmTokenInformation paytmTokenInformation);

    void onError(String message);
}
