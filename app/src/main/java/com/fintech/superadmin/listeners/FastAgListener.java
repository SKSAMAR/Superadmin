package com.fintech.superadmin.listeners;
import com.fintech.superadmin.data.network.responses.OperatorResponse;

import java.util.List;

public interface FastAgListener {
    void setRecycler(List<OperatorResponse.OperatorData> data);
}
