package com.fintech.paytcash.deer_listener;

public interface MasterListener {
    <T>void onAchievedResponse(T data);
    void onErrorReceived(String message);
    <T, W>void onTaskPerformer(T data, W work);
}
