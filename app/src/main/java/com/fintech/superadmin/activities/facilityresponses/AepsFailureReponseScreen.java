package com.fintech.superadmin.activities.facilityresponses;

import android.content.Intent;
import android.os.Bundle;
import android.os.SystemClock;
import android.view.View;

import androidx.appcompat.app.ActionBar;

import com.fintech.superadmin.activities.aeps.CashWithdrawal;
import com.fintech.superadmin.activities.common.ShareActivity;
import com.fintech.superadmin.data.db.AppDatabase;
import com.fintech.superadmin.data.db.entities.User;
import com.fintech.superadmin.databinding.ActivityAepsFailureReponseScreenBinding;
import com.fintech.superadmin.util.ViewUtils;
import com.fintech.superadmin.viewmodel.AepsViewModel;

import java.util.Objects;

public class AepsFailureReponseScreen extends ShareActivity {

    private long mLastClickTime = 0;
    ActivityAepsFailureReponseScreenBinding binding;
    User user;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityAepsFailureReponseScreenBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        Objects.requireNonNull(getSupportActionBar()).setDisplayHomeAsUpEnabled(true);
        ActionBar actionBar = getSupportActionBar();
        actionBar.setTitle("Failed");
        binding.ackno.setText(String.valueOf(AepsViewModel.globalAePSBalanceEnquiryResponse.getAckno()));
        binding.clientrefno.setText(String.valueOf(AepsViewModel.globalAePSBalanceEnquiryResponse.getClientrefno()));
        binding.message.setText(String.valueOf(AepsViewModel.globalAePSBalanceEnquiryResponse.getMessage()));
        binding.fromBank.setText(AepsViewModel.selectedAepsBankModel.getBankname());

        AppDatabase appDatabase = AppDatabase.getAppDatabase(AepsFailureReponseScreen.this);
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
                            + "\nReference ID: "+AepsViewModel.globalAePSBalanceEnquiryResponse.getClientrefno()
                            +"\nBank: "+AepsViewModel.selectedAepsBankModel.getBankname()+
                            "\nRemarks: "+AepsViewModel.globalAePSBalanceEnquiryResponse.getMessage()
                            +"\nSystem User: "+user.getName()+" "+user.getLastname()
                            +"\nSystem User Mobile: "+user.getMobile()
                    );
                    try {

                        startActivity(Intent.createChooser(whatsappIntent, "Send Using: "));

                    } catch (android.content.ActivityNotFoundException ex) {

                        ViewUtils.showToast(AepsFailureReponseScreen.this, "No app found to share..");

                    }

                }
                mLastClickTime = SystemClock.elapsedRealtime();
        }

    public void takeItCashWithdrawal(View view) {
        Intent intent = new Intent(AepsFailureReponseScreen.this, CashWithdrawal.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        startActivity(intent);
    }
}