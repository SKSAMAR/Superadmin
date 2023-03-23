package com.fintech.webspidysoftware.listeners;

import android.view.View;

import com.fintech.webspidysoftware.data.model.ProfileListModel;

public interface ProfileListListener {
    void onListItemSelected(View view, ProfileListModel model);
}
