package com.fintech.payware.masterListener;

public interface RecieverListener<T> {
    void SuccessfullyBrought(T data);
    void Error(String message);
}
