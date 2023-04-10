package com.fintech.scnpay.listeners;

import com.fintech.scnpay.data.network.responses.PaytmTokenInformation;

public interface ResponseTypeListener {

    void onResponseStart();

    void onResponse(PaytmTokenInformation paytmTokenInformation);

    void onError(String message);
}
