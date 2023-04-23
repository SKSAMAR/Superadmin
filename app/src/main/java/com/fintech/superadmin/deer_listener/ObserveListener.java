package com.fintech.superadmin.deer_listener;

public interface ObserveListener<T> {
    void onObserve(boolean status, T data);
}
