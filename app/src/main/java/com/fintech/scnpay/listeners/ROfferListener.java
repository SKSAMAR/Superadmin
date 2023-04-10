package com.fintech.scnpay.listeners;

import com.fintech.scnpay.data.network.responses.CustomerInfoResponse;
import com.fintech.scnpay.data.network.responses.MyOfferResponse;

public interface ROfferListener {
    void onStartLooking();
    void getMeROffer(MyOfferResponse response);
    void onCausedError(String message);
    void onCustomerInfo(CustomerInfoResponse customerInfoResponse);
}
