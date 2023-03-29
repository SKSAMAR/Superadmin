package com.fintech.kkpayments.listeners;

import com.fintech.kkpayments.data.network.responses.PaymentResponse;

public interface PaymentListener {

    public void paymentResult(PaymentResponse paymentResponse);
    public void paymentMessage(String message);

    public void errorResult(String error);
}
