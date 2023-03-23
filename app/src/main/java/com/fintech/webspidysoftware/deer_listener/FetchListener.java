package com.fintech.webspidysoftware.deer_listener;

import android.content.Intent;

public interface FetchListener<T> {

    void getData(Intent intent);
    void passData(T data);
}
