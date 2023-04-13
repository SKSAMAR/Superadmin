package com.fintech.paytcash.listeners;

import com.fintech.paytcash.data.model.OperatorModel;

import java.util.List;

public interface ResponseMessageListener {

    public void onOperatorFetch(List<OperatorModel> operatorList);

}
