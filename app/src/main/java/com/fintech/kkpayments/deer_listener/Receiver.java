package com.fintech.kkpayments.deer_listener;

public interface Receiver<T> {
    void getData(T data);
}
