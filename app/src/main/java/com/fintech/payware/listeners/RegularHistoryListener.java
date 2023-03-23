package com.fintech.payware.listeners;

import com.fintech.payware.data.network.responses.RegularHistoryResponse;
import com.fintech.payware.databinding.RegularHistoryDesignBinding;

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
