package com.fintech.superadmin.activities.utilactivities;

import android.graphics.Color;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;

import androidx.annotation.Nullable;
import androidx.appcompat.app.ActionBar;
import com.fintech.superadmin.activities.common.BaseActivity;
import androidx.recyclerview.widget.GridLayoutManager;

import com.fintech.superadmin.databinding.ActivityUtilHistoryBinding;
import com.fintech.superadmin.deer_listener.NotRecords;

import java.util.Objects;

public class UtilHistory extends BaseActivity implements NotRecords {

    public GridLayoutManager gridLayoutManager;
    public ActivityUtilHistoryBinding binding;
    public ActionBar actionBar;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityUtilHistoryBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        Objects.requireNonNull(getSupportActionBar()).setDisplayHomeAsUpEnabled(true);
        actionBar = getSupportActionBar();
        int search_plateId = getResources().getIdentifier("android:id/search_plate", null, null);
        View mSearchPlate = binding.searchView.findViewById(search_plateId);
        mSearchPlate.setBackgroundColor(Color.TRANSPARENT);
        initialize();
    }

    private void initialize(){
        gridLayoutManager = new GridLayoutManager(UtilHistory.this, 1, GridLayoutManager.VERTICAL, false);
        binding.recyclerView.setOverScrollMode(View.OVER_SCROLL_NEVER);
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
    public void notFound(boolean result) {
        if(result){
            binding.noRecords.setVisibility(View.VISIBLE);
        }else{
            binding.noRecords.setVisibility(View.GONE);
        }
    }
}
