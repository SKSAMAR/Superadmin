package com.fintech.webspidysoftware.listeners;

public interface AuthoriseListener<T> {
    void onAuth(T data);
}
