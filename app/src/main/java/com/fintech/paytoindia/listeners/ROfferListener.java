package com.fintech.paytoindia.listeners;

import com.fintech.paytoindia.data.network.responses.CustomerInfoResponse;
import com.fintech.paytoindia.data.network.responses.MyOfferResponse;

public interface ROfferListener {
    void onStartLooking();
    void getMeROffer(MyOfferResponse response);
    void onCausedError(String message);
    void onCustomerInfo(CustomerInfoResponse customerInfoResponse);
}
