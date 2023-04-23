package com.fintech.superadmin.listeners;

import android.view.View;

import com.fintech.superadmin.data.model.ProfileListModel;

public interface ProfileListListener {
    void onListItemSelected(View view, ProfileListModel model);
}
