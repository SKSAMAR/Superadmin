package com.fintech.prepe.deer_listener;

public interface Receiver<T> {
    void getData(T data);
}
