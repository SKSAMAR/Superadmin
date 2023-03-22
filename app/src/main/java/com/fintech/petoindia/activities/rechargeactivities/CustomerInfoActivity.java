package com.fintech.petoindia.activities.rechargeactivities;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;

import androidx.appcompat.app.ActionBar;
import com.fintech.petoindia.activities.common.BaseActivity;

import com.fintech.petoindia.data.network.responses.CustomerInfoModel;
import com.fintech.petoindia.databinding.ActivityCustomerInfoBinding;

import java.util.Objects;

public class CustomerInfoActivity extends BaseActivity {


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ActivityCustomerInfoBinding binding = ActivityCustomerInfoBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        Objects.requireNonNull(getSupportActionBar()).setDisplayHomeAsUpEnabled(true);
        ActionBar actionBar = getSupportActionBar();
        actionBar.setTitle("Customer Info");
        CustomerInfoModel model = (CustomerInfoModel)getIntent().getSerializableExtra("infoDetails");
        if(model!=null){
            binding.setCustomerInfoModel(model);
            binding.confirm.setVisibility(View.VISIBLE);
            binding.confirm.setOnClickListener(v -> {
                Intent intentData = new Intent();
                if(model.getLastrechargeamount()==null){
                    model.setLastrechargeamount("");
                }
                intentData.putExtra("Price",model.getMonthlyRecharge());
                setResult(Activity.RESULT_OK, intentData);
                finish();
            });
        }
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