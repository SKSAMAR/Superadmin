package com.fintech.scnpay.activities.creditcard;

import android.content.Intent;
import android.os.Bundle;
import android.os.SystemClock;
import android.view.Menu;
import android.view.MenuItem;

import androidx.appcompat.app.ActionBar;

import com.fintech.scnpay.R;
import com.fintech.scnpay.activities.common.ShareActivity;
import com.fintech.scnpay.data.db.AppDatabase;
import com.fintech.scnpay.data.db.entities.User;
import com.fintech.scnpay.data.network.responses.AnalyticsResponseModel;
import com.fintech.scnpay.data.network.responses.DetailedHistoryResponse;
import com.fintech.scnpay.databinding.ActivityCcdetailedAnalyticBinding;
import com.fintech.scnpay.util.ViewUtils;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.Objects;

public class CCDetailedAnalytic extends ShareActivity {

    String myStatus = "";
    String shareResponse = "";
    String transactionType = "";
    User user;
    private long mLastClickTime = 0;
    DetailedHistoryResponse historyResponse;
    AnalyticsResponseModel model;

    ActivityCcdetailedAnalyticBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityCcdetailedAnalyticBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        Objects.requireNonNull(getSupportActionBar()).setDisplayHomeAsUpEnabled(true);
        ActionBar actionBar = getSupportActionBar();
        actionBar.setTitle("Credit Card Transaction");
        historyResponse = (DetailedHistoryResponse)getIntent().getSerializableExtra("detailed");
        model = (AnalyticsResponseModel)getIntent().getSerializableExtra("regular");
        setJsonData();
        binding.setRegularDetailModel(model);
        AppDatabase appDatabase = AppDatabase.getAppDatabase(CCDetailedAnalytic.this);
        user = appDatabase.getUserDao().getRegularUser();
    }

    private void setJsonData() {
        String success = "Success";
        String pending = "Pending";
        String failed = "Failed";
        String refunded = "Refunded";
        String comStatus = "";
        if(model.getStatus()!=null){
            comStatus = model.getStatus().toLowerCase();
        }
        try {
            JSONObject object = new JSONObject(historyResponse.getData_response());
            JSONObject detObject = new JSONObject(historyResponse.getData_check_response());
            switch (comStatus) {
                case "success":
                    binding.gifStatus.setImageResource(R.drawable.success);
                    binding.textStatus.setText(success);
                    myStatus = success;
                    break;
                case "pending":
                    binding.gifStatus.setImageResource(R.drawable.warning);
                    binding.textStatus.setText(pending);
                    myStatus = pending;
                    break;
                case "refund":
                    binding.gifStatus.setImageResource(R.drawable.success);
                    binding.textStatus.setText(refunded);
                    myStatus = refunded;
                    break;
                default:
                    binding.gifStatus.setImageResource(R.drawable.failed);
                    binding.textStatus.setText(failed);
                    myStatus = failed;
                    break;
            }
            shareResponse = object.getString("message");
            binding.responseMessage.setText(object.getString("message"));
            transactionType = historyResponse.getType_response();
            if(transactionType==null){
                transactionType = "";
            }
            binding.transactionType.setText(transactionType.toUpperCase());

        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == android.R.id.home) {// todo: goto back activity from here
            onBackPressed();
            finish();
            return true;
        }
        else if(item.getItemId() == R.id.share){
            shareTheData();
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.response_menu, menu);
        return super.onCreateOptionsMenu(menu);
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
                    +"\nTransaction Type: "+transactionType
                    +"\nTransaction Card: "+model.getOp_id()
                    +"\nAmount: "+model.getAmount()
                    +"\nCommission: "+model.getCommission_amount()
                    +"\nOpening Balance: "+model.getAmount_earlier()
                    +"\nClosing Balance: "+model.getAmount_left()
                    +"\nTransaction id: "+model.getTxn_id()
                    +"\nResponse: "+shareResponse
                    +"\nDate-Time"+model.getDate()
                    +"\nSystem User: "+user.getName()+" "+user.getLastname()
                    +"\nSystem User Mobile: "+user.getMobile()
            );
            try {

                startActivity(Intent.createChooser(whatsappIntent, "Send Using: "));

            } catch (android.content.ActivityNotFoundException ex) {

                ViewUtils.showToast(CCDetailedAnalytic.this, "No app found to share..");

            }

        }
        mLastClickTime = SystemClock.elapsedRealtime();
    }
}