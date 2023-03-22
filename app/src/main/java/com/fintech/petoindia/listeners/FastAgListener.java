package com.fintech.petoindia.listeners;
import com.fintech.petoindia.data.network.responses.OperatorResponse;

import java.util.List;

public interface FastAgListener {
    void setRecycler(List<OperatorResponse.OperatorData> data);
}
