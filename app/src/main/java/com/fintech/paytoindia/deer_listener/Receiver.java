package com.fintech.paytoindia.deer_listener;

public interface Receiver<T> {
    void getData(T data);
}
