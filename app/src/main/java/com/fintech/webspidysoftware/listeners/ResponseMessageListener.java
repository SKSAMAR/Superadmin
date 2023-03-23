package com.fintech.webspidysoftware.listeners;

import com.fintech.webspidysoftware.data.model.OperatorModel;

import java.util.List;

public interface ResponseMessageListener {

    public void onOperatorFetch(List<OperatorModel> operatorList);

}
