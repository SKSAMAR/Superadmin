package com.fintech.superadmin.listeners;

public interface StateChange {
    <T> void changeState(T data);
}
