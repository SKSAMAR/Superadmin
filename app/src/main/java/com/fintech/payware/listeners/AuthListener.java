package com.fintech.payware.listeners;

import com.fintech.payware.data.network.responses.AuthResponse;
import com.fintech.payware.data.network.responses.RegularResponse;

public interface AuthListener {
    void onStarted();
    void onSuccess(AuthResponse authResponse);
    void onFailure(String message);
    void onChangeFragmentPage(String fragmentName);
    void displayMessage(String message);
    void otp_verification(RegularResponse response);
}

