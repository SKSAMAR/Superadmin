package com.fintech.superadmin.activities.eko_tobank;

import android.content.ClipData;
import android.content.ClipboardManager;
import android.content.Context;
import android.os.Bundle;
import android.text.Html;
import android.view.MenuItem;

import com.fintech.superadmin.activities.common.BaseActivity;
import com.fintech.superadmin.data.eko.EkoDtmTransactionHistory;
import com.fintech.superadmin.databinding.ActivityEkoDetailedHistoryScreenBinding;
import com.fintech.superadmin.util.ViewUtils;
import com.fintech.superadmin.viewmodel.EkoToBankViewModel;

import java.util.Objects;

public class DetailedHistoryScreen extends BaseActivity {

    ActivityEkoDetailedHistoryScreenBinding binding;
    EkoDtmTransactionHistory history;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityEkoDetailedHistoryScreenBinding.inflate(getLayoutInflater());
        history = (EkoDtmTransactionHistory)getIntent().getParcelableExtra("selectedHistory");
        binding.setWholeInfoModel(history);
        setContentView(binding.getRoot());
        Objects.requireNonNull(getSupportActionBar()).setDisplayHomeAsUpEnabled(true);
        try {
            getSupportActionBar().setTitle(Html.fromHtml("<small>"+history.getMessage()+"</small>"));
            getSupportActionBar().setSubtitle(Html.fromHtml("<small>"+history.getData().getTimestamp()+"</small>"));
        }catch (Exception e){
            e.printStackTrace();
        }

        clicks();
    }

    private void clicks() {
        binding.copyReferenceId.setOnClickListener(v -> {
            ClipboardManager clipboard = (ClipboardManager) getSystemService(Context.CLIPBOARD_SERVICE);
            ClipData clip = ClipData.newPlainText("Copied", ""+history.getData().getClientRefId());
            clipboard.setPrimaryClip(clip);
            ViewUtils.showToast(this,"Copied");
        });
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
    public void onBackPressed() {
        super.onBackPressed();
        EkoToBankViewModel.amount = null;
        EkoToBankViewModel.transType = null;

    }
}