package com.fintech.paytcash.listeners;

import com.fintech.paytcash.data.model.GraphModel;
import com.fintech.paytcash.data.network.responses.AnalCardReport;

import java.util.ArrayList;

public interface BaseAnalyticListener {
    void getCardReport(AnalCardReport report);
    void getGraphReport(ArrayList<GraphModel> graphList);
}
