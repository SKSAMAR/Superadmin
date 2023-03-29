package com.fintech.kkpayments.deer_listener;

public interface ObserveListener<T> {
    void onObserve(boolean status, T data);
}
