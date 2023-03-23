package com.fintech.payware.listeners;

import com.fintech.payware.data.network.responses.CustomerInfoResponse;
import com.fintech.payware.data.network.responses.MyOfferResponse;

public interface ROfferListener {
    void onStartLooking();
    void getMeROffer(MyOfferResponse response);
    void onCausedError(String message);
    void onCustomerInfo(CustomerInfoResponse customerInfoResponse);
}
