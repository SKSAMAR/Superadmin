package com.fintech.petoindia.activities.tobank;

import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;

import androidx.appcompat.app.ActionBar;
import androidx.lifecycle.ViewModelProvider;

import com.fintech.petoindia.R;
import com.fintech.petoindia.activities.common.BaseActivity;
import com.fintech.petoindia.data.network.responses.BeneficiaryBank;
import com.fintech.petoindia.databinding.ActivitySendAmountDmtBinding;
import com.fintech.petoindia.listeners.PayoutListener;
import com.fintech.petoindia.listeners.SendAmountViewsListener;
import com.fintech.petoindia.util.ExecuteUtil;
import com.fintech.petoindia.util.ViewUtils;
import com.fintech.petoindia.viewmodel.ToBankViewModel;

import java.util.Objects;

import dagger.hilt.android.AndroidEntryPoint;

@AndroidEntryPoint
public class SendAmountDMT extends BaseActivity implements PayoutListener, SendAmountViewsListener {

    ActivitySendAmountDmtBinding binding;
    ToBankViewModel viewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivitySendAmountDmtBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        Objects.requireNonNull(getSupportActionBar()).setDisplayHomeAsUpEnabled(true);
        ActionBar actionBar = getSupportActionBar();
        actionBar.setTitle("Send");
        viewModel = new ViewModelProvider(this).get(ToBankViewModel.class);
        viewModel.globalSelectedMobile = getIntent().getStringExtra("number");
        viewModel.selectedBeneficiaryModel = (BeneficiaryBank) getIntent().getSerializableExtra("selectedBankModel");
        binding.setSendAmountViewModel(viewModel);
        viewModel.payoutListener = this;
        viewModel.sendAmountViewsListener = this;
        ViewUtils.setFocusable(binding.enteredAmount, SendAmountDMT.this);
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
    @Override
    public void onTransactionTypeChange(String string) {
        binding.transactionType.setText(string);
    }

    @Override
    public void eraseAmountText() {
        binding.enteredAmount.setText("");
    }
}