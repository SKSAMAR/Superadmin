package com.fintech.superadmin.listeners;

public interface AuthoriseListener<T> {
    void onAuth(T data);
}
