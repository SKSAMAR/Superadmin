package com.fintech.webspidysoftware.listeners;

import android.view.View;

import com.google.android.material.button.MaterialButton;
import com.fintech.webspidysoftware.data.network.responses.AnalyticsResponseModel;

public interface AnalyticOperationListener {
    void checkMyDetailsOf(View view, AnalyticsResponseModel model);
    void updateMyDetailsOf(View view, AnalyticsResponseModel model);
    void observerData(MaterialButton button, AnalyticsResponseModel model);
    void resetAllData();
}
