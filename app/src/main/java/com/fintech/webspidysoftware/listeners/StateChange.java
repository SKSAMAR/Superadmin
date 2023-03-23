package com.fintech.webspidysoftware.listeners;

public interface StateChange {
    <T> void changeState(T data);
}
