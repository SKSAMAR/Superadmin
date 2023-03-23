package com.fintech.payware.listeners;

import com.fintech.payware.data.network.responses.AuthResponse;

public interface NumberPayListener {

    void isNumberValid(AuthResponse authResponse);

    void showMessage(String message);

    void showProgress(String message);
}
