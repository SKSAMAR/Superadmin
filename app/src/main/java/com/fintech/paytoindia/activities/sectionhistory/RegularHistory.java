package com.fintech.paytoindia.activities.sectionhistory;

import android.graphics.Color;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;

import androidx.appcompat.app.ActionBar;
import com.fintech.paytoindia.activities.common.BaseActivity;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.GridLayoutManager;

import com.fintech.paytoindia.adapters.RegularHistoryAdapter;
import com.fintech.paytoindia.data.network.responses.RegularHistoryResponse;
import com.fintech.paytoindia.databinding.ActivityRegularHistoryBinding;
import com.fintech.paytoindia.databinding.RegularHistoryDesignBinding;
import com.fintech.paytoindia.listeners.RegularHistoryListener;
import com.fintech.paytoindia.util.Accessable;
import com.fintech.paytoindia.viewmodel.MobileRechargeViewModel;

import java.util.List;
import java.util.Objects;

import dagger.hilt.android.AndroidEntryPoint;

@AndroidEntryPoint
public class RegularHistory extends BaseActivity implements RegularHistoryListener {

    MobileRechargeViewModel viewModel;
    ActivityRegularHistoryBinding binding;
    String section = "";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityRegularHistoryBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        Objects.requireNonNull(getSupportActionBar()).setDisplayHomeAsUpEnabled(true);
        ActionBar actionBar = getSupportActionBar();
        actionBar.setTitle("History");
        section = getIntent().getStringExtra("section");
        viewModel = new ViewModelProvider(this).get(MobileRechargeViewModel.class);
        int search_plateId = getResources().getIdentifier("android:id/search_plate", null, null);
        View mSearchPlate = binding.searchView.findViewById(search_plateId);
        mSearchPlate.setBackgroundColor(Color.TRANSPARENT);
        viewModel.mobileRechargesRepository.bringUsualHistory(RegularHistory.this,RegularHistory.this, section);
        viewModel.mobileRechargesRepository.regularHistoryListener = this;
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
    public void bringTheHistory(List<RegularHistoryResponse.EveryDayData> data) {
        binding.HistorySearchCard.setVisibility(View.VISIBLE);
        RegularHistoryAdapter adapter = new RegularHistoryAdapter(data, RegularHistory.this);
        binding.historyRecycler.setLayoutManager(new GridLayoutManager(RegularHistory.this, 1, GridLayoutManager.VERTICAL, false));
        binding.historyRecycler.setAdapter(adapter);
        binding.searchView.setOnQueryTextListener(new android.widget.SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String s) {
                adapter.getFilter().filter(s);
                return false;
            }

            @Override
            public boolean onQueryTextChange(String s) {
                adapter.getFilter().filter(s);
                return false;
            }
        });
    }

    @Override
    public void thereWasNoData() {
        binding.noRecords.setVisibility(View.VISIBLE);
        binding.historyRecycler.setVisibility(View.GONE);
        binding.HistorySearchCard.setVisibility(View.GONE);
    }

    @Override
    public void onClickedData(RegularHistoryResponse.EveryDayData history) {

    }

    @Override
    public void onCheckStatusData(RegularHistoryResponse.EveryDayData history) {

        if(section.equals("payoutdeposit")){
            if(Accessable.isAccessable()){
                viewModel.mobileRechargesRepository.cfPayoutCheckStatus(RegularHistory.this, history.getReference());
            }
        }
        else{
            viewModel.mobileRechargesRepository.checkHistoryStatusRegular(RegularHistory.this, section + "status", history.getReference());
        }
    }

    @Override
    public void disableListener(RegularHistoryDesignBinding binding, RegularHistoryResponse.EveryDayData history) {

    }

    @Override
    public void onRefundClick(RegularHistoryResponse.EveryDayData history) {
        if(section.equals("creditcard")){
            //viewModel.sendOTPForRefundCreditCard(RegularHistory.this, history.getReference());
        }
    }

    @Override
    public void bringTheHistoryAgain() {
        viewModel.mobileRechargesRepository.bringUsualHistory(RegularHistory.this,RegularHistory.this, section);
    }
}