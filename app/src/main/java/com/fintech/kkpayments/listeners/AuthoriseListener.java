package com.fintech.kkpayments.listeners;

public interface AuthoriseListener<T> {
    void onAuth(T data);
}
