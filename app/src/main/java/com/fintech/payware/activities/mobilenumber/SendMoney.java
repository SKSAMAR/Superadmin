package com.fintech.payware.activities.mobilenumber;

import androidx.appcompat.app.ActionBar;
import com.fintech.payware.activities.common.BaseActivity;
import androidx.lifecycle.ViewModelProvider;

import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;

import com.fintech.payware.R;
import com.fintech.payware.databinding.ActivitySendMoneyBinding;
import com.fintech.payware.listeners.ResetListener;
import com.fintech.payware.util.ExecuteUtil;
import com.fintech.payware.viewmodel.MobileNumberPayViewModel;
import java.util.Objects;

import dagger.hilt.android.AndroidEntryPoint;

@AndroidEntryPoint
public class SendMoney extends BaseActivity implements ResetListener {

    ActivitySendMoneyBinding binding;

    MobileNumberPayViewModel viewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivitySendMoneyBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        Objects.requireNonNull(getSupportActionBar()).setDisplayHomeAsUpEnabled(true);
        ActionBar actionBar = getSupportActionBar();
        actionBar.setTitle("Send Money");
        viewModel = new ViewModelProvider(this).get(MobileNumberPayViewModel.class);
        Bundle bundle = getIntent().getExtras();
        String receiver_id = bundle.getString("receiver_id");
        String receiver_name = bundle.getString("receiver_name");
        String receiver_mobile = bundle.getString("receiver_mobile");
        binding.setSendMoneyViewModel(viewModel);
        viewModel.resetListener= this;
        viewModel.receiver_id = receiver_id;
        viewModel.receiver_mobile = receiver_mobile;
        binding.amountbalance.setCompoundDrawablesWithIntrinsicBounds(R.drawable.ic_rupee, 0, 0, 0);
        binding.amountbalance.setCompoundDrawablePadding(20);
        setListeners();

    }

    private void setListeners(){
        binding.one.setOnClickListener(v -> {
            String value = binding.amountbalance.getText().toString()+binding.one.getText().toString();
            binding.amountbalance.setText(value);
        });

        binding.two.setOnClickListener(v -> {
            String value = binding.amountbalance.getText().toString()+binding.two.getText().toString();
            binding.amountbalance.setText(value);
        });

        binding.three.setOnClickListener(v -> {
            String value = binding.amountbalance.getText().toString()+binding.three.getText().toString();
            binding.amountbalance.setText(value);
        });

        binding.four.setOnClickListener(v -> {
            String value = binding.amountbalance.getText().toString()+binding.four.getText().toString();
            binding.amountbalance.setText(value);
        });

        binding.five.setOnClickListener(v -> {
            String value = binding.amountbalance.getText().toString()+binding.five.getText().toString();
            binding.amountbalance.setText(value);
        });

        binding.six.setOnClickListener(v -> {
            String value = binding.amountbalance.getText().toString()+binding.six.getText().toString();
            binding.amountbalance.setText(value);
        });

        binding.seven.setOnClickListener(v -> {
            String value = binding.amountbalance.getText().toString()+binding.seven.getText().toString();
            binding.amountbalance.setText(value);
        });

        binding.eight.setOnClickListener(v -> {
            String value = binding.amountbalance.getText().toString()+binding.eight.getText().toString();
            binding.amountbalance.setText(value);
        });

        binding.nine.setOnClickListener(v -> {
            String value = binding.amountbalance.getText().toString()+binding.nine.getText().toString();
            binding.amountbalance.setText(value);
        });

        binding.zeo.setOnClickListener(v -> {
            if(!binding.amountbalance.getText().toString().isEmpty()){
                String value = binding.amountbalance.getText().toString()+binding.zeo.getText().toString();
                binding.amountbalance.setText(value);
            }
        });

        binding.erase.setOnClickListener(v -> {
            StringBuilder builder = new StringBuilder();
            for( int i = 0; i< binding.amountbalance.getText().toString().length()-1;i++){
                builder.append(binding.amountbalance.getText().toString().charAt(i));
            }

//            String value = binding.amountbalance.getText().toString()+binding.nine.getText().toString();
            binding.amountbalance.setText(String.valueOf(builder));
        });
    }


    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == android.R.id.home) {// todo: goto back activity from here
            onBackPressed();
            finish();
            return true;
        }
        else if(item.getItemId() == R.id.homePage){
            ExecuteUtil.ThrowOut(this);
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.response_menu, menu);
        menu.getItem(0).setVisible(false);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public void resetRequiredData(boolean result) {
        binding.amountbalance.setText("");
        viewModel.amount = "";
    }
}