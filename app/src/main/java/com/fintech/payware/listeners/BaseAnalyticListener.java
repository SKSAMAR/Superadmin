package com.fintech.payware.listeners;

import com.fintech.payware.data.model.GraphModel;
import com.fintech.payware.data.network.responses.AnalCardReport;

import java.util.ArrayList;

public interface BaseAnalyticListener {
    void getCardReport(AnalCardReport report);
    void getGraphReport(ArrayList<GraphModel> graphList);
}
