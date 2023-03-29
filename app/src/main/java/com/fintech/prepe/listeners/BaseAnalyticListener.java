package com.fintech.prepe.listeners;

import com.fintech.prepe.data.model.GraphModel;
import com.fintech.prepe.data.network.responses.AnalCardReport;

import java.util.ArrayList;

public interface BaseAnalyticListener {
    void getCardReport(AnalCardReport report);
    void getGraphReport(ArrayList<GraphModel> graphList);
}
