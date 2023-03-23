package com.fintech.webspidysoftware.masterListener;

public interface RecieverListener<T> {
    void SuccessfullyBrought(T data);
    void Error(String message);
}
