package com.fintech.paytcash.listeners;

import com.fintech.paytcash.data.network.responses.CustomerInfoResponse;
import com.fintech.paytcash.data.network.responses.MyOfferResponse;

public interface ROfferListener {
    void onStartLooking();
    void getMeROffer(MyOfferResponse response);
    void onCausedError(String message);
    void onCustomerInfo(CustomerInfoResponse customerInfoResponse);
}
