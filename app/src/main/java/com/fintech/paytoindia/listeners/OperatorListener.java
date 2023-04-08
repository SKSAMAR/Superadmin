package com.fintech.paytoindia.listeners;

import android.view.View;

import com.fintech.paytoindia.data.model.OperatorModel;
import com.fintech.paytoindia.data.network.responses.OperatorResponse;

public interface OperatorListener {
    void myOperatorClicks(View view, OperatorModel model);
    void myOperatorClicks(View view, OperatorResponse.OperatorData data);
    void noOperatorFound(boolean result);
}
