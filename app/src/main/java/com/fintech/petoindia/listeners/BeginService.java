package com.fintech.petoindia.listeners;

import com.fintech.petoindia.data.network.responses.BankItResponse;

public interface BeginService {
    void beginBankIt(BankItResponse response);
}
