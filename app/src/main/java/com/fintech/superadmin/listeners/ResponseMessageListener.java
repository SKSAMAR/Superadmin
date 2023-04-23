package com.fintech.superadmin.listeners;

import com.fintech.superadmin.data.model.OperatorModel;

import java.util.List;

public interface ResponseMessageListener {

    public void onOperatorFetch(List<OperatorModel> operatorList);

}
