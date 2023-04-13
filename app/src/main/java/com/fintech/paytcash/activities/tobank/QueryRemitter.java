package com.fintech.paytcash.activities.tobank;

import android.os.Bundle;
import android.view.MenuItem;

import androidx.appcompat.app.ActionBar;
import androidx.lifecycle.ViewModelProvider;

import com.fintech.paytcash.activities.common.BaseActivity;
import com.fintech.paytcash.databinding.ActivityQueueryRemitterBinding;
import com.fintech.paytcash.util.ViewUtils;
import com.fintech.paytcash.viewmodel.ToBankViewModel;

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