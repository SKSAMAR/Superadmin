package com.fintech.superadmin.listeners;

import com.fintech.superadmin.data.network.responses.CustomerInfoResponse;
import com.fintech.superadmin.data.network.responses.MyOfferResponse;

public interface ROfferListener {
    void onStartLooking();
    void getMeROffer(MyOfferResponse response);
    void onCausedError(String message);
    void onCustomerInfo(CustomerInfoResponse customerInfoResponse);
}
