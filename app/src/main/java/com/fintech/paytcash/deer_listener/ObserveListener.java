package com.fintech.paytcash.deer_listener;

public interface ObserveListener<T> {
    void onObserve(boolean status, T data);
}
