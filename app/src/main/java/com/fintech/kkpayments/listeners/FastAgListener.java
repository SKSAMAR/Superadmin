package com.fintech.kkpayments.listeners;
import com.fintech.kkpayments.data.network.responses.OperatorResponse;

import java.util.List;

public interface FastAgListener {
    void setRecycler(List<OperatorResponse.OperatorData> data);
}
