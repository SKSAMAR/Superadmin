package com.fintech.kkpayments.listeners;

import com.fintech.kkpayments.data.model.GraphModel;
import com.fintech.kkpayments.data.network.responses.AnalCardReport;

import java.util.ArrayList;

public interface BaseAnalyticListener {
    void getCardReport(AnalCardReport report);
    void getGraphReport(ArrayList<GraphModel> graphList);
}
