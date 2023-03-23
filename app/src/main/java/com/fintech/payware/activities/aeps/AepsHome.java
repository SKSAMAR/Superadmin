package com.fintech.payware.activities.aeps;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;

import androidx.appcompat.app.ActionBar;
import com.fintech.payware.activities.common.BaseActivity;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.GridLayoutManager;

import com.fintech.payware.R;
import com.fintech.payware.adapters.MenuAdapter;
import com.fintech.payware.data.model.MenuModel;
import com.fintech.payware.databinding.ActivityAepsHomeBinding;
import com.fintech.payware.listeners.RecyclerViewClickListener;
import com.fintech.payware.viewmodel.AepsViewModel;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import dagger.hilt.android.AndroidEntryPoint;

@AndroidEntryPoint
public class AepsHome extends BaseActivity implements RecyclerViewClickListener {

    ActivityAepsHomeBinding binding;
    private long mLastClickTime = 0;
    AepsViewModel viewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityAepsHomeBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        Objects.requireNonNull(getSupportActionBar()).setDisplayHomeAsUpEnabled(true);
        ActionBar actionBar = getSupportActionBar();
        actionBar.setTitle("AePS");
        viewModel = new ViewModelProvider(this).get(AepsViewModel.class);
        binding.setAepsHomeViewModel(viewModel);
        setHomeMenus();
    }


    private void setHomeMenus(){

        binding.aepsHomeMenu.setLayoutManager(new GridLayoutManager(this, 4, GridLayoutManager.VERTICAL, false));
        List<MenuModel> moneyTransferList = new ArrayList<>();
        moneyTransferList.add(new MenuModel(R.drawable.ic_balance_enquiry_new,"Balance\nEnquiry"));
        moneyTransferList.add(new MenuModel(R.drawable.ic_mini_statment_new,"Mini\nStatement"));
        moneyTransferList.add(new MenuModel(R.drawable.ic_withdrawal_new,"Cash\nWithdrawal"));
        moneyTransferList.add(new MenuModel(R.drawable.ic_aadharpay_new,"Aadhaar\nPay"));
        binding.aepsHomeMenu.setAdapter(new MenuAdapter(moneyTransferList, this));
        binding.aepsHomeMenu.setOverScrollMode(View.OVER_SCROLL_NEVER);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == android.R.id.home) {// todo: goto back activity from here
            onBackPressed();
            finish();
            return true;
        }
        return true;
    }
    @Override
    public void onRecyclerViewClickItem(View view, MenuModel model) {
        switch (model.getTitle()) {
            case "Balance\nEnquiry":
                startActivity(new Intent(AepsHome.this, BalanceEnquiry.class));
                break;
            case "Cash\nWithdrawal":
                Intent intent = new Intent(AepsHome.this, CashWithdrawal.class);
                intent.putExtra("aepsType","CW");
                startActivity(intent);
                break;
            case "Aadhaar\nPay":
                Intent intent_fix = new Intent(AepsHome.this, CashWithdrawal.class);
                intent_fix.putExtra("aepsType","M");
                startActivity(intent_fix);
                break;
            case "Mini\nStatement":
                startActivity(new Intent(AepsHome.this, MiniStatement.class));
                break;
        }
    }
}