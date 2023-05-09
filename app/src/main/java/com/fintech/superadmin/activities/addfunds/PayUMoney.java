package com.fintech.superadmin.activities.addfunds;

import android.content.pm.ApplicationInfo;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.MenuItem;
import android.webkit.WebView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.ActionBar;
import androidx.lifecycle.ViewModelProvider;

import com.fintech.superadmin.BuildConfig;
import com.fintech.superadmin.R;
import com.fintech.superadmin.activities.common.BaseActivity;
import com.fintech.superadmin.data.db.entities.User;
import com.fintech.superadmin.databinding.ActivityPayUmoenyBinding;
import com.fintech.superadmin.util.DisplayMessageUtil;
import com.fintech.superadmin.viewmodel.FundViewModel;
import com.google.android.material.snackbar.Snackbar;
import com.payu.base.models.ErrorResponse;
import com.payu.base.models.PayUPaymentParams;
import com.payu.checkoutpro.PayUCheckoutPro;
import com.payu.checkoutpro.models.PayUCheckoutProConfig;
import com.payu.checkoutpro.utils.PayUCheckoutProConstants;
import com.payu.ui.model.listeners.PayUCheckoutProListener;
import com.payu.ui.model.listeners.PayUHashGenerationListener;

import org.json.JSONException;
import org.json.JSONObject;

import java.security.Key;
import java.util.HashMap;
import java.util.Objects;

import javax.crypto.Mac;
import javax.crypto.spec.SecretKeySpec;

import dagger.hilt.android.AndroidEntryPoint;

@AndroidEntryPoint
public class PayUMoney extends BaseActivity {

    FundViewModel viewModel;
    ActivityPayUmoenyBinding binding;
    User user;
    String prodInfo;
    String txnId;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityPayUmoenyBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        Objects.requireNonNull(getSupportActionBar()).setDisplayHomeAsUpEnabled(true);
        ActionBar actionBar = getSupportActionBar();
        actionBar.setTitle("PayU Money");
        viewModel = new ViewModelProvider(this).get(FundViewModel.class);
        binding.setFundViewModel(viewModel);
        user = viewModel.fundRepository.appDatabase.getUserDao().getRegularUser();
        binding.amountbalance.setCompoundDrawablesWithIntrinsicBounds(R.drawable.ic_rupee, 0, 0, 0);
        binding.amountbalance.setCompoundDrawablePadding(20);
        setListeners();
        makeThePayment();
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
                DisplayMessageUtil.error(PayUMoney.this, "Provide Valid Amount");
            } else {

                prodInfo = "Add Wallet";
                txnId = System.currentTimeMillis() + user.getId();
                viewModel.getPayuCredential(PayUMoney.this, data -> {
                    DisplayMessageUtil.dismissDialog();
                    initUiSdk(preparePayUBizParams(data.getKey()));
                }, txnId, binding.amountbalance.getText().toString());
            }

        });
    }

    private PayUPaymentParams preparePayUBizParams(String key) {
        HashMap<String, Object> additionalParams = new HashMap<>();
        additionalParams.put(PayUCheckoutProConstants.CP_UDF1, "");
        additionalParams.put(PayUCheckoutProConstants.CP_UDF2, "");
        additionalParams.put(PayUCheckoutProConstants.CP_UDF3, "");
        additionalParams.put(PayUCheckoutProConstants.CP_UDF4, "");
        additionalParams.put(PayUCheckoutProConstants.CP_UDF5, user.getId());

        PayUPaymentParams.Builder builder = new PayUPaymentParams.Builder();
        builder.setAmount(binding.amountbalance.getText().toString())
                .setIsProduction(true)
                .setProductInfo(prodInfo)
                .setKey(key)
                .setPhone(user.getMobile())
                .setTransactionId(txnId)
                .setFirstName(user.getName())
                .setEmail(user.getEmail())
                .setSurl("https://"+ BuildConfig.APPLICATION_ID+"/Backend/Merchant/API/app/temp/sdkGateway/Payu/callback.php")
                .setFurl("https://"+ BuildConfig.APPLICATION_ID+"/Backend/Merchant/API/app/temp/sdkGateway/Payu/callback.php")
                .setUserCredential(user.getId())
                .setAdditionalParams(additionalParams)
                .setPayUSIParams(null);
        return builder.build();
    }


    private void initUiSdk(PayUPaymentParams payUPaymentParams) {
        PayUCheckoutPro.open(
                this,
                payUPaymentParams,
                getCheckoutProConfig(),
                new PayUCheckoutProListener() {

                    @Override
                    public void onPaymentSuccess(@NonNull Object response) {
                        showAlertDialog(response, true);
                    }

                    @Override
                    public void onPaymentFailure(@NonNull Object response) {
                        showAlertDialog(response, false);
                    }

                    @Override
                    public void onPaymentCancel(boolean isTxnInitiated) {
                        showSnackBar("Transaction Cancelled by the user");
                    }

                    @Override
                    public void onError(@NonNull ErrorResponse errorResponse) {
                        String errorMessage = errorResponse.getErrorMessage();
                        if (TextUtils.isEmpty(errorMessage))
                            errorMessage = "Some error occurred";
                        showSnackBar(errorMessage + "on Error");
                    }

                    @Override
                    public void setWebViewProperties(@Nullable WebView webView, @Nullable Object o) {
                        //For setting webview properties, if any. Check Customized Integration section for more details on this
                    }

                    @Override
                    public void generateHash(@NonNull HashMap<String, String> valueMap, @NonNull PayUHashGenerationListener hashGenerationListener) {
                        String hashName = valueMap.get(PayUCheckoutProConstants.CP_HASH_NAME);
                        String hashData = valueMap.get(PayUCheckoutProConstants.CP_HASH_STRING);

                        if (!TextUtils.isEmpty(hashName) && !TextUtils.isEmpty(hashData)) {
                            //Generate Hash from your backend here
                            String hash;
                            if (hashName.equalsIgnoreCase(PayUCheckoutProConstants.CP_LOOKUP_API_HASH)) {
                                //Calculate HmacSHA1 HASH for calculating Lookup API Hash
                                ///Do not generate hash from local, it needs to be calculated from server side only. Here, hashString contains hash created from your server side.
                                hash = calculateHmacSHA1Hash(hashData, "hash");
                                HashMap<String, String> dataMap = new HashMap<>();
                                dataMap.put(hashName, hash);
                                hashGenerationListener.onHashGenerated(dataMap);

                            } else {
                                //Calculate SHA-512 Hash here
                                viewModel.getPayuHashify(PayUMoney.this, data -> {
                                    HashMap<String, String> dataMap = new HashMap<>();
                                    dataMap.put(hashName, data);
                                    hashGenerationListener.onHashGenerated(dataMap);
                                }, hashData);
                            }
                        }
                    }
                }
        );
    }

    private void showSnackBar(String message) {
        Snackbar.make(binding.getRoot(), message, Snackbar.LENGTH_LONG).show();
    }


    private String getHexString(byte[] array) {
        StringBuilder hash = new StringBuilder();
        for (byte hashByte : array) {
            hash.append(Integer.toString((hashByte & 0xff) + 0x100, 16).substring(1));
        }
        return hash.toString();
    }


    private String calculateHmacSHA1Hash(String data, String key) {
        String HMAC_SHA1_ALGORITHM = "HmacSHA1";
        String result = null;

        try {
            Key signingKey = new SecretKeySpec(key.getBytes(), HMAC_SHA1_ALGORITHM);
            Mac mac = Mac.getInstance(HMAC_SHA1_ALGORITHM);
            mac.init(signingKey);
            byte[] rawHmac = mac.doFinal(data.getBytes());
            result = getHexString(rawHmac);
        } catch (Exception e) {
            e.printStackTrace();
        }

        return result;
    }


    private PayUCheckoutProConfig getCheckoutProConfig() {
        PayUCheckoutProConfig checkoutProConfig = new PayUCheckoutProConfig();
        checkoutProConfig.setPaymentModesOrder(null);
        checkoutProConfig.setOfferDetails(null);
        // uncomment below code for performing enforcement
//        checkoutProConfig.setEnforcePaymentList(getEnforcePaymentList());
        checkoutProConfig.setShowCbToolbar(true);
        checkoutProConfig.setAutoSelectOtp(true);
        checkoutProConfig.setAutoApprove(true);
        checkoutProConfig.setSurePayCount(0);
        checkoutProConfig.setShowExitConfirmationOnPaymentScreen(true);
        checkoutProConfig.setShowExitConfirmationOnCheckoutScreen(true);
        checkoutProConfig.setMerchantName(initNames());
        checkoutProConfig.setMerchantLogo(R.drawable.logo);
        checkoutProConfig.setWaitingTime(30000);
        checkoutProConfig.setMerchantResponseTimeout(30000);
        checkoutProConfig.setCustomNoteDetails(null);
        return checkoutProConfig;
    }

    private String initNames() {
        try {
            PackageManager packageManager = getApplicationContext().getPackageManager();
            ApplicationInfo applicationInfo = packageManager.getApplicationInfo(getApplicationContext().getPackageName(), 0);
            return (String) packageManager.getApplicationLabel(applicationInfo);
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
        }
        return "";
    }

    private void showAlertDialog(Object response, Boolean status) {
        HashMap<String, Object> result = (HashMap<String, Object>) response;

        Object ob = result.get(PayUCheckoutProConstants.CP_PAYU_RESPONSE);
        JSONObject jsonObject;
        try {
            assert ob != null;
            jsonObject = new JSONObject(ob.toString());
            if (status) {
                binding.amountbalance.setText("");
                DisplayMessageUtil.success(PayUMoney.this, jsonObject.getString("status"));
            } else {
                DisplayMessageUtil.error(PayUMoney.this, jsonObject.getString("status"));
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }


}