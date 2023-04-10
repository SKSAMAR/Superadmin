package com.fintech.scnpay.listeners;

import android.view.View;

import com.fintech.scnpay.data.model.ProfileListModel;

public interface ProfileListListener {
    void onListItemSelected(View view, ProfileListModel model);
}
