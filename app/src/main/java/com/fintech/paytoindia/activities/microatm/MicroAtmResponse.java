package com.fintech.paytoindia.activities.microatm;

import androidx.appcompat.app.ActionBar;
import com.fintech.paytoindia.activities.common.BaseActivity;

import android.os.Bundle;
import android.view.MenuItem;

import com.fintech.paytoindia.data.model.MicroAtmModel;
import com.fintech.paytoindia.databinding.ActivityMicroAtmResponseBinding;

import java.util.Objects;

public class MicroAtmResponse extends BaseActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ActivityMicroAtmResponseBinding binding = ActivityMicroAtmResponseBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        MicroAtmModel microAtmModel = (MicroAtmModel) getIntent().getSerializableExtra("atmResponse");
        binding.setMicroAtmData(microAtmModel);
        Objects.requireNonNull(getSupportActionBar()).setDisplayHomeAsUpEnabled(true);
        ActionBar actionBar = getSupportActionBar();
        actionBar.setTitle("ATM Details");
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