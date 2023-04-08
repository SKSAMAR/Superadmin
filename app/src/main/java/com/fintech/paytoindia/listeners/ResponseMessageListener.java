package com.fintech.paytoindia.listeners;

import com.fintech.paytoindia.data.model.OperatorModel;

import java.util.List;

public interface ResponseMessageListener {

    public void onOperatorFetch(List<OperatorModel> operatorList);

}
