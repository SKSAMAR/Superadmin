package com.fintech.webspidysoftware.listeners;

import com.fintech.webspidysoftware.data.network.responses.CustomerInfoResponse;
import com.fintech.webspidysoftware.data.network.responses.MyOfferResponse;

public interface ROfferListener {
    void onStartLooking();
    void getMeROffer(MyOfferResponse response);
    void onCausedError(String message);
    void onCustomerInfo(CustomerInfoResponse customerInfoResponse);
}
