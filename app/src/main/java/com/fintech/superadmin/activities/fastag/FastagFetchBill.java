package com.fintech.superadmin.activities.fastag;

import android.os.Bundle;
import android.text.InputFilter;
import android.view.MenuItem;
import android.view.View;

import androidx.appcompat.app.ActionBar;
import androidx.lifecycle.ViewModelProvider;

import com.fintech.superadmin.activities.common.BaseActivity;
import com.fintech.superadmin.data.network.responses.OperatorResponse;
import com.fintech.superadmin.databinding.ActivityFastagFetchBillBinding;
import com.fintech.superadmin.listeners.ResetListener;
import com.fintech.superadmin.util.StartGettingLocation;
import com.fintech.superadmin.viewmodel.MobileRechargeViewModel;

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
        manageState();
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

    private void manageState(){
        if (viewModel.fastagOpperatorModel.getViewbill().trim().equals("1")){
            binding.fetchLicBill.setVisibility(View.VISIBLE);
            binding.directPayFasTag.setVisibility(View.GONE);
            binding.directPayAmount.setVisibility(View.GONE);
        }else{
            binding.fetchLicBill.setVisibility(View.GONE);
            binding.directPayFasTag.setVisibility(View.VISIBLE);
            binding.directPayAmount.setVisibility(View.VISIBLE);
        }
    }
}