package com.fintech.superadmin.activities.eko_tobank;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.widget.SearchView;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.GridLayoutManager;

import com.fintech.superadmin.activities.common.BaseActivity;
import com.fintech.superadmin.adapters.BanksAdapter;
import com.fintech.superadmin.adapters.EkoBanksAdapter;
import com.fintech.superadmin.data.model.BankModel;
import com.fintech.superadmin.data.model.EkoBankModel;
import com.fintech.superadmin.databinding.ActivityEkoSelectBankBinding;
import com.fintech.superadmin.listeners.BanksListener;
import com.fintech.superadmin.listeners.EkoBanksListener;
import com.fintech.superadmin.listeners.ToBankListener;
import com.fintech.superadmin.listeners.ToEkoBankListener;
import com.fintech.superadmin.util.ViewUtils;
import com.fintech.superadmin.viewmodel.EkoToBankViewModel;

import java.util.ArrayList;
import java.util.Objects;

import dagger.hilt.android.AndroidEntryPoint;


@AndroidEntryPoint
public class SelectBank extends BaseActivity implements EkoBanksListener, ToEkoBankListener {

    ActivityEkoSelectBankBinding binding;
    ProgressDialog dialog;
    EkoBanksAdapter adapter;
    EkoToBankViewModel viewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityEkoSelectBankBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        Objects.requireNonNull(getSupportActionBar()).setDisplayHomeAsUpEnabled(true);
        ActionBar actionBar = getSupportActionBar();
        actionBar.setTitle("Select Bank");
        viewModel = new ViewModelProvider(this).get(EkoToBankViewModel.class);
        viewModel.globalSelectedMobile = getIntent().getStringExtra("number");
        binding.setHomeViewModel(viewModel);
        viewModel.listener = this;
        viewModel.getBankListsOnline();
    }

    @Override
    public void selectedBanks(View view, EkoBankModel model) {

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
    public void setAllBanks(ArrayList<EkoBankModel> list) {
        adapter = new EkoBanksAdapter(list, this);
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