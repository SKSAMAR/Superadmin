package com.fintech.paytoindia.listeners;

import com.fintech.paytoindia.data.network.responses.DmtUserData;

import java.util.List;

public interface DMTHomeListeners {
    void setWholeRecycler(List<DmtUserData> list);
    void setErrorInFetch(String value);
    void initiateStart();
}
