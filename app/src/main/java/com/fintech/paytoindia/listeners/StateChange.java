package com.fintech.paytoindia.listeners;

public interface StateChange {
    <T> void changeState(T data);
}
