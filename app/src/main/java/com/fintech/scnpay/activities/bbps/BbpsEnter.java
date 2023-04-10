package com.fintech.scnpay.activities.bbps;

import android.os.Bundle;
import android.view.MenuItem;

import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.NavController;
import androidx.navigation.fragment.NavHostFragment;

import com.fintech.scnpay.R;
import com.fintech.scnpay.activities.common.BaseActivity;
import com.fintech.scnpay.data.model.MenuModel;
import com.fintech.scnpay.databinding.ActivityBbpsEnterBinding;
import com.fintech.scnpay.pagestate.BBPSState;
import com.fintech.scnpay.util.StartGettingLocation;
import com.fintech.scnpay.viewmodel.MobileRechargeViewModel;

import java.util.Objects;

import dagger.hilt.android.AndroidEntryPoint;

@AndroidEntryPoint
public class BbpsEnter extends BaseActivity {

    ActivityBbpsEnterBinding binding;
    NavController navController;
    MobileRechargeViewModel viewModel;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityBbpsEnterBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        Objects.requireNonNull(getSupportActionBar()).setDisplayHomeAsUpEnabled(true);
        Objects.requireNonNull(getSupportActionBar()).setTitle("BBPS");
        viewModel = new ViewModelProvider(this).get(MobileRechargeViewModel.class);
//      viewModel.operatorModel = (OperatorModel) getIntent().getSerializableExtra("operatorModel");
        viewModel.menuModel = (MenuModel) getIntent().getSerializableExtra("menuModel");
        NavHostFragment navHostFragment = (NavHostFragment) getSupportFragmentManager().findFragmentById(R.id.fragment_container);
        navController = Objects.requireNonNull(navHostFragment).getNavController();
        binding.setDirectToHomeViewModel(viewModel);
        manageState();
        StartGettingLocation.setAllTheLocations(BbpsEnter.this);
        binding.setLifecycleOwner(this);

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


    private void manageState(){
        viewModel.bBBPsState.observe(this, state->{
            if(state!=null){
                if(state == BBPSState.SELECTION_OF_BILLER){
                    navController.navigate(R.id.selectionOfBiller);
                }
                else if(state == BBPSState.BILL_FETCH){
                    navController.navigate(R.id.billFetch);
                }
                else {
                    navController.navigate(R.id.billPayment);
                }
            }
        });
    }

}