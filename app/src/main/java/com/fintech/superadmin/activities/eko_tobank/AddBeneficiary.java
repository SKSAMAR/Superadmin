package com.fintech.superadmin.activities.eko_tobank;

import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.MenuItem;

import androidx.appcompat.app.ActionBar;
import androidx.lifecycle.ViewModelProvider;

import com.fintech.superadmin.activities.common.BaseActivity;
import com.fintech.superadmin.data.model.EkoBankModel;
import com.fintech.superadmin.databinding.ActivityEkoAddBeneficiaryBinding;
import com.fintech.superadmin.listeners.RemitterListener;
import com.fintech.superadmin.util.ViewUtils;
import com.fintech.superadmin.viewmodel.EkoToBankViewModel;

import java.util.Objects;

import dagger.hilt.android.AndroidEntryPoint;

@AndroidEntryPoint
public class AddBeneficiary extends BaseActivity implements RemitterListener {

    ActivityEkoAddBeneficiaryBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding =  ActivityEkoAddBeneficiaryBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        Objects.requireNonNull(getSupportActionBar()).setDisplayHomeAsUpEnabled(true);
        ActionBar actionBar = getSupportActionBar();
        actionBar.setTitle("Add Bank Account");
        EkoToBankViewModel viewModel = new ViewModelProvider(this).get(EkoToBankViewModel.class);
        viewModel.globalSelectedMobile = getIntent().getStringExtra("number");
        viewModel.selectedBank = (EkoBankModel) getIntent().getParcelableExtra("selectedBankModel");
        binding.setBeneficiaryViewModel(viewModel);
        viewModel.remitterListener = this;
        watcher();
        ViewUtils.setFocusable(binding.accountNumber, AddBeneficiary.this);

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



    private void watcher(){
        binding.confirmAccountNumber.addTextChangedListener(new TextWatcher() {
            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

                if(binding.confirmAccountNumber.getText().toString().trim().length() == binding.accountNumber.getText().toString().trim().length()){
                    if(!Objects.requireNonNull(binding.confirmAccountNumber.getText()).toString().equals(Objects.requireNonNull(binding.accountNumber.getText()).toString())){
                        binding.confirmAccountNumber.setError("Account Numbers don't match");
                    }
                }
            }

            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
    }

    @Override
    public void dateSetter(String date) {
//        binding.beneDob.setText(date);
    }
}