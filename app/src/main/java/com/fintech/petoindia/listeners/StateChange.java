package com.fintech.petoindia.listeners;

public interface StateChange {
    <T> void changeState(T data);
}
