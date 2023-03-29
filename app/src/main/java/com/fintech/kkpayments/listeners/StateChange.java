package com.fintech.kkpayments.listeners;

public interface StateChange {
    <T> void changeState(T data);
}
