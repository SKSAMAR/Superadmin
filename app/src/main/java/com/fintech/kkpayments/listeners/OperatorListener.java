package com.fintech.kkpayments.listeners;

import android.view.View;

import com.fintech.kkpayments.data.model.OperatorModel;
import com.fintech.kkpayments.data.network.responses.OperatorResponse;

public interface OperatorListener {
    void myOperatorClicks(View view, OperatorModel model);
    void myOperatorClicks(View view, OperatorResponse.OperatorData data);
    void noOperatorFound(boolean result);
}
