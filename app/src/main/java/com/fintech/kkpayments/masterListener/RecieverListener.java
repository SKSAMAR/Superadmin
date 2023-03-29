package com.fintech.kkpayments.masterListener;

public interface RecieverListener<T> {
    void SuccessfullyBrought(T data);
    void Error(String message);
}
