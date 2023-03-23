package com.fintech.webspidysoftware.listeners;

import com.fintech.webspidysoftware.data.network.responses.BankItResponse;

public interface BeginService {
    void beginBankIt(BankItResponse response);
}
