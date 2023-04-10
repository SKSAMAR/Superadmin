package com.fintech.scnpay.activities.pins;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;

import com.fintech.scnpay.activities.common.BaseActivity;
import androidx.lifecycle.ViewModelProvider;

import com.fintech.scnpay.R;
import com.fintech.scnpay.data.network.responses.RegularResponse;
import com.fintech.scnpay.deer_listener.TaskListener;
import com.fintech.scnpay.util.ViewUtils;
import com.fintech.scnpay.viewmodel.ProfileViewModel;

import java.util.Objects;

import dagger.hilt.android.AndroidEntryPoint;

@AndroidEntryPoint
public class PinActivity extends BaseActivity  implements TaskListener<RegularResponse> {

    CreateMPinFragment createMPinFragment;
    ChangeMPinFragment changeMPinFragment;
    ProfileViewModel viewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pin);
        Objects.requireNonNull(getSupportActionBar()).setTitle("M-Pin");
        Objects.requireNonNull(getSupportActionBar()).setDisplayHomeAsUpEnabled(true);
        viewModel = new ViewModelProvider(this).get(ProfileViewModel.class);
        viewModel.taskListener = this;
        checkPage();
    }

    private void checkPage(){
       String task =  getIntent().getStringExtra("action_given");
       if(task.equals("CREATE")){
           createMPinFragment = new CreateMPinFragment();
           getSupportFragmentManager().beginTransaction()
                   .replace(R.id.my_fragment_container, createMPinFragment, "CREATE")
                   .commit();
       }
       else if(task.equals("CHANGE")){
           changeMPinFragment = new ChangeMPinFragment();
           getSupportFragmentManager().beginTransaction()
                   .replace(R.id.my_fragment_container, changeMPinFragment, "CHANGE")
                   .commit();
       }
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
    public void onBackPressed() {
        if(viewModel._pinState.getValue()!=null && viewModel._pinState.getValue().equals(ProfileViewModel.M_PIN_STATE.VERIFICATION)){
            viewModel._pinState.setValue(ProfileViewModel.M_PIN_STATE.CREATION);
            return;
        }
        super.onBackPressed();
    }

    @Override
    public void onLoading() {

    }

    @Override
    public void onResponse(RegularResponse data) {
        Intent intentData = new Intent();
        intentData.putExtra("action_status", data.isStatus());
        intentData.putExtra("action_result", data.getMessage());
        ViewUtils.showToast(PinActivity.this, data.getMessage());
        setResult(Activity.RESULT_OK, intentData);
        finish();
    }

    @Override
    public void onError(String message) {

    }
}