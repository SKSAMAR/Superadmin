package com.fintech.prepe.listeners;

import com.fintech.prepe.data.network.responses.CustomerInfoResponse;
import com.fintech.prepe.data.network.responses.MyOfferResponse;

public interface ROfferListener {
    void onStartLooking();
    void getMeROffer(MyOfferResponse response);
    void onCausedError(String message);
    void onCustomerInfo(CustomerInfoResponse customerInfoResponse);
}
