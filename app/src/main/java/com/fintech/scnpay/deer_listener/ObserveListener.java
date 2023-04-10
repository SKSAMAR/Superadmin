package com.fintech.scnpay.deer_listener;

public interface ObserveListener<T> {
    void onObserve(boolean status, T data);
}
