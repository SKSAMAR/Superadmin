package com.fintech.scnpay.listeners;

import com.fintech.scnpay.data.model.GraphModel;
import com.fintech.scnpay.data.network.responses.AnalCardReport;

import java.util.ArrayList;

public interface BaseAnalyticListener {
    void getCardReport(AnalCardReport report);
    void getGraphReport(ArrayList<GraphModel> graphList);
}
