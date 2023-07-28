package com.fintech.superadmin.activities.eko_tobank;

import android.os.Bundle;
import android.view.MenuItem;

import androidx.appcompat.app.ActionBar;
import androidx.lifecycle.ViewModelProvider;

import com.fintech.superadmin.activities.common.BaseActivity;
import com.fintech.superadmin.databinding.ActivityEkoQueueryRemitterBinding;
import com.fintech.superadmin.util.ViewUtils;
import com.fintech.superadmin.viewmodel.EkoToBankViewModel;

import java.util.Objects;

import dagger.hilt.android.AndroidEntryPoint;

@AndroidEntryPoint
public class QueryRemitter extends BaseActivity {

    ActivityEkoQueueryRemitterBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityEkoQueueryRemitterBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        Objects.requireNonNull(getSupportActionBar()).setDisplayHomeAsUpEnabled(true);
        ActionBar actionBar = getSupportActionBar();
        actionBar.setTitle("Sender Verification");
        EkoToBankViewModel viewModel = new ViewModelProvider(this).get(EkoToBankViewModel.class);
        binding.setQueueryRemitter(viewModel);
        ViewUtils.setFocusable(binding.mobileNumber, QueryRemitter.this);

    }



    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == android.R.id.home) {// todo: goto back activity from here
            onBackPressed();
            finish();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

}