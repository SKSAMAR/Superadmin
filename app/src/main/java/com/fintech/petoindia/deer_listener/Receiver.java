package com.fintech.petoindia.deer_listener;

public interface Receiver<T> {
    void getData(T data);
}
