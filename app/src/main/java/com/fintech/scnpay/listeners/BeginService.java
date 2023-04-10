package com.fintech.scnpay.listeners;

import com.fintech.scnpay.data.network.responses.BankItResponse;

public interface BeginService {
    void beginBankIt(BankItResponse response);
}
