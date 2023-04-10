package com.fintech.scnpay.deer_listener;

public interface Receiver<T> {
    void getData(T data);
}
