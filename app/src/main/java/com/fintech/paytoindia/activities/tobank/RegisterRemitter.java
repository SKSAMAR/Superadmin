package com.fintech.paytoindia.activities.tobank;

import android.os.Bundle;
import android.view.MenuItem;

import androidx.appcompat.app.ActionBar;
import androidx.lifecycle.ViewModelProvider;

import com.fintech.paytoindia.activities.common.BaseActivity;
import com.fintech.paytoindia.databinding.ActivityRegisterRemitterBinding;
import com.fintech.paytoindia.listeners.RemitterListener;
import com.fintech.paytoindia.util.ViewUtils;
import com.fintech.paytoindia.viewmodel.ToBankViewModel;

import java.util.Objects;

import dagger.hilt.android.AndroidEntryPoint;

@AndroidEntryPoint
public class RegisterRemitter extends BaseActivity implements RemitterListener {

    ActivityRegisterRemitterBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityRegisterRemitterBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        Objects.requireNonNull(getSupportActionBar()).setDisplayHomeAsUpEnabled(true);
        ActionBar actionBar = getSupportActionBar();
        actionBar.setTitle("Add Sender");
        ToBankViewModel viewModel = new ViewModelProvider(this).get(ToBankViewModel.class);
        binding.setRegisterRemitter(viewModel);
        viewModel.remitterListener = this;
        ViewUtils.setFocusable(binding.firstName, RegisterRemitter.this);
        if (getIntent().getBooleanExtra("toUpdate", false)) {
             viewModel.str =  "111";
            viewModel.remitter_otp = "111";
        } else {
            viewModel.str =  ToBankViewModel.MyQueryRemitterResponse.getStateresp();
            viewModel.remitter_otp = getIntent().getStringExtra("enteredOTP");
        }

    }

    @Override
    public void dateSetter(String date) {
        binding.dateOfBirth.setText(date);
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