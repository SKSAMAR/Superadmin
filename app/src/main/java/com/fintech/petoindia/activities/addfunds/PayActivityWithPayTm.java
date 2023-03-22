//package com.fintech.paytoindia.activities.addfunds;
//
//import android.content.Intent;
//import android.os.Bundle;
//import android.util.Log;
//import android.view.MenuItem;
//
//import androidx.annotation.Nullable;
//import androidx.appcompat.app.ActionBar;
//import androidx.appcompat.app.AppCompatActivity;
//import androidx.lifecycle.ViewModelProvider;
//
//import com.fintech.paytoindia.util.ViewUtils;
//import com.paytm.pgsdk.PaytmOrder;
//import com.paytm.pgsdk.PaytmPaymentTransactionCallback;
//import com.paytm.pgsdk.TransactionManager;
//import com.fintech.paytoindia.R;
//import com.fintech.paytoindia.data.db.AppDatabase;
//import com.fintech.paytoindia.data.db.entities.User;
//import com.fintech.paytoindia.data.network.responses.PaytmTokenInformation;
//import com.fintech.paytoindia.databinding.ActivityPayWithPayTmBinding;
//import com.fintech.paytoindia.listeners.ResponseTypeListener;
//import com.fintech.paytoindia.util.MyAlertUtils;
//import com.fintech.paytoindia.viewmodel.FundViewModel;
//
//import org.json.JSONException;
//import org.json.JSONObject;
//
//import java.util.Objects;
//
//import dagger.hilt.android.AndroidEntryPoint;
//
//@AndroidEntryPoint
//public class PayActivityWithPayTm extends AppCompatActivity implements ResponseTypeListener {
//
//    ActivityPayWithPayTmBinding binding;
//
//    User user;
//    FundViewModel viewModel;
//    private final Integer activityRequestCode = 2;
//    String orderId = "";
//    String amount = "";
//
//    @Override
//    protected void onCreate(Bundle savedInstanceState) {
//        super.onCreate(savedInstanceState);
//        binding = ActivityPayWithPayTmBinding.inflate(getLayoutInflater());
//        setContentView(binding.getRoot());
//        Objects.requireNonNull(getSupportActionBar()).setDisplayHomeAsUpEnabled(true);
//        ActionBar actionBar = getSupportActionBar();
//        actionBar.setTitle("Desired Amount");
//        viewModel = new ViewModelProvider(this).get(FundViewModel.class);
//        binding.setFundViewModel(viewModel);
//        binding.amountbalance.setCompoundDrawablesWithIntrinsicBounds(R.drawable.ic_rupee, 0, 0, 0);
//        binding.amountbalance.setCompoundDrawablePadding(20);
//        setListeners();
//        user = AppDatabase.getAppDatabase(PayActivityWithPayTm.this).getUserDao().getRegularUser();
//        makeThePayment();
//    }
//
//    @Override
//    public boolean onOptionsItemSelected(MenuItem item) {
//        if (item.getItemId() == android.R.id.home) {// todo: goto back activity from here
//            onBackPressed();
//            finish();
//            return true;
//        }
//        return super.onOptionsItemSelected(item);
//    }
//
//    private void setListeners(){
//        binding.one.setOnClickListener(v -> {
//            String value = binding.amountbalance.getText().toString()+binding.one.getText().toString();
//            binding.amountbalance.setText(value);
//        });
//
//        binding.two.setOnClickListener(v -> {
//            String value = binding.amountbalance.getText().toString()+binding.two.getText().toString();
//            binding.amountbalance.setText(value);
//        });
//
//        binding.three.setOnClickListener(v -> {
//            String value = binding.amountbalance.getText().toString()+binding.three.getText().toString();
//            binding.amountbalance.setText(value);
//        });
//
//        binding.four.setOnClickListener(v -> {
//            String value = binding.amountbalance.getText().toString()+binding.four.getText().toString();
//            binding.amountbalance.setText(value);
//        });
//
//        binding.five.setOnClickListener(v -> {
//            String value = binding.amountbalance.getText().toString()+binding.five.getText().toString();
//            binding.amountbalance.setText(value);
//        });
//
//        binding.six.setOnClickListener(v -> {
//            String value = binding.amountbalance.getText().toString()+binding.six.getText().toString();
//            binding.amountbalance.setText(value);
//        });
//
//        binding.seven.setOnClickListener(v -> {
//            String value = binding.amountbalance.getText().toString()+binding.seven.getText().toString();
//            binding.amountbalance.setText(value);
//        });
//
//        binding.eight.setOnClickListener(v -> {
//            String value = binding.amountbalance.getText().toString()+binding.eight.getText().toString();
//            binding.amountbalance.setText(value);
//        });
//
//        binding.nine.setOnClickListener(v -> {
//            String value = binding.amountbalance.getText().toString()+binding.nine.getText().toString();
//            binding.amountbalance.setText(value);
//        });
//
//        binding.zeo.setOnClickListener(v -> {
//            if(!binding.amountbalance.getText().toString().isEmpty()){
//                String value = binding.amountbalance.getText().toString()+binding.zeo.getText().toString();
//                binding.amountbalance.setText(value);
//            }
//        });
//
//        binding.erase.setOnClickListener(v -> {
//            StringBuilder builder = new StringBuilder();
//            for( int i = 0; i< binding.amountbalance.getText().toString().length()-1;i++){
//                builder.append(binding.amountbalance.getText().toString().charAt(i));
//            }
//            binding.amountbalance.setText(String.valueOf(builder));
//        });
//    }
//
//
//
//    private void makeThePayment(){
//        binding.onRequestMoneyButton.setOnClickListener(view -> {
//            if(binding.amountbalance.getText().toString().isEmpty()){
//                MyAlertUtils.showAlertDialog(PayActivityWithPayTm.this,"Warning","Provide Valid Amount",R.drawable.warning);
//            }
//            else {
//                orderId = System.currentTimeMillis()+"";
//                ViewUtils.showToast(getApplicationContext(), orderId);
//                getTheToken();
//            }
//
//        });
//    }
//
//    @Override
//    public void onResponseStart() {
//        MyAlertUtils.showProgressAlertDialog(PayActivityWithPayTm.this);
//    }
//
//    @Override
//    public void onResponse(PaytmTokenInformation tokenInformation) {
//        if(tokenInformation.getResponse().getBody().getTxnToken()!=null){
//            MyAlertUtils.dismissAlertDialog();
//            startPaytmPayment(tokenInformation.getResponse().getBody().getTxnToken(), tokenInformation.getMid());
//        }
//        else{
//            MyAlertUtils.showServerAlertDialog(PayActivityWithPayTm.this, "Something is Wrong on Server, Ask Admin to fix on Server");
//        }
//    }
//
//
//    @Override
//    public void onError(String message) {
//        MyAlertUtils.showServerAlertDialog(PayActivityWithPayTm.this, message);
//    }
//
//
//
//
//    private void getTheToken(){
//        String chkCode = "12345";
//        amount = binding.amountbalance.getText().toString();
//        viewModel.fundRepository.bringPaytmToken(PayActivityWithPayTm.this,chkCode,orderId,amount);
//    }
//
//
//    private void startPaytmPayment(String txnToken, String merchant_id) {
//        user = AppDatabase.getAppDatabase(PayActivityWithPayTm.this).getUserDao().getRegularUser();
//        String host = "https://securegw.paytm.in/";
//
//        String callBackUrl = host + "theia/paytmCallback?ORDER_ID="+orderId+"?USID="+ user.getId();
//        PaytmOrder paytmOrder =  new PaytmOrder(orderId,merchant_id,txnToken,binding.amountbalance.getText().toString(),callBackUrl);
//        TransactionManager transactionManager = new TransactionManager(paytmOrder, new PaytmPaymentTransactionCallback() {
//            @Override
//            public void onTransactionResponse(@Nullable Bundle bundle) {
//                Log.e("Payment", Objects.requireNonNull(bundle).toString());
//
//            }
//
//            @Override
//            public void networkNotAvailable() {
//                MyAlertUtils.showServerAlertDialog(PayActivityWithPayTm.this, "Network not available");
//            }
//
//            @Override
//            public void onErrorProceed(String s) {
//                MyAlertUtils.showServerAlertDialog(PayActivityWithPayTm.this, s);
//            }
//
//            @Override
//            public void clientAuthenticationFailed(String s) {
//                MyAlertUtils.showServerAlertDialog(PayActivityWithPayTm.this, s);
//            }
//
//            @Override
//            public void someUIErrorOccurred(String s) {
//
//            }
//
//            @Override
//            public void onErrorLoadingWebPage(int i, String s, String s1) {
//
//            }
//
//            @Override
//            public void onBackPressedCancelTransaction() {
//
//                MyAlertUtils.dismissAlertDialog();
//
//            }
//
//            @Override
//            public void onTransactionCancel(String s, Bundle bundle) {
//                MyAlertUtils.dismissAlertDialog();
//            }
//        });
//
//        transactionManager.setShowPaymentUrl(host + "theia/api/v1/showPaymentPage");
//        transactionManager.startTransaction(PayActivityWithPayTm.this, activityRequestCode);
//
//    }
//
//    @Override
//    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
//        super.onActivityResult(requestCode, resultCode, data);
//        if (activityRequestCode == requestCode &&data !=null)
//        {
//            Bundle bundle =  data.getExtras();
//            String status = null;
//            if (bundle!=null)
//            {
//                try {
//                    JSONObject object = new JSONObject(data.getStringExtra("response"));
//                    status = object.getString("STATUS");
//                } catch (JSONException e) {
//                    e.printStackTrace();
//                }
//                if(status!=null && status.trim().equals("TXN_SUCCESS")){
//                    MyAlertUtils.showAlertDialog(PayActivityWithPayTm.this, status, status, R.drawable.success);
//                    binding.amountbalance.setText("");
//                }
//                else{
//                    MyAlertUtils.showServerAlertDialog(PayActivityWithPayTm.this, status);
//                }
//            }
//
//        }
//        else
//        {
//            MyAlertUtils.showServerAlertDialog(PayActivityWithPayTm.this, "Some Error took place");
//        }
//    }
//
//}