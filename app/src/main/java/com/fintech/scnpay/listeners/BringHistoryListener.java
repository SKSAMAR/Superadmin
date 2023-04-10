package com.fintech.scnpay.listeners;

import com.fintech.scnpay.data.model.HistoryModel;
import com.fintech.scnpay.data.network.responses.AnalyticsResponseModel;

import java.util.List;

public interface BringHistoryListener {

    void onStart(String message);
    void onComplete(String message);
    void onHistoryBrought(List<HistoryModel> list);
    void onFailure(String message);
    void onAnalyticsBrought(List<AnalyticsResponseModel> list);


}
