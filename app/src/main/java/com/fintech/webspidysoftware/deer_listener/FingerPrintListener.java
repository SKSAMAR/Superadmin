package com.fintech.webspidysoftware.deer_listener;

public interface FingerPrintListener {
    void captureFingerBegin();
    void checkDevice();
    void fingerPrintCaptured(String result);
    void fingerError(String error);
}
