package com.fintech.webspidysoftware.deer_listener;

public interface DownloadListener {
    void inProgress();
    <T>void success(T data);
    void error(String message);
}
