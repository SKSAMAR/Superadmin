package com.fintech.superadmin.listeners;

import com.fintech.superadmin.data.network.responses.DmtUserData;

import java.util.List;

public interface DMTHomeListeners {
    void setWholeRecycler(List<DmtUserData> list);
    void setErrorInFetch(String value);
    void initiateStart();
}
