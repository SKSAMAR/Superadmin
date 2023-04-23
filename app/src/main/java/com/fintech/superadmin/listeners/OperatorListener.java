package com.fintech.superadmin.listeners;

import android.view.View;

import com.fintech.superadmin.data.model.OperatorModel;
import com.fintech.superadmin.data.network.responses.OperatorResponse;

public interface OperatorListener {
    void myOperatorClicks(View view, OperatorModel model);
    void myOperatorClicks(View view, OperatorResponse.OperatorData data);
    void noOperatorFound(boolean result);
}
