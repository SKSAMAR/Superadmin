package com.fintech.prepe.deer_listener;

public interface NetworkResponse<T> {
    void onEmpty();
    void onLoading();
    void onSuccess(T data);
    void onError(String message);
}
