package com.fintech.superadmin.listeners;

import com.fintech.superadmin.data.network.responses.PaymentResponse;

public interface PaymentListener {

    public void paymentResult(PaymentResponse paymentResponse);
    public void paymentMessage(String message);

    public void errorResult(String error);
}
