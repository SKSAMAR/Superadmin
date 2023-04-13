package com.fintech.paytcash.deer_listener;

public interface Receiver<T> {
    void getData(T data);
}
