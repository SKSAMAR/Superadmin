package com.fintech.superadmin.activities.addfunds;

import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModelProvider;

import com.fintech.superadmin.R;
import com.fintech.superadmin.activities.common.BaseActivity;
import com.fintech.superadmin.data.db.AppDatabase;
import com.fintech.superadmin.data.db.entities.User;
import com.fintech.superadmin.data.network.APIServices;
import com.fintech.superadmin.databinding.ActivityAddFundListBinding;
import com.fintech.superadmin.util.NetworkUtil;
import com.fintech.superadmin.viewmodel.FundViewModel;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import javax.inject.Inject;

import dagger.hilt.android.AndroidEntryPoint;

@AndroidEntryPoint
public class AddFundList extends BaseActivity {

    @Inject
    APIServices apiServices;
    ActivityAddFundListBinding binding;
    private final MutableLiveData<List<String>> additionalGatewaysList = new MutableLiveData<>();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityAddFundListBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        Objects.requireNonNull(getSupportActionBar()).setDisplayHomeAsUpEnabled(true);
        ActionBar actionBar = getSupportActionBar();
        actionBar.setTitle("Add Fund");
        FundViewModel viewModel = new ViewModelProvider(this).get(FundViewModel.class);
        binding.setFundViewModel(viewModel);
        checkAvailability();
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            onBackPressed();
            finish();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    private void checkAvailability() {
        String type = getString(R.string.app_type);
        if (type.trim().equalsIgnoreCase("b2c")){
            binding.walletExchangeLayout.setVisibility(View.GONE);
        }
        NetworkUtil.getNetworkResult(apiServices.getGatewayLists("getGatewayLists"), this, additionalGatewaysList::setValue);
        additionalGatewaysList.observe(this, result -> {
            try {
                if (result != null && !result.isEmpty()) {
                    for (String name : result) {
                        if (name != null && name.trim().equalsIgnoreCase("UPIGATEWAY")) {
                            binding.upiGatewayApi.setVisibility(View.VISIBLE);
                        }
                        if (name != null && name.trim().equalsIgnoreCase("PAYU")) {
                            binding.payuGatewayApi.setVisibility(View.VISIBLE);
                        }
                        if (name != null && name.trim().equalsIgnoreCase("CASHFREE")) {
                            binding.cashFreeContainer.setVisibility(View.VISIBLE);
                        }
                    }
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        });
    }
}