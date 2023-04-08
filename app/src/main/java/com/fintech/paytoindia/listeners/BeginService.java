package com.fintech.paytoindia.listeners;

import com.fintech.paytoindia.data.network.responses.BankItResponse;

public interface BeginService {
    void beginBankIt(BankItResponse response);
}
