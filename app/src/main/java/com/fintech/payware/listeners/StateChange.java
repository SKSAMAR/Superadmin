package com.fintech.payware.listeners;

public interface StateChange {
    <T> void changeState(T data);
}
