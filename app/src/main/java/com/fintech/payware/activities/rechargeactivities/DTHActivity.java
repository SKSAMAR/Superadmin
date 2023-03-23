package com.fintech.payware.activities.rechargeactivities;

import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;

import androidx.appcompat.app.ActionBar;
import com.fintech.payware.activities.common.BaseActivity;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.RecyclerView;

import com.fintech.payware.R;
import com.fintech.payware.data.model.OperatorModel;
import com.fintech.payware.util.ExecuteUtil;
import com.fintech.payware.viewmodel.MobileRechargeViewModel;

import java.util.Objects;

import dagger.hilt.android.AndroidEntryPoint;

@AndroidEntryPoint
public class DTHActivity extends BaseActivity {

    MobileRechargeViewModel viewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dthactivity);
        Objects.requireNonNull(getSupportActionBar()).setDisplayHomeAsUpEnabled(true);
        ActionBar actionBar = getSupportActionBar();
        actionBar.setTitle("Dth Info");
        viewModel = new ViewModelProvider(this).get(MobileRechargeViewModel.class);
        RecyclerView recyclerView = findViewById(R.id.dth_info_recycler);
        viewModel.operatorModel = (OperatorModel) getIntent().getSerializableExtra("operatorModel");
        viewModel.mobileNumber.setValue(getIntent().getStringExtra("ca"));
        viewModel.findCustomerInfoDth(DTHActivity.this);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == android.R.id.home) {// todo: goto back activity from here
            onBackPressed();
            finish();
            return true;
        }
        else if(item.getItemId() == R.id.homePage){
            ExecuteUtil.ThrowOut(this);
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.response_menu, menu);
        menu.getItem(0).setVisible(false);
        return super.onCreateOptionsMenu(menu);
    }
}