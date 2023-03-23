package com.fintech.payware.deer_listener;

public interface Receiver<T> {
    void getData(T data);
}
