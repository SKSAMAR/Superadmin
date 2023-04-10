package com.fintech.scnpay.listeners;

public interface StateChange {
    <T> void changeState(T data);
}
