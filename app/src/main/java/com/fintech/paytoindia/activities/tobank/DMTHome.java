package com.fintech.paytoindia.activities.tobank;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;

import androidx.appcompat.app.ActionBar;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.GridLayoutManager;

import com.fintech.paytoindia.activities.common.BaseActivity;
import com.fintech.paytoindia.adapters.DmtUserAdapter;
import com.fintech.paytoindia.data.network.responses.DmtUserData;
import com.fintech.paytoindia.databinding.ActivityDmthomeBinding;
import com.fintech.paytoindia.listeners.DMTAccountListener;
import com.fintech.paytoindia.listeners.DMTHomeListeners;
import com.fintech.paytoindia.util.DisplayMessageUtil;
import com.fintech.paytoindia.util.MyAlertUtils;
import com.fintech.paytoindia.viewmodel.ToBankViewModel;

import java.util.List;
import java.util.Objects;

import dagger.hilt.android.AndroidEntryPoint;

@AndroidEntryPoint
public class DMTHome extends BaseActivity implements DMTHomeListeners, DMTAccountListener {

    ActivityDmthomeBinding binding;
    ToBankViewModel viewModel;
    DmtUserAdapter adapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityDmthomeBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        Objects.requireNonNull(getSupportActionBar()).setDisplayHomeAsUpEnabled(true);
        ActionBar actionBar = getSupportActionBar();
        actionBar.setTitle("Direct Money Transfer");
        viewModel = new ViewModelProvider(this).get(ToBankViewModel.class);
        binding.setToBankViewModel(viewModel);
        viewModel.toBankRepository.getAllMyDMTAccounts(DMTHome.this, "");
        binding.addMoreBeneficiary.setOnClickListener(v -> startActivity(new Intent(DMTHome.this, QueryRemitter.class)));
        String message = getIntent().getStringExtra("message");
        if(message!=null){
            DisplayMessageUtil.anotherDialogSuccess(DMTHome.this,message);
        }

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
    public void setWholeRecycler(List<DmtUserData> list) {
        binding.dmtUserRecycler.setLayoutManager(new GridLayoutManager(DMTHome.this, 1, GridLayoutManager.VERTICAL, false));
        adapter = new DmtUserAdapter(list, DMTHome.this);
        binding.dmtUserRecycler.setAdapter(adapter);
        MyAlertUtils.dismissAlertDialog();
        if(list==null || list.isEmpty()){
            binding.noRecords.setVisibility(View.VISIBLE);
            binding.userSearchCard.setVisibility(View.VISIBLE);
        }else{
            binding.noRecords.setVisibility(View.GONE);
            binding.userSearchCard.setVisibility(View.VISIBLE);
        }

        searchingPoint();


    }

    private void searchingPoint(){

        binding.searchView.setOnQueryTextListener(new android.widget.SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String s) {
                viewModel.toBankRepository.getAllMyDMTAccounts(DMTHome.this, s);
//                adapter.getFilter().filter(s);
                return false;
            }

            @Override
            public boolean onQueryTextChange(String s) {
                viewModel.toBankRepository.getAllMyDMTAccounts(DMTHome.this, s);
//                adapter.getFilter().filter(s);
                return false;
            }
        });

    }

    @Override
    public void setErrorInFetch(String value) {
        MyAlertUtils.showServerAlertDialog(DMTHome.this, value);
    }

    @Override
    public void initiateStart() {
        MyAlertUtils.showProgressAlertDialog(DMTHome.this);
    }

    @Override
    public void onAccountClick(View view, DmtUserData data) {
        Intent intent = new Intent(DMTHome.this, ToAccount.class);
        intent.putExtra("number", data.getMobile());
        intent.putExtra("status", data.isStatus());
        intent.putExtra("message",data.getMessage());
        startActivity(intent);
    }

    @Override
    public void onRefreshClick(View view, DmtUserData data) {
        viewModel.refreshDMTUserAccount(data.getId(), data.getMobile(), DMTHome.this, DMTHome.this);
    }

    @Override
    public void onDeleteClick(View view, DmtUserData data) {
        viewModel.deleteDMTUserAccount(data.getId(), DMTHome.this, DMTHome.this);
    }

    @Override
    protected void onResume() {
        super.onResume();
    }
}