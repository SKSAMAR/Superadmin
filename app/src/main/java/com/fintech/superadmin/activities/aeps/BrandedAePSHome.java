package com.fintech.superadmin.activities.aeps;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;

import androidx.annotation.Nullable;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.fintech.superadmin.R;
import com.fintech.superadmin.activities.aeps.brandedComp.AllString;
import com.fintech.superadmin.activities.aeps.brandedComp.CommonBankList;
import com.fintech.superadmin.activities.aeps.brandedComp.DeviceDataModel;
import com.fintech.superadmin.activities.aeps.brandedComp.RecyclerViewClickListener;
import com.fintech.superadmin.activities.aeps.brandedComp.TopUsesBankAdaptor;
import com.fintech.superadmin.activities.aeps.brandedComp.util;
import com.fintech.superadmin.activities.aeps.brandedComp.utilDevices;
import com.fintech.superadmin.activities.common.BaseActivity;
import com.fintech.superadmin.data.model.AEPSBanksModel;
import com.fintech.superadmin.databinding.WActivityDashboardAepsBinding;
import com.fintech.superadmin.listeners.ResetListener;
import com.fintech.superadmin.util.AadhaarVerify;
import com.fintech.superadmin.util.AuthorizedSingleton;
import com.fintech.superadmin.util.Constant;
import com.fintech.superadmin.util.StartGettingLocation;
import com.fintech.superadmin.util.UtilHolder;
import com.fintech.superadmin.viewmodel.AepsViewModel;

import java.util.ArrayList;
import java.util.Objects;

import dagger.hilt.android.AndroidEntryPoint;

@AndroidEntryPoint
public class BrandedAePSHome extends BaseActivity implements View.OnClickListener, RecyclerViewClickListener, ResetListener {

    String apiType = "";
    private String txnType = "BE";
    private String IciciPidData;
    private Context context = this;
    private DeviceDataModel morphoDeviceData;
    private Double minAmount = -1.0D;
    private Double maxAmount = 100000.0D;
    private TopUsesBankAdaptor topBankAdaptor = null;
    private ArrayList<AEPSBanksModel> topBankList;
    private RecyclerViewClickListener recyclerViewClick = this;
    AepsViewModel viewModel;
    WActivityDashboardAepsBinding binding;

    public BrandedAePSHome() {
        Constant.toReset = false;
        setPidData();
    }

    private void setPidData() {
        IciciPidData = "<?xml version=\"1.0\"?><PidOptions ver=\"1.0\"><Opts env=\"P\" fCount=\"1\" fType=\"2\" iCount=\"0\" format=\"0\" pidVer=\"2.0\" timeout=\"10000\" posh=\"UNKNOWN\" /></PidOptions>";
    }


    @SuppressLint({"SetTextI18n", "UseCompatLoadingForDrawables"})
    void inIt() {

        try {
            viewModel.bank = getIntent().getStringExtra("bank");
            apiType = getIntent().getStringExtra("apiName");
            if (apiType.trim().equalsIgnoreCase("fingPay")) {
                topBankList = new ArrayList<>();
                topBankList.add(new AEPSBanksModel("5", "State Bank of India", "607094", "1", R.drawable.sbi));
                topBankList.add(new AEPSBanksModel("2", "Bank of India", "508505", "1", R.drawable.boi));
                topBankList.add(new AEPSBanksModel("27", "Union Bank of India", "607161", "1", R.drawable.unionbank));
                topBankList.add(new AEPSBanksModel("10", "Punjab National Bank", "607027", "1", R.drawable.punjabnationalbank));
                topBankList.add(new AEPSBanksModel("40", "India Post Payment Bank", "608314", "1", R.drawable.indiapostpaymentbank));
                topBankList.add(new AEPSBanksModel("28", "HDFC Bank", "607152", "1", R.drawable.hdfc));
                topBankList.add(new AEPSBanksModel("8", "Indian bank", "607105", "1", R.drawable.indianbank));
                topBankList.add(new AEPSBanksModel("34", "Baroda Uttar Pradesh Gramin Bank", "606993", "1", R.drawable.barodaup));

            } else {
                topBankList = new ArrayList<>();
                topBankList.add(new AEPSBanksModel("85", "State Bank of India", "607094", "1", R.drawable.sbi));
                topBankList.add(new AEPSBanksModel("14", "Bank of India", "508505", "1", R.drawable.boi));
                topBankList.add(new AEPSBanksModel("94", "Union Bank of India", "607161", "1", R.drawable.unionbank));
                topBankList.add(new AEPSBanksModel("65", "Punjab National Bank", "607027", "1", R.drawable.punjabnationalbank));
                topBankList.add(new AEPSBanksModel("40", "India Post Payment Bank", "608314", "1", R.drawable.indiapostpaymentbank));
                topBankList.add(new AEPSBanksModel("35", "HDFC Bank", "607152", "1", R.drawable.hdfc));
                topBankList.add(new AEPSBanksModel("41", "Indian bank", "607105", "1", R.drawable.indianbank));
                topBankList.add(new AEPSBanksModel("18", "Baroda Uttar Pradesh Gramin Bank", "606993", "1", R.drawable.barodaup));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }


        binding.btnColor1.setOnClickListener(this);
        binding.btnColor2.setOnClickListener(this);
        binding.btnColor3.setOnClickListener(this);
        binding.btnColor4.setOnClickListener(this);
        String selectedDevice = AllString.getValue(this, AllString.SELECTED_DEVICEONE);
        if (selectedDevice != null && selectedDevice.length() > 0) {
            String device = "Withdraw from " + selectedDevice;
            String device1 = "" + selectedDevice;
            binding.textDeviceName.setText(device1);
            binding.textLinearDevice.setText(device);
            if (selectedDevice.equalsIgnoreCase("Mantra")) {
                binding.imgDevice.setImageResource(R.drawable.w_mantrimages);
            } else if (selectedDevice.equalsIgnoreCase("Morpho")) {
                binding.imgDevice.setImageResource(R.drawable.w_moprhoimages);
            } else if (selectedDevice.equalsIgnoreCase("Tatvik")) {
                binding.imgDevice.setImageResource(R.drawable.w_tatvikimagesl);
            } else if (selectedDevice.equalsIgnoreCase("Startek")) {
                binding.imgDevice.setImageResource(R.drawable.w_startekimage);
            } else if (selectedDevice.equalsIgnoreCase("Secugen")) {
                binding.imgDevice.setImageResource(R.drawable.w_secugenimages);
            } else if (selectedDevice.equalsIgnoreCase("Evolute")) {
                binding.imgDevice.setImageResource(R.drawable.w_evoluteimages);
            } else if (selectedDevice.equalsIgnoreCase("NextBio")) {
                binding.imgDevice.setImageResource(R.drawable.w_nextbioimages);
            }
        } else {
            this.deviceSelection();
        }

    }


    @SuppressLint({"UseCompatLoadingForDrawables"})
    public void onClick(View view, int position) {
        (new util()).hideKeyBoard(view, this);
        binding.BankNameTextView.setText(((AEPSBanksModel) this.topBankList.get(position)).getBankname());
        binding.linearAllBank.setBackground(this.getResources().getDrawable(R.drawable.w_lauout_bank_background));
        AepsViewModel.selectedAepsBankModel = topBankList.get(position);
        this.isAllInputValid();
    }


    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = WActivityDashboardAepsBinding.inflate(getLayoutInflater());
        this.setContentView(binding.getRoot());
        preSetup();
        viewModel = new ViewModelProvider(this).get(AepsViewModel.class);
        Objects.requireNonNull(getSupportActionBar()).setDisplayHomeAsUpEnabled(true);
        ActionBar actionBar = getSupportActionBar();
        actionBar.setTitle("AePS");
        StartGettingLocation.setAllTheLocations(BrandedAePSHome.this);
        this.inIt();
        this.setOnClick();
        insertLatLongApi();
        BankList();
    }


    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            onBackPressed();
            finish();
            return true;
        }
        return true;
    }

    @SuppressLint({"UseCompatLoadingForDrawables"})
    private void setOnClick() {
        binding.textChangeDeviceName.setOnClickListener((v) -> this.deviceSelection());
        binding.linearCheckBalance.setOnClickListener((v) -> {
            if (this.txnType.equalsIgnoreCase("emp")) {
                (new util()).snackBar(binding.mainLayout, "Please Select TransactionType First", AllString.SnackBarBackGroundColor);
            } else if (this.txnType.equalsIgnoreCase("BE")) {
                if (binding.BankNameTextView.getText().toString().trim().equalsIgnoreCase("VIEW ALL BANK")) {
                    (new util()).snackBar(binding.mainLayout, "Please Select bank First", AllString.SnackBarBackGroundColor);
                } else if (binding.MobileNoEditText.getText().toString().trim().length() < 10) {
                    (new util()).snackBar(binding.mainLayout, "Enter Valid Mobile Number", AllString.SnackBarBackGroundColor);
                } else if (AadhaarVerify.validateVerhoeff(binding.aadharNoEditText.getText().toString().replace("-", ""))) {
                    this.scanDevice();
                } else {
                    (new util()).snackBar(binding.mainLayout, "Please enter valid Aadhar Number!", AllString.SnackBarBackGroundColor);
                }
            } else if (this.txnType.equalsIgnoreCase("MS")) {
                if (binding.BankNameTextView.getText().toString().trim().equalsIgnoreCase("VIEW ALL BANK")) {
                    (new util()).snackBar(binding.mainLayout, "Please Select bank First", AllString.SnackBarBackGroundColor);
                } else if (binding.MobileNoEditText.getText().toString().trim().length() < 10) {
                    (new util()).snackBar(binding.mainLayout, "Enter Valid Mobile Number", AllString.SnackBarBackGroundColor);
                } else if (AadhaarVerify.validateVerhoeff(binding.aadharNoEditText.getText().toString().replace("-", ""))) {
                    this.scanDevice();
                } else {
                    (new util()).snackBar(binding.mainLayout, "Please enter valid Aadhar Number!", AllString.SnackBarBackGroundColor);
                }
            }

        });
        binding.linearDeviceButton.setOnClickListener((v) -> {
            if (this.txnType.equalsIgnoreCase("emp")) {
                (new util()).snackBar(binding.mainLayout, "Please Select txTyp First", AllString.SnackBarBackGroundColor);
            } else if (this.txnType.equalsIgnoreCase("CW")) {
                if (binding.BankNameTextView.getText().toString().trim().equalsIgnoreCase("VIEW ALL BANK")) {
                    (new util()).snackBar(binding.mainLayout, "Please Select bank First", AllString.SnackBarBackGroundColor);
                } else if (binding.amountEditText.getText().toString().isEmpty()) {
                    (new util()).snackBar(binding.mainLayout, "Enter Amount First!", AllString.SnackBarBackGroundColor);
                } else if (this.minAmount > (double) Float.parseFloat(binding.amountEditText.getText().toString())) {
                    (new util()).snackBar(binding.mainLayout, "Amount should be more than ₹" + AuthorizedSingleton.getInstance().getMinAmount() + "!", AllString.SnackBarBackGroundColor);
                } else if (this.maxAmount < (double) Float.parseFloat(binding.amountEditText.getText().toString())) {
                    (new util()).snackBar(binding.mainLayout, "Amount should be less than ₹" + AuthorizedSingleton.getInstance().getMaxAmount() + "!", AllString.SnackBarBackGroundColor);
                } else if (binding.MobileNoEditText.getText().toString().trim().length() < 10) {
                    (new util()).snackBar(binding.mainLayout, "Enter Valid Mobile Number", AllString.SnackBarBackGroundColor);
                } else if (AadhaarVerify.validateVerhoeff(binding.aadharNoEditText.getText().toString().replace("-", ""))) {
                    this.scanDevice();
                } else {
                    (new util()).snackBar(binding.mainLayout, "Please enter valid Aadhar Number!", AllString.SnackBarBackGroundColor);
                }
            } else if (this.txnType.equalsIgnoreCase("M")) {
                if (binding.BankNameTextView.getText().toString().trim().equalsIgnoreCase("VIEW ALL BANK")) {
                    (new util()).snackBar(binding.mainLayout, "Please Select bank First", AllString.SnackBarBackGroundColor);
                } else if (binding.amountEditText.getText().toString().isEmpty()) {
                    (new util()).snackBar(binding.mainLayout, "Enter Amount First!", AllString.SnackBarBackGroundColor);
                } else if (this.minAmount > (double) Float.parseFloat(binding.amountEditText.getText().toString())) {
                    (new util()).snackBar(binding.mainLayout, "Amount should be more than ₹" + AuthorizedSingleton.getInstance().getMinAmount() + "!", AllString.SnackBarBackGroundColor);
                } else if (this.maxAmount < (double) Float.parseFloat(binding.amountEditText.getText().toString())) {
                    (new util()).snackBar(binding.mainLayout, "Amount should be less than ₹" + AuthorizedSingleton.getInstance().getMaxAmount() + "!", AllString.SnackBarBackGroundColor);
                } else if (AadhaarVerify.validateVerhoeff(binding.aadharNoEditText.getText().toString().replace("-", ""))) {

                    this.scanDevice();


                } else if (binding.MobileNoEditText.getText().toString().trim().length() < 10) {
                    (new util()).snackBar(binding.mainLayout, "Enter Valid Mobile Number", AllString.SnackBarBackGroundColor);
                } else {
                    (new util()).snackBar(binding.mainLayout, "Please enter valid Aadhar Number!", AllString.SnackBarBackGroundColor);
                }
            }

        });
        this.isAllInputValid();
        binding.aadharNoEditText.addTextChangedListener(new TextWatcher() {
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            public void onTextChanged(CharSequence s, int start, int before, int count) {
                String temp = binding.aadharNoEditText.getText().toString();
                binding.aadharNoEditText.removeTextChangedListener(this);
                if (count == 0) {
                    if (temp.length() == 5 || temp.length() == 10) {
                        binding.aadharNoEditText.setText(temp.substring(0, temp.length() - 1));
                    }
                } else if (temp.length() >= 1 && temp.length() <= 10) {

                    if (temp.length() == 4 || temp.length() == 9) {
                        binding.aadharNoEditText.setText(temp.substring(0, temp.length()) + "-");
                    } else if (temp.length() == 5 || temp.length() == 10) {
                        binding.aadharNoEditText.setText(temp.substring(0, temp.length() - 1) + "-" + temp.charAt(temp.length() - 1));
                    }
                }
                binding.aadharNoEditText.addTextChangedListener(this);
                Log.w("test", "card No: " + binding.aadharNoEditText.getText().toString().replace("-", ""));

                BrandedAePSHome.this.isAllInputValid();
            }

            public void afterTextChanged(Editable s) {
                binding.aadharNoEditText.setSelection(binding.aadharNoEditText.getText().length());
            }
        });
        binding.amountEditText.addTextChangedListener(new TextWatcher() {
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            public void onTextChanged(CharSequence s, int start, int before, int count) {
            }

            public void afterTextChanged(Editable s) {
            }
        });
        Log.e("abhishek:", "" + CommonBankList.getInstance().getBankListData());
    }


    @SuppressLint("UseCompatLoadingForDrawables")
    private void preSetup() {
        binding.linearCashAmount.setVisibility(View.GONE);
        String transType = getIntent().getStringExtra("aepsType");
        if (transType != null && !transType.trim().isEmpty()) {
            txnType = transType;
            if (txnType.equals("BE")) {
                BrandedAePSHome.this.txnType = "BE";
                binding.linearCheckBalance.setVisibility(View.VISIBLE);
                binding.linearCashAmount.setVisibility(View.GONE);
                binding.linearDeviceButton.setVisibility(View.GONE);
                binding.linearAadhaar.setBackground(BrandedAePSHome.this.getResources().getDrawable(R.drawable.curve_rect_gray));
                binding.linearCashWith.setBackground(BrandedAePSHome.this.getResources().getDrawable(R.drawable.curve_rect_gray));
                binding.linearBalance.setBackground(BrandedAePSHome.this.getResources().getDrawable(R.drawable.layout_bg_blue_head));
                binding.linearMiniState.setBackground(BrandedAePSHome.this.getResources().getDrawable(R.drawable.curve_rect_gray));
                binding.textBalance.setText("Check Balance");
            } else if (txnType.equals("CW")) {
                BrandedAePSHome.this.txnType = "CW";
                binding.linearCheckBalance.setVisibility(View.GONE);
                binding.linearCashAmount.setVisibility(View.VISIBLE);
                binding.linearDeviceButton.setVisibility(View.VISIBLE);
                binding.linearBalance.setBackground(BrandedAePSHome.this.getResources().getDrawable(R.drawable.curve_rect_gray));
                binding.linearAadhaar.setBackground(BrandedAePSHome.this.getResources().getDrawable(R.drawable.curve_rect_gray));
                binding.linearCashWith.setBackground(BrandedAePSHome.this.getResources().getDrawable(R.drawable.layout_bg_blue_head));
                binding.linearMiniState.setBackground(BrandedAePSHome.this.getResources().getDrawable(R.drawable.curve_rect_gray));

            } else if (txnType.equals("MS")) {
                BrandedAePSHome.this.txnType = "MS";
                binding.linearCheckBalance.setVisibility(View.VISIBLE);
                binding.linearCashAmount.setVisibility(View.GONE);
                binding.linearDeviceButton.setVisibility(View.GONE);
                binding.linearMiniState.setBackground(BrandedAePSHome.this.getResources().getDrawable(R.drawable.layout_bg_blue_head));
                binding.linearAadhaar.setBackground(BrandedAePSHome.this.getResources().getDrawable(R.drawable.curve_rect_gray));
                binding.linearBalance.setBackground(BrandedAePSHome.this.getResources().getDrawable(R.drawable.curve_rect_gray));
                binding.linearCashWith.setBackground(BrandedAePSHome.this.getResources().getDrawable(R.drawable.curve_rect_gray));
                binding.textBalance.setText("Mini Statement");
            } else if (txnType.equals("M")) {

                BrandedAePSHome.this.txnType = "M";
                binding.linearCheckBalance.setVisibility(View.GONE);
                binding.linearCashAmount.setVisibility(View.VISIBLE);
                binding.linearDeviceButton.setVisibility(View.VISIBLE);
                binding.linearAadhaar.setBackground(BrandedAePSHome.this.getResources().getDrawable(R.drawable.layout_bg_blue_head));
                binding.linearCashWith.setBackground(BrandedAePSHome.this.getResources().getDrawable(R.drawable.curve_rect_gray));
                binding.linearBalance.setBackground(BrandedAePSHome.this.getResources().getDrawable(R.drawable.curve_rect_gray));
                binding.linearMiniState.setBackground(BrandedAePSHome.this.getResources().getDrawable(R.drawable.curve_rect_gray));
            } else {
                BrandedAePSHome.this.txnType = "BE";
                binding.linearCheckBalance.setVisibility(View.VISIBLE);
                binding.linearCashAmount.setVisibility(View.GONE);
                binding.linearDeviceButton.setVisibility(View.GONE);
                binding.linearAadhaar.setBackground(BrandedAePSHome.this.getResources().getDrawable(R.drawable.curve_rect_gray));
                binding.linearCashWith.setBackground(BrandedAePSHome.this.getResources().getDrawable(R.drawable.curve_rect_gray));
                binding.linearBalance.setBackground(BrandedAePSHome.this.getResources().getDrawable(R.drawable.layout_bg_blue_head));
                binding.linearMiniState.setBackground(BrandedAePSHome.this.getResources().getDrawable(R.drawable.curve_rect_gray));
                binding.textBalance.setText("Check Balance");
            }
        } else {
            BrandedAePSHome.this.txnType = "BE";
            binding.linearCheckBalance.setVisibility(View.VISIBLE);
            binding.linearCashAmount.setVisibility(View.GONE);
            binding.linearDeviceButton.setVisibility(View.GONE);
            binding.linearAadhaar.setBackground(BrandedAePSHome.this.getResources().getDrawable(R.drawable.curve_rect_gray));
            binding.linearCashWith.setBackground(BrandedAePSHome.this.getResources().getDrawable(R.drawable.curve_rect_gray));
            binding.linearBalance.setBackground(BrandedAePSHome.this.getResources().getDrawable(R.drawable.layout_bg_blue_head));
            binding.linearMiniState.setBackground(BrandedAePSHome.this.getResources().getDrawable(R.drawable.curve_rect_gray));
            binding.textBalance.setText("Check Balance");
        }
    }


    @SuppressLint({"UseCompatLoadingForDrawables", "SetTextI18n"})
    private void deviceSelection() {
        String[] deviceType = new String[]{"Mantra", "Morpho", "Tatvik", "Startek", "Secugen", "Evolute", "NextBio"};
        androidx.appcompat.app.AlertDialog.Builder alert = new androidx.appcompat.app.AlertDialog.Builder(this);
        alert.setTitle("Please select one option");
        alert.setSingleChoiceItems(deviceType, -1, (dialog, which) -> {
            AllString.setValue(this, AllString.SELECTED_DEVICEONE, deviceType[which]);
            AllString.setValue(this, AllString.SELECTED_DEVICE_INDEX, String.valueOf(which));
            binding.textDeviceName.setText(deviceType[which]);
            binding.textLinearDevice.setText("Withdraw from " + deviceType[which]);
            if (deviceType[which].equalsIgnoreCase("Mantra")) {
                binding.imgDevice.setImageResource(R.drawable.w_mantrimages);
            } else if (deviceType[which].equalsIgnoreCase("Morpho")) {
                binding.imgDevice.setImageResource(R.drawable.w_moprhoimages);
            } else if (deviceType[which].equalsIgnoreCase("Tatvik")) {
                binding.imgDevice.setImageResource(R.drawable.w_tatvikimagesl);
                //w_tatvikimagesl
            } else if (deviceType[which].equalsIgnoreCase("Startek")) {
                binding.imgDevice.setImageResource(R.drawable.w_startekimage);
            } else if (deviceType[which].equalsIgnoreCase("Secugen")) {
                binding.imgDevice.setImageResource(R.drawable.w_secugenimages);
            } else if (deviceType[which].equalsIgnoreCase("Evolute")) {
                binding.imgDevice.setImageResource(R.drawable.w_evoluteimages);
            } else if (deviceType[which].equalsIgnoreCase("NextBio")) {
                binding.imgDevice.setImageResource(R.drawable.w_nextbioimages);
            }

        });
        alert.setPositiveButton("OK", (dialog, which) -> {
            dialog.dismiss();
        });
        alert.setCancelable(false);
        alert.show();
    }

    private void scanDevice() {
        int indexCount = 50;
        String deviceValue = AllString.getValue(this, AllString.SELECTED_DEVICE_INDEX);
        if (deviceValue != null && deviceValue.length() > 0) {
            indexCount = Integer.parseInt(deviceValue);
        }

        switch (indexCount) {
            case 0:
                this.MantraFinger();
                break;
            case 1:
                this.MorphoDevice();
                break;
            case 2:
                this.TatvikFinger();
                break;
            case 3:
                this.StarTekFinger();
                break;
            case 4:
                this.SecuGenFinger();
                break;
            case 5:
                this.EvoluteFinger();
                break;
            case 6:
                this.NextBiometric();
        }

    }

    private void MorphoDevice() {
        PackageManager packageManager = this.context.getPackageManager();
        if (util.isPackageInstalled("com.scl.rdservice", packageManager)) {
            Intent intent = new Intent("in.gov.uidai.rdservice.fp.INFO");
            intent.setPackage("com.scl.rdservice");
            this.startActivityForResult(intent, 1);
        } else {
            AlertDialog.Builder alertDialog = new AlertDialog.Builder(this, R.style.alertDialog);
            alertDialog.setTitle("Get Service");
            alertDialog.setMessage("Morpho RD Services Not Found.Click OK to Download Now.");
            alertDialog.setPositiveButton("OK", (dialog, which) -> {
                try {
                    this.startActivity(new Intent("androR.id.intent.action.VIEW", Uri.parse("https://play.google.com/store/apps/details?id=com.scl.rdservice")));
                } catch (Exception var4) {
                    (new util()).snackBar(binding.mainLayout, "Something went wrong.Please try again later.", AllString.SnackBarBackGroundColor);
                    var4.printStackTrace();
                }

            });
            alertDialog.setNegativeButton("Cancel", (dialog, which) -> {
                dialog.dismiss();
            });
            alertDialog.show();
        }

    }

    private void MorphoFinger() {
        Intent intent2 = new Intent();
        intent2.setComponent(new ComponentName("com.scl.rdservice", "com.scl.rdservice.FingerCaptureActivity"));
        intent2.setAction("in.gov.uidai.rdservice.fp.CAPTURE");
        intent2.putExtra("PID_OPTIONS", this.IciciPidData);
        this.startActivityForResult(intent2, 2);
    }

    private void MantraFinger() {
        PackageManager packageManager = this.context.getPackageManager();
        if (util.isPackageInstalled("com.mantra.rdservice", packageManager)) {
            Intent intent2 = new Intent();
            intent2.setComponent(new ComponentName("com.mantra.rdservice", "com.mantra.rdservice.RDServiceActivity"));
            intent2.setAction("in.gov.uidai.rdservice.fp.CAPTURE");
            intent2.putExtra("PID_OPTIONS", this.IciciPidData);
            this.startActivityForResult(intent2, 3);
        } else {
            AlertDialog.Builder alertDialog = new AlertDialog.Builder(this, R.style.alertDialog);
            alertDialog.setTitle("Get Service");
            alertDialog.setMessage("Mantra RD Services Not Found.Click OK to Download Now.");
            alertDialog.setPositiveButton("OK", (dialog, which) -> {
                try {
                    this.startActivity(new Intent("androR.id.intent.action.VIEW", Uri.parse("https://play.google.com/store/apps/details?id=com.mantra.rdservice")));
                } catch (Exception var4) {
                    (new util()).snackBar(binding.mainLayout, "Something went wrong.Please try again later.", AllString.SnackBarBackGroundColor);
                    var4.printStackTrace();
                    Log.e("deviceerror", "" + var4);
                }

            });
            alertDialog.setNegativeButton("Cancel", (dialog, which) -> {
                dialog.dismiss();
            });
            alertDialog.show();
        }

    }

    private void SecuGenFinger() {
        PackageManager packageManager = this.context.getPackageManager();
        if (util.isPackageInstalled("com.secugen.rdservice", packageManager)) {
            Intent intent2 = new Intent();
            intent2.setComponent(new ComponentName("com.secugen.rdservice", "com.secugen.rdservice.Capture"));
            intent2.setAction("in.gov.uidai.rdservice.fp.CAPTURE");
            intent2.putExtra("PID_OPTIONS", this.IciciPidData);
            this.startActivityForResult(intent2, 4);
        } else {
            AlertDialog.Builder alertDialog = new AlertDialog.Builder(this, R.style.alertDialog);
            alertDialog.setTitle("Get Service");
            alertDialog.setMessage("SecuGen RD Services Not Found.Click OK to Download Now.");
            alertDialog.setPositiveButton("OK", (dialog, which) -> {
                try {
                    this.startActivity(new Intent("androR.id.intent.action.VIEW", Uri.parse("https://play.google.com/store/apps/details?id=com.secugen.rdservice")));
                } catch (Exception var4) {
                    (new util()).snackBar(binding.mainLayout, "Something went wrong.Please try again later.", AllString.SnackBarBackGroundColor);
                    var4.printStackTrace();
                }

            });
            alertDialog.setNegativeButton("Cancel", (dialog, which) -> {
                dialog.dismiss();
            });
            alertDialog.show();
        }

    }

    private void TatvikFinger() {
        PackageManager packageManager = this.context.getPackageManager();
        if (util.isPackageInstalled("com.tatvik.bio.tmf20", packageManager)) {
            Intent intent2 = new Intent();
            intent2.setComponent(new ComponentName("com.tatvik.bio.tmf20", "com.tatvik.bio.tmf20.RDMainActivity"));
            intent2.setAction("in.gov.uidai.rdservice.fp.CAPTURE");
            intent2.putExtra("PID_OPTIONS", this.IciciPidData);
            this.startActivityForResult(intent2, 5);
        } else {
            AlertDialog.Builder alertDialog = new AlertDialog.Builder(this, R.style.alertDialog);
            alertDialog.setTitle("Get Service");
            alertDialog.setMessage("Tatvik RD Services Not Found.Click OK to Download Now.");
            alertDialog.setPositiveButton("OK", (dialog, which) -> {
                try {
                    this.startActivity(new Intent("androR.id.intent.action.VIEW", Uri.parse("https://play.google.com/store/apps/details?id=com.tatvik.bio.tmf20")));
                } catch (Exception var4) {
                    (new util()).snackBar(binding.mainLayout, "Something went wrong.Please try again later.", AllString.SnackBarBackGroundColor);
                    var4.printStackTrace();
                    Log.e("deviceerror", "" + var4);
                }

            });
            alertDialog.setNegativeButton("Cancel", (dialog, which) -> {
                dialog.dismiss();
            });
            alertDialog.show();
        }

    }

    public void StarTekFinger() {
        PackageManager packageManager = this.context.getPackageManager();
        if (util.isPackageInstalled("com.acpl.registersdk", packageManager)) {
            Intent intent = new Intent();
            intent.setAction("in.gov.uidai.rdservice.fp.CAPTURE");
            intent.setComponent(new ComponentName("com.acpl.registersdk", "com.acpl.registersdk.MainActivity"));
            intent.putExtra("PID_OPTIONS", this.IciciPidData);
            this.startActivityForResult(intent, 6);
        } else {
            AlertDialog.Builder alertDialog = new AlertDialog.Builder(this, R.style.alertDialog);
            alertDialog.setTitle("Get Service");
            alertDialog.setMessage("Startek RD Service not found. Click OK to download now.");
            alertDialog.setPositiveButton("OK", (dialogInterface, i) -> {
                try {
                    this.startActivity(new Intent("androR.id.intent.action.VIEW", Uri.parse("https://play.google.com/store/apps/details?id=com.acpl.registersdk")));
                } catch (Exception var4) {
                    var4.printStackTrace();
                }

            });
            alertDialog.setNegativeButton("Cancel", (dialog, i) -> {
                dialog.dismiss();
            });
            alertDialog.show();
        }

    }

    private void EvoluteFinger() {
        PackageManager packageManager = this.context.getPackageManager();
        if (util.isPackageInstalled("com.evolute.rdservice", packageManager)) {
            Intent intent2 = new Intent();
            intent2.setAction("in.gov.uidai.rdservice.fp.CAPTURE");
            intent2.setComponent(new ComponentName("com.evolute.rdservice", "com.evolute.rdservice.RDserviceActivity"));
            intent2.putExtra("PID_OPTIONS", this.IciciPidData);
            this.startActivityForResult(intent2, 7);
        } else {
            AlertDialog.Builder alertDialog = new AlertDialog.Builder(this, R.style.alertDialog);
            alertDialog.setTitle("Get Service");
            alertDialog.setMessage("Evolute RD Services Not Found.Click OK to Download Now.");
            alertDialog.setPositiveButton("OK", (dialog, which) -> {
                try {
                    this.startActivity(new Intent("androR.id.intent.action.VIEW", Uri.parse("https://play.google.com/store/apps/details?id=com.evolute.rdservice")));
                } catch (Exception var4) {
                    (new util()).snackBar(binding.mainLayout, "Something went wrong.Please try again later.", AllString.SnackBarBackGroundColor);
                    var4.printStackTrace();
                }

            });
            alertDialog.setNegativeButton("Cancel", (dialog, which) -> {
                dialog.dismiss();
            });
            alertDialog.show();
        }

    }

    private void NextBiometric() {
        PackageManager packageManager = this.context.getPackageManager();
        if (util.isPackageInstalled("com.nextbiometrics.onetouchrdservice", packageManager)) {
            Intent intent2 = new Intent();
            intent2.setAction("in.gov.uidai.rdservice.fp.CAPTURE");
            intent2.putExtra("PID_OPTIONS", this.IciciPidData);
            intent2.setPackage("com.nextbiometrics.onetouchrdservice");
            this.startActivityForResult(intent2, 8);
        } else {
            AlertDialog.Builder alertDialog = new AlertDialog.Builder(this, R.style.alertDialog_sdk);
            alertDialog.setTitle("Get Service");
            alertDialog.setMessage("NEXT Biometrics L0 Is Not Found.Click OK to Download Now.");
            alertDialog.setPositiveButton("OK", (dialog, which) -> {
                try {
                    this.startActivity(new Intent("androR.id.intent.action.VIEW", Uri.parse("https://play.google.com/store/apps/details?id=com.nextbiometrics.onetouchrdservice&hl=en_IN&gl=US")));
                } catch (Exception var4) {
                    (new util()).snackBar(binding.mainLayout, "Something went wrong.Please try again later.", AllString.SnackBarBackGroundColor);
                    var4.printStackTrace();
                }

            });
            alertDialog.setNegativeButton("Cancel", (dialog, which) -> {
                dialog.dismiss();
            });
            alertDialog.show();
        }

    }

    private void isAllInputValid() {
        boolean isValid = false;
        if (binding.aadharNoEditText.getText().toString().replace("-", "").length() != 12) {
            isValid = false;
        } else if (binding.MobileNoEditText.getText().toString().replace("", "").length() != 10) {
            isValid = false;
        } else {
            isValid = true;
        }

        if (binding.aadharNoEditText.getText().toString().length() == 14 && binding.aadharNoEditText.isFocused()) {
            (new util()).hideKeyBoard(binding.aadharNoEditText, this);
        }

        if (isValid) {
            binding.linearCheckBalance.setClickable(true);
            binding.linearCheckBalance.setBackgroundResource(R.drawable.w_lauout_bank_background);
        } else {
            binding.linearCheckBalance.setClickable(false);
            binding.linearCheckBalance.setBackgroundResource(R.drawable.w_curve_rect_gray);
        }

    }


    private void balanceInquiryApi(String fingerData) {
        String mobileNumber = binding.MobileNoEditText.getText().toString().trim();
        String aadhaarNumber = binding.aadharNoEditText.getText().toString().trim();
        aadhaarNumber = aadhaarNumber.replaceAll("-", "");

        viewModel.startAEPSServices(BrandedAePSHome.this, apiType, aadhaarNumber, fingerData, mobileNumber, txnType, UtilHolder.getLongitude(), UtilHolder.getLatitude(), "0", BrandedAePSHome.this);

    }

    private void miniStatementApi(String fingerData) {

        String mobileNumber = binding.MobileNoEditText.getText().toString().trim();
        String aadhaarNumber = binding.aadharNoEditText.getText().toString().trim();
        aadhaarNumber = aadhaarNumber.replaceAll("-", "");

        viewModel.startAEPSServices(BrandedAePSHome.this, apiType, aadhaarNumber, fingerData, mobileNumber, txnType, UtilHolder.getLongitude(), UtilHolder.getLatitude(), "0", BrandedAePSHome.this);


    }

    private void cashWithdrawApi(String fingerData) {
        String mobileNumber = binding.MobileNoEditText.getText().toString().trim();
        String aadhaarNumber = binding.aadharNoEditText.getText().toString().trim();
        aadhaarNumber = aadhaarNumber.replaceAll("-", "");
        String amount = binding.amountEditText.getText().toString().trim();

        viewModel.startAEPSServices(BrandedAePSHome.this, apiType, aadhaarNumber, fingerData, mobileNumber, txnType, UtilHolder.getLongitude(), UtilHolder.getLatitude(), amount, BrandedAePSHome.this);

    }

    private void AadharPayApi(String fingerData) {
        String mobileNumber = binding.MobileNoEditText.getText().toString().trim();
        String aadhaarNumber = binding.aadharNoEditText.getText().toString().trim();
        aadhaarNumber = aadhaarNumber.replaceAll("-", "");
        String amount = binding.amountEditText.getText().toString().trim();

        viewModel.startAEPSServices(BrandedAePSHome.this, apiType, aadhaarNumber, fingerData, mobileNumber, txnType, UtilHolder.getLongitude(), UtilHolder.getLatitude(), amount, BrandedAePSHome.this);

    }

    public void onClick(View view) {
        int amount = 0;
        int amountone = 0;
        if (!binding.amountEditText.getText().toString().equalsIgnoreCase("")) {
            amount = Integer.parseInt(binding.amountEditText.getText().toString().trim());
        } else {
            amount = 0;
        }
        if (view.getId() == R.id.btn_color1) {

            amount += 500;
            Animation animFadein = AnimationUtils.loadAnimation(getApplicationContext(), R.anim.w_zoom_in);

            binding.btnColor1.startAnimation(animFadein);
        } else if (view.getId() == R.id.btn_color2) {
            amount += 1000;
            Animation animFadein = AnimationUtils.loadAnimation(getApplicationContext(), R.anim.w_zoom_in);
            binding.btnColor2.startAnimation(animFadein);
        } else if (view.getId() == R.id.btn_color3) {
            amount += 2000;
            Animation animFadein = AnimationUtils.loadAnimation(getApplicationContext(), R.anim.w_zoom_in);
            binding.btnColor3.startAnimation(animFadein);
        } else if (view.getId() == R.id.btn_color4) {
            amount += 3000;
            Animation animFadein = AnimationUtils.loadAnimation(getApplicationContext(), R.anim.w_zoom_in);
            binding.btnColor4.startAnimation(animFadein);
        }
        if (amount > this.maxAmount.doubleValue()) {
            (new util()).snackBar((View) binding.mainLayout, "Amount Should be less than ₹" + AuthorizedSingleton.getInstance().getMaxAmount() + "!", AllString.SnackBarBackGroundColor);
        } else {
            binding.amountEditText.setText("" + amount);
        }
    }

    private void BankList() {
        binding.linearAllBank.setOnClickListener((v) -> {
            viewModel.selectBankList(v, apiType, selected -> {
                AepsViewModel.selectedAepsBankModel = selected;
                binding.BankNameTextView.setText(selected.getBankname());
            });
            this.isAllInputValid();
        });
    }


    @SuppressLint("UseCompatLoadingForDrawables")
    private void insertLatLongApi() {
        binding.linearBalance.setClickable(true);
        binding.linearBalance.setBackground(BrandedAePSHome.this.getResources().getDrawable(R.drawable.layout_bg_blue_head));
        binding.linearCheckBalance.setVisibility(View.VISIBLE);
        binding.linearCashAmount.setVisibility(View.GONE);
        binding.linearDeviceButton.setVisibility(View.GONE);
        binding.textBalance.setText("Check Balance");
        binding.linearBalance.setOnClickListener(v -> {
            BrandedAePSHome.this.txnType = "BE";
            binding.linearCheckBalance.setVisibility(View.VISIBLE);
            binding.linearCashAmount.setVisibility(View.GONE);
            binding.linearDeviceButton.setVisibility(View.GONE);
            binding.linearAadhaar.setBackground(BrandedAePSHome.this.getResources().getDrawable(R.drawable.curve_rect_gray));
            binding.linearCashWith.setBackground(BrandedAePSHome.this.getResources().getDrawable(R.drawable.curve_rect_gray));
            binding.linearBalance.setBackground(BrandedAePSHome.this.getResources().getDrawable(R.drawable.layout_bg_blue_head));
            binding.linearMiniState.setBackground(BrandedAePSHome.this.getResources().getDrawable(R.drawable.curve_rect_gray));
            binding.textBalance.setText("Check Balance");

        });
        binding.linearAadhaar.setOnClickListener(v -> {

            BrandedAePSHome.this.txnType = "M";
            binding.linearCheckBalance.setVisibility(View.GONE);
            binding.linearCashAmount.setVisibility(View.VISIBLE);
            binding.linearDeviceButton.setVisibility(View.VISIBLE);
            binding.linearAadhaar.setBackground(BrandedAePSHome.this.getResources().getDrawable(R.drawable.layout_bg_blue_head));
            binding.linearCashWith.setBackground(BrandedAePSHome.this.getResources().getDrawable(R.drawable.curve_rect_gray));
            binding.linearBalance.setBackground(BrandedAePSHome.this.getResources().getDrawable(R.drawable.curve_rect_gray));
            binding.linearMiniState.setBackground(BrandedAePSHome.this.getResources().getDrawable(R.drawable.curve_rect_gray));

        });
        /* 1427 */
        binding.linearCashWith.setOnClickListener(v -> {

            BrandedAePSHome.this.txnType = "CW";
            binding.linearCheckBalance.setVisibility(View.GONE);
            binding.linearCashAmount.setVisibility(View.VISIBLE);
            binding.linearDeviceButton.setVisibility(View.VISIBLE);
            binding.linearBalance.setBackground(BrandedAePSHome.this.getResources().getDrawable(R.drawable.curve_rect_gray));
            binding.linearAadhaar.setBackground(BrandedAePSHome.this.getResources().getDrawable(R.drawable.curve_rect_gray));
            binding.linearCashWith.setBackground(BrandedAePSHome.this.getResources().getDrawable(R.drawable.layout_bg_blue_head));
            binding.linearMiniState.setBackground(BrandedAePSHome.this.getResources().getDrawable(R.drawable.curve_rect_gray));

        });
        /* 1447 */
        binding.linearMiniState.setOnClickListener(v -> {

            BrandedAePSHome.this.txnType = "MS";
            binding.linearCheckBalance.setVisibility(View.VISIBLE);
            binding.linearCashAmount.setVisibility(View.GONE);
            binding.linearDeviceButton.setVisibility(View.GONE);
            binding.linearMiniState.setBackground(BrandedAePSHome.this.getResources().getDrawable(R.drawable.layout_bg_blue_head));
            binding.linearAadhaar.setBackground(BrandedAePSHome.this.getResources().getDrawable(R.drawable.curve_rect_gray));
            binding.linearBalance.setBackground(BrandedAePSHome.this.getResources().getDrawable(R.drawable.curve_rect_gray));
            binding.linearCashWith.setBackground(BrandedAePSHome.this.getResources().getDrawable(R.drawable.curve_rect_gray));
            binding.textBalance.setText("Mini Statement");
        });


        this.topBankAdaptor = new TopUsesBankAdaptor(this.context, this.topBankList, this.recyclerViewClick);
        GridLayoutManager layoutManager = new GridLayoutManager(this, 4, RecyclerView.VERTICAL, false);
        binding.recyclerBank.setLayoutManager(layoutManager);
        binding.recyclerBank.setItemAnimator(new DefaultItemAnimator());
        binding.recyclerBank.setAdapter(this.topBankAdaptor);

    }


    @SuppressLint({"UseCompatLoadingForDrawables"})
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        switch (requestCode) {

            case 1:
                if (resultCode == -1) {
                    this.morphoDeviceData = (new utilDevices()).morphoDeviceData(binding.mainLayout, data.getStringExtra("DEVICE_INFO"));
                    if (this.morphoDeviceData.getErrCode().equalsIgnoreCase("919")) {
                        MorphoFinger();
                    }
                }

                break;

            case 2:
                if (resultCode == -1) {
                    DeviceDataModel dataModel = (new utilDevices()).morphoFingerData(binding.mainLayout, data.getStringExtra("PID_DATA"), this.morphoDeviceData);
                    if (dataModel.getErrCode().equalsIgnoreCase("0")) {
                        if (this.txnType.equalsIgnoreCase("BE")) {
                            balanceInquiryApi(data.getStringExtra("PID_DATA"));
                        } else if (this.txnType.equalsIgnoreCase("CW")) {
                            cashWithdrawApi(data.getStringExtra("PID_DATA"));
                        } else if (this.txnType.equalsIgnoreCase("M")) {
                            AadharPayApi(data.getStringExtra("PID_DATA"));
                        } else if (this.txnType.equalsIgnoreCase("MS")) {
                            miniStatementApi(data.getStringExtra("PID_DATA"));
                        }
                    }
                    isAllInputValid();

                }

                break;

            case 3:
                if (resultCode == -1) {
                    DeviceDataModel dataModel = (new utilDevices()).mantraData(binding.mainLayout, data.getStringExtra("PID_DATA"));
                    if (dataModel.getErrCode().equalsIgnoreCase("0")) {
                        if (this.txnType.equalsIgnoreCase("BE")) {
                            balanceInquiryApi(data.getStringExtra("PID_DATA"));
                        } else if (this.txnType.equalsIgnoreCase("CW")) {
                            cashWithdrawApi(data.getStringExtra("PID_DATA"));
                        } else if (this.txnType.equalsIgnoreCase("M")) {
                            AadharPayApi(data.getStringExtra("PID_DATA"));
                        } else if (this.txnType.equalsIgnoreCase("MS")) {
                            miniStatementApi(data.getStringExtra("PID_DATA"));

                        }
                    }
                    isAllInputValid();

                }

                break;

            case 4:
                if (resultCode == -1) {
                    DeviceDataModel dataModel = (new utilDevices()).secugenData(binding.mainLayout, data.getStringExtra("PID_DATA"));
                    if (dataModel.getErrCode().equalsIgnoreCase("0")) {
                        if (this.txnType.equalsIgnoreCase("BE")) {
                            balanceInquiryApi(data.getStringExtra("PID_DATA"));
                        } else if (this.txnType.equalsIgnoreCase("CW")) {
                            cashWithdrawApi(data.getStringExtra("PID_DATA"));
                        } else if (this.txnType.equalsIgnoreCase("M")) {
                            AadharPayApi(data.getStringExtra("PID_DATA"));
                        } else if (this.txnType.equalsIgnoreCase("MS")) {
                            miniStatementApi(data.getStringExtra("PID_DATA"));
                        }
                    }
                    isAllInputValid();
                }
                break;
            case 5:
                if (resultCode == -1) {
                    DeviceDataModel dataModel = (new utilDevices()).tatvikData(binding.mainLayout, data.getStringExtra("PID_DATA"));
                    if (dataModel.getErrCode().equalsIgnoreCase("0")) {
                        if (this.txnType.equalsIgnoreCase("BE")) {
                            balanceInquiryApi(data.getStringExtra("PID_DATA"));
                        } else if (this.txnType.equalsIgnoreCase("CW")) {
                            cashWithdrawApi(data.getStringExtra("PID_DATA"));
                        } else if (this.txnType.equalsIgnoreCase("M")) {
                            AadharPayApi(data.getStringExtra("PID_DATA"));
                        } else if (this.txnType.equalsIgnoreCase("MS")) {
                            miniStatementApi(data.getStringExtra("PID_DATA"));
                        }

                    }
                    //isAllInputValid();
                }
                break;
            case 6:
                if (resultCode == -1) {
                    DeviceDataModel dataModel = (new utilDevices()).starTekData(binding.mainLayout, data.getStringExtra("PID_DATA"));
                    if (dataModel.getErrCode().equalsIgnoreCase("0")) {
                        if (this.txnType.equalsIgnoreCase("BE")) {
                            balanceInquiryApi(data.getStringExtra("PID_DATA"));
                        } else if (this.txnType.equalsIgnoreCase("CW")) {
                            cashWithdrawApi(data.getStringExtra("PID_DATA"));
                        } else if (this.txnType.equalsIgnoreCase("M")) {
                            AadharPayApi(data.getStringExtra("PID_DATA"));
                        } else if (this.txnType.equalsIgnoreCase("MS")) {
                            miniStatementApi(data.getStringExtra("PID_DATA"));
                        }

                    }
                    //isAllInputValid();
                }
                break;
            case 7:
                if (resultCode == -1) {
                    DeviceDataModel dataModel = (new utilDevices()).EvoluteData(binding.mainLayout, data.getStringExtra("PID_DATA"));
                    if (dataModel.getErrCode().equalsIgnoreCase("0")) {
                        if (this.txnType.equalsIgnoreCase("BE")) {
                            balanceInquiryApi(data.getStringExtra("PID_DATA"));
                        } else if (this.txnType.equalsIgnoreCase("CW")) {
                            cashWithdrawApi(data.getStringExtra("PID_DATA"));
                        } else if (this.txnType.equalsIgnoreCase("M")) {
                            AadharPayApi(data.getStringExtra("PID_DATA"));
                        } else if (this.txnType.equalsIgnoreCase("MS")) {
                            miniStatementApi(data.getStringExtra("PID_DATA"));
                        }
                    }
                    isAllInputValid();

                }

                break;

            case 8:
                if (resultCode == -1) {
                    DeviceDataModel dataModel = (new utilDevices()).NextBIoData(binding.mainLayout, data.getStringExtra("PID_DATA"));
                    if (dataModel.getErrCode().equalsIgnoreCase("0")) {
                        if (this.txnType.equalsIgnoreCase("BE")) {
                            balanceInquiryApi(data.getStringExtra("PID_DATA"));
                        } else if (this.txnType.equalsIgnoreCase("CW")) {
                            cashWithdrawApi(data.getStringExtra("PID_DATA"));
                        } else if (this.txnType.equalsIgnoreCase("M")) {
                            AadharPayApi(data.getStringExtra("PID_DATA"));
                        } else if (this.txnType.equalsIgnoreCase("MS")) {
                            miniStatementApi(data.getStringExtra("PID_DATA"));
                        }
                    }
                    isAllInputValid();
                }
                break;

        }

    }

    @Override
    public void resetRequiredData(boolean result) {
//        if (!txnType.trim().equals("BE")) {
//            //binding.aadharNoEditText.setText("");
//            //binding.amountEditText.setText("");
//            //binding.MobileNoEditText.setText("");
//        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        if (Constant.toReset) {
            binding.aadharNoEditText.setText("");
            binding.amountEditText.setText("");
            binding.MobileNoEditText.setText("");
            Constant.toReset = false;
        }
    }

}
