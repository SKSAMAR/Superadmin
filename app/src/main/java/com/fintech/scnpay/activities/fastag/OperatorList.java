package com.fintech.scnpay.activities.fastag;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;

import androidx.appcompat.app.ActionBar;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.GridLayoutManager;

import com.fintech.scnpay.activities.common.BaseActivity;
import com.fintech.scnpay.adapters.APIOperatorAdapter;
import com.fintech.scnpay.data.model.OperatorModel;
import com.fintech.scnpay.data.network.responses.OperatorResponse;
import com.fintech.scnpay.databinding.ActivityOperatoListBinding;
import com.fintech.scnpay.listeners.FastAgListener;
import com.fintech.scnpay.listeners.OperatorListener;
import com.fintech.scnpay.viewmodel.MobileRechargeViewModel;

import java.util.List;
import java.util.Objects;

import dagger.hilt.android.AndroidEntryPoint;

@AndroidEntryPoint
public class OperatorList extends BaseActivity implements OperatorListener, FastAgListener {

    MobileRechargeViewModel viewModel;
    ActivityOperatoListBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityOperatoListBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        Objects.requireNonNull(getSupportActionBar()).setDisplayHomeAsUpEnabled(true);
        ActionBar actionBar = getSupportActionBar();
        actionBar.setTitle("FASTag");
        viewModel = new ViewModelProvider(this).get(MobileRechargeViewModel.class);
        viewModel.bring_fastAgList(OperatorList.this, OperatorList.this);
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
    public void myOperatorClicks(View view, OperatorResponse.OperatorData data) {
        Intent intent = new Intent(OperatorList.this, FastagFetchBill.class);
        intent.putExtra("fastAgOP", data);
        startActivity(intent);
    }

    @Override
    public void noOperatorFound(boolean result) {

    }

    @Override
    public void myOperatorClicks(View view, OperatorModel model) {

    }

    @Override
    public void setRecycler(List<OperatorResponse.OperatorData> data) {
        if(data==null || data.isEmpty()){
            binding.noRecords.setVisibility(View.VISIBLE);
            binding.opUserRecycler.setVisibility(View.GONE);
            binding.opSearchCard.setVisibility(View.GONE);
        }
        else{
            binding.opSearchCard.setVisibility(View.VISIBLE);
            APIOperatorAdapter adapter = new APIOperatorAdapter(data, OperatorList.this);
            binding.opUserRecycler.setLayoutManager(new GridLayoutManager(OperatorList.this, 1, GridLayoutManager.VERTICAL, false));
            binding.opUserRecycler.setAdapter(adapter);
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
    }
}