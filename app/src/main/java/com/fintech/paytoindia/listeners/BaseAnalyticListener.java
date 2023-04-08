package com.fintech.paytoindia.listeners;

import com.fintech.paytoindia.data.model.GraphModel;
import com.fintech.paytoindia.data.network.responses.AnalCardReport;

import java.util.ArrayList;

public interface BaseAnalyticListener {
    void getCardReport(AnalCardReport report);
    void getGraphReport(ArrayList<GraphModel> graphList);
}
