package com.fintech.superadmin.listeners;

import com.fintech.superadmin.data.ROfferPlan;
import com.fintech.superadmin.data.network.responses.CustomerInfoResponse;
import com.fintech.superadmin.data.network.responses.MyOfferResponse;

import java.util.List;

public interface DynamicROfferListener {
    void onStartLooking();
    void getMeROffer(List<ROfferPlan> response);
    void onCausedError(String message);
    void onCustomerInfo(CustomerInfoResponse customerInfoResponse);
}
