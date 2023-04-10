package com.fintech.scnpay.listeners;

import com.fintech.scnpay.data.model.OperatorModel;

import java.util.List;

public interface ResponseMessageListener {

    public void onOperatorFetch(List<OperatorModel> operatorList);

}
