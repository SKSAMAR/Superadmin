package com.fintech.webspidysoftware.listeners;

import com.fintech.webspidysoftware.data.network.responses.AuthResponse;

public interface NumberPayListener {

    void isNumberValid(AuthResponse authResponse);

    void showMessage(String message);

    void showProgress(String message);
}
