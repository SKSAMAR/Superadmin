package com.fintech.payware.listeners;

import android.view.View;

import com.fintech.payware.data.model.ProfileListModel;

public interface ProfileListListener {
    void onListItemSelected(View view, ProfileListModel model);
}
