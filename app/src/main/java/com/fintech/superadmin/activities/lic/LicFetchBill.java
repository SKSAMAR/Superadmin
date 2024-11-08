package com.fintech.superadmin.activities.lic;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;

import com.fintech.superadmin.R;
import com.fintech.superadmin.activities.common.BaseActivity;
import com.fintech.superadmin.activities.sectionhistory.RegularHistory;
import com.fintech.superadmin.databinding.ActivityLicFetchBillBinding;
import com.fintech.superadmin.listeners.ResetListener;
import com.fintech.superadmin.util.StartGettingLocation;
import com.fintech.superadmin.viewmodel.MobileRechargeViewModel;

import java.util.Objects;

import dagger.hilt.android.AndroidEntryPoint;

@AndroidEntryPoint
public class LicFetchBill extends BaseActivity implements ResetListener {

    MobileRechargeViewModel viewModel;
    ActivityLicFetchBillBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityLicFetchBillBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        String title = getIntent().getStringExtra("title");
        Objects.requireNonNull(getSupportActionBar()).setTitle(title);
        Objects.requireNonNull(getSupportActionBar()).setDisplayHomeAsUpEnabled(true);
        viewModel = new ViewModelProvider(this).get(MobileRechargeViewModel.class);
        viewModel.resetListener = this;
        binding.setLicViewModel(viewModel);
        viewModel.LICMODE = getIntent().getStringExtra("LICMODE");
        StartGettingLocation.setAllTheLocations(LicFetchBill.this);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == android.R.id.home) {// todo: goto back activity from here
            onBackPressed();
            finish();
            return true;
        }
        else if(item.getItemId() == R.id.history_check){
            Intent intent = new Intent(LicFetchBill.this, RegularHistory.class);
            intent.putExtra("section", "lic");
            startActivity(intent);
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.history_menu, menu);
        return super.onCreateOptionsMenu(menu);
    }


    @Override
    public void resetRequiredData(boolean result) {
        binding.consumerEmail.setText("");
        binding.consumerId.setText("");
    }
}