package com.fintech.webspidysoftware.listeners;

import com.fintech.webspidysoftware.data.model.GraphModel;
import com.fintech.webspidysoftware.data.network.responses.AnalCardReport;

import java.util.ArrayList;

public interface BaseAnalyticListener {
    void getCardReport(AnalCardReport report);
    void getGraphReport(ArrayList<GraphModel> graphList);
}
