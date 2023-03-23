package com.fintech.webspidysoftware.activities.aeps;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;

import androidx.appcompat.app.ActionBar;
import androidx.lifecycle.ViewModelProvider;

import com.fintech.webspidysoftware.R;
import com.fintech.webspidysoftware.databinding.ActivityCashWithdrawalBinding;
import com.fintech.webspidysoftware.listeners.BankNameListener;
import com.fintech.webspidysoftware.listeners.ResetListener;
import com.fintech.webspidysoftware.util.Accessable;
import com.fintech.webspidysoftware.util.DisplayMessageUtil;
import com.fintech.webspidysoftware.util.ExecuteUtil;
import com.fintech.webspidysoftware.util.MyAlertUtils;
import com.fintech.webspidysoftware.util.PopupUtil;
import com.fintech.webspidysoftware.util.StartGettingLocation;
import com.fintech.webspidysoftware.util.UtilHolder;
import com.fintech.webspidysoftware.util.ViewUtils;
import com.fintech.webspidysoftware.viewmodel.AepsViewModel;

import java.util.Objects;

import dagger.hilt.android.AndroidEntryPoint;

@AndroidEntryPoint
public class CashWithdrawal extends BaseFingerActivity implements BankNameListener, ResetListener {


    ActivityCashWithdrawalBinding binding;
    AepsViewModel viewModel;


    @SuppressLint("SetTextI18n")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityCashWithdrawalBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        Objects.requireNonNull(getSupportActionBar()).setDisplayHomeAsUpEnabled(true);
        ActionBar actionBar = getSupportActionBar();
        actionBar.setTitle("Cash Withdrawal..");
        viewModel = new ViewModelProvider(this).get(AepsViewModel.class);
        binding.setCashWithdrawalViewModel(viewModel);
        viewModel.bankNameListener = this;
        setListeners();
        ViewUtils.setFocusable(binding.aadhaarNumber, CashWithdrawal.this);
        viewModel.transactionType = getIntent().getStringExtra("aepsType");
        if(viewModel.transactionType!=null){
            if(viewModel.transactionType.equals("M")){
                actionBar.setTitle("Aadhaar Pay..");
                binding.captureFingerPrintButton.setText("Aadhaar Pay..");
            }else{
                actionBar.setTitle("Cash Withdraw..");
            }
        }
        StartGettingLocation.setAllTheLocations(CashWithdrawal.this);
    }



    private void setListeners() {
        binding.captureFingerPrintButton.setOnClickListener(v -> {

            if(Objects.requireNonNull(binding.aadhaarNumber.getText()).toString().length()!=12){
                MyAlertUtils.showAlertDialog(CashWithdrawal.this,"Warning","Enter a valid Aadhaar Number", R.drawable.warning);
            }
            else if(Objects.requireNonNull(binding.mobileNumber.getText()).toString().length()!=10){
                MyAlertUtils.showAlertDialog(CashWithdrawal.this,"Warning","Enter a valid Mobile Number", R.drawable.warning);
            }
            else if(Objects.requireNonNull(binding.amountNumber.getText()).toString().length()<1){
                MyAlertUtils.showAlertDialog(CashWithdrawal.this,"Warning","Enter a valid Amount Number", R.drawable.warning);
            }
            else if(Objects.requireNonNull(binding.selectedBankName.getText()).toString().isEmpty()){
                MyAlertUtils.showAlertDialog(CashWithdrawal.this,"Warning","Select Your Bank", R.drawable.warning);
            }
            else{
                if(PopupUtil.access){

                    try{
                        long minBalance = Long.parseLong(binding.amountNumber.getText().toString());
                        if(minBalance<100){
                            DisplayMessageUtil.error(CashWithdrawal.this, "Minimum amount should be at-least 100 Rs.");
                            return;
                        }
                    }catch (NumberFormatException exception){
                        DisplayMessageUtil.error(CashWithdrawal.this, "Minimum amount should be at-least 100 Rs.");
                        return;
                    }

                    captureFingerBegin();
                }else{
                    PopupUtil.tPinSystem(CashWithdrawal.this, viewModel.aepsRepository.apiServices);
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
        ViewUtils.showToast(CashWithdrawal.this, "Error "+error);

    }

    @Override
    public void fingerPrintCaptured(String result) {
        super.fingerPrintCaptured(result);
        startService(result);
    }

    private void startService(String fingerprint){
        if (Accessable.isAccessable()){
            viewModel.startAEPSServices(CashWithdrawal.this, Objects.requireNonNull(binding.aadhaarNumber.getText()).toString(),fingerprint, Objects.requireNonNull(binding.mobileNumber.getText()).toString(), viewModel.transactionType, UtilHolder.getLongitude(), UtilHolder.getLatitude(), Objects.requireNonNull(binding.amountNumber.getText()).toString(), CashWithdrawal.this);
        }
    }


    @Override
    public void setBankName(String bankName) {
        binding.selectedBankName.setText(bankName);
    }

    @Override
    public void resetRequiredData(boolean result) {
        binding.aadhaarNumber.setText("");
        binding.amountNumber.setText("");
        binding.mobileNumber.setText("");
    }
}