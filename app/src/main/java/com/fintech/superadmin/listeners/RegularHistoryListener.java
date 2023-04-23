package com.fintech.superadmin.listeners;

import com.fintech.superadmin.data.network.responses.RegularHistoryResponse;
import com.fintech.superadmin.databinding.RegularHistoryDesignBinding;

import java.util.List;

public interface RegularHistoryListener {
    void bringTheHistory(List<RegularHistoryResponse.EveryDayData> data);
    void thereWasNoData();
    void onClickedData(RegularHistoryResponse.EveryDayData history);
    void onCheckStatusData(RegularHistoryResponse.EveryDayData history);
    void disableListener(RegularHistoryDesignBinding binding, RegularHistoryResponse.EveryDayData history);
    void onRefundClick(RegularHistoryResponse.EveryDayData history);
    void bringTheHistoryAgain();
}
