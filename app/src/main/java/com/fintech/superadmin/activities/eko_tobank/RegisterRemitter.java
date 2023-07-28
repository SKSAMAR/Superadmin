package com.fintech.superadmin.activities.eko_tobank;

import android.os.Bundle;
import android.view.MenuItem;

import androidx.appcompat.app.ActionBar;
import androidx.lifecycle.ViewModelProvider;

import com.fintech.superadmin.activities.common.BaseActivity;
import com.fintech.superadmin.databinding.ActivityEkoRegisterRemitterBinding;
import com.fintech.superadmin.listeners.RemitterListener;
import com.fintech.superadmin.util.ViewUtils;
import com.fintech.superadmin.viewmodel.EkoToBankViewModel;

import java.util.Objects;

import dagger.hilt.android.AndroidEntryPoint;

@AndroidEntryPoint
public class RegisterRemitter extends BaseActivity implements RemitterListener {

    ActivityEkoRegisterRemitterBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityEkoRegisterRemitterBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        Objects.requireNonNull(getSupportActionBar()).setDisplayHomeAsUpEnabled(true);
        ActionBar actionBar = getSupportActionBar();
        actionBar.setTitle("Add Sender");
        EkoToBankViewModel viewModel = new ViewModelProvider(this).get(EkoToBankViewModel.class);
        binding.setRegisterRemitter(viewModel);
        viewModel.remitterListener = this;
        ViewUtils.setFocusable(binding.firstName, RegisterRemitter.this);

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