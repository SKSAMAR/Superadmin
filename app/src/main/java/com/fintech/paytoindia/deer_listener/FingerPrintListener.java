package com.fintech.paytoindia.deer_listener;

public interface FingerPrintListener {
    void captureFingerBegin();
    void checkDevice();
    void fingerPrintCaptured(String result);
    void fingerError(String error);
}
