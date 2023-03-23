package com.fintech.webspidysoftware.activities.microatm;

import android.content.Intent;
import android.os.Bundle;
import android.os.SystemClock;

import androidx.appcompat.app.ActionBar;

import com.fintech.webspidysoftware.R;
import com.fintech.webspidysoftware.activities.common.ShareActivity;
import com.fintech.webspidysoftware.data.db.AppDatabase;
import com.fintech.webspidysoftware.data.db.entities.User;
import com.fintech.webspidysoftware.data.network.responses.AnalyticsResponseModel;
import com.fintech.webspidysoftware.data.network.responses.DetailedHistoryResponse;
import com.fintech.webspidysoftware.databinding.ActivityMicroAtmDetailedAnalyticBinding;
import com.fintech.webspidysoftware.util.ViewUtils;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.Objects;

public class MicroAtmDetailedAnalytic extends ShareActivity {

    String acc_rem_bal = "";
    String myStatus = "";
    String shareResponse = "";
    String cardType = "";
    String transactionType = "";
    User user;
    private long mLastClickTime = 0;
    ActivityMicroAtmDetailedAnalyticBinding binding;
    DetailedHistoryResponse historyResponse;
    AnalyticsResponseModel model;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityMicroAtmDetailedAnalyticBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        Objects.requireNonNull(getSupportActionBar()).setDisplayHomeAsUpEnabled(true);
        ActionBar actionBar = getSupportActionBar();
        actionBar.setTitle("Micro ATM Transaction");
        historyResponse = (DetailedHistoryResponse)getIntent().getSerializableExtra("detailed");
        model = (AnalyticsResponseModel)getIntent().getSerializableExtra("regular");
        binding.setRegularDetailModel(model);
        AppDatabase appDatabase = AppDatabase.getAppDatabase(MicroAtmDetailedAnalytic.this);
        user = appDatabase.getUserDao().getRegularUser();
        setJsonData();

    }


    private void setJsonData() {
        String success = "Success";
        String failed = "Failed";
        try {
            JSONObject object = new JSONObject(historyResponse.getData_response());
            if(object.getString("STATUS").equals("1")){
                binding.gifStatus.setImageResource(R.drawable.success);
                binding.textStatus.setText(success);
                myStatus = success;
            }else{
                binding.gifStatus.setImageResource(R.drawable.failed);
                binding.textStatus.setText(failed);
                myStatus = failed;
            }
            acc_rem_bal = object.getString("BALAMOUNT");
            cardType = object.getString("CARDTYPE");
            transactionType = object.getString("TRANSTYPE");
            binding.responseAccountBalance.setText(acc_rem_bal);
            binding.cardType.setText(cardType);
            binding.transactionType.setText(transactionType);


        } catch (JSONException e) {
            e.printStackTrace();
        }
    }


    private void shareTheData(){

        if (SystemClock.elapsedRealtime() - mLastClickTime < 1000){
            return;
        }
        else{

            if(shareResponse==null){
                shareResponse="nothing";
            }

            Intent whatsappIntent = new Intent(Intent.ACTION_SEND);
            whatsappIntent.setType("text/plain");

            whatsappIntent.putExtra(Intent.EXTRA_TEXT, "Status: "+ myStatus
                    +"\nCard Number: "+model.getOnMobile()
                    +"\nCard Type: "+cardType
                    +"\nBank Name: "+model.getOperator_name()
                    +"\nAmount: "+model.getAmount()
                    +"\nAccount Balance: "+acc_rem_bal
                    +"\nCommission: "+model.getCommission_amount()
                    +"\nOpening Balance: "+model.getAmount_earlier()
                    +"\nClosing Balance: "+model.getAmount_left()
                    +"\nTransaction id: "+model.getTxn_id()
                    +"\nTransaction Type: "+transactionType
                    +"\nDate-Time"+model.getDate()
                    +"\nSystem User: "+user.getName()+" "+user.getLastname()
                    +"\nSystem User Mobile: "+user.getMobile()
            );
            try {

                startActivity(Intent.createChooser(whatsappIntent, "Send Using: "));

            } catch (android.content.ActivityNotFoundException ex) {

                ViewUtils.showToast(MicroAtmDetailedAnalytic.this, "No app found to share..");

            }

        }
        mLastClickTime = SystemClock.elapsedRealtime();
    }
}