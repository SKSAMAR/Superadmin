package com.fintech.petoindia.listeners;

import android.view.View;

import com.fintech.petoindia.data.model.ProfileListModel;

public interface ProfileListListener {
    void onListItemSelected(View view, ProfileListModel model);
}
