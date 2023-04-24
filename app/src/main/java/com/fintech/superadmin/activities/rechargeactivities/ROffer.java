package com.fintech.superadmin.activities.rechargeactivities;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.SearchView;

import androidx.appcompat.app.ActionBar;

import com.fintech.superadmin.activities.common.BaseActivity;

import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.GridLayoutManager;

import com.fintech.superadmin.adapters.DynamicROfferAdapter;
import com.fintech.superadmin.adapters.ROfferAdapter;
import com.fintech.superadmin.data.ROfferPlan;
import com.fintech.superadmin.data.model.OperatorModel;
import com.fintech.superadmin.data.network.responses.CustomerInfoResponse;
import com.fintech.superadmin.data.network.responses.MyOfferResponse;
import com.fintech.superadmin.data.network.responses.ROfferModel;
import com.fintech.superadmin.databinding.ActivityRofferBinding;
import com.fintech.superadmin.listeners.BrowsePlanListener;
import com.fintech.superadmin.listeners.DynamicROfferListener;
import com.fintech.superadmin.listeners.ROfferListener;
import com.fintech.superadmin.listeners.RegularClick;
import com.fintech.superadmin.util.MyAlertUtils;
import com.fintech.superadmin.viewmodel.MobileRechargeViewModel;

import java.util.List;
import java.util.Objects;

import dagger.hilt.android.AndroidEntryPoint;

@AndroidEntryPoint
public class ROffer extends BaseActivity implements DynamicROfferListener, RegularClick, BrowsePlanListener {

    ActivityRofferBinding binding;
    DynamicROfferAdapter adapter;
    MobileRechargeViewModel viewModel;

    ActionBar actionBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityRofferBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        Objects.requireNonNull(getSupportActionBar()).setDisplayHomeAsUpEnabled(true);
        actionBar = getSupportActionBar();
        actionBar.setTitle("R Offer");
        viewModel = new ViewModelProvider(this).get(MobileRechargeViewModel.class);
        binding.setROfferViewModel(viewModel);
        viewModel.operatorModel = (OperatorModel) getIntent().getSerializableExtra("operatorModel");
        String num = getIntent().getStringExtra("num");
        String type = getIntent().getStringExtra("type");
        bringROffer(viewModel.operatorModel.getOperatorcode(), num);
        searchAble();
    }


    private void bringROffer(String op, String num) {
        viewModel.mode = getIntent().getStringExtra("mode");
        String service_type = "1";
        if (viewModel.mode.equals("dth")) {
            service_type = "2";
        }
        viewModel.mobileRechargesRepository.getMeMyROffer(service_type, ROffer.this, op, num, ROffer.this);
    }

    @Override
    public void onStartLooking() {
        MyAlertUtils.showProgressAlertDialog(ROffer.this);
    }


    @Override
    public void getMeROffer(List<ROfferPlan> response) {
        MyAlertUtils.dismissAlertDialog();
        setAdapter(response);
    }

    @Override
    public void onCausedError(String message) {
        MyAlertUtils.showServerAlertDialog(ROffer.this, message);
    }

    @Override
    public void onCustomerInfo(CustomerInfoResponse customerInfoResponse) {

    }

    public void searchAble() {
        binding.planSearchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                adapter.getFilter().filter(query);
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                adapter.getFilter().filter(newText);
                return false;
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

    private void setAdapter(List<ROfferPlan> list) {
        adapter = new DynamicROfferAdapter(list, this, this);
        binding.allContactsRecycler.setLayoutManager(new GridLayoutManager(ROffer.this, 1, GridLayoutManager.VERTICAL, false));
        binding.allContactsRecycler.setAdapter(adapter);
    }

    @Override
    public void onClickItem(View view, String data) {
        Intent intentData = new Intent();
        intentData.putExtra("Price", data);
        setResult(Activity.RESULT_OK, intentData);
        finish();
    }

    @Override
    public void onBackPressed() {
        // When the user hits the back button set the resultCode
        // as Activity.RESULT_CANCELED to indicate a failure
        setResult(Activity.RESULT_CANCELED);
        super.onBackPressed();
    }

    @Override
    public void notFoundListener(boolean result) {

    }
}