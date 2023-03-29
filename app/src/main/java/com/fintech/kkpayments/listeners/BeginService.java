package com.fintech.kkpayments.listeners;

import com.fintech.kkpayments.data.network.responses.BankItResponse;

public interface BeginService {
    void beginBankIt(BankItResponse response);
}
