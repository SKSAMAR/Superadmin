package com.fintech.petoindia.activities.facilityresponses;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.os.SystemClock;
import android.view.View;

import androidx.appcompat.app.ActionBar;

import com.fintech.petoindia.activities.aeps.CashWithdrawal;
import com.fintech.petoindia.activities.common.ShareActivity;
import com.fintech.petoindia.data.db.AppDatabase;
import com.fintech.petoindia.data.db.entities.User;
import com.fintech.petoindia.databinding.ActivityAePsresponseScreenSuccessBinding;
import com.fintech.petoindia.util.ViewUtils;
import com.fintech.petoindia.viewmodel.AepsViewModel;

import java.util.Objects;

public class AePSSuccessResponseScreen extends ShareActivity {

    User user;
    private long mLastClickTime = 0;
    ActivityAePsresponseScreenSuccessBinding binding;

    @SuppressLint("SetTextI18n")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityAePsresponseScreenSuccessBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        Objects.requireNonNull(getSupportActionBar()).setDisplayHomeAsUpEnabled(true);
        ActionBar actionBar = getSupportActionBar();
        actionBar.setTitle("Success");
        binding.ackno.setText(String.valueOf(AepsViewModel.globalAePSBalanceEnquiryResponse.getAckno()));
        binding.amount.setText(String.valueOf(AepsViewModel.globalAePSBalanceEnquiryResponse.getAmount()));
        binding.balanceamount.setText(String.valueOf(AepsViewModel.globalAePSBalanceEnquiryResponse.getBalanceamount()));
        binding.clientrefno.setText(String.valueOf(AepsViewModel.globalAePSBalanceEnquiryResponse.getClientrefno()));
        binding.lastAadhar.setText("**** **** "+String.valueOf(AepsViewModel.globalAePSBalanceEnquiryResponse.last_aadhar));
        binding.message.setText(String.valueOf(AepsViewModel.globalAePSBalanceEnquiryResponse.getMessage()));
        binding.name.setText(String.valueOf(AepsViewModel.globalAePSBalanceEnquiryResponse.getName()));
        binding.fromBank.setText(AepsViewModel.selectedAepsBankModel.getBankname());

        AppDatabase appDatabase = AppDatabase.getAppDatabase(AePSSuccessResponseScreen.this);
        user = appDatabase.getUserDao().getRegularUser();

    }


//    @Override
//    public boolean onOptionsItemSelected(MenuItem item) {
//        if (item.getItemId() == android.R.id.home) {// todo: goto back activity from here
//            onBackPressed();
//            finish();
//            return true;
//        }
//        else if(item.getItemId() == R.id.share){
//            shareTheData();
//        }
//        else if(item.getItemId() == R.id.homePage){
//            ExecuteUtil.ThrowOut(this);
//        }
//        return super.onOptionsItemSelected(item);
//    }
//
//    @Override
//    public boolean onCreateOptionsMenu(Menu menu) {
//        getMenuInflater().inflate(R.menu.response_menu, menu);
//        return super.onCreateOptionsMenu(menu);
//    }

    private void shareTheData(){

        if (SystemClock.elapsedRealtime() - mLastClickTime < 1000){
            return;
        }
        else{

            Intent whatsappIntent = new Intent(Intent.ACTION_SEND);
            whatsappIntent.setType("text/plain");

            whatsappIntent.putExtra(Intent.EXTRA_TEXT, "Acknowledgement no: "+ AepsViewModel.globalAePSBalanceEnquiryResponse.getAckno()
                    +"\nStatus: "+"Failed"
                    +"\nRemaining Balance: "+AepsViewModel.globalAePSBalanceEnquiryResponse.getBalanceamount()
                    +"\nAmount: "+AepsViewModel.globalAePSBalanceEnquiryResponse.getAmount()
                    +"\nAadhaar: "+"**** **** **** "+AepsViewModel.globalAePSBalanceEnquiryResponse.getLast_aadhar()
                    +"\nName: "+AepsViewModel.globalAePSBalanceEnquiryResponse.getName()
                    + "\nReference ID: "+AepsViewModel.globalAePSBalanceEnquiryResponse.getClientrefno()
                    +"\nBank: "+AepsViewModel.selectedAepsBankModel.getBankname()+
                    "\nRemarks: "+AepsViewModel.globalAePSBalanceEnquiryResponse.getMessage()
                    +"\nSystem User: "+user.getName()+" "+user.getLastname()
                    +"\nSystem User Mobile: "+user.getMobile()
            );
            try {

                startActivity(Intent.createChooser(whatsappIntent, "Send Using: "));

            } catch (android.content.ActivityNotFoundException ex) {

                ViewUtils.showToast(AePSSuccessResponseScreen.this, "No app found to share..");

            }

        }
        mLastClickTime = SystemClock.elapsedRealtime();
    }

    public void takeItCashWithdrawal(View view) {
        Intent intent = new Intent(AePSSuccessResponseScreen.this, CashWithdrawal.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        startActivity(intent);
    }
}