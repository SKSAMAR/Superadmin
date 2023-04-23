package com.fintech.superadmin.listeners;

import com.fintech.superadmin.data.model.GraphModel;
import com.fintech.superadmin.data.network.responses.AnalCardReport;

import java.util.ArrayList;

public interface BaseAnalyticListener {
    void getCardReport(AnalCardReport report);
    void getGraphReport(ArrayList<GraphModel> graphList);
}
