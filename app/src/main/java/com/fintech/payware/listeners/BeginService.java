package com.fintech.payware.listeners;

import com.fintech.payware.data.network.responses.BankItResponse;

public interface BeginService {
    void beginBankIt(BankItResponse response);
}
