package com.fintech.paytcash.listeners;

public interface AuthoriseListener<T> {
    void onAuth(T data);
}
