package com.fintech.prepe.listeners;

import android.view.View;

import com.fintech.prepe.data.model.ProfileListModel;

public interface ProfileListListener {
    void onListItemSelected(View view, ProfileListModel model);
}
