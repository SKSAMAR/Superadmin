package com.fintech.paytcash.listeners;
import com.fintech.paytcash.data.network.responses.OperatorResponse;

import java.util.List;

public interface FastAgListener {
    void setRecycler(List<OperatorResponse.OperatorData> data);
}
