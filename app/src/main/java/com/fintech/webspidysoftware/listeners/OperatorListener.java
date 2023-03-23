package com.fintech.webspidysoftware.listeners;

import android.view.View;

import com.fintech.webspidysoftware.data.model.OperatorModel;
import com.fintech.webspidysoftware.data.network.responses.OperatorResponse;

public interface OperatorListener {
    void myOperatorClicks(View view, OperatorModel model);
    void myOperatorClicks(View view, OperatorResponse.OperatorData data);
    void noOperatorFound(boolean result);
}
