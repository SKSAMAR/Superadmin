package com.fintech.prepe.listeners;

public interface StateChange {
    <T> void changeState(T data);
}
