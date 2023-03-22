package com.fintech.petoindia.listeners;

import com.fintech.petoindia.data.network.responses.DmtUserData;

import java.util.List;

public interface DMTHomeListeners {
    void setWholeRecycler(List<DmtUserData> list);
    void setErrorInFetch(String value);
    void initiateStart();
}
