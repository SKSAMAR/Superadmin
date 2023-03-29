package com.fintech.prepe.activities.aeps;

import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;

import androidx.appcompat.app.ActionBar;
import androidx.lifecycle.ViewModelProvider;

import com.fintech.prepe.R;
import com.fintech.prepe.databinding.ActivityBalanceEnquiryBinding;
import com.fintech.prepe.listeners.BankNameListener;
import com.fintech.prepe.listeners.ResetListener;
import com.fintech.prepe.util.Accessable;
import com.fintech.prepe.util.ExecuteUtil;
import com.fintech.prepe.util.MyAlertUtils;
import com.fintech.prepe.util.PopupUtil;
import com.fintech.prepe.util.StartGettingLocation;
import com.fintech.prepe.util.UtilHolder;
import com.fintech.prepe.util.ViewUtils;
import com.fintech.prepe.viewmodel.AepsViewModel;

import java.util.Objects;

import dagger.hilt.android.AndroidEntryPoint;

@AndroidEntryPoint
public class BalanceEnquiry extends BaseFingerActivity implements BankNameListener, ResetListener {

    ActivityBalanceEnquiryBinding binding;
    private AepsViewModel viewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityBalanceEnquiryBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        Objects.requireNonNull(getSupportActionBar()).setDisplayHomeAsUpEnabled(true);
        ActionBar actionBar = getSupportActionBar();
        actionBar.setTitle("Balance Enquiry");
        viewModel = new ViewModelProvider(this).get(AepsViewModel.class);
        binding.setBalanceEnquiryViewModel(viewModel);
        viewModel.bankNameListener = this;
        setListeners();
        ViewUtils.setFocusable(binding.aadhaarNumber, BalanceEnquiry.this);
        StartGettingLocation.setAllTheLocations(BalanceEnquiry.this);
    }

    private void setListeners() {
        binding.captureFingerPrintButton.setOnClickListener(v -> {

            if(Objects.requireNonNull(binding.aadhaarNumber.getText()).toString().length()!=12){
                MyAlertUtils.showAlertDialog(BalanceEnquiry.this,"Warning","Enter a valid Aadhaar Number", R.drawable.warning);
            }
            else if(Objects.requireNonNull(binding.mobileNumber.getText()).toString().length()!=10){
                MyAlertUtils.showAlertDialog(BalanceEnquiry.this,"Warning","Enter a valid Mobile Number", R.drawable.warning);
            }
            else if(Objects.requireNonNull(binding.selectedBankName.getText()).toString().isEmpty()){
                MyAlertUtils.showAlertDialog(BalanceEnquiry.this,"Warning","Select Your Bank", R.drawable.warning);
            }
            else{
                if(PopupUtil.access){
                    captureFingerBegin();
                    //PopupUtil.access = false;
                }else{
                    PopupUtil.tPinSystem(BalanceEnquiry.this, viewModel.aepsRepository.apiServices);
                }
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
    public void fingerError(String error) {
        super.fingerError(error);
        ViewUtils.showToast(BalanceEnquiry.this, "Error "+error);

    }

    @Override
    public void fingerPrintCaptured(String result) {
        super.fingerPrintCaptured(result);
        startService(result);
    }

    private void startService(String fingerprint){
         if(Accessable.isAccessable()){
            viewModel.startAEPSServices(BalanceEnquiry.this, Objects.requireNonNull(binding.aadhaarNumber.getText()).toString(),fingerprint, Objects.requireNonNull(binding.mobileNumber.getText()).toString(), "BE", UtilHolder.getLongitude(), UtilHolder.getLatitude(),"0", BalanceEnquiry.this);
        }
    }


    @Override
    public void setBankName(String bankName) {
        binding.selectedBankName.setText(bankName);
    }

    @Override
    public void resetRequiredData(boolean result) {
        binding.aadhaarNumber.setText("");
        binding.mobileNumber.setText("");
    }
}