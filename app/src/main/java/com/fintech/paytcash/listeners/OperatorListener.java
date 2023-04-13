package com.fintech.paytcash.listeners;

import android.view.View;

import com.fintech.paytcash.data.model.OperatorModel;
import com.fintech.paytcash.data.network.responses.OperatorResponse;

public interface OperatorListener {
    void myOperatorClicks(View view, OperatorModel model);
    void myOperatorClicks(View view, OperatorResponse.OperatorData data);
    void noOperatorFound(boolean result);
}
