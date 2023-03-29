package com.fintech.kkpayments.listeners;

import com.fintech.kkpayments.data.network.responses.CustomerInfoResponse;
import com.fintech.kkpayments.data.network.responses.MyOfferResponse;

public interface ROfferListener {
    void onStartLooking();
    void getMeROffer(MyOfferResponse response);
    void onCausedError(String message);
    void onCustomerInfo(CustomerInfoResponse customerInfoResponse);
}
