package com.fintech.paytcash.activities.common;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import com.fintech.paytcash.util.DisplayMessageUtil;
import com.fintech.paytcash.util.MyAlertUtils;

public class BaseActivity extends AppCompatActivity {

    public ActionBar actionBar;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        actionBar = getSupportActionBar();
    }

    @Override
    protected void onSaveInstanceState(@NonNull Bundle state) {
        super.onSaveInstanceState(state);

        // Save the state so that it can be restored in onCreate or onRestoreInstanceState
        if (MyAlertUtils.alertDialog != null) {
            state.putBoolean("dialog_showing", MyAlertUtils.alertDialog.isShowing());
        }
    }


    @Override
    public void onRestoreInstanceState(Bundle savedInstanceState) {
        super.onRestoreInstanceState(savedInstanceState);

        if(savedInstanceState.getBoolean("dialog_showing", false)) {
            MyAlertUtils.alertDialog.show();
        }
    }

    @Override
    protected void onPause() {
        super.onPause();
    }

    @Override
    protected void onResume() {
        super.onResume();
    }

    @Override
    protected void onStop() {
        super.onStop();
    }


    @Override
    protected void onDestroy() {
        DisplayMessageUtil.dismissDialog();
        super.onDestroy();
    }
}
