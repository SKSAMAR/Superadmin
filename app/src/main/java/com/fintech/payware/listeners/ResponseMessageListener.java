package com.fintech.payware.listeners;

import com.fintech.payware.data.model.OperatorModel;

import java.util.List;

public interface ResponseMessageListener {

    public void onOperatorFetch(List<OperatorModel> operatorList);

}
