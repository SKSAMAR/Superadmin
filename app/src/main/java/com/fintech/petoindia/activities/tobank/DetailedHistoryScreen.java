package com.fintech.petoindia.activities.tobank;

import android.content.ClipData;
import android.content.ClipboardManager;
import android.content.Context;
import android.os.Bundle;
import android.text.Html;
import android.view.MenuItem;

import com.fintech.petoindia.activities.common.BaseActivity;
import com.fintech.petoindia.data.network.responses.BeneficiaryHistoryResponse;
import com.fintech.petoindia.databinding.ActivityDetailedHistoryScreenBinding;
import com.fintech.petoindia.util.ViewUtils;
import com.fintech.petoindia.viewmodel.ToBankViewModel;

import java.util.Objects;

public class DetailedHistoryScreen extends BaseActivity {

    ActivityDetailedHistoryScreenBinding binding;
    BeneficiaryHistoryResponse history;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityDetailedHistoryScreenBinding.inflate(getLayoutInflater());
        history = (BeneficiaryHistoryResponse)getIntent().getSerializableExtra("selectedHistory");
        binding.setWholeInfoModel(history);
        setContentView(binding.getRoot());
        Objects.requireNonNull(getSupportActionBar()).setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setTitle(Html.fromHtml("<small>"+history.data.getRemarks()+"</small>"));
        getSupportActionBar().setSubtitle(Html.fromHtml("<small>"+history.getTime()+"</small>"));

        clicks();
    }

    private void clicks() {


        binding.copyReferenceId.setOnClickListener(v -> {
            ClipboardManager clipboard = (ClipboardManager) getSystemService(Context.CLIPBOARD_SERVICE);
            ClipData clip = ClipData.newPlainText("Copied", history.getReference_id());
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
        ToBankViewModel.amount = null;
        ToBankViewModel.transType = null;

    }
}