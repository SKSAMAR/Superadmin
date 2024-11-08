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

import com.fintech.superadmin.data.hlr.HLRResponse;
import com.google.android.material.tabs.TabLayout;
import com.fintech.superadmin.adapters.ROfferAdapter;
import com.fintech.superadmin.data.browseplan.Info;
import com.fintech.superadmin.data.browseplan.Plan;
import com.fintech.superadmin.data.model.OperatorModel;
import com.fintech.superadmin.databinding.ActivityBrowseplanBinding;
import com.fintech.superadmin.viewmodel.MobileRechargeViewModel;

import java.util.List;
import java.util.Objects;

import dagger.hilt.android.AndroidEntryPoint;

@AndroidEntryPoint
public class BrowsePlan extends BaseActivity {

    MobileRechargeViewModel viewModel;
    public String amount = null;
    ActivityBrowseplanBinding binding;
    ActionBar actionBar;
    public String num;
    ROfferAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityBrowseplanBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        Objects.requireNonNull(getSupportActionBar()).setDisplayHomeAsUpEnabled(true);
        actionBar = getSupportActionBar();
        actionBar.setTitle("Browse Plan");
        viewModel = new ViewModelProvider(this).get(MobileRechargeViewModel.class);
        viewModel.mode = getIntent().getStringExtra("mode");
        viewModel.operatorModel = (OperatorModel) getIntent().getSerializableExtra("operatorModel");
        viewModel.hlrResponse = (HLRResponse)getIntent().getSerializableExtra("hlrResponse");
        num = getIntent().getStringExtra("num");
        viewModel.getBrowsePlan(BrowsePlan.this)
                .observe(this, data->{
                    if(data!=null){
                        setAdapter(data.info.fULLTT);
                        myTabListener(data.info);
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

    void myTabListener(Info info){
        binding.daysTab.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                String options = Objects.requireNonNull(tab.getText()).toString();
                switch(options){
                    case "FULLTT": setAdapter(info.fULLTT);
                        break;
                    case "TOPUP": setAdapter(info.tOPUP);
                        break;
                    case "3G/4G": setAdapter(info.g4G);
                        break;
                    case "Rate Cutter": setAdapter(info.rATECUTTER);
                        break;
                    case "2G": setAdapter(info.g);
                        break;
                    case "Roaming": setAdapter(info.romaing);
                        break;
                    case "Local": setAdapter(info.lOCAL);
                        break;
                    case "SMS": setAdapter(info.sMS);
                        break;
                    case "Other": setAdapter(info.oTher);
                        break;
                    case "Combo": setAdapter(info.cOMBO);
                        break;
                }
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {

            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {

            }
        });
    }



    private void setAdapter(List<Plan> plans){
        if(plans==null){
            binding.noOperators.setVisibility(View.VISIBLE);
        }
        else{
            binding.noOperators.setVisibility(View.GONE);
        }
        adapter = new ROfferAdapter(plans, (view, data) -> {
            Intent intentData = new Intent();
            intentData.putExtra("Price", data);
            setResult(Activity.RESULT_OK, intentData);
            finish();
        }, result -> {
            if(result){
                binding.noOperators.setVisibility(View.VISIBLE);
            }
            else{
                binding.noOperators.setVisibility(View.GONE);
            }
        });
        binding.allContactsRecycler.setLayoutManager(new GridLayoutManager(BrowsePlan.this, 1, GridLayoutManager.VERTICAL, false));
        binding.allContactsRecycler.setAdapter(adapter);
        searchAble();
    }

    @Override
    public void onBackPressed() {
        // When the user hits the back button set the resultCode
        setResult(Activity.RESULT_CANCELED);
        super.onBackPressed();
    }

    public void searchAble(){
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

}