package com.fintech.petoindia.activities.fastag;

import android.os.Bundle;
import android.text.InputFilter;
import android.view.MenuItem;

import androidx.appcompat.app.ActionBar;
import androidx.lifecycle.ViewModelProvider;

import com.fintech.petoindia.activities.common.BaseActivity;
import com.fintech.petoindia.data.network.responses.OperatorResponse;
import com.fintech.petoindia.databinding.ActivityFastagFetchBillBinding;
import com.fintech.petoindia.listeners.ResetListener;
import com.fintech.petoindia.util.StartGettingLocation;
import com.fintech.petoindia.viewmodel.MobileRechargeViewModel;

import java.util.Objects;

import dagger.hilt.android.AndroidEntryPoint;

@AndroidEntryPoint
public class FastagFetchBill extends BaseActivity implements ResetListener {

    MobileRechargeViewModel viewModel;
    ActivityFastagFetchBillBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityFastagFetchBillBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        Objects.requireNonNull(getSupportActionBar()).setDisplayHomeAsUpEnabled(true);
        ActionBar actionBar = getSupportActionBar();
        actionBar.setTitle("Fetch FASTag Bill");
        viewModel = new ViewModelProvider(this).get(MobileRechargeViewModel.class);
        viewModel.resetListener = this;
        viewModel.fastagOpperatorModel  = (OperatorResponse.OperatorData) getIntent().getSerializableExtra("fastAgOP");
        binding.setFastagViewModel(viewModel);
        StartGettingLocation.setAllTheLocations(FastagFetchBill.this);
        binding.caNumber.setFilters(new InputFilter[] {new InputFilter.AllCaps()});
    }

    @Override
    public void resetRequiredData(boolean result) {
        binding.caNumber.setText("");
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