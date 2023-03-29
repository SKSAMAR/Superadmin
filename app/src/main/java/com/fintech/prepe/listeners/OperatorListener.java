package com.fintech.prepe.listeners;

import android.view.View;

import com.fintech.prepe.data.model.OperatorModel;
import com.fintech.prepe.data.network.responses.OperatorResponse;

public interface OperatorListener {
    void myOperatorClicks(View view, OperatorModel model);
    void myOperatorClicks(View view, OperatorResponse.OperatorData data);
    void noOperatorFound(boolean result);
}
