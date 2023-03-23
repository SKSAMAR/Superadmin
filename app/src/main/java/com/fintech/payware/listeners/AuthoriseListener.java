package com.fintech.payware.listeners;

public interface AuthoriseListener<T> {
    void onAuth(T data);
}
