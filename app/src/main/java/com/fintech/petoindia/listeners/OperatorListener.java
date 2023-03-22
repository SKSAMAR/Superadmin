package com.fintech.petoindia.listeners;

import android.view.View;

import com.fintech.petoindia.data.model.OperatorModel;
import com.fintech.petoindia.data.network.responses.OperatorResponse;

public interface OperatorListener {
    void myOperatorClicks(View view, OperatorModel model);
    void myOperatorClicks(View view, OperatorResponse.OperatorData data);
    void noOperatorFound(boolean result);
}
