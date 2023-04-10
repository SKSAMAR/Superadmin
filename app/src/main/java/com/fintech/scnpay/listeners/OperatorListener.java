package com.fintech.scnpay.listeners;

import android.view.View;

import com.fintech.scnpay.data.model.OperatorModel;
import com.fintech.scnpay.data.network.responses.OperatorResponse;

public interface OperatorListener {
    void myOperatorClicks(View view, OperatorModel model);
    void myOperatorClicks(View view, OperatorResponse.OperatorData data);
    void noOperatorFound(boolean result);
}
