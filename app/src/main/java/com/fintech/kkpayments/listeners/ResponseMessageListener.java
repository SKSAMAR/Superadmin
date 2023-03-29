package com.fintech.kkpayments.listeners;

import com.fintech.kkpayments.data.model.OperatorModel;

import java.util.List;

public interface ResponseMessageListener {

    public void onOperatorFetch(List<OperatorModel> operatorList);

}
