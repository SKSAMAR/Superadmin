package com.fintech.superadmin.listeners;

import com.fintech.superadmin.data.network.responses.AuthResponse;

public interface NumberPayListener {

    void isNumberValid(AuthResponse authResponse);

    void showMessage(String message);

    void showProgress(String message);
}
