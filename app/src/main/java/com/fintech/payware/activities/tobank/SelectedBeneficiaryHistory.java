package com.fintech.payware.activities.tobank;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.SearchView;

import androidx.appcompat.app.ActionBar;
import androidx.lifecycle.ViewModelProvider;

import com.fintech.payware.activities.common.BaseActivity;
import com.fintech.payware.data.network.responses.BeneficiaryBank;
import com.fintech.payware.data.network.responses.BeneficiaryHistoryResponse;
import com.fintech.payware.databinding.ActivitySelectedBenefeciaryHistoryBinding;
import com.fintech.payware.listeners.BeneficiaryHistoryListener;
import com.fintech.payware.masterListener.NotFoundListener;
import com.fintech.payware.util.ViewUtils;
import com.fintech.payware.viewmodel.ToBankViewModel;

import java.util.Objects;

import dagger.hilt.android.AndroidEntryPoint;

@AndroidEntryPoint
public class SelectedBeneficiaryHistory extends BaseActivity implements BeneficiaryHistoryListener, NotFoundListener {

    ToBankViewModel viewModel;
    ActivitySelectedBenefeciaryHistoryBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivitySelectedBenefeciaryHistoryBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        Objects.requireNonNull(getSupportActionBar()).setDisplayHomeAsUpEnabled(true);
        ActionBar actionBar = getSupportActionBar();
        actionBar.setTitle("Beneficiary History");
        viewModel = new ViewModelProvider(this).get(ToBankViewModel.class);
        viewModel.globalSelectedMobile = getIntent().getStringExtra("number");
        viewModel.selectedBeneficiaryModel = (BeneficiaryBank) getIntent().getSerializableExtra("selectedBankModel");
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
    public void clickOnMoreInfo(View view, BeneficiaryHistoryResponse history) {
        Intent intent = new Intent(SelectedBeneficiaryHistory.this, DetailedHistoryScreen.class);
        intent.putExtra("selectedHistory", history);
        startActivity(intent);
    }

    @Override
    public void clickOnUpdateInfo(View view, BeneficiaryHistoryResponse history) {
        viewModel.updateDMTTransactionNow(SelectedBeneficiaryHistory.this, history.getReference_id(), binding.selectedBeneficiaryHistory,this,"selected");
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
    public void clickOnRefund(View view, BeneficiaryHistoryResponse history) {
        if(history.getData().getAckno()==null || history.getData().getAckno().isEmpty()){
            ViewUtils.showToast(SelectedBeneficiaryHistory.this, "You are not acknowledge to use this service");
        }else{
            viewModel.applyForRefundDmtTransaction(SelectedBeneficiaryHistory.this,this, history.getData().getAckno(), history.getReference_id());
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