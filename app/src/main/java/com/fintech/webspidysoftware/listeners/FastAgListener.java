package com.fintech.webspidysoftware.listeners;
import com.fintech.webspidysoftware.data.network.responses.OperatorResponse;

import java.util.List;

public interface FastAgListener {
    void setRecycler(List<OperatorResponse.OperatorData> data);
}
