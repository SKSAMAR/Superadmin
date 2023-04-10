package com.fintech.scnpay.deer_listener;

import android.view.View;

public interface MyRecyclerListener<T> {
    void onItemPassed(View view, T data);
}
