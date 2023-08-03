package com.fintech.superadmin.activities.payoutpaysprint;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;

import androidx.appcompat.app.ActionBar;
import com.fintech.superadmin.activities.common.BaseActivity;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.GridLayoutManager;

import com.fintech.superadmin.R;
import com.fintech.superadmin.adapters.PaysprintPayoutAdapter;
import com.fintech.superadmin.data.network.responses.PayoutList;
import com.fintech.superadmin.databinding.ActivityPaysprintPayoutBinding;
import com.fintech.superadmin.listeners.PayoutHomeListener;
import com.fintech.superadmin.util.MyAlertUtils;
import com.fintech.superadmin.viewmodel.PayoutViewModel;
import java.util.List;
import java.util.Objects;


import dagger.hilt.android.AndroidEntryPoint;


@AndroidEntryPoint
public class PaysprintPayout extends BaseActivity implements PayoutHomeListener {
    PayoutViewModel viewModel;
    ActivityPaysprintPayoutBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityPaysprintPayoutBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        Objects.requireNonNull(getSupportActionBar()).setDisplayHomeAsUpEnabled(true);
        ActionBar actionBar = getSupportActionBar();
        actionBar.setTitle("Payout Accounts");
        viewModel = new ViewModelProvider(this).get(PayoutViewModel.class);
        binding.setPayoutViewModel(viewModel);
        viewModel.repository.paySprintPayoutList(PaysprintPayout.this);
        int search_plateId = getResources().getIdentifier("android:id/search_plate", null, null);
        View mSearchPlate = binding.searchView.findViewById(search_plateId);
        mSearchPlate.setBackgroundColor(Color.TRANSPARENT);
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
    public void setWholeRecycler(List<PayoutList> list) {

        binding.dmtUserRecycler.setLayoutManager(new GridLayoutManager(PaysprintPayout.this, 1, GridLayoutManager.VERTICAL, false));
        PaysprintPayoutAdapter adapter = new PaysprintPayoutAdapter(list, PaysprintPayout.this);
        binding.dmtUserRecycler.setAdapter(adapter);
        MyAlertUtils.dismissAlertDialog();
        if(list==null || list.isEmpty()){
            binding.noRecords.setVisibility(View.VISIBLE);
            binding.userSearchCard.setVisibility(View.GONE);
        }else{
            binding.noRecords.setVisibility(View.GONE);
            binding.userSearchCard.setVisibility(View.VISIBLE);
        }
        binding.searchView.setOnQueryTextListener(new android.widget.SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String s) {
                adapter.getFilter().filter(s);
                return false;
            }

            @Override
            public boolean onQueryTextChange(String s) {
                adapter.getFilter().filter(s);
                return false;
            }
        });

    }

    @Override
    public void setErrorInFetch(String value) {
        MyAlertUtils.showServerAlertDialog(PaysprintPayout.this, value);
    }

    @Override
    public void initiateStart() {
        MyAlertUtils.showProgressAlertDialog(PaysprintPayout.this);
    }

    @Override
    public void openBeneficiary(View view, PayoutList data) {
        try {
            if (data.getVerified().equals("1") && data.getStatus().equals("1")) {
                viewModel.sendPayoutScreen(data.getBeneid(), PaysprintPayout.this, data);
            } else {
                MyAlertUtils.showServerAlertDialog(PaysprintPayout.this, "Un Eligible, Please verify the account and Check Status");
            }
        } catch (Exception e) {
            MyAlertUtils.showServerAlertDialog(PaysprintPayout.this, "Un Eligible. due to\n" + e.getLocalizedMessage());
            e.printStackTrace();
        }
    }

    @Override
    public void verifyPayoutBeneficiary(View view, PayoutList data) {
        try {
            Intent intent = new Intent(PaysprintPayout.this, VerifyAccounts.class);
            intent.putExtra("bene_id", data.getBeneid());
            intent.putExtra("account_no", data.getAccount());
            startActivity(intent);
        } catch (Exception e) {
            MyAlertUtils.showServerAlertDialog(PaysprintPayout.this, "Un Eligible. due to\n" + e.getLocalizedMessage());
            e.printStackTrace();
        }
    }

    @Override
    public void checkStatusPayoutBeneficiary(View view, PayoutList data) {
        try {
            viewModel.repository.checkVerificationStatus(PaysprintPayout.this, data.getBeneid(), data.getAccount(), result -> viewModel.repository.paySprintPayoutList(PaysprintPayout.this));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void deleteBeneficiary(View view, PayoutList data) {
//        ViewUtils.showToast(view.getContext(), data.getId());
    }
}