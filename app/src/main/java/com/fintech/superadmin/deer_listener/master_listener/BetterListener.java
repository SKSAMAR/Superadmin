package com.fintech.superadmin.deer_listener.master_listener;

public interface BetterListener {
    <T>void onAchieved(T data);
    void onFailure(String message);
}
