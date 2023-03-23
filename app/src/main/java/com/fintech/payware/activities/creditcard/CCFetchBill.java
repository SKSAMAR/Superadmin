package com.fintech.payware.activities.creditcard;

import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AlertDialog;
import androidx.compose.ui.platform.ComposeView;
import androidx.lifecycle.ViewModelProvider;

import com.fintech.payware.R;
import com.fintech.payware.activities.common.BaseActivity;
import com.fintech.payware.databinding.ActivityCcfetchBillBinding;
import com.fintech.payware.listeners.ResetListener;
import com.fintech.payware.listeners.VisibilityListener;
import com.fintech.payware.util.DisplayMessageUtil;
import com.fintech.payware.util.MyAlertUtils;
import com.fintech.payware.util.ViewUtils;
import com.fintech.payware.viewmodel.MobileRechargeViewModel;

import java.util.Objects;

import dagger.hilt.android.AndroidEntryPoint;

@AndroidEntryPoint
public class CCFetchBill extends BaseActivity implements ResetListener, VisibilityListener {

    MobileRechargeViewModel viewModel;
    ActivityCcfetchBillBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityCcfetchBillBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        Objects.requireNonNull(getSupportActionBar()).setDisplayHomeAsUpEnabled(true);
        ActionBar actionBar = getSupportActionBar();
        actionBar.setTitle("Credit Card");
        viewModel = new ViewModelProvider(this).get(MobileRechargeViewModel.class);
        binding.setCcViewModel(viewModel);
        viewModel.resetListener = this;
        viewModel.visibilityListener = this;
    }

    @Override
    public void resetRequiredData(boolean result) {
        binding.ccAmount.setText("");
        binding.ccMobile.setText("");
        binding.ccName.setText("");
        binding.ccNumber.setText("");
        binding.otp.setText("");
        binding.otpLayout.setVisibility(View.GONE);
        binding.sendOtp.setVisibility(View.VISIBLE);
        binding.payMoney.setVisibility(View.GONE);


        binding.ccAmount.setCursorVisible(true);
        binding.ccAmount.setFocusableInTouchMode(true);
        binding.ccAmount.setFocusable(true);
        binding.ccAmount.setClickable(true);

        binding.ccNumber.setCursorVisible(true);
        binding.ccNumber.setFocusableInTouchMode(true);
        binding.ccNumber.setFocusable(true);
        binding.ccNumber.setClickable(true);

        binding.ccMobile.setCursorVisible(true);
        binding.ccMobile.setFocusableInTouchMode(true);
        binding.ccMobile.setFocusable(true);
        binding.ccMobile.setClickable(true);

        binding.cardType.setCursorVisible(true);
        binding.cardType.setFocusableInTouchMode(true);
        binding.cardType.setFocusable(true);
        binding.cardType.setClickable(true);

        binding.ccName.setCursorVisible(true);
        binding.ccName.setFocusableInTouchMode(true);
        binding.ccName.setFocusable(true);
        binding.ccName.setClickable(true);

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
    public void startVisibility() {
        binding.otpLayout.setVisibility(View.VISIBLE);
        binding.sendOtp.setVisibility(View.GONE);
        binding.payMoney.setVisibility(View.VISIBLE);

        binding.ccAmount.setCursorVisible(false);
        binding.ccAmount.setFocusableInTouchMode(false);
        binding.ccAmount.setFocusable(false);
        binding.ccAmount.setClickable(false);

        binding.ccNumber.setCursorVisible(false);
        binding.ccNumber.setFocusableInTouchMode(false);
        binding.ccNumber.setFocusable(false);
        binding.ccNumber.setClickable(false);

        binding.ccMobile.setCursorVisible(false);
        binding.ccMobile.setFocusableInTouchMode(false);
        binding.ccMobile.setFocusable(false);
        binding.ccMobile.setClickable(false);

        binding.cardType.setCursorVisible(false);
        binding.cardType.setFocusableInTouchMode(false);
        binding.cardType.setFocusable(false);
        binding.cardType.setClickable(false);

        binding.ccName.setCursorVisible(false);
        binding.ccName.setFocusableInTouchMode(false);
        binding.ccName.setFocusable(false);
        binding.ccName.setClickable(false);
        ViewUtils.setFocusable(binding.otp, CCFetchBill.this);
    }

    private void doSetupRefund(){
        DisplayMessageUtil.dismissDialog();
        MyAlertUtils.alertDialog = new AlertDialog.Builder(CCFetchBill.this).create();
        AlertDialog alertDialog = MyAlertUtils.alertDialog;
        alertDialog.setContentView(R.layout.credit_card_refund);
        ComposeView composeView = alertDialog.findViewById(R.id.composeView);


    }
}