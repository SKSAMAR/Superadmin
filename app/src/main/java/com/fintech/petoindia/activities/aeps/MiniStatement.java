package com.fintech.petoindia.activities.aeps;

import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;

import androidx.appcompat.app.ActionBar;
import androidx.lifecycle.ViewModelProvider;

import com.fintech.petoindia.R;
import com.fintech.petoindia.data.db.AppDatabase;
import com.fintech.petoindia.data.db.entities.User;
import com.fintech.petoindia.databinding.ActivityMiniStatementBinding;
import com.fintech.petoindia.listeners.BankNameListener;
import com.fintech.petoindia.listeners.ResetListener;
import com.fintech.petoindia.util.Accessable;
import com.fintech.petoindia.util.ExecuteUtil;
import com.fintech.petoindia.util.MyAlertUtils;
import com.fintech.petoindia.util.PopupUtil;
import com.fintech.petoindia.util.StartGettingLocation;
import com.fintech.petoindia.util.UtilHolder;
import com.fintech.petoindia.util.ViewUtils;
import com.fintech.petoindia.viewmodel.AepsViewModel;

import java.util.Objects;

import dagger.hilt.android.AndroidEntryPoint;

@AndroidEntryPoint
public class MiniStatement extends BaseFingerActivity implements BankNameListener, ResetListener {
    
    ActivityMiniStatementBinding binding;
    AepsViewModel viewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityMiniStatementBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        Objects.requireNonNull(getSupportActionBar()).setDisplayHomeAsUpEnabled(true);
        ActionBar actionBar = getSupportActionBar();
        actionBar.setTitle("Mini Statement");
        viewModel = new ViewModelProvider(this).get(AepsViewModel.class);
        binding.setMiniStatementViewModel(viewModel);
        viewModel.bankNameListener = this;
        setListeners();
        ViewUtils.setFocusable(binding.aadhaarNumber, MiniStatement.this);
        StartGettingLocation.setAllTheLocations(MiniStatement.this);
    }


    private void setListeners() {
        binding.captureFingerPrintButton.setOnClickListener(v -> {

            if(Objects.requireNonNull(binding.aadhaarNumber.getText()).toString().length()!=12){
                MyAlertUtils.showAlertDialog(MiniStatement.this,"Warning","Enter a valid Aadhaar Number", R.drawable.warning);
            }
            else if(Objects.requireNonNull(binding.mobileNumber.getText()).toString().length()!=10){
                MyAlertUtils.showAlertDialog(MiniStatement.this,"Warning","Enter a valid Mobile Number", R.drawable.warning);
            }
            else if(Objects.requireNonNull(binding.selectedBankName.getText()).toString().isEmpty()){
                MyAlertUtils.showAlertDialog(MiniStatement.this,"Warning","Select Your Bank", R.drawable.warning);
            }
            else{
                if(PopupUtil.access){
                    captureFingerBegin();
                }else{
                    PopupUtil.tPinSystem(MiniStatement.this,viewModel.aepsRepository.apiServices);
                }
            }
        });
    }


    @Override
    public void fingerError(String error) {
        super.fingerError(error);
        ViewUtils.showToast(MiniStatement.this, "Error "+error);

    }

    @Override
    public void fingerPrintCaptured(String result) {
        super.fingerPrintCaptured(result);
        startService(result);
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

    private void startService(String fingerprint){
        if (Accessable.isAccessable()){
            viewModel.startAepsResponseStatement(MiniStatement.this, Objects.requireNonNull(binding.aadhaarNumber.getText()).toString(),fingerprint, Objects.requireNonNull(binding.mobileNumber.getText()).toString(), "MS", UtilHolder.getLongitude(), UtilHolder.getLatitude(), "", MiniStatement.this);
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