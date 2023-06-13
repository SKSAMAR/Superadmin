package com.fintech.superadmin.activities.microatm;

import android.Manifest;
import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.Dialog;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.MenuItem;
import android.view.WindowManager;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.ActionBar;
import androidx.lifecycle.ViewModelProvider;

import com.fintech.superadmin.R;
import com.fintech.superadmin.activities.common.BaseActivity;
import com.fintech.superadmin.data.apiResponse.merchant.MerchantCred;
import com.fintech.superadmin.data.db.AppDatabase;
import com.fintech.superadmin.data.db.entities.User;
import com.fintech.superadmin.data.dto.AtmProceedableDto;
import com.fintech.superadmin.data.dto.PaysprintApiCred;
import com.fintech.superadmin.data.dto.PaysprintMerchantCred;
import com.fintech.superadmin.data.model.MicroAtmModel;
import com.fintech.superadmin.databinding.ActivityMicroAtmHomeBinding;
import com.fintech.superadmin.deer_listener.master_listener.BetterListener;
import com.fintech.superadmin.listeners.TaskPerformer;
import com.fintech.superadmin.util.Constants;
import com.fintech.superadmin.util.DisplayMessageUtil;
import com.fintech.superadmin.util.LocationUtil;
import com.fintech.superadmin.util.MyAlertUtils;
import com.fintech.superadmin.util.PermissionUtil;
import com.fintech.superadmin.util.UtilHolder;
import com.fintech.superadmin.util.ViewUtils;
import com.fintech.superadmin.viewmodel.AtmViewModel;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import dagger.hilt.android.AndroidEntryPoint;


@AndroidEntryPoint
public class MicroAtmHome extends BaseActivity implements BetterListener<AtmProceedableDto> {

    ActivityMicroAtmHomeBinding binding;
    User user;
    String txnType;
    String amount = "0";
    Integer atm = 0;
    AtmViewModel viewModel;
    List<AtmDeviceModels> list = new ArrayList<>();

    @SuppressLint("SetTextI18n")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityMicroAtmHomeBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        Objects.requireNonNull(getSupportActionBar()).setDisplayHomeAsUpEnabled(true);
        ActionBar actionBar = getSupportActionBar();
        actionBar.setTitle("Micro ATM");
        AppDatabase appDatabase = AppDatabase.getAppDatabase(MicroAtmHome.this);
        appDatabase.getUserDao().getUser().observe(this, currentUser -> user = currentUser);
        viewModel = new ViewModelProvider(this).get(AtmViewModel.class);
        viewModel.listener = this;
        observeAtmState();
        binding.selectedBankName.setText("Select ATM");
        ViewUtils.setFocusable(binding.edAmount, MicroAtmHome.this);
        LocationUtil.getLocationWithCheckNetworkAndGPS(MicroAtmHome.this);
    }


    private void checkPermission(TaskPerformer performer) {
        String[] permissions;
        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.S) {
            permissions = new String[]{
                    Manifest.permission.BLUETOOTH,
                    Manifest.permission.BLUETOOTH_SCAN,
                    Manifest.permission.BLUETOOTH_CONNECT,
                    Manifest.permission.ACCESS_FINE_LOCATION,
                    Manifest.permission.ACCESS_COARSE_LOCATION,
            };
        } else if (android.os.Build.VERSION.SDK_INT == android.os.Build.VERSION_CODES.R) {
            permissions = new String[]{
                    Manifest.permission.BLUETOOTH,
                    Manifest.permission.ACCESS_FINE_LOCATION,
                    Manifest.permission.ACCESS_COARSE_LOCATION,
            };
        } else {
            permissions = new String[]{
                    Manifest.permission.BLUETOOTH,
                    Manifest.permission.ACCESS_FINE_LOCATION,
                    Manifest.permission.ACCESS_COARSE_LOCATION,
            };
        }


        PermissionUtil.givePermissions(MicroAtmHome.this, permissions, res -> {
            if (res.equals(1)) {
                performer.yes();
            } else {
                ViewUtils.showToast(getApplicationContext(), "Permission Denied for Bluetooth to use Micro ATM");
            }
        });
    }


    private void onNewClick() {

        binding.btnSubmit.setOnClickListener(v -> {
            if (atm == 0) {
                DisplayMessageUtil.error(MicroAtmHome.this, "Select your ATM Device");
            } else {
                txnType = "ATMBE";
                binding.edAmount.setText("");
                amount = "0";
                checkPermission(() -> viewModel.recordTransaction(MicroAtmHome.this, amount, txnType));
            }
        });

        binding.btnWithdraw.setOnClickListener(v -> {
            if (Objects.requireNonNull(binding.edAmount.getText()).toString().isEmpty()) {
                DisplayMessageUtil.error(MicroAtmHome.this, "Enter a valid amount");
            }
            if (Objects.requireNonNull(binding.edAmount.getText()).toString().isEmpty()) {
                DisplayMessageUtil.error(MicroAtmHome.this, "Enter a valid amount");
            } else if (atm == 0) {
                DisplayMessageUtil.error(MicroAtmHome.this, "Select your ATM Device");
            } else {
                double amountBal = Double.parseDouble(binding.edAmount.getText().toString());
                if (amountBal < 100) {
                    DisplayMessageUtil.error(MicroAtmHome.this, "Minimum withdraw amount is 100");
                    return;
                }
                txnType = "ATMCW";
                amount = binding.edAmount.getText().toString();
                checkPermission(() -> viewModel.recordTransaction(MicroAtmHome.this, amount, txnType));
            }
        });
        binding.selectedBankName.setOnClickListener(v -> {
            ViewUtils.showToast(this, "Clicked");
            onSpinnerClick();
        });
    }

    private void onOldClick() {

        binding.btnSubmit.setOnClickListener(v -> {
            if (atm == 0) {
                DisplayMessageUtil.error(MicroAtmHome.this, "Select your ATM Device");
            } else {
                txnType = "BE";
                amount = "0";
                checkPermission(() -> viewModel.recordTransaction(MicroAtmHome.this, amount, txnType));
            }
        });

        binding.btnWithdraw.setOnClickListener(v -> {
            if (Objects.requireNonNull(binding.edAmount.getText()).toString().isEmpty()) {
                DisplayMessageUtil.error(MicroAtmHome.this, "Enter a valid amount");
            }
            if (Objects.requireNonNull(binding.edAmount.getText()).toString().isEmpty()) {
                DisplayMessageUtil.error(MicroAtmHome.this, "Enter a valid amount");
            } else if (atm == 0) {
                DisplayMessageUtil.error(MicroAtmHome.this, "Select your ATM Device");
            } else {
                double amountBal = Double.parseDouble(binding.edAmount.getText().toString());
                if (amountBal < 100) {
                    DisplayMessageUtil.error(MicroAtmHome.this, "Minimum withdraw amount is 100");
                    return;
                }
                txnType = "CW";
                amount = binding.edAmount.getText().toString();
                checkPermission(() -> viewModel.recordTransaction(MicroAtmHome.this, amount, txnType));
            }
        });
        binding.selectedBankName.setOnClickListener(v -> onSpinnerClick());
    }

    private void onFingPayClick() {

        binding.btnSubmit.setOnClickListener(v -> {
            if (atm == 0) {
                DisplayMessageUtil.error(MicroAtmHome.this, "Select your ATM Device");
            } else {
                txnType = "ATMBE";
                binding.edAmount.setText("");
                amount = "0";
                checkPermission(() -> viewModel.recordTransaction(MicroAtmHome.this, amount, txnType));
            }
        });

        binding.btnWithdraw.setOnClickListener(v -> {
            if (Objects.requireNonNull(binding.edAmount.getText()).toString().isEmpty()) {
                DisplayMessageUtil.error(MicroAtmHome.this, "Enter a valid amount");
            }
            if (Objects.requireNonNull(binding.edAmount.getText()).toString().isEmpty()) {
                DisplayMessageUtil.error(MicroAtmHome.this, "Enter a valid amount");
            } else if (atm == 0) {
                DisplayMessageUtil.error(MicroAtmHome.this, "Select your ATM Device");
            } else {
                double amountBal = Double.parseDouble(binding.edAmount.getText().toString());
                if (amountBal < 100) {
                    DisplayMessageUtil.error(MicroAtmHome.this, "Minimum withdraw amount is 100");
                    return;
                }
                txnType = "ATMCW";
                amount = binding.edAmount.getText().toString();
                checkPermission(() -> viewModel.recordTransaction(MicroAtmHome.this, amount, txnType));
            }
        });
        binding.selectedBankName.setOnClickListener(v -> onSpinnerClick());
    }

    private void initiateNewAtmTransaction(AtmProceedableDto dto) {

        PaysprintMerchantCred merchant = (PaysprintMerchantCred) getIntent().getSerializableExtra("paySprintMerchant");
        PaysprintApiCred paysprintApiCred = (PaysprintApiCred) getIntent().getSerializableExtra("paySprintApi");
        Intent intent;
        try {
            intent = new Intent(getApplicationContext(), Class.forName("com.service.finopayment.Hostnew"));
        } catch (ClassNotFoundException e) {
            DisplayMessageUtil.error(MicroAtmHome.this, "" + e.getMessage());
            throw new RuntimeException(e);
        }
        intent.putExtra("partnerId", paysprintApiCred.getPARTNERID());
        intent.putExtra("apiKey", paysprintApiCred.getJWTKEY());
        intent.putExtra("transactionType", txnType);   // ATMBE  or  ATMCW
        intent.putExtra("amount", amount);
        intent.putExtra("merchantCode", merchant.getMERCHANTCODE()); //merchantCode
        intent.putExtra("remarks", "Test Transaction");
        intent.putExtra("mobileNumber", user.getMobile());  // customer mobile number
        intent.putExtra("referenceNumber", dto.getReference());  // txn unqiue referenceNumber number
        intent.putExtra("latitude", UtilHolder.getLatitude());
        intent.putExtra("longitude", UtilHolder.getLongitude());
        intent.putExtra("subMerchantId", merchant.getMERCHANTCODE());  //merchantCode
        intent.putExtra("deviceManufacturerId", atm.toString());

        Bundle extras = intent.getExtras();
        JSONObject jsonObject = new JSONObject();
        if (extras != null) {
            for (String key : extras.keySet()) {
                try {
                    jsonObject.put(key, extras.get(key));
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        }
        System.out.println(jsonObject.toString());
        Log.d("SEND_RESPONSE", jsonObject.toString());
        startActivityForResult(intent, 999);
    }

    private void initiateOldTransaction(AtmProceedableDto dto) {

        PaysprintApiCred api = (PaysprintApiCred) getIntent().getSerializableExtra("paySprintApi");
        PaysprintMerchantCred merchant = (PaysprintMerchantCred) getIntent().getSerializableExtra("paySprintMerchant");

        Intent intent;
        try {
            intent = new Intent(MicroAtmHome.this, Class.forName("com.paysprint.microatmlib.activities.HostActivity"));
        } catch (ClassNotFoundException e) {
            DisplayMessageUtil.anotherDialogFailure(this, "" + e.getMessage());
            throw new RuntimeException(e);
        }
        intent.putExtra("partnerId", merchant.getPARTNERID());
        intent.putExtra("apiKey", api.getJWTKEY());
        intent.putExtra("merchantCode", merchant.getMERCHANTCODE());
        intent.putExtra("transactionType", txnType); // BE for Balance Enquiry and CW for Cash Withdrawal
        intent.putExtra("amount", amount); // 0 for Balance Enquiry and Amount for Cash Withdrawal
        intent.putExtra("remarks", "Test Transaction"); // Transaction remarks
        intent.putExtra("mobileNumber", user.getMobile()); // Customer Mobile Number
        intent.putExtra("referenceNumber", dto.getReference()); // Reference Number
        intent.putExtra("latitude", UtilHolder.getLatitude()); // Latitude
        intent.putExtra("longitude", UtilHolder.getLongitude()); // Longitude
        intent.putExtra("subMerchantId", merchant.getMERCHANTCODE());
        intent.putExtra("deviceManufacturerId", atm);
        intent.putExtra("email", user.getEmail());
        startActivityForResult(intent, 999);
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
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 999) {
            if (resultCode == Activity.RESULT_OK) {
                if (data != null) {

                    boolean status = data.getBooleanExtra("status", false);
                    int response = data.getIntExtra("response", 0);
                    String message = data.getStringExtra("message");
                    if (status) {

                        String dataResponse = data.getStringExtra("data:response");
                        String dataTransAmount = data.getStringExtra("data:transAmount");
                        String dataBalAmount = data.getStringExtra("data:balAmount");
                        String dataBalRrn = data.getStringExtra("data:bankRrn");
                        String dataTxnId = data.getStringExtra("data:txnid");
                        String dataTransType = data.getStringExtra("data:transType");
                        String dataType = data.getStringExtra("data:type");
                        String dataCardNumber = data.getStringExtra("data:cardNumber");
                        String dataCardType = data.getStringExtra("data:cardType");
                        String dataTerminalId = data.getStringExtra("data:terminalId");
                        String dataBankName = data.getStringExtra("data:bankName");
                        MicroAtmModel microAtmModel = new MicroAtmModel(message, String.valueOf(dataResponse), dataTransAmount, dataBalAmount, dataBalRrn, dataTxnId, dataTransType, dataType, dataCardNumber, dataCardType, dataTerminalId, dataBankName);
                        Intent intent = new Intent(MicroAtmHome.this, MicroAtmResponse.class);
                        intent.putExtra("atmResponse", microAtmModel);

                        Log.d("ATM_RESPONSE", microAtmModel.toString());

                        startActivity(intent);
                    } else {
                        DisplayMessageUtil.anotherDialogFailure(MicroAtmHome.this, "" + message);
                    }
                } else {
                    DisplayMessageUtil.anotherDialogFailure(MicroAtmHome.this, "" + data.toString());
                }
            }


        }
        if (requestCode == 998) {
            if (resultCode == Activity.RESULT_OK) {
                boolean status = data.getBooleanExtra(Constants.TRANS_STATUS, false);
                String response = data.getStringExtra(Constants.MESSAGE);
                double transAmount = data.getDoubleExtra(Constants.TRANS_AMOUNT, 0.0);
                double balAmount = data.getDoubleExtra(Constants.BALANCE_AMOUNT, 0.0);
                String bankRrn = data.getStringExtra(Constants.RRN);
                String transType = data.getStringExtra(Constants.TRANS_TYPE);
                int type = data.getIntExtra(Constants.TYPE, Constants.CASH_WITHDRAWAL);
                String cardNum = data.getStringExtra(Constants.CARD_NUM);
                String rrn = data.getStringExtra(Constants.RRN);
                String Ttype = data.getStringExtra(Constants.TRANS_TYPE);
                String bankName = data.getStringExtra(Constants.BANK_NAME);
                String cardType = data.getStringExtra(Constants.CARD_TYPE);
                String terminalId = data.getStringExtra(Constants.TERMINAL_ID);
                String fpId = data.getStringExtra(Constants.FP_TRANS_ID);
                String transId = data.getStringExtra(Constants.TXN_ID);

                MicroAtmModel microAtmModel = new MicroAtmModel(response, String.valueOf(response), String.valueOf(transAmount), String.valueOf(balAmount), bankRrn, transId, transType, Ttype, cardNum, cardType, terminalId, bankName);
                Intent intent = new Intent(MicroAtmHome.this, MicroAtmResponse.class);
                intent.putExtra("atmResponse", microAtmModel);
            }
        }


    }


    @SuppressLint("SetTextI18n")
    public void onSpinnerClick() {
        Dialog dialog = new Dialog(MicroAtmHome.this);
        dialog.setContentView(R.layout.my_spinner_row);
        dialog.getWindow().setLayout(WindowManager.LayoutParams.MATCH_PARENT, WindowManager.LayoutParams.WRAP_CONTENT);
        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        dialog.show();
        TextView head_title_section = dialog.findViewById(R.id.head_title_section);
        EditText searchOperator = dialog.findViewById(R.id.SearchOperator);
        ListView myOperatorListView = dialog.findViewById(R.id.MyOperatorListView);
        head_title_section.setText("Micro ATM Device");
        ArrayAdapter<AtmDeviceModels> adapter = new ArrayAdapter<>(MicroAtmHome.this, android.R.layout.simple_list_item_1, list);
        myOperatorListView.setAdapter(adapter);
        searchOperator.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                adapter.getFilter().filter(s);
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });

        myOperatorListView.setOnItemClickListener((parent, view1, position, id) -> {
            binding.selectedBankName.setText(adapter.getItem(position).getName());
            atm = adapter.getItem(position).getId();
            dialog.dismiss();
        });
    }

    void initiateFingpayTransaction(AtmProceedableDto data) {
        MerchantCred merchantCred = (MerchantCred) getIntent().getParcelableExtra("merchantCred");
        int tType;
        if (txnType.trim().equals("ATMBE")) {
            tType = 4;
        } else {
            tType = 2;
        }

        Intent intent;
        try {
            intent = new Intent(MicroAtmHome.this, Class.forName("com.fingpay.microatmsdk.MicroAtmLoginScreen"));
            intent.putExtra(Constants.MERCHANT_USERID, merchantCred.getMERCHANTCODE());
            // this MERCHANT_USERID be given by FingPay depending on the merchant, only that value need to sent from App to SDK

            // this MERCHANT_USERID be given by FingPay depending on the merchant, only that value need to sent from App to SDK
            intent.putExtra(Constants.MERCHANT_PASSWORD, merchantCred.getMERCHANTCODE());
            // this MERCHANT_PASSWORD be given by FingPay depending on the merchant, only that value need to sent from App to SDK

            // this MERCHANT_PASSWORD be given by FingPay depending on the merchant, only that value need to sent from App to SDK
            intent.putExtra(Constants.AMOUNT, amount);
            intent.putExtra(Constants.REMARKS, "transaction");
            intent.putExtra(Constants.MOBILE_NUMBER, merchantCred.getMOBILE());
            // send a valid 10 digit mobile number from the app to SDK
            // send a valid 10 digit mobile number from the app to SDK
            intent.putExtra(Constants.AMOUNT_EDITABLE, false);
            // send true if Amount can be edited in the SDK or send false then Amount cant be edited in the SDK
            intent.putExtra(Constants.TXN_ID, data.getReference());
            // this SUPER_MERCHANT_ID be given by FingPay to you, only that value need to sent from App to SDK
            intent.putExtra(Constants.IMEI, viewModel.getImei(MicroAtmHome.this));
            // some dummy value is given in TXN_ID for now but some proper value should come from App to SDK
            intent.putExtra(Constants.SUPER_MERCHANTID, merchantCred.getSUPERMERCHANT());
            intent.putExtra(Constants.LATITUDE, Double.valueOf(UtilHolder.getLatitude()));
            intent.putExtra(Constants.LONGITUDE, Double.valueOf(UtilHolder.getLongitude()));
            intent.putExtra(Constants.TYPE, tType);
            intent.putExtra(Constants.MICROATM_MANUFACTURER, Constants.MoreFun);

            Log.d("FINGMATM", "Send Response " + intent.getExtras().toString());
            startActivityForResult(intent, 998);
        } catch (ClassNotFoundException e) {
            DisplayMessageUtil.error(this, e.getLocalizedMessage());
        }

    }


    @Override
    public void onAchieved(AtmProceedableDto data) {

        String atmType = getResources().getString(R.string.atmType).trim().toLowerCase();
        switch (atmType) {
            case "paysprintnew":
                initiateNewAtmTransaction(data);
                break;
            case "paysprintold":
                initiateOldTransaction(data);
                break;
            case "fingpay":
                initiateFingpayTransaction(data);
                break;
        }
    }

    @Override
    public void onFailure(String message) {
        MyAlertUtils.showServerAlertDialog(this, message);
    }


    private void observeAtmState() {

        try {
            String result = getString(R.string.atmType).trim().toLowerCase();
            list = new ArrayList<>();
            if (result.trim().toLowerCase().contains("paysprintold")) {
                if (atm == 0) {
                    atm = 1;
                }
                list.add(new AtmDeviceModels("AF60S", 1));
                list.add(new AtmDeviceModels("MP-63", 2));
                onOldClick();
            } else if (result.trim().toLowerCase().contains("paysprintnew")) {
                if (atm == 0) {
                    atm = 3;
                }
                list.add(new AtmDeviceModels("AF60S", 3));
                onNewClick();
            } else if (result.trim().toLowerCase().contains("fingpay")) {
                if (atm == 0) {
                    atm = 2;
                }
                list.add(new AtmDeviceModels("ATM-Device", 2));
                onFingPayClick();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}

class AtmDeviceModels {
    String name;
    Integer id;

    public AtmDeviceModels(String name, Integer id) {
        this.name = name;
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    @NonNull
    @Override
    public String toString() {
        return name;
    }
}