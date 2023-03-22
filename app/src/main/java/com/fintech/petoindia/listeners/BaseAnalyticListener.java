package com.fintech.petoindia.listeners;

import com.fintech.petoindia.data.model.GraphModel;
import com.fintech.petoindia.data.network.responses.AnalCardReport;

import java.util.ArrayList;

public interface BaseAnalyticListener {
    void getCardReport(AnalCardReport report);
    void getGraphReport(ArrayList<GraphModel> graphList);
}
