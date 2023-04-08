package com.fintech.paytoindia.listeners;

public interface AuthoriseListener<T> {
    void onAuth(T data);
}
