package com.fintech.superadmin.activities.addfunds;

//import static com.morpho.android.usb.USBManager.context;

import android.os.Bundle;
import android.os.SystemClock;
import android.speech.tts.TextToSpeech;
import android.view.MenuItem;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;

import com.fintech.superadmin.R;
import com.fintech.superadmin.data.db.AppDatabase;
import com.fintech.superadmin.data.db.entities.User;
import com.fintech.superadmin.data.network.responses.DeviceContext;
import com.fintech.superadmin.data.network.responses.PaymentInstrument;
import com.fintech.superadmin.databinding.ActivityPayBinding;
import com.fintech.superadmin.util.MyAlertUtils;
import com.fintech.superadmin.util.ViewUtils;
import com.fintech.superadmin.viewmodel.FundViewModel;
import com.google.gson.Gson;
import com.phonepe.intent.sdk.api.PhonePe;
import com.phonepe.intent.sdk.api.PhonePeInitException;
import com.phonepe.intent.sdk.api.UPIApplicationInfo;

import java.nio.charset.StandardCharsets;
import java.util.Base64;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Objects;

import dagger.hilt.android.AndroidEntryPoint;

@AndroidEntryPoint
public class PayActivityWebView extends AppCompatActivity {

    ActivityPayBinding binding;
    FundViewModel viewModel;
    TextToSpeech tts;

    private static final int REQUEST_CODE_PHONEPE_PAYMENT = 1001;


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
        makeThePayment();
        PhonePe.init(this);
        tts = new TextToSpeech(PayActivityWebView.this, status -> {
            if (status == TextToSpeech.SUCCESS) {
                int result = tts.setLanguage(Locale.forLanguageTag("hi-IN"));
                if (result == TextToSpeech.LANG_MISSING_DATA || result == TextToSpeech.LANG_NOT_SUPPORTED) {
                    ViewUtils.showToast(PayActivityWebView.this, "Hindi Language is not supported");
                }
            }
        });
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

    private void setListeners() {

        binding.one.setOnClickListener(v -> {
            String value = binding.amountbalance.getText().toString() + binding.one.getText().toString();
            binding.amountbalance.setText(value);
        });

        binding.two.setOnClickListener(v -> {
            String value = binding.amountbalance.getText().toString() + binding.two.getText().toString();
            binding.amountbalance.setText(value);
        });

        binding.three.setOnClickListener(v -> {
            String value = binding.amountbalance.getText().toString() + binding.three.getText().toString();
            binding.amountbalance.setText(value);
        });

        binding.four.setOnClickListener(v -> {
            String value = binding.amountbalance.getText().toString() + binding.four.getText().toString();
            binding.amountbalance.setText(value);
        });

        binding.five.setOnClickListener(v -> {
            String value = binding.amountbalance.getText().toString() + binding.five.getText().toString();
            binding.amountbalance.setText(value);
        });

        binding.six.setOnClickListener(v -> {
            String value = binding.amountbalance.getText().toString() + binding.six.getText().toString();
            binding.amountbalance.setText(value);
        });

        binding.seven.setOnClickListener(v -> {
            String value = binding.amountbalance.getText().toString() + binding.seven.getText().toString();
            binding.amountbalance.setText(value);
        });

        binding.eight.setOnClickListener(v -> {
            String value = binding.amountbalance.getText().toString() + binding.eight.getText().toString();
            binding.amountbalance.setText(value);
        });

        binding.nine.setOnClickListener(v -> {
            String value = binding.amountbalance.getText().toString() + binding.nine.getText().toString();
            binding.amountbalance.setText(value);
        });

        binding.zeo.setOnClickListener(v -> {
            if (!binding.amountbalance.getText().toString().isEmpty()) {
                String value = binding.amountbalance.getText().toString() + binding.zeo.getText().toString();
                binding.amountbalance.setText(value);
            }
        });

        binding.erase.setOnClickListener(v -> {
            StringBuilder builder = new StringBuilder();
            for (int i = 0; i < binding.amountbalance.getText().toString().length() - 1; i++) {
                builder.append(binding.amountbalance.getText().toString().charAt(i));
            }

            binding.amountbalance.setText(String.valueOf(builder));
        });
    }


    private void makeThePayment() {
        binding.onRequestMoneyButton.setOnClickListener(view -> {
            if (binding.amountbalance.getText().toString().isEmpty()) {
                MyAlertUtils.showAlertDialog(PayActivityWebView.this, "Warning", "Provide Valid Amount", R.drawable.warning);
            } else {
                String audioAmount = binding.amountbalance.getText().toString();
                initiateTransaction();
                /**viewModel.madeValidPayment(audioAmount, PayActivityWebView.this, receiver->{
                    initiateTransaction();
                });**/
            }

        });
    }


    private void initiateTransaction(){
        try {
            List<UPIApplicationInfo> upiApps = PhonePe.getUpiApps();
        } catch (PhonePeInitException exception) {
            exception.printStackTrace();
        }
        User user = AppDatabase.getAppDatabase(this).getUserDao().getRegularUser();
        String apiEndPoint = "/pg/v1/pay";
        HashMap data = new HashMap();
        data.put("merchantTransactionId", "PHONEPE"+SystemClock.currentThreadTimeMillis());        //String. Mandatory
        data.put("merchantId", "M43O1HRNE");             //String. Mandatory
        data.put("merchantUserId", "M43O1HRNE");             //String. Conditional
        // merchantUserId - Mandatory if paymentInstrument.type is: PAY_PAGE, CARD, SAVED_CARD, TOKEN.
        // merchantUserId - Optional if paymentInstrument.type is: UPI_INTENT, UPI_COLLECT, UPI_QR.
        data.put("amount",200);                         //Long. Mandatory
        data.put("mobileNumber", user.getMobile());          //String. Optional
        data.put("callbackUrl","https://webhook.site/callback-url");    //String. Mandatory
        PaymentInstrument mPaymentInstrument = new PaymentInstrument("com.phonepe.app", "UPI_INTENT");//String. Mandatory
        data.put("paymentInstrument",mPaymentInstrument);   //OBJECT. Mandatory
        DeviceContext mDeviceContext = new DeviceContext("ANDROID");
        data.put("deviceContext",mDeviceContext);
        String base64Body = encodeToBase64(new Gson().toJson(data));

        ViewUtils.showToast(PayActivityWebView.this, new Gson().toJson(data));
    }

    public static String encodeToBase64(String input) {
        byte[] bytes = input.getBytes(StandardCharsets.UTF_8); // Convert input string to bytes
        byte[] encodedBytes = new byte[0]; // Encode bytes to Base64
        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.O) {
            encodedBytes = Base64.getEncoder().encode(bytes);
        }else{
            encodedBytes = android.util.Base64.encode(bytes, android.util.Base64.DEFAULT);
        }
        return new String(encodedBytes, StandardCharsets.UTF_8); // Convert encoded bytes to string
    }

    @Override
    protected void onDestroy() {
        if (tts != null) {
            tts.stop();
            tts.shutdown();
        }
        super.onDestroy();
    }
}