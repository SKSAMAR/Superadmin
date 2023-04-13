package com.fintech.paytcash.listeners;

import com.fintech.paytcash.data.network.responses.PaymentResponse;

public interface PaymentListener {

    public void paymentResult(PaymentResponse paymentResponse);
    public void paymentMessage(String message);

    public void errorResult(String error);
}
