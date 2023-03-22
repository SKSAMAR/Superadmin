package com.fintech.petoindia.listeners;

import com.fintech.petoindia.data.network.responses.AuthResponse;

public interface NumberPayListener {

    void isNumberValid(AuthResponse authResponse);

    void showMessage(String message);

    void showProgress(String message);
}
