package com.fintech.paytoindia.activities.rechargeactivities;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;

import androidx.appcompat.app.ActionBar;

import androidx.appcompat.widget.SearchView;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.GridLayoutManager;
import com.fintech.paytoindia.activities.bbps.BbpsEnter;
import com.fintech.paytoindia.activities.common.BaseActivity;
import com.fintech.paytoindia.adapters.OperatorAdapter;
import com.fintech.paytoindia.data.model.OperatorModel;
import com.fintech.paytoindia.data.network.responses.OperatorResponse;
import com.fintech.paytoindia.databinding.ActivitySelectOperatorBinding;
import com.fintech.paytoindia.fragments.sliders.SliderFragment;
import com.fintech.paytoindia.listeners.OperatorListener;
import com.fintech.paytoindia.viewmodel.MobileRechargeViewModel;

import java.util.List;
import java.util.Objects;

import dagger.hilt.android.AndroidEntryPoint;

@AndroidEntryPoint
public class SelectOperator extends BaseActivity implements OperatorListener {

    ActivitySelectOperatorBinding binding;

    String operatorType;
    OperatorAdapter operatorAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivitySelectOperatorBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        Objects.requireNonNull(getSupportActionBar()).setDisplayHomeAsUpEnabled(true);
        ActionBar actionBar = getSupportActionBar();
        actionBar.setTitle("Select Operator");
        operatorType = getIntent().getStringExtra("operatorType");
        MobileRechargeViewModel viewModel = new ViewModelProvider(this).get(MobileRechargeViewModel.class);
        binding.setSelectOperatorViewModel(viewModel);
        binding.getRoot().setOverScrollMode(View.OVER_SCROLL_NEVER);
        setFragment(new SliderFragment(), binding.selectOperatorSliders);

        viewModel.getOperators(operatorType, SelectOperator.this).observe(this, operatorModelList -> {
            if(operatorModelList!=null){
                setOperators(operatorModelList);
            }
        });


    }

    public void setFragment(Fragment fragment, View view){
        FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();
        fragmentTransaction.replace(view.getId(),fragment);
        fragmentTransaction.commit();
    }

    public void setOperators(List<OperatorModel> list){
        if(list==null || list.isEmpty()){
            noOperatorFound(true);
        }
        binding.operatorsRecycler.setLayoutManager(new GridLayoutManager(SelectOperator.this, 1, GridLayoutManager.VERTICAL, false));
        operatorAdapter = new OperatorAdapter(list, this);
        binding.operatorsRecycler.setAdapter(operatorAdapter);
        binding.operatorsRecycler.setOverScrollMode(View.OVER_SCROLL_NEVER);
        binding.OperatorListCard.setVisibility(View.VISIBLE);
        searchAble();
    }



    public void searchAble(){
        binding.operatorSearchList.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                operatorAdapter.getFilter().filter(query);
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                operatorAdapter.getFilter().filter(newText);
                return false;
            }
        });

    }


    @Override
    public void myOperatorClicks(View view, OperatorModel model) {

        Intent intent;
        try {
            if(MobileRechargeViewModel.service.equals("Mobile\nRecharge") || MobileRechargeViewModel.service.equals("DTH")){
                intent = new Intent(SelectOperator.this, RechargeMyPlan.class);
                intent.putExtra("mode", "dth");
            }
            else{
                intent = new Intent(SelectOperator.this, BbpsEnter.class);
            }
            intent.putExtra("operatorModel", model);
            startActivity(intent);

        }catch (NullPointerException e){
            e.printStackTrace();
        }
    }

    @Override
    public void myOperatorClicks(View view, OperatorResponse.OperatorData data) {

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
    public void noOperatorFound(boolean result) {
        if(result){
            binding.noOperators.setVisibility(View.VISIBLE);
        }
        else{
            binding.noOperators.setVisibility(View.GONE);
        }
    }
}