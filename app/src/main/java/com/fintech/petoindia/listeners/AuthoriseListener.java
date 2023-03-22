package com.fintech.petoindia.listeners;

public interface AuthoriseListener<T> {
    void onAuth(T data);
}
