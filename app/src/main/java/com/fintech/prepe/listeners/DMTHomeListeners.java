package com.fintech.prepe.listeners;

import com.fintech.prepe.data.network.responses.DmtUserData;

import java.util.List;

public interface DMTHomeListeners {
    void setWholeRecycler(List<DmtUserData> list);
    void setErrorInFetch(String value);
    void initiateStart();
}
