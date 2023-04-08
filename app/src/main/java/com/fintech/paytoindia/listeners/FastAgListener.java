package com.fintech.paytoindia.listeners;
import com.fintech.paytoindia.data.network.responses.OperatorResponse;

import java.util.List;

public interface FastAgListener {
    void setRecycler(List<OperatorResponse.OperatorData> data);
}
