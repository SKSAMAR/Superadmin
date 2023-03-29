package com.fintech.kkpayments.listeners;

import com.fintech.kkpayments.data.network.responses.AuthResponse;

public interface NumberPayListener {

    void isNumberValid(AuthResponse authResponse);

    void showMessage(String message);

    void showProgress(String message);
}
