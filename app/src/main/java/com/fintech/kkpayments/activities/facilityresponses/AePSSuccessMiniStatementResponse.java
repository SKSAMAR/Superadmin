package com.fintech.kkpayments.activities.facilityresponses;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

import androidx.appcompat.app.ActionBar;
import com.fintech.kkpayments.activities.common.BaseActivity;
import androidx.recyclerview.widget.GridLayoutManager;

import com.fintech.kkpayments.R;
import com.fintech.kkpayments.activities.aeps.CashWithdrawal;
import com.fintech.kkpayments.adapters.MiniStatementAdapter;
import com.fintech.kkpayments.databinding.ActivityAePssuccessMiniStatementResponseBinding;
import com.fintech.kkpayments.util.ExecuteUtil;
import com.fintech.kkpayments.viewmodel.AepsViewModel;

import java.util.Objects;

public class AePSSuccessMiniStatementResponse extends BaseActivity {

    ActivityAePssuccessMiniStatementResponseBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityAePssuccessMiniStatementResponseBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        Objects.requireNonNull(getSupportActionBar()).setDisplayHomeAsUpEnabled(true);
        ActionBar actionBar = getSupportActionBar();
        actionBar.setTitle("Statement");
        setData();
        setRecyclerView();
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
        return super.onCreateOptionsMenu(menu);
    }


    private void setRecyclerView(){
        binding.miniStateRecycler.setLayoutManager(new GridLayoutManager(AePSSuccessMiniStatementResponse.this, 1, GridLayoutManager.VERTICAL, false));
        binding.miniStateRecycler.setAdapter(new MiniStatementAdapter(AepsViewModel.globalMiniStatementResponse.getMinistatement()));
    }

    private void setData(){
        binding.givenAckno.setText(String.valueOf(AepsViewModel.globalMiniStatementResponse.getAckno()));
        binding.givenBalance.setText(AepsViewModel.globalMiniStatementResponse.getBalanceamount());
        binding.givenBankName.setText(AepsViewModel.selectedAepsBankModel.getBankname());
        binding.givenDateTime.setText(AepsViewModel.globalMiniStatementResponse.getDatetime());
    }

    public void takeItCashWithdrawal(View view) {
        Intent intent = new Intent(AePSSuccessMiniStatementResponse.this, CashWithdrawal.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        startActivity(intent);
    }
}