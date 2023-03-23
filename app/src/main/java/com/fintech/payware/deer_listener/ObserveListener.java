package com.fintech.payware.deer_listener;

public interface ObserveListener<T> {
    void onObserve(boolean status, T data);
}
