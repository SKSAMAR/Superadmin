package com.fintech.prepe.listeners;

import com.fintech.prepe.data.network.responses.BankItResponse;

public interface BeginService {
    void beginBankIt(BankItResponse response);
}
