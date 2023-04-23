package com.fintech.superadmin.listeners;

import com.fintech.superadmin.data.network.responses.BankItResponse;

public interface BeginService {
    void beginBankIt(BankItResponse response);
}
