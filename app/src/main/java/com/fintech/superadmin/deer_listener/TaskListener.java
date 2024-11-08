package com.fintech.superadmin.deer_listener;

public interface TaskListener<T> {
    void onLoading();
    void onResponse(T data);
    void onError(String message);
}
