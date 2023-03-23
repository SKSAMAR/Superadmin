package com.fintech.webspidysoftware.deer_listener;

public interface ResultListener {
    void Success(boolean status, String result);
    void Error(String message);
}
