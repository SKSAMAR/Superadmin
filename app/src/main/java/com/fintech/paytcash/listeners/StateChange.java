package com.fintech.paytcash.listeners;

public interface StateChange {
    <T> void changeState(T data);
}
