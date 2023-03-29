package com.fintech.kkpayments.listeners;

import com.fintech.kkpayments.data.network.responses.DmtUserData;

import java.util.List;

public interface DMTHomeListeners {
    void setWholeRecycler(List<DmtUserData> list);
    void setErrorInFetch(String value);
    void initiateStart();
}
