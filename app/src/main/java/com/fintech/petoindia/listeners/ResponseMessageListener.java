package com.fintech.petoindia.listeners;

import com.fintech.petoindia.data.model.OperatorModel;

import java.util.List;

public interface ResponseMessageListener {

    public void onOperatorFetch(List<OperatorModel> operatorList);

}
