package com.fintech.kkpayments.listeners;

import android.view.View;

import com.fintech.kkpayments.data.model.ProfileListModel;

public interface ProfileListListener {
    void onListItemSelected(View view, ProfileListModel model);
}
