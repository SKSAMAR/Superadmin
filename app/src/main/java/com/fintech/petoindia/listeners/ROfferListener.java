package com.fintech.petoindia.listeners;

import com.fintech.petoindia.data.network.responses.CustomerInfoResponse;
import com.fintech.petoindia.data.network.responses.MyOfferResponse;

public interface ROfferListener {
    void onStartLooking();
    void getMeROffer(MyOfferResponse response);
    void onCausedError(String message);
    void onCustomerInfo(CustomerInfoResponse customerInfoResponse);
}
