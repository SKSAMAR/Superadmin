package com.fintech.petoindia.activities.tobank;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.widget.SearchView;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.GridLayoutManager;

import com.fintech.petoindia.activities.common.BaseActivity;
import com.fintech.petoindia.adapters.BanksAdapter;
import com.fintech.petoindia.data.model.BankModel;
import com.fintech.petoindia.databinding.ActivitySelectBankBinding;
import com.fintech.petoindia.listeners.BanksListener;
import com.fintech.petoindia.listeners.ToBankListener;
import com.fintech.petoindia.util.ViewUtils;
import com.fintech.petoindia.viewmodel.ToBankViewModel;

import java.util.ArrayList;
import java.util.Objects;

import dagger.hilt.android.AndroidEntryPoint;


@AndroidEntryPoint
public class SelectBank extends BaseActivity implements BanksListener, ToBankListener {

    ActivitySelectBankBinding binding;
    ProgressDialog dialog;
    BanksAdapter adapter;
    ToBankViewModel viewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivitySelectBankBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        Objects.requireNonNull(getSupportActionBar()).setDisplayHomeAsUpEnabled(true);
        ActionBar actionBar = getSupportActionBar();
        actionBar.setTitle("Select Bank");
        viewModel = new ViewModelProvider(this).get(ToBankViewModel.class);
        viewModel.globalSelectedMobile = getIntent().getStringExtra("number");
        binding.setHomeViewModel(viewModel);
        viewModel.listener = this;
        viewModel.getBankListsOnline();
    }

    @Override
    public void selectedBanks(View view, BankModel model) {

        Intent intent = new Intent(SelectBank.this, AddBeneficiary.class);
        intent.putExtra("number", viewModel.globalSelectedMobile);
        intent.putExtra("selectedBankModel", model);
        startActivity(intent);

    }

    @Override
    public void onStarted(String message) {
        if(dialog == null){
            dialog = new ProgressDialog(this);
        }
        dialog.setTitle(message);
        dialog.show();
    }

    @Override
    public void onCompleted(String message) {
        if(dialog!=null){
            dialog.dismiss();

        }

    }

    @Override
    public void onError(String message) {
        ViewUtils.showToast(SelectBank.this,message);
    }

    @Override
    public void setAllBanks(ArrayList<BankModel> list) {
        adapter = new BanksAdapter(list, this);
        binding.banksRecycler.setLayoutManager(new GridLayoutManager(SelectBank.this, 1, GridLayoutManager.VERTICAL, false));
        binding.banksRecycler.setAdapter(adapter);
        searchAble();
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

    public void searchAble(){
        binding.searchMyBanks.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                adapter.getFilter().filter(query);
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                adapter.getFilter().filter(newText);
                return false;
            }
        });

    }


}