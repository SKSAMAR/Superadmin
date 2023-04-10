package com.fintech.scnpay.listeners;

import com.fintech.scnpay.data.network.responses.PaymentResponse;

public interface PaymentListener {

    public void paymentResult(PaymentResponse paymentResponse);
    public void paymentMessage(String message);

    public void errorResult(String error);
}
