package com.fintech.prepe.listeners;

import android.view.View;

import com.fintech.prepe.data.network.responses.DmtUserData;

public interface DMTAccountListener {
    void onAccountClick(View view, DmtUserData data);
    void onRefreshClick(View view, DmtUserData data);
    void onDeleteClick(View view, DmtUserData data);

}
