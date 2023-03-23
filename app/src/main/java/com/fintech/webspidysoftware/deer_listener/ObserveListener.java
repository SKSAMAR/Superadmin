package com.fintech.webspidysoftware.deer_listener;

public interface ObserveListener<T> {
    void onObserve(boolean status, T data);
}
