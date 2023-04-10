package com.fintech.scnpay.listeners;

import android.view.View;

import com.fintech.scnpay.data.network.responses.DmtUserData;

public interface DMTAccountListener {
    void onAccountClick(View view, DmtUserData data);
    void onRefreshClick(View view, DmtUserData data);
    void onDeleteClick(View view, DmtUserData data);

}
