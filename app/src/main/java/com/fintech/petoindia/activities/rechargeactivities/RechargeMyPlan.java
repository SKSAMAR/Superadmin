package com.fintech.petoindia.activities.rechargeactivities;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.text.InputFilter;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.appcompat.app.ActionBar;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;
import androidx.lifecycle.ViewModelProvider;

import com.fintech.petoindia.R;
import com.fintech.petoindia.activities.common.BaseActivity;
import com.fintech.petoindia.data.model.OperatorModel;
import com.fintech.petoindia.data.network.responses.CustomerInfoResponse;
import com.fintech.petoindia.data.network.responses.MyOfferResponse;
import com.fintech.petoindia.data.network.responses.PaymentResponse;
import com.fintech.petoindia.databinding.ActivityRechargeMyPlanBinding;
import com.fintech.petoindia.fragments.sliders.SliderFragment;
import com.fintech.petoindia.listeners.PaymentListener;
import com.fintech.petoindia.listeners.ROfferListener;
import com.fintech.petoindia.listeners.ResetListener;
import com.fintech.petoindia.util.BindingUtils;
import com.fintech.petoindia.util.DisplayMessageUtil;
import com.fintech.petoindia.util.ExecuteUtil;
import com.fintech.petoindia.util.MyAlertUtils;
import com.fintech.petoindia.util.ViewUtils;
import com.fintech.petoindia.viewmodel.MobileRechargeViewModel;

import java.util.Objects;
import java.util.regex.Pattern;

import dagger.hilt.android.AndroidEntryPoint;

@AndroidEntryPoint
public class RechargeMyPlan extends BaseActivity implements PaymentListener, ResetListener, ROfferListener {

    ActivityRechargeMyPlanBinding binding;
    MobileRechargeViewModel rechargeViewModel;

    @SuppressLint("SetTextI18n")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityRechargeMyPlanBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        Objects.requireNonNull(getSupportActionBar()).setDisplayHomeAsUpEnabled(true);
        ActionBar actionBar = getSupportActionBar();
        actionBar.setTitle("Select Plan");
        rechargeViewModel= new ViewModelProvider(this).get(MobileRechargeViewModel.class);
        binding.setRechargeViewModel(rechargeViewModel);
        rechargeViewModel.mode = getIntent().getStringExtra("mode");
        rechargeViewModel.listener = this;
        rechargeViewModel.resetListener = this;
        setFragment(new SliderFragment(), binding.selectNumberSliders);
        setListeners();
        ViewUtils.setFocusable(binding.customerId, RechargeMyPlan.this);
        conditionals();
        rOffer();
        if(rechargeViewModel.mode.equals("dth")){
            rechargeViewModel.operatorModel = (OperatorModel) getIntent().getSerializableExtra("operatorModel");
            dthMode();
        }
        else{
            observeState();
            binding.operatorEditText.setOnClickListener(v->{
                rechargeViewModel.getOperators("Prepaid", RechargeMyPlan.this).observe(this, operatorModelList -> {
                    if(operatorModelList!=null){
                        rechargeViewModel.setOperators(operatorModelList, RechargeMyPlan.this);
                    }
                });
            });
        }
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
    public void paymentResult(PaymentResponse paymentResponse) {
        if(paymentResponse.responsecode.equals(100)){
            MyAlertUtils.showAlertDialog(RechargeMyPlan.this,"Success", paymentResponse.getMessage(), R.drawable.success);
            binding.customerPlan.setText("");
            binding.customerId.setText("");
        }
        else{
            MyAlertUtils.showAlertDialog(RechargeMyPlan.this,"Failed", paymentResponse.getMessage(), R.drawable.failed);
        }
    }

    @Override
    public void paymentMessage(String message) {
        ViewUtils.showToast(RechargeMyPlan.this, message);
    }

    @Override
    public void errorResult(String error) {
        MyAlertUtils.showServerAlertDialog(RechargeMyPlan.this, error);
    }

    private void setFragment(Fragment fragment, View view){
        FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();
        fragmentTransaction.replace(view.getId(),fragment);
        fragmentTransaction.commit();
    }

    public void setListeners(){
        binding.getTheContacts.setOnClickListener(v -> {
            Intent intent = new Intent(Intent.ACTION_PICK, ContactsContract.CommonDataKinds.Phone.CONTENT_URI);
            someActivityResultLauncher.launch(intent);
        });

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
                        binding.customerId.setText(String.valueOf(builder));
                    }

                    Objects.requireNonNull(cursor).close();


                }
            });

    @Override
    public void resetRequiredData(boolean result) {
        if(result){
            binding.customerPlan.setText("");
            binding.customerId.setText("");
            rechargeViewModel.mobileNumber.setValue("");
            rechargeViewModel.plan = "";
            rechargeViewModel.hlr_state.setValue(MobileRechargeViewModel.HLR_STATE.FETCH);
        }
    }

    private void conditionals(){
        if(MobileRechargeViewModel.service!=null &&MobileRechargeViewModel.service.equals("DTH")){
            binding.numberLayout.setHint("DTH Number");
            binding.customerId.setFilters(new InputFilter[]{filter,new InputFilter.LengthFilter(12)});
        }

    }

    InputFilter filter = (source, start, end, dest, dstart, dend) -> {
        for (int i = start; i < end; ++i)
        {
            if (!Pattern.compile("[1234567890]*").matcher(String.valueOf(source.charAt(i))).matches())
            {
                return "";
            }
        }
        return null;
    };


    @SuppressLint("SetTextI18n")
    private void rOffer(){
        if(MobileRechargeViewModel.service.equals("DTH")){
            binding.browsePlan.setText("Customer Info.");
            binding.ROffer.setOnClickListener(v -> {
                if(Objects.requireNonNull(binding.customerId.getText()).toString().isEmpty()){
                    myWarnings();
                }else{
                    Intent intent = new Intent(RechargeMyPlan.this, ROffer.class);
                    intent.putExtra("operatorModel", rechargeViewModel.operatorModel);
                    intent.putExtra("num",binding.customerId.getText().toString());
                    intent.putExtra("type","dth_rOffer");
                    rOfferActivityResultLauncher.launch(intent);
                }
            });
            binding.browsePlan.setOnClickListener(v->{
                rechargeViewModel.findCustomerInfoDth(RechargeMyPlan.this);
            });
        }
        else{
            binding.ROffer.setOnClickListener(v -> {
                if(Objects.requireNonNull(binding.customerId.getText()).toString().isEmpty()){
                    myWarnings();
                }else{

                    if(rechargeViewModel.operatorModel == null){
                        DisplayMessageUtil.error(RechargeMyPlan.this, "Select a valid operator");
                        return;
                    }

                    Intent intent = new Intent(RechargeMyPlan.this, ROffer.class);
                    intent.putExtra("operatorModel", rechargeViewModel.operatorModel);
                    intent.putExtra("num",binding.customerId.getText().toString());
                    intent.putExtra("type","mobile_r");
                    rOfferActivityResultLauncher.launch(intent);
                }
            });

            binding.browsePlan.setOnClickListener(v -> {
                if(Objects.requireNonNull(binding.customerId.getText()).toString().isEmpty()){
                    myWarnings();
                }else{

                    if(rechargeViewModel.operatorModel == null){
                        DisplayMessageUtil.error(RechargeMyPlan.this, "Select a valid operator");
                        return;
                    }

                    Intent intent = new Intent(RechargeMyPlan.this, BrowsePlan.class);
                    intent.putExtra("operatorModel", rechargeViewModel.operatorModel);
                    intent.putExtra("num",binding.customerId.getText().toString());
                    rOfferActivityResultLauncher.launch(intent);
                }
            });

        }
    }

    private void myWarnings(){
        MyAlertUtils.showAlertDialog(RechargeMyPlan.this, "Warning", "Provide Number", R.drawable.warning);
    }

    ActivityResultLauncher<Intent> rOfferActivityResultLauncher = registerForActivityResult(
            new ActivityResultContracts.StartActivityForResult(),
            result -> {
                if (result.getResultCode() == Activity.RESULT_OK) {
                    // There are no request codes
                    Intent data = result.getData();
                    Bundle bundle = Objects.requireNonNull(data).getExtras();
                    String val = bundle.getString("Price");
                    binding.customerPlan.setText(val);
                }
            });

    @Override
    public void onStartLooking() {
        MyAlertUtils.showProgressAlertDialog(RechargeMyPlan.this);
    }

    @Override
    public void getMeROffer(MyOfferResponse response) {

    }



    @Override
    public void onCausedError(String message) {
        MyAlertUtils.showServerAlertDialog(RechargeMyPlan.this, message);
    }

    @Override
    public void onCustomerInfo(CustomerInfoResponse response) {
        if(response.getCode()==1){
            MyAlertUtils.dismissAlertDialog();
            Intent intent = new Intent(RechargeMyPlan.this, CustomerInfoActivity.class);
            intent.putExtra("infoDetails",response.getoffers().get(0));
            rOfferActivityResultLauncher.launch(intent);
        }
        else{
            MyAlertUtils.showServerAlertDialog(RechargeMyPlan.this, "Failed due to API Issue");
        }
    }

    private void observeState(){
        rechargeViewModel.hlr_state.observe(this, hlr_state -> {
            switch (hlr_state){
                case FETCH:{
                    binding.hlrLayout.setVisibility(View.GONE);
                    binding.operatorImage.setVisibility(View.GONE);
                    binding.mobileRechargeButton.setVisibility(View.GONE);
                    binding.browsePlan.setVisibility(View.GONE);
                    binding.ROffer.setVisibility(View.GONE);
                    binding.dthInfoCard.setVisibility(View.GONE);
                    break;
                }
                case FOUND:{

                    ViewUtils.hideKeyboard(RechargeMyPlan.this);
                    binding.setHlrModel(rechargeViewModel.hlrResponse);
                    BindingUtils.loadSvgUrl(binding.operatorImage, rechargeViewModel.operatorModel.getLogo());
                    binding.hlrLayout.setVisibility(View.VISIBLE);
                    binding.operatorImage.setVisibility(View.VISIBLE);
                    binding.mobileRechargeButton.setVisibility(View.VISIBLE);
                    binding.browsePlan.setVisibility(View.VISIBLE);
                    binding.ROffer.setVisibility(View.GONE);
                    binding.dthInfoCard.setVisibility(View.VISIBLE);
                    break;
                }
            }
        });

        rechargeViewModel.mobileNumber.observe(this, s -> {
            if(s.trim().length()>9){
                rechargeViewModel.getHLRData(RechargeMyPlan.this);
            }
        });

    }


    private void dthMode(){
        ViewUtils.hideKeyboard(RechargeMyPlan.this);
        BindingUtils.loadUrlImage(binding.operatorImage, rechargeViewModel.operatorModel.getLogo());
        binding.hlrLayout.setVisibility(View.GONE);
//        binding.checkHLR.setVisibility(View.GONE);
        binding.operatorImage.setVisibility(View.VISIBLE);
        binding.mobileRechargeButton.setVisibility(View.VISIBLE);
        binding.browsePlan.setVisibility(View.VISIBLE);
        binding.ROffer.setVisibility(View.GONE);
        binding.dthInfoCard.setVisibility(View.VISIBLE);
    }
}