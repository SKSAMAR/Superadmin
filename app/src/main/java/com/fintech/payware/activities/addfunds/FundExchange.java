package com.fintech.payware.activities.addfunds;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.view.MenuItem;

import androidx.appcompat.app.ActionBar;
import com.fintech.payware.activities.common.BaseActivity;
import androidx.lifecycle.ViewModelProvider;

import com.fintech.payware.data.db.AppDatabase;
import com.fintech.payware.databinding.ActivityFundExchangeBinding;
import com.fintech.payware.listeners.ResetListener;
import com.fintech.payware.viewmodel.FundViewModel;


import java.util.Objects;

import dagger.hilt.android.AndroidEntryPoint;


@AndroidEntryPoint
public class FundExchange extends BaseActivity implements ResetListener {

    FundViewModel viewModel;

    ActivityFundExchangeBinding binding;
    String aeps_bal;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityFundExchangeBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        Objects.requireNonNull(getSupportActionBar()).setDisplayHomeAsUpEnabled(true);
        ActionBar actionBar = getSupportActionBar();
        actionBar.setTitle("Fund Exchange");
        viewModel = new ViewModelProvider(this).get(FundViewModel.class);
        binding.setFundViewModel(viewModel);
        viewModel.resetListener = this;
        setLiveData();
    }

    @SuppressLint("SetTextI18n")
    private void setLiveData(){
        AppDatabase appDatabase = AppDatabase.getAppDatabase(FundExchange.this);
        appDatabase.getUserDao().getUser().observe(this, user -> {
            if(user!=null){
                binding.aepsBalText.setText("Settlement: "+ user.getAepsbalance());
                binding.mainBalText.setText("Main: "+ user.getMainbalance());
                aeps_bal = user.getAepsbalance();
            }
        });
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

    @Override
    public void resetRequiredData(boolean result) {
        binding.amountBalance.setText("");
    }
}