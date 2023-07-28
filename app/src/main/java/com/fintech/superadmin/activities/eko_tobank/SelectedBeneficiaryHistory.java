package com.fintech.superadmin.activities.eko_tobank;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.SearchView;

import androidx.appcompat.app.ActionBar;
import androidx.lifecycle.ViewModelProvider;

import com.fintech.superadmin.activities.common.BaseActivity;
import com.fintech.superadmin.data.eko.EkoDtmTransactionHistory;
import com.fintech.superadmin.data.eko.RecipientListItem;
import com.fintech.superadmin.databinding.ActivityEkoSelectedBenefeciaryHistoryBinding;
import com.fintech.superadmin.listeners.BeneficiaryHistoryListener;
import com.fintech.superadmin.masterListener.NotFoundListener;
import com.fintech.superadmin.util.ViewUtils;
import com.fintech.superadmin.viewmodel.EkoToBankViewModel;

import java.util.Objects;

import dagger.hilt.android.AndroidEntryPoint;

@AndroidEntryPoint
public class SelectedBeneficiaryHistory extends BaseActivity implements BeneficiaryHistoryListener<EkoDtmTransactionHistory>, NotFoundListener {

    EkoToBankViewModel viewModel;
    ActivityEkoSelectedBenefeciaryHistoryBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityEkoSelectedBenefeciaryHistoryBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        Objects.requireNonNull(getSupportActionBar()).setDisplayHomeAsUpEnabled(true);
        ActionBar actionBar = getSupportActionBar();
        actionBar.setTitle("Beneficiary History");
        viewModel = new ViewModelProvider(this).get(EkoToBankViewModel.class);
        viewModel.globalSelectedMobile = getIntent().getStringExtra("number");
        viewModel.selectedBeneficiaryModel = (RecipientListItem) getIntent().getParcelableExtra("selectedBankModel");
        viewModel.notFoundListener = this;
        binding.setSelectedBeneficiaryViewModel(viewModel);
        binding.noRecords.setVisibility(View.GONE);
        int search_plateId = getResources().getIdentifier("android:id/search_plate", null, null);
        View mSearchPlate = binding.historySearchView.findViewById(search_plateId);
        mSearchPlate.setBackgroundColor(Color.TRANSPARENT);
        viewModel.getSelectedBeneficiaryHistory(SelectedBeneficiaryHistory.this,"", binding.selectedBeneficiaryHistory, this);
        initialize();
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
    public void clickOnMoreInfo(View view, EkoDtmTransactionHistory history) {
        Intent intent = new Intent(SelectedBeneficiaryHistory.this, DetailedHistoryScreen.class);
        intent.putExtra("selectedHistory", history);
        startActivity(intent);
    }

    @Override
    public void clickOnUpdateInfo(View view, EkoDtmTransactionHistory history) {
        viewModel.updateDMTTransactionNow(SelectedBeneficiaryHistory.this, history.getData().getClientRefId(), binding.selectedBeneficiaryHistory,this,"selected");
    }

    @Override
    public void notifierScreen(boolean result) {
        if(result){
            binding.noRecords.setVisibility(View.GONE);
        }
        else{
            binding.noRecords.setVisibility(View.VISIBLE);
        }
    }

    @Override
    public void clickOnRefund(View view, EkoDtmTransactionHistory history) {
        if(history.getData().getUtilityAccNo()==null || history.getData().getUtilityAccNo().isEmpty()){
            ViewUtils.showToast(SelectedBeneficiaryHistory.this, "You are not acknowledge to use this service");
        }else{
            viewModel.applyForRefundDmtTransaction(SelectedBeneficiaryHistory.this,this, history.getData().getUtilityAccNo(), history.getData().getClientRefId());
        }
    }

    @Override
    public void bringAllOverHistoryAgain(boolean status) {
        if(status){
            viewModel.getSelectedBeneficiaryHistory(SelectedBeneficiaryHistory.this, "", binding.selectedBeneficiaryHistory, this);
        }
    }

    @Override
    public void loading() {
        binding.progressBar.setVisibility(View.VISIBLE);
        binding.noRecords.setVisibility(View.GONE);
    }

    @Override
    public void notFound() {
        binding.progressBar.setVisibility(View.GONE);
        binding.noRecords.setVisibility(View.VISIBLE);
    }

    @Override
    public void found() {
        binding.progressBar.setVisibility(View.GONE);
        binding.noRecords.setVisibility(View.GONE);
    }

    private void initialize(){


        binding.historySearchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                viewModel.getSelectedBeneficiaryHistory(SelectedBeneficiaryHistory.this,query, binding.selectedBeneficiaryHistory, SelectedBeneficiaryHistory.this);
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                viewModel.getSelectedBeneficiaryHistory(SelectedBeneficiaryHistory.this,newText, binding.selectedBeneficiaryHistory, SelectedBeneficiaryHistory.this);
                return false;
            }
        });
    }
}