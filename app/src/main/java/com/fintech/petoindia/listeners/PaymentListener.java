package com.fintech.petoindia.listeners;

import com.fintech.petoindia.data.network.responses.PaymentResponse;

public interface PaymentListener {

    public void paymentResult(PaymentResponse paymentResponse);
    public void paymentMessage(String message);

    public void errorResult(String error);
}
