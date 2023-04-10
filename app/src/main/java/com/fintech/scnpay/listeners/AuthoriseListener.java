package com.fintech.scnpay.listeners;

public interface AuthoriseListener<T> {
    void onAuth(T data);
}
