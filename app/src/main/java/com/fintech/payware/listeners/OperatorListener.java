package com.fintech.payware.listeners;

import android.view.View;

import com.fintech.payware.data.model.OperatorModel;
import com.fintech.payware.data.network.responses.OperatorResponse;

public interface OperatorListener {
    void myOperatorClicks(View view, OperatorModel model);
    void myOperatorClicks(View view, OperatorResponse.OperatorData data);
    void noOperatorFound(boolean result);
}
