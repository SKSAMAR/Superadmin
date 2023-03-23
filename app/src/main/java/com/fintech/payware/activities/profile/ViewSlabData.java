package com.fintech.payware.activities.profile;

import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;

import com.fintech.payware.activities.common.BaseActivity;
import androidx.recyclerview.widget.GridLayoutManager;

import com.fintech.payware.adapters.SlabAdapter;
import com.fintech.payware.data.network.responses.SlabInfoResponse;
import com.fintech.payware.databinding.ActivityViewSlabDataBinding;

import java.util.ArrayList;
import java.util.Objects;

public class ViewSlabData extends BaseActivity {

    ActivityViewSlabDataBinding binding;
    String package_name = "";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityViewSlabDataBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        package_name = getIntent().getStringExtra("package_name");
        Objects.requireNonNull(getSupportActionBar()).setTitle(package_name);
        Objects.requireNonNull(getSupportActionBar()).setDisplayHomeAsUpEnabled(true);
        setAllData();
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

    private void setAllData(){
        ArrayList<SlabInfoResponse> list = (ArrayList<SlabInfoResponse>)getIntent().getSerializableExtra("slabInfoResponse");
        binding.slabDataInfo.setLayoutManager(new GridLayoutManager(ViewSlabData.this, 1, GridLayoutManager.VERTICAL, false));
        binding.slabDataInfo.setAdapter(new SlabAdapter(list));
        binding.slabDataInfo.setOverScrollMode(View.OVER_SCROLL_NEVER);

        String maxAmount = "0";
        try {
            for(int i = 0; i < list.size(); i++){
                if(Double.parseDouble(list.get(i).getAmount()) > Double.parseDouble(maxAmount)){
                    maxAmount = list.get(i).getAmount();
                }
            }
        }catch (NullPointerException e){
            e.fillInStackTrace();
        }
        String note = "Note : "+package_name +" Earn Maximum Rs. "+ maxAmount;
        binding.noteText.setText(note);
    }

}