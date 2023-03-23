package com.fintech.payware.activities.tobank;

import android.os.Bundle;
import android.view.MenuItem;

import androidx.appcompat.app.ActionBar;
import androidx.lifecycle.ViewModelProvider;

import com.fintech.payware.activities.common.BaseActivity;
import com.fintech.payware.databinding.ActivityQueueryRemitterBinding;
import com.fintech.payware.util.ViewUtils;
import com.fintech.payware.viewmodel.ToBankViewModel;

import java.util.Objects;

import dagger.hilt.android.AndroidEntryPoint;

@AndroidEntryPoint
public class QueryRemitter extends BaseActivity {

    ActivityQueueryRemitterBinding binding;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityQueueryRemitterBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        Objects.requireNonNull(getSupportActionBar()).setDisplayHomeAsUpEnabled(true);
        ActionBar actionBar = getSupportActionBar();
        actionBar.setTitle("Sender Verification");
        ToBankViewModel viewModel = new ViewModelProvider(this).get(ToBankViewModel.class);
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