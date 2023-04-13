package com.fintech.paytcash.deer_listener;

public interface TaskListener<T> {
    void onLoading();
    void onResponse(T data);
    void onError(String message);
}
