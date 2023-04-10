package com.fintech.scnpay.activities.rechargeactivities;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.SearchView;

import androidx.appcompat.app.ActionBar;
import com.fintech.scnpay.activities.common.BaseActivity;
import androidx.lifecycle.ViewModelProvider;

import com.fintech.scnpay.adapters.ROfferAdapter;
import com.fintech.scnpay.data.model.OperatorModel;
import com.fintech.scnpay.data.network.responses.CustomerInfoResponse;
import com.fintech.scnpay.data.network.responses.MyOfferResponse;
import com.fintech.scnpay.data.network.responses.ROfferModel;
import com.fintech.scnpay.databinding.ActivityRofferBinding;
import com.fintech.scnpay.listeners.ROfferListener;
import com.fintech.scnpay.listeners.RegularClick;
import com.fintech.scnpay.util.MyAlertUtils;
import com.fintech.scnpay.viewmodel.MobileRechargeViewModel;

import java.util.List;
import java.util.Objects;

import dagger.hilt.android.AndroidEntryPoint;

@AndroidEntryPoint
public class ROffer extends BaseActivity implements ROfferListener, RegularClick {

    ActivityRofferBinding binding;
    ROfferAdapter adapter;
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
        viewModel= new ViewModelProvider(this).get(MobileRechargeViewModel.class);
        binding.setROfferViewModel(viewModel);
        viewModel.operatorModel = (OperatorModel) getIntent().getSerializableExtra("operatorModel");
        String num = getIntent().getStringExtra("num");
        String type = getIntent().getStringExtra("type");
        bringROffer(viewModel.operatorModel.getOperatorcode(), num, type);
        searchAble();
    }


    private void bringROffer(String op, String num, String type){
            if(type.equals("mobile_r")){


//                viewModel.mobileRechargesRepository.getMeMyROffer(ROffer.this, op, num, ROffer.this, type);
            }
            else if(type.equals("dth_rOffer")){


//                viewModel.mobileRechargesRepository.getMeMyDthROffer(ROffer.this, op, num, ROffer.this, type);
            }
    }

    @Override
    public void onStartLooking() {
        MyAlertUtils.showProgressAlertDialog(ROffer.this);
    }

    @Override
    public void getMeROffer(MyOfferResponse response) {
        if(response!=null){
            if(response.getCode()!=0){
                //set
                MyAlertUtils.dismissAlertDialog();
                setAdapter(response.getOffers());
            }
            else{
                MyAlertUtils.showServerAlertDialog(ROffer.this, response.getMessage());
            }
        }
    }

    @Override
    public void onCausedError(String message) {
        MyAlertUtils.showServerAlertDialog(ROffer.this, message);
    }

    @Override
    public void onCustomerInfo(CustomerInfoResponse customerInfoResponse) {

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

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == android.R.id.home) {// todo: goto back activity from here
            onBackPressed();
            finish();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    private void setAdapter(List<ROfferModel> list){
//        adapter = new ROfferAdapter(list, this);
//        binding.allContactsRecycler.setLayoutManager(new GridLayoutManager(ROffer.this, 1, GridLayoutManager.VERTICAL, false));
//        binding.allContactsRecycler.setAdapter(adapter);
    }

    @Override
    public void onClickItem(View view, String data) {
        Intent intentData = new Intent();
        intentData.putExtra("Price",data);
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
}