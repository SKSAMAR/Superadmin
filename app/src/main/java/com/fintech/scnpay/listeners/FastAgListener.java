package com.fintech.scnpay.listeners;
import com.fintech.scnpay.data.network.responses.OperatorResponse;

import java.util.List;

public interface FastAgListener {
    void setRecycler(List<OperatorResponse.OperatorData> data);
}
