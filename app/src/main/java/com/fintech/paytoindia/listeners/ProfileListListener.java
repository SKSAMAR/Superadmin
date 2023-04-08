package com.fintech.paytoindia.listeners;

import android.view.View;

import com.fintech.paytoindia.data.model.ProfileListModel;

public interface ProfileListListener {
    void onListItemSelected(View view, ProfileListModel model);
}
