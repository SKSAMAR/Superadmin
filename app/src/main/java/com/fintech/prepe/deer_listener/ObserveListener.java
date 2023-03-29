package com.fintech.prepe.deer_listener;

public interface ObserveListener<T> {
    void onObserve(boolean status, T data);
}
