package com.fintech.superadmin.fragments.screenmenus;

import android.Manifest;
import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.ProgressDialog;
import android.content.ComponentName;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.core.content.ContextCompat;
import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.GridLayoutManager;

import com.fintech.superadmin.BuildConfig;
import com.fintech.superadmin.R;
import com.fintech.superadmin.activities.addfunds.AddFundList;
import com.fintech.superadmin.activities.aeps.BrandedAePSHome;
import com.fintech.superadmin.activities.bbps.BbpsEnter;
import com.fintech.superadmin.activities.creditcard.CCFetchBillK;
import com.fintech.superadmin.activities.fastag.OperatorList;
import com.fintech.superadmin.activities.lic.LicFetchBill;
import com.fintech.superadmin.activities.mahagrm_bc.BcRegistration;
import com.fintech.superadmin.activities.microatm.MicroAtmHome;
import com.fintech.superadmin.activities.mobilenumber.SendMoney;
import com.fintech.superadmin.activities.payoutpaysprint.PaysprintPayout;
import com.fintech.superadmin.activities.rechargeactivities.RechargeMyPlan;
import com.fintech.superadmin.activities.rechargeactivities.SelectOperator;
import com.fintech.superadmin.activities.tobank.QueryRemitter;
import com.fintech.superadmin.adapters.MenuAdapter;
import com.fintech.superadmin.clean.presentation.referEarn.ReferEarnActivity;
import com.fintech.superadmin.clean.presentation.rewards.RewardActivity;
import com.fintech.superadmin.clean.presentation.suvidhaPayout.SuvidhaPayout;
import com.fintech.superadmin.clean.presentation.wallet.WalletBalanceActivity;
import com.fintech.superadmin.data.apiResponse.merchant.MerchantCred;
import com.fintech.superadmin.data.db.AppDatabase;
import com.fintech.superadmin.data.db.entities.User;
import com.fintech.superadmin.data.dto.MahagramApiCred;
import com.fintech.superadmin.data.dto.MahagramMerchant;
import com.fintech.superadmin.data.dto.PaysprintApiCred;
import com.fintech.superadmin.data.dto.PaysprintMerchantCred;
import com.fintech.superadmin.data.model.MenuModel;
import com.fintech.superadmin.data.network.responses.AuthResponse;
import com.fintech.superadmin.databinding.CustomerHomeMenuFragmentsBinding;
import com.fintech.superadmin.fragments.sliders.SliderFragment;
import com.fintech.superadmin.helper.SimpleCustomChromeTabsHelper;
import com.fintech.superadmin.listeners.NumberPayListener;
import com.fintech.superadmin.listeners.RecyclerViewClickListener;
import com.fintech.superadmin.log.MahaDashActReturnResp;
import com.fintech.superadmin.util.Accessable;
import com.fintech.superadmin.util.Constant;
import com.fintech.superadmin.util.DisplayMessageUtil;
import com.fintech.superadmin.util.MyAlertUtils;
import com.fintech.superadmin.util.UtilHolder;
import com.fintech.superadmin.util.ViewUtils;
import com.fintech.superadmin.viewmodel.HomeViewModel;
import com.fintech.superadmin.viewmodel.MobileRechargeViewModel;
import com.paysprint.onboardinglib.activities.HostActivity;

import java.security.SecureRandom;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Objects;
import java.util.Random;

import dagger.hilt.android.AndroidEntryPoint;

@AndroidEntryPoint
public class CustomerMenuFragments extends Fragment implements RecyclerViewClickListener {

    private CustomerHomeMenuFragmentsBinding binding;
    private HomeViewModel viewModel;
    private ProgressDialog dialog;
    private User user = null;


    public CustomerMenuFragments() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // Inflate the layout for this fragment

        binding = DataBindingUtil.inflate(inflater, R.layout.customer_home_menu_fragments, container, false);
        binding.getRoot().setOverScrollMode(View.OVER_SCROLL_NEVER);
        viewModel = new ViewModelProvider(requireActivity()).get(HomeViewModel.class);
        binding.setHomeViewModel(viewModel);
        dialog = new ProgressDialog(requireActivity());
        return binding.getRoot();
    }

    @SuppressLint("SetTextI18n")
    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        if (savedInstanceState == null) {
            setFragment(new SliderFragment(), binding.HomeSliderFragment);
            setFragment(new SliderFragment(), binding.HomeSliderFragment2);
        }
        AppDatabase appDatabase = AppDatabase.getAppDatabase(requireActivity());
        appDatabase.getUserDao().getUser().observe(requireActivity(), currentUser -> {
            user = currentUser;
        });
        viewModel.bringTheNews(binding.everynews, binding.newsSection);
        binding.everynews.setSelected(true);
        newsModal();
        setBuToCMenus();
    }


    private void setBuToCMenus() {
        binding.referearn.setOnClickListener(v -> startActivity(new Intent(requireActivity(), ReferEarnActivity.class)));
        binding.walletPe.setOnClickListener(v -> startActivity(new Intent(requireActivity(), AddFundList.class)));
        binding.reawrds.setOnClickListener(v -> startActivity(new Intent(requireActivity(), RewardActivity.class)));
        binding.moneyTransfer.setLayoutManager(new GridLayoutManager(requireContext(), 3, GridLayoutManager.VERTICAL, false));
        List<MenuModel> moneyTransferList = new ArrayList<>();
        moneyTransferList.add(new MenuModel(R.drawable.contact, "Mobile Payments"));
        moneyTransferList.add(new MenuModel(R.drawable.banktransfer, "Bank Transfer"));
        moneyTransferList.add(new MenuModel(R.drawable.checkbalance, "Check Balance"));
        binding.moneyTransfer.setAdapter(new MenuAdapter(moneyTransferList, this));
        binding.moneyTransfer.setOverScrollMode(View.OVER_SCROLL_NEVER);


        //Recharge
        binding.firstHomeMenu.setLayoutManager(new GridLayoutManager(requireContext(), 4, GridLayoutManager.VERTICAL, false));
        List<MenuModel> rechargeList = new ArrayList<>();
        rechargeList.add(new MenuModel(R.drawable.mobilerecharge, "Mobile\nRecharge"));
        rechargeList.add(new MenuModel(R.drawable.dth, "DTH"));
        rechargeList.add(new MenuModel(R.drawable.electricity, "Electricity"));
        rechargeList.add(new MenuModel(R.drawable.creditcard, "Credit Card"));
        rechargeList.add(new MenuModel(R.drawable.fastag, "FASTag"));
        rechargeList.add(new MenuModel(R.drawable.cable_tv, "Cable Tv", "Cable"));
        rechargeList.add(new MenuModel(R.drawable.subtract, "Broadband"));
        rechargeList.add(new MenuModel(R.drawable.seeall, "See All"));
        binding.firstHomeMenu.setAdapter(new MenuAdapter(rechargeList, this));
        binding.firstHomeMenu.setOverScrollMode(View.OVER_SCROLL_NEVER);


        //Utilities
        binding.utilitiesHomeMenu.setLayoutManager(new GridLayoutManager(requireContext(), 4, GridLayoutManager.VERTICAL, false));
        List<MenuModel> utilitiesList = new ArrayList<>();
        utilitiesList.add(new MenuModel(R.drawable.bbps_menu, "BBPS", "BBPS"));
        utilitiesList.add(new MenuModel(R.drawable.gas_cylinder, "LPG", "LPG"));
        utilitiesList.add(new MenuModel(R.drawable.water, "Water"));
        utilitiesList.add(new MenuModel(R.drawable.postpaid, "Postpaid"));
//        utilitiesList.add(new MenuModel(R.drawable.broadband, "Broadband"));
        utilitiesList.add(new MenuModel(R.drawable.piped_gas, "Piped Gas", "Gas"));
//        utilitiesList.add(new MenuModel(R.drawable.c_datacard_postpaid, "Data Card\nPrepaid", "Datacard Prepaid"));
        utilitiesList.add(new MenuModel(R.drawable.rentpay, "Rent Pay"));
//        utilitiesList.add(new MenuModel(R.drawable.ic_data_card_postpaid, "Data Card\nPostpaid", "BBPS"));
        binding.utilitiesHomeMenu.setAdapter(new MenuAdapter(utilitiesList, this));
        binding.utilitiesHomeMenu.setOverScrollMode(View.OVER_SCROLL_NEVER);


        //Finances and Taxes
        binding.taxesHomeMenu.setLayoutManager(new GridLayoutManager(requireContext(), 4, GridLayoutManager.VERTICAL, false));
        List<MenuModel> financesList = new ArrayList<>();
        financesList.add(new MenuModel(R.drawable.reccuringdeposit, "EMI"));
        financesList.add(new MenuModel(R.drawable.insurance, "Insurance", "Insurance"));
        financesList.add(new MenuModel(R.drawable.lic_insurance, "LIC", "LIC"));
        financesList.add(new MenuModel(R.drawable.muncipaltax, "Municipal", "MUNICIPALITY"));
        financesList.add(new MenuModel(R.drawable.carinsurance, "Car Insurance"));
        binding.taxesHomeMenu.setAdapter(new MenuAdapter(financesList, this));
        binding.taxesHomeMenu.setOverScrollMode(View.OVER_SCROLL_NEVER);


        //Travels & Holiday
        binding.travelMenus.setLayoutManager(new GridLayoutManager(requireContext(), 4, GridLayoutManager.VERTICAL, false));
        List<MenuModel> BookingList = new ArrayList<>();
        BookingList.add(new MenuModel(R.drawable.flightsbooking, "Flights"));
        BookingList.add(new MenuModel(R.drawable.busbooking, "Bus"));
        BookingList.add(new MenuModel(R.drawable.trainbooking, "Train"));
        BookingList.add(new MenuModel(R.drawable.hotelbooking, "Hotels"));
        binding.travelMenus.setAdapter(new MenuAdapter(BookingList, this));
        binding.travelMenus.setOverScrollMode(View.OVER_SCROLL_NEVER);

        //Account Opening
        binding.accountOpening.setLayoutManager(new GridLayoutManager(requireContext(), 3, GridLayoutManager.VERTICAL, false));
        List<MenuModel> accountOpening = new ArrayList<>();
        accountOpening.add(new MenuModel(R.drawable.ic_axis_bank_01, "Saving Account"));
        accountOpening.add(new MenuModel(R.drawable.ic_axis_bank_01, "Current Account"));
        accountOpening.add(new MenuModel(R.drawable.ic_axis_bank_01, "Current Properitor"));
        binding.accountOpening.setAdapter(new MenuAdapter(accountOpening, this));
        binding.accountOpening.setOverScrollMode(View.OVER_SCROLL_NEVER);
    }


    //Should be not use this but the one which has been commented
    @SuppressLint({"UseCompatLoadingForDrawables", "NotifyDataSetChanged"})
    @Override
    public void onRecyclerViewClickItem(View view, MenuModel model) {
        switch (model.getTitle()) {

            case "Bank Transfer": {
                Intent intent = new Intent(requireActivity(), SuvidhaPayout.class);
                startActivity(intent);
                break;
            }
            case "Mobile\nRecharge": {
                MobileRechargeViewModel.service = "Mobile\nRecharge";
                Intent intent = new Intent(requireActivity(), RechargeMyPlan.class);
                intent.putExtra("operatorType", "Prepaid");
                intent.putExtra("mode", "mobile");
                startActivity(intent);
                break;
            }
            case "DTH": {
                MobileRechargeViewModel.service = "DTH";
                Intent intent = new Intent(requireActivity(), SelectOperator.class);
                intent.putExtra("operatorType", "DTH");
                intent.putExtra("mode", "dth");
                startActivity(intent);
                break;
            }
            case "Check Balance": {
                startActivity(new Intent(requireActivity(), WalletBalanceActivity.class));
                break;
            }
            case "See All": {
                viewModel.switchMore();
                setBuToCMenus();
                break;
            }
            case "See Less": {
                viewModel.switchLess();
                setBuToCMenus();
                break;
            }
            case "Current Properitor": {
                viewModel.checkPaysprintServiceExistence(requireActivity(), onBoard -> startPaysprintOnboard(onBoard.getPaysprintApiCredentials()), start -> startAccountOpening(3));
                break;
            }
            case "Current Account": {
                viewModel.checkPaysprintServiceExistence(requireActivity(), onBoard -> startPaysprintOnboard(onBoard.getPaysprintApiCredentials()), start -> startAccountOpening(2));
                break;
            }
            case "Saving Account": {
                viewModel.checkPaysprintServiceExistence(requireActivity(), onBoard -> startPaysprintOnboard(onBoard.getPaysprintApiCredentials()), start -> startAccountOpening(1));
                break;
            }

            case "LIC": {
                Intent intent = new Intent(requireActivity(), LicFetchBill.class);
                startActivity(intent);
                break;
            }
            case "Credit Card":
                startCreditCard();
                break;
            case "Fing AePS":
                viewModel.checkFindPayServiceExistence(requireActivity(), start -> startFingAEPS());
                break;
            case "AePS":
                viewModel.checkPaysprintServiceExistence(requireActivity(), onBoard -> startPaysprintOnboard(onBoard.getPaysprintApiCredentials()), start -> startAEPS());
                break;
            case "Micro ATM": {
                String atm = getString(R.string.atmType).trim().toLowerCase();
                if (atm.equals("fingpay")) {
                    viewModel.checkFindPayServiceExistence(requireActivity(), start -> startFingPayMicroAtm(start.getMerchantCredentials()));
                } else {
                    viewModel.checkPaysprintServiceExistence(requireActivity(), onBoard -> startPaysprintOnboard(onBoard.getPaysprintApiCredentials()), start -> startMicroAtm(start.getPaysprintMerchantCredentials(), start.getPaysprintApiCredentials()));
                }
                break;
            }
            case "DMT": {
                Intent intent = new Intent(requireActivity(), QueryRemitter.class);
                startActivity(intent);
                break;
            }
            case "FASTag":
                startActivity(new Intent(requireActivity(), OperatorList.class));
                break;
            case "Mobile Payments": {
                proceedToSeekMobileNumber();
                break;
            }
            case "Postpaid":
            case "LPG":
            case "Data Card\nPostpaid":
            case "Piped Gas":
            case "Landline":
            case "Cable Tv":
            case "Municipal":
            case "Book A\nCylinder":
            case "Water":
            case "EMI":
            case "Insurance":
            case "Data Card\nPrepaid":
            case "Broadband":
            case "Gas":
            case "BBPS":
            case "Electricity": {
                Intent intent = new Intent(requireActivity(), BbpsEnter.class);
                intent.putExtra("menuModel", model);
                startActivity(intent);
                break;
            }

            case "Apply PAN": {
                String panPage = "https://" + BuildConfig.APPLICATION_ID + "/Dashboard/User/ApplyPanAgent";
                Intent browserIntent = new Intent(Intent.ACTION_VIEW, Uri.parse(panPage));
                startActivity(browserIntent);
                break;
            }

            case "Balance\nEnquiry":
                viewModel.checkPaysprintServiceExistence(requireActivity(), onBoard -> startPaysprintOnboard(onBoard.getPaysprintApiCredentials()), start -> startAePSBE());
                break;
            case "Cash\nWithdrawal":
                viewModel.checkPaysprintServiceExistence(requireActivity(), onBoard -> startPaysprintOnboard(onBoard.getPaysprintApiCredentials()), start -> startAePSCW());
                break;
            case "Aadhaar\nPay":
                viewModel.checkPaysprintServiceExistence(requireActivity(), onBoard -> startPaysprintOnboard(onBoard.getPaysprintApiCredentials()), start -> startAePSM());
                break;
            case "Mini\nStatement":
                viewModel.checkPaysprintServiceExistence(requireActivity(), onBoard -> startPaysprintOnboard(onBoard.getPaysprintApiCredentials()), start -> startAePSMS());
                break;
            case "Payout":
                viewModel.checkPaysprintServiceExistence(requireActivity(), onBoard -> startPaysprintOnboard(onBoard.getPaysprintApiCredentials()), start -> startPayout());
                break;

            default:
                break;
        }
    }

    public void startFingPayMicroAtm(MerchantCred merchantCred) {
        DisplayMessageUtil.dismissDialog();
        Intent intent = new Intent(requireActivity(), MicroAtmHome.class);
        intent.putExtra("merchantCred", merchantCred);
        startActivity(intent);
    }

    public void setFragment(Fragment fragment, View view) {
        FragmentTransaction fragmentTransaction = requireActivity().getSupportFragmentManager().beginTransaction();
        fragmentTransaction.replace(view.getId(), fragment);
        fragmentTransaction.commit();
    }


    private boolean checkPermission() {

        return ContextCompat.checkSelfPermission(requireContext(),
                Manifest.permission.READ_CONTACTS) == PackageManager.PERMISSION_GRANTED && ContextCompat.checkSelfPermission(requireContext(),
                Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED;
        //When Permission is Granted
    }


    private void startPaysprintOnboard(PaysprintApiCred paysprintApiCred) {
        if (checkPermission()) {
            Intent intent = new Intent(requireActivity(), HostActivity.class);
            intent.putExtra("pId", paysprintApiCred.getPARTNERID());
            intent.putExtra("pApiKey", paysprintApiCred.getJWTKEY());
            intent.putExtra("mCode", paysprintApiCred.getMERCHANTCODE() + user.getId()); //merchant unique code
            intent.putExtra("mobile", user.getMobile()); // merchant mobile no.
            intent.putExtra("lat", UtilHolder.getLatitude());
            intent.putExtra("lng", UtilHolder.getLongitude());
            intent.putExtra("firm", paysprintApiCred.getFIRM());
            intent.putExtra("email", user.getEmail());
            intent.addFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION);
            startActivityForResult(intent, 993192);

        } else {
            ViewUtils.showToast(requireActivity(), "Location Permissions are not granted");
        }
    }

    private void startAccountOpening(int i) {
        viewModel.openAnAccount(requireActivity(), i, data -> {
            SimpleCustomChromeTabsHelper simple = new SimpleCustomChromeTabsHelper(requireActivity());
            simple.openUrlForResult(data, Constant.CHROME_CUSTOM_TAB_REQUEST_CODE);
        });
    }

    public void proceedToSeekMobileNumber() {
        Intent intent = new Intent(Intent.ACTION_PICK, ContactsContract.CommonDataKinds.Phone.CONTENT_URI);
        startActivityForResult(intent, 1412);
    }


    Integer randomInteger(int len) {
        final String AB = "0123456789";
        SecureRandom rnd = new SecureRandom();
        StringBuilder sb = new StringBuilder(len);
        for (int i = 0; i < len; i++)
            sb.append(AB.charAt(rnd.nextInt(AB.length())));
        return Integer.parseInt(sb.toString());
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 1412 && resultCode == Activity.RESULT_OK) {
            Uri contactUri = Objects.requireNonNull(data).getData();
            String[] projection = new String[]{ContactsContract.CommonDataKinds.Phone.NUMBER};
            Cursor cursor = requireActivity().getContentResolver().query(contactUri, projection,
                    null, null, null);
            String selectedPhone = "";
            // If the cursor returned is valid, get the phone number
            if (cursor != null && cursor.moveToFirst()) {
                int numberIndex = cursor.getColumnIndex(ContactsContract.CommonDataKinds.Phone.NUMBER);
                String number = cursor.getString(numberIndex);
                // Do something with the phone number
                String message = number.replaceAll("[^\\w]", "");
                StringBuilder builder = new StringBuilder(message);
                if (builder.charAt(0) == '9' && builder.charAt(1) == '1') {
                    builder.deleteCharAt(0);
                    builder.deleteCharAt(0);
                }
                selectedPhone = String.valueOf(builder);
            }

            Objects.requireNonNull(cursor).close();
            User user = AppDatabase.getAppDatabase(requireActivity()).getUserDao().getRegularUser();
            if (!user.getMobile().equals(selectedPhone)) {
                viewModel.checkIfAccountExists(requireActivity(), selectedPhone);
            } else {
                MyAlertUtils.showServerAlertDialog(requireActivity(), "Invalid choice");
            }
        } else if (requestCode == 993192 && resultCode == Activity.RESULT_OK) {

            boolean status = Objects.requireNonNull(data).getBooleanExtra("status", false);
            int response = data.getIntExtra("response", 0);
            String message = data.getStringExtra("message");

            String details = "Status: " + status + "\nResponse: " + response + "\nMessage: " + message;
            if (status) {
                DisplayMessageUtil.anotherDialogSuccess(requireActivity(), details);
            } else {
                DisplayMessageUtil.anotherDialogFailure(requireActivity(), details);
            }
        } else if (requestCode == 993193) {
            String statusCode = data.getStringExtra("StatusCode");
            String message = data.getStringExtra("Message");

            MahaDashActReturnResp returnResp = new MahaDashActReturnResp(String.valueOf(statusCode), message);
            Log.d("SEND_RESPONSE_MAHAGRAM", returnResp.toString());

            ViewUtils.showToast(requireActivity(), "Status Code=" + statusCode + "\n" + message);
        } else if (requestCode == 12322) {
            if (resultCode == Activity.RESULT_OK) {

                String message = data.getStringExtra("message"); //to get response message
                if (message == null) {
                    message = "Some failure";
                }
                String respCode = Objects.requireNonNull(data).getStringExtra("respcode");
                if (respCode.equals("999")) {//Response code from bank(999 for pending transactions 00 for success)
                    String requesttxn = data.getStringExtra("requesttxn ");//Type of transaction
                    String refstan = data.getStringExtra("refstan");// Mahagram Stan Numbe
                    String txnamount = data.getStringExtra("txnamount");//Transaction amount (0 in case of balance
                    String mid = data.getStringExtra("mid");//Mid
                    String tid = data.getStringExtra("tid");//Tid
                    String clientrefid = data.getStringExtra("clientrefid");//Your reference Id
                    String vendorid = data.getStringExtra("vendorid");//Your define value
                    String udf1 = data.getStringExtra("udf1");//Your define value
                    String udf2 = data.getStringExtra("udf2");//Your define value
                    String udf3 = data.getStringExtra("udf3");//Your define value
                    String udf4 = data.getStringExtra("udf4");//Your define value
                    String date = data.getStringExtra("date");//Date of transaction
                    DisplayMessageUtil.error(requireActivity(), "Pending");
                } else {
                    String requesttxn = data.getStringExtra("requesttxn ");//Type of transaction
                    String bankremarks = data.getStringExtra("msg");//Bank message
                    String refstan = data.getStringExtra("refstan");// Mahagram Stan Number
                    String cardno = data.getStringExtra("cardno");//Atm card number
                    String date = data.getStringExtra("date");//Date of transaction
                    String amount = data.getStringExtra("amount");//Account Balance
                    String invoicenumber = data.getStringExtra("invoicenumber");//Invoice Number
                    String mid = data.getStringExtra("mid");//Mid
                    String tid = data.getStringExtra("tid");//Tid
                    String clientrefid = data.getStringExtra("clientrefid");//Your reference Id
                    String vendorid = data.getStringExtra("vendorid");//Your define value
                    String udf1 = data.getStringExtra("udf1");//Your define value
                    String udf2 = data.getStringExtra("udf2");//Your define value
                    String udf3 = data.getStringExtra("udf3");//Your define value
                    String udf4 = data.getStringExtra("udf4");//Your define value
                    String txnamount = data.getStringExtra("txnamount");//Transaction amount (0 in case of balance
                    String rrn = data.getStringExtra("rrn");//Bank RRN number
                    DisplayMessageUtil.error(requireActivity(), bankremarks);
                }
                ViewUtils.showToast(requireActivity(), message);
            } else {
                if (data != null) {
                    String statusCode = Objects.requireNonNull(data).getStringExtra("statuscode"); //to get status code
                    String message = data.getStringExtra("message"); //to get response message
                    if (statusCode == null) {
                        statusCode = "0";
                    }
                    if (message == null) {
                        message = "Some failure";
                    }
                    if (data.getStringExtra("statuscode").equals("111")) {
                        try {
                            startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse("https://play.google.com/store/apps/details?id=org.egram.microatm")));
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    } else {
                        DisplayMessageUtil.error(requireActivity(), statusCode + "\n" + message);
                    }
                    ViewUtils.showToast(requireActivity(), statusCode + "" + message);
                }
            }
        }
    }


    private void newsModal() {
        binding.newsSection.setOnClickListener(v -> {
            try {
                AlertDialog.Builder alertDialog = new AlertDialog.Builder(requireActivity());
                alertDialog.setPositiveButton("Exit", (dialog, which) -> dialog.dismiss());
                alertDialog.setTitle("News");
                alertDialog.setMessage(binding.everynews.getText().toString());
                alertDialog.show();
            } catch (NullPointerException e) {
                ViewUtils.showToast(requireActivity(), "Unable to show the news");
            }
        });
    }


    private void startFingAEPS() {
        Intent intent = new Intent(requireActivity(), BrandedAePSHome.class);
        intent.putExtra("apiName", "fingPay");
        startActivity(intent);
    }

    private void startAEPS() {
        Intent intent = new Intent(requireActivity(), BrandedAePSHome.class);
        intent.putExtra("apiName", "paysprint");
        startActivity(intent);
    }

    private void startAePSBE() {
        Intent intent = new Intent(requireActivity(), BrandedAePSHome.class);
        intent.putExtra("aepsType", "BE");
        intent.putExtra("apiName", "paysprint");
        startActivity(intent);
    }

    private void startAePSMS() {
        Intent intent = new Intent(requireActivity(), BrandedAePSHome.class);
        intent.putExtra("aepsType", "MS");
        intent.putExtra("apiName", "paysprint");
        startActivity(intent);
    }

    private void startAePSCW() {
        Intent intent = new Intent(requireActivity(), BrandedAePSHome.class);
        intent.putExtra("aepsType", "CW");
        intent.putExtra("apiName", "paysprint");

        startActivity(intent);
    }

    private void startAePSM() {
        Intent intent = new Intent(requireActivity(), BrandedAePSHome.class);
        intent.putExtra("aepsType", "M");
        intent.putExtra("apiName", "paysprint");
        startActivity(intent);
    }

    private void startPayout() {
        Intent intent = new Intent(requireActivity(), PaysprintPayout.class);
        startActivity(intent);
    }

    public void startCreditCard() {
        startActivity(new Intent(requireActivity(), CCFetchBillK.class));
    }


    public void startMicroAtm(PaysprintMerchantCred paySprintMerchant, PaysprintApiCred paysprintApiCred) {

        if (ContextCompat.checkSelfPermission(requireActivity(), Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            ViewUtils.showToast(requireActivity(), "Need Location permission to use this facility");
        } else {
            Intent intent = new Intent(requireActivity(), MicroAtmHome.class);
            intent.putExtra("paySprintMerchant", paySprintMerchant);
            intent.putExtra("paySprintApi", paysprintApiCred);
            startActivity(intent);
        }
    }
}