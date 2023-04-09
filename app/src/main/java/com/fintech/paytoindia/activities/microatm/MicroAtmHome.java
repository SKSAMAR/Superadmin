package com.fintech.paytoindia.activities.microatm;

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

import com.fintech.paytoindia.R;
import com.fintech.paytoindia.activities.common.BaseActivity;
import com.fintech.paytoindia.data.db.AppDatabase;
import com.fintech.paytoindia.data.db.entities.PaySprintApi;
import com.fintech.paytoindia.data.db.entities.User;
import com.fintech.paytoindia.data.dto.PaysprintApiCred;
import com.fintech.paytoindia.data.dto.PaysprintMerchantCred;
import com.fintech.paytoindia.data.model.MicroAtmModel;
import com.fintech.paytoindia.data.network.APIServices;
import com.fintech.paytoindia.data.network.responses.RegularResponse;
import com.fintech.paytoindia.databinding.ActivityMicroAtmHomeBinding;
import com.fintech.paytoindia.deer_listener.master_listener.BetterListener;
import com.fintech.paytoindia.listeners.TaskPerformer;
import com.fintech.paytoindia.util.DisplayMessageUtil;
import com.fintech.paytoindia.util.LocationUtil;
import com.fintech.paytoindia.util.MyAlertUtils;
import com.fintech.paytoindia.util.PermissionUtil;
import com.fintech.paytoindia.util.ViewUtils;
import com.fintech.paytoindia.viewmodel.AtmViewModel;
//import com.paysprint.microatmlib.activities.HostActivity;
import com.service.finopayment.Hostnew;
//import com.service.finopayment.Hostnew;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Random;

import javax.inject.Inject;

import dagger.hilt.android.AndroidEntryPoint;


@AndroidEntryPoint
public class MicroAtmHome extends BaseActivity implements BetterListener {

    ActivityMicroAtmHomeBinding binding;
    User user;
    PaySprintApi sprintApi;
    String txnType;
    String amount= "0";
    String referenceNo;
    Integer atm = 3;
    char[] chars = new char[]{ 'A', 'B', 'C', 'D', 'E', 'F', 'G', 'H', 'I', 'J', 'K', 'L', 'M', 'N', 'O', 'P', 'Q', 'R', 'S', 'T', 'U', 'V', 'W', 'X', 'Y', 'Z',
            'a', 'b', 'c', 'd', 'e', 'f', 'g', 'h', 'i', 'j', 'k', 'l', 'm', 'n', 'o', 'p', 'q', 'r', 's', 't', 'u', 'v', 'w', 'x', 'y', 'z',
            '1', '2', '3', '4', '5', '6', '7', '8', '9', '0' };

    @Inject
    APIServices apiServices;
    AtmViewModel viewModel;

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
        user = appDatabase.getUserDao().getRegularUser();
        sprintApi = appDatabase.getPaySprint().getRegularPaySprint();
        viewModel = new ViewModelProvider(this).get(AtmViewModel.class);
        viewModel.listener = this;
        onClick();
        binding.selectedBankName.setText("AF60S");
        ViewUtils.setFocusable(binding.edAmount, MicroAtmHome.this);
        LocationUtil.getLocationWithCheckNetworkAndGPS(MicroAtmHome.this);
    }


    private void checkPermission(TaskPerformer performer){
        String[] permissions;
        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.S) {
            permissions = new String[]{
                    Manifest.permission.BLUETOOTH,
                    Manifest.permission.BLUETOOTH_SCAN,
                    Manifest.permission.BLUETOOTH_CONNECT,
                    Manifest.permission.ACCESS_FINE_LOCATION,
                    Manifest.permission.ACCESS_COARSE_LOCATION,
            };
        }
        else if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.R) {
            permissions = new String[]{
                    Manifest.permission.BLUETOOTH,
                    Manifest.permission.ACCESS_FINE_LOCATION,
                    Manifest.permission.ACCESS_COARSE_LOCATION,
            };
        }
        else{
            permissions = new String[]{
                    Manifest.permission.BLUETOOTH,
                    Manifest.permission.ACCESS_FINE_LOCATION,
                    Manifest.permission.ACCESS_COARSE_LOCATION,
            };
        }
        PermissionUtil.givePermissions(MicroAtmHome.this, permissions, res->{
            if (res.equals(1)){
                performer.yes();
            }
            else{
                ViewUtils.showToast(getApplicationContext(), "Permission Denied for Bluetooth to use Micro ATM");
            }
        });
    }


    private void onClick(){

        binding.btnSubmit.setOnClickListener(v -> {
            if(atm==0){
                DisplayMessageUtil.error(MicroAtmHome.this, "Select your ATM Device");
            }
            else{
                referenceNo = "ETH"+getRandomString(15,chars);
                txnType = "ATMBE";
                binding.edAmount.setText("");
                amount = "0";
                checkPermission(() -> viewModel.recordTransaction(MicroAtmHome.this, amount, txnType, referenceNo));
            }
        });

        binding.btnWithdraw.setOnClickListener(v -> {
            if(Objects.requireNonNull(binding.edAmount.getText()).toString().isEmpty()){
                DisplayMessageUtil.error(MicroAtmHome.this, "Enter a valid amount");
            }
            if(Objects.requireNonNull(binding.edAmount.getText()).toString().isEmpty()){
                DisplayMessageUtil.error(MicroAtmHome.this, "Enter a valid amount");
            }
            else if(atm==0){
                DisplayMessageUtil.error(MicroAtmHome.this, "Select your ATM Device");
            }
            else{
                double amountBal = Double.parseDouble(binding.edAmount.getText().toString());
                if(amountBal<100){
                    DisplayMessageUtil.error(MicroAtmHome.this, "Minimum withdraw amount is 100");
                    return;
                }
                referenceNo = "ATM"+getRandomString(15,chars);
                txnType = "ATMCW";
                amount = binding.edAmount.getText().toString();
                checkPermission(()-> viewModel.recordTransaction(MicroAtmHome.this, amount, txnType, referenceNo));
            }
        });
        binding.selectedBankName.setOnClickListener(v -> onSpinnerClick());
    }


    private void initiateTransaction(){

        PaysprintApiCred api = (PaysprintApiCred) getIntent().getSerializableExtra("paySprintApi");
        PaysprintMerchantCred merchant = (PaysprintMerchantCred) getIntent().getSerializableExtra("paySprintMerchant");

        Intent intent = new Intent(getApplicationContext(), Hostnew.class);
        intent.putExtra("partnerId",merchant.getPARTNERID());
        intent.putExtra("apiKey", api.getJWTKEY());
        intent.putExtra("transactionType",txnType);   // ATMBE  or  ATMCW
        intent.putExtra("amount",amount);
        intent.putExtra("merchantCode",merchant.getMERCHANTCODE()); //merchantCode
        intent.putExtra("remarks", "Test Transaction") ;
        intent.putExtra("mobileNumber", user.getMobile()) ;  // customer mobile number
        intent.putExtra("referenceNumber", referenceNo) ;  // txn unqiue referenceNumber number
        intent.putExtra("latitude", "22.572646");
        intent.putExtra("longitude", "88.363895");
        intent.putExtra("subMerchantId", merchant.getMERCHANTCODE());  //merchantCode
        intent.putExtra("deviceManufacturerId", atm.toString());//new one requires in string type string old one in int
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

    public String getRandomString(int length, char[] characterSet) {
        StringBuilder sb = new StringBuilder();

        for (int loop = 0; loop < length; loop++) {
            int index = new Random().nextInt(characterSet.length);
            sb.append(characterSet[index]);
        }
        String nonce = sb.toString();
        return nonce;
    }


    private String revertMyData(String value){
        if(value==null){
            return "No Response for this value";
        }
        return value;
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode ==999){
            if(resultCode == Activity.RESULT_OK){
                if(data != null){

                    boolean status = data.getBooleanExtra("status", false);
                    int response = data.getIntExtra("response", 0);
                    String message = data.getStringExtra("message");
                    if(status){

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
                        MicroAtmModel microAtmModel = new MicroAtmModel(message, String.valueOf(dataResponse),dataTransAmount,dataBalAmount,dataBalRrn,dataTxnId,dataTransType,dataType,dataCardNumber,dataCardType,dataTerminalId,dataBankName);
                        Intent intent = new Intent(MicroAtmHome.this, MicroAtmResponse.class);
                        intent.putExtra("atmResponse", microAtmModel);
                        startActivity(intent);
                    }else{
                        DisplayMessageUtil.error(MicroAtmHome.this, ""+message);
                    }
                }
                else {
                    DisplayMessageUtil.error(MicroAtmHome.this, ""+data.toString());
                }
            }



        }


    }


    @SuppressLint("SetTextI18n")
    public void onSpinnerClick(){
        Dialog dialog = new Dialog(MicroAtmHome.this);
        dialog.setContentView(R.layout.my_spinner_row);
        dialog.getWindow().setLayout(WindowManager.LayoutParams.MATCH_PARENT, WindowManager.LayoutParams.WRAP_CONTENT);
        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        dialog.show();
        TextView head_title_section = dialog.findViewById(R.id.head_title_section);
        EditText searchOperator = dialog.findViewById(R.id.SearchOperator);
        ListView myOperatorListView = dialog.findViewById(R.id.MyOperatorListView);
        List<AtmDeviceModels> list = new ArrayList<>();
        head_title_section.setText("Micro ATM Device");
        list.add(new AtmDeviceModels("AF60S",3));
//        list.add(new AtmDeviceModels("MP-63",2));
        ArrayAdapter<AtmDeviceModels> adapter = new ArrayAdapter<>(MicroAtmHome.this, android.R.layout.simple_list_item_1,list);
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

    @SuppressLint("SetTextI18n")
    @Override
    protected void onResume() {
        super.onResume();
        atm = 3;
        binding.selectedBankName.setText("AF60S");
    }

    @Override
    public <T> void onAchieved(T data) {
        RegularResponse response = (RegularResponse) data;
        if(response.isStatus() && response.getResponse_code().equals(1)){
            initiateTransaction();
        }
    }

    @Override
    public void onFailure(String message) {
        MyAlertUtils.showServerAlertDialog(this, message);
    }
}

class AtmDeviceModels{
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