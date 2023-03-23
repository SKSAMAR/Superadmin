package com.fintech.webspidysoftware.listeners;

import com.fintech.webspidysoftware.data.network.responses.PaytmTokenInformation;

public interface ResponseTypeListener {

    void onResponseStart();

    void onResponse(PaytmTokenInformation paytmTokenInformation);

    void onError(String message);
}
