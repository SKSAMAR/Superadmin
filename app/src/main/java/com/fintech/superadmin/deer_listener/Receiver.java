package com.fintech.superadmin.deer_listener;

public interface Receiver<T> {
    void getData(T data);
}
