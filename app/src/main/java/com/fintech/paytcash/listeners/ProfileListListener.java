package com.fintech.paytcash.listeners;

import android.view.View;

import com.fintech.paytcash.data.model.ProfileListModel;

public interface ProfileListListener {
    void onListItemSelected(View view, ProfileListModel model);
}
