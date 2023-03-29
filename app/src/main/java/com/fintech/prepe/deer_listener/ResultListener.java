package com.fintech.prepe.deer_listener;

public interface ResultListener {
    void Success(boolean status, String result);
    void Error(String message);
}
