package com.fintech.superadmin.activities.addfunds;

import android.app.Activity;
import android.os.Bundle;
import android.view.MenuItem;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;

import com.fintech.superadmin.R;
import com.fintech.superadmin.activities.common.BaseActivity;
import com.fintech.superadmin.data.db.AppDatabase;
import com.fintech.superadmin.data.db.entities.RazorpayData;
import com.fintech.superadmin.data.db.entities.User;
import com.fintech.superadmin.databinding.ActivityPayBinding;
import com.fintech.superadmin.util.MyAlertUtils;
import com.fintech.superadmin.viewmodel.FundViewModel;
import com.razorpay.Checkout;
import com.razorpay.PaymentResultListener;

import org.json.JSONObject;

import java.util.Objects;

import dagger.hilt.android.AndroidEntryPoint;

@AndroidEntryPoint
public class PayActivity extends BaseActivity implements PaymentResultListener {

    ActivityPayBinding binding;
    User user;
    FundViewModel viewModel;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityPayBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        Objects.requireNonNull(getSupportActionBar()).setDisplayHomeAsUpEnabled(true);
        viewModel = new ViewModelProvider(this).get(FundViewModel.class);
        binding.setFundViewModel(viewModel);
        binding.amountbalance.setCompoundDrawablesWithIntrinsicBounds(R.drawable.ic_rupee, 0, 0, 0);
        binding.amountbalance.setCompoundDrawablePadding(20);
        setListeners();
        Checkout.preload(PayActivity.this);
        user = AppDatabase.getAppDatabase(PayActivity.this).getUserDao().getRegularUser();
        makeThePayment();
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

            binding.amountbalance.setText(String.valueOf(builder));
        });
    }



    private void makeThePayment(){
        binding.onRequestMoneyButton.setOnClickListener(view -> {
            if(binding.amountbalance.getText().toString().isEmpty()){
                MyAlertUtils.showAlertDialog(PayActivity.this,"Warning","Provide Valid Amount",R.drawable.warning);
            }
            else {
                viewModel.fundRepository.bringTheRazorPayData(PayActivity.this, this::startPayment, binding.amountbalance.getText().toString());
            }

        });
    }

    public void startPayment(RazorpayData data) {


        final Activity activity = this;
        final Checkout co = new Checkout();
        co.setKeyID(data.getKeyid());
        co.setImage(R.drawable.logo);
        try {
            JSONObject options = new JSONObject();
            options.put("name", user.getName()+" "+user.getLastname());
            options.put("description", "order");
            options.put("send_sms_hash",true);
            options.put("allow_rotation", true);
//            options.put("image",data.getImage());
            options.put("order_id", data.getId());
            options.put("currency", data.getCurrency());
            options.put("amount", data.getAmount());
            options.put("callback_url",data.getCallback_url());
            options.put("redirect", true);
            JSONObject preFill = new JSONObject();
            preFill.put("email", user.getEmail());
            preFill.put("contact", user.getMobile());
            options.put("prefill", preFill);

            co.open(activity, options);
        } catch (Exception e) {
            Toast.makeText(activity, "Error in payment: " + e.getMessage(), Toast.LENGTH_SHORT)
                    .show();
            e.printStackTrace();
        }
    }


    @Override
    public void onPaymentSuccess(String razorpayPaymentID) {
        binding.amountbalance.setText("");
        finish();
    }


    @Override
    public void onPaymentError(int code, String response) {
        binding.amountbalance.setText("");
    }

}