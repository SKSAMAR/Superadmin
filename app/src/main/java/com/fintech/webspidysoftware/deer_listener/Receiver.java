package com.fintech.webspidysoftware.deer_listener;

public interface Receiver<T> {
    void getData(T data);
}
