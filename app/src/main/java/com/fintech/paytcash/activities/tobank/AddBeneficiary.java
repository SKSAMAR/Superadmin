package com.fintech.paytcash.activities.tobank;

import android.app.Activity;
import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.MenuItem;

import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.appcompat.app.ActionBar;
import androidx.lifecycle.ViewModelProvider;

import com.fintech.paytcash.activities.common.BaseActivity;
import com.fintech.paytcash.data.model.BankModel;
import com.fintech.paytcash.databinding.ActivityAddBeneficiaryBinding;
import com.fintech.paytcash.listeners.RemitterListener;
import com.fintech.paytcash.util.ViewUtils;
import com.fintech.paytcash.viewmodel.ToBankViewModel;

import java.util.Objects;

import dagger.hilt.android.AndroidEntryPoint;

@AndroidEntryPoint
public class AddBeneficiary extends BaseActivity implements RemitterListener {

    ActivityAddBeneficiaryBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding =  ActivityAddBeneficiaryBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        Objects.requireNonNull(getSupportActionBar()).setDisplayHomeAsUpEnabled(true);
        ActionBar actionBar = getSupportActionBar();
        actionBar.setTitle("Add Bank Account");
        ToBankViewModel viewModel = new ViewModelProvider(this).get(ToBankViewModel.class);
        viewModel.globalSelectedMobile = getIntent().getStringExtra("number");
        viewModel.selectedBank = (BankModel) getIntent().getSerializableExtra("selectedBankModel");
        binding.setBeneficiaryViewModel(viewModel);
        viewModel.remitterListener = this;
        setListener();
        watcher();
        ViewUtils.setFocusable(binding.accountNumber, AddBeneficiary.this);

    }


    public void setListener(){
//        binding.getTheContacts.setOnClickListener(v -> {
//            Intent intent = new Intent(Intent.ACTION_PICK, ContactsContract.CommonDataKinds.Phone.CONTENT_URI);
//            someActivityResultLauncher.launch(intent);
//        });
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

    ActivityResultLauncher<Intent> someActivityResultLauncher = registerForActivityResult(
            new ActivityResultContracts.StartActivityForResult(),
            result -> {
                if (result.getResultCode() == Activity.RESULT_OK) {
                    // There are no request codes
                    Intent data = result.getData();
                    Uri contactUri = Objects.requireNonNull(data).getData();
                    String[] projection = new String[]{ContactsContract.CommonDataKinds.Phone.NUMBER};
                    Cursor cursor = getContentResolver().query(contactUri, projection,
                            null, null, null);

                    // If the cursor returned is valid, get the phone number
                    if (cursor != null && cursor.moveToFirst()) {
                        int numberIndex = cursor.getColumnIndex(ContactsContract.CommonDataKinds.Phone.NUMBER);
                        String number = cursor.getString(numberIndex);
                        // Do something with the phone number
                        String message = number.replaceAll("[^\\w]", "");
                        StringBuilder builder = new StringBuilder(message);
                        if(builder.charAt(0)=='9' && builder.charAt(1)=='1'){
                            builder.deleteCharAt(0);
                            builder.deleteCharAt(0);
                        }
//                        binding.phoneNumber.setText(String.valueOf(builder));
                    }

                    Objects.requireNonNull(cursor).close();


                }
            });


    private void watcher(){
        binding.confirmAccountNumber.addTextChangedListener(new TextWatcher() {
            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

                if(binding.confirmAccountNumber.getText().toString().trim().length() == binding.accountNumber.getText().toString().trim().length()){
                    if(!Objects.requireNonNull(binding.confirmAccountNumber.getText()).toString().equals(Objects.requireNonNull(binding.accountNumber.getText()).toString())){
                        binding.confirmAccountNumber.setError("Account Numbers don't match");
                    }
                    // TODO Auto-generated method stub
                }
            }

            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

                // TODO Auto-generated method stub
            }

            @Override
            public void afterTextChanged(Editable s) {

                // TODO Auto-generated method stub
            }
        });
    }

    @Override
    public void dateSetter(String date) {
//        binding.beneDob.setText(date);
    }
}