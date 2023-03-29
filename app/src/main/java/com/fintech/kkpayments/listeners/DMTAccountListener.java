package com.fintech.kkpayments.listeners;

import android.view.View;

import com.fintech.kkpayments.data.network.responses.DmtUserData;

public interface DMTAccountListener {
    void onAccountClick(View view, DmtUserData data);
    void onRefreshClick(View view, DmtUserData data);
    void onDeleteClick(View view, DmtUserData data);

}
