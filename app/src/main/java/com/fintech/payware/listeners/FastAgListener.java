package com.fintech.payware.listeners;
import com.fintech.payware.data.network.responses.OperatorResponse;

import java.util.List;

public interface FastAgListener {
    void setRecycler(List<OperatorResponse.OperatorData> data);
}
