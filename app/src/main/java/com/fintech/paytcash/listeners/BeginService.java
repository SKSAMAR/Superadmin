package com.fintech.paytcash.listeners;

import com.fintech.paytcash.data.network.responses.BankItResponse;

public interface BeginService {
    void beginBankIt(BankItResponse response);
}
