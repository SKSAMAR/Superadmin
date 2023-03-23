package com.fintech.payware.activities.payoutpaysprint;

import androidx.appcompat.app.ActionBar;
import com.fintech.payware.activities.common.BaseActivity;
import androidx.lifecycle.ViewModelProvider;

import android.os.Bundle;
import android.view.MenuItem;

import com.fintech.payware.databinding.ActivityPayoutAddAccountBinding;
import com.fintech.payware.viewmodel.PayoutViewModel;
import java.util.Objects;

import dagger.hilt.android.AndroidEntryPoint;

@AndroidEntryPoint
public class PayoutAddAccount extends BaseActivity {
    PayoutViewModel viewModel;
    ActivityPayoutAddAccountBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityPayoutAddAccountBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        Objects.requireNonNull(getSupportActionBar()).setDisplayHomeAsUpEnabled(true);
        ActionBar actionBar = getSupportActionBar();
        actionBar.setTitle("Add Account");
        viewModel = new ViewModelProvider(this).get(PayoutViewModel.class);
        binding.setPayoutViewModel(viewModel);
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