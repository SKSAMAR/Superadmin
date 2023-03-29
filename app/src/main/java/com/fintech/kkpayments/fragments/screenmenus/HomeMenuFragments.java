package com.fintech.kkpayments.fragments.screenmenus;

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
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.core.content.ContextCompat;
import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.GridLayoutManager;

import com.fintech.kkpayments.R;
import com.fintech.kkpayments.activities.addfunds.AddFundList;
import com.fintech.kkpayments.activities.aeps.AepsHome;
import com.fintech.kkpayments.activities.aeps.BalanceEnquiry;
import com.fintech.kkpayments.activities.aeps.CashWithdrawal;
import com.fintech.kkpayments.activities.aeps.MiniStatement;
import com.fintech.kkpayments.activities.bbps.BbpsEnter;
import com.fintech.kkpayments.activities.creditcard.CCFetchBillK;
import com.fintech.kkpayments.activities.fastag.OperatorList;
import com.fintech.kkpayments.activities.lic.LicFetchBill;
import com.fintech.kkpayments.activities.mahagrm_bc.BcRegistration;
import com.fintech.kkpayments.activities.microatm.MicroAtmHome;
import com.fintech.kkpayments.activities.mobilenumber.SendMoney;
import com.fintech.kkpayments.activities.payoutpaysprint.PaysprintPayout;
import com.fintech.kkpayments.activities.rechargeactivities.RechargeMyPlan;
import com.fintech.kkpayments.activities.rechargeactivities.SelectOperator;
import com.fintech.kkpayments.activities.tobank.QueryRemitter;
import com.fintech.kkpayments.adapters.MenuAdapter;
import com.fintech.kkpayments.data.db.AppDatabase;
import com.fintech.kkpayments.data.db.entities.User;
import com.fintech.kkpayments.data.dto.MahagramApiCred;
import com.fintech.kkpayments.data.dto.MahagramMerchant;
import com.fintech.kkpayments.data.dto.PaysprintApiCred;
import com.fintech.kkpayments.data.dto.PaysprintMerchantCred;
import com.fintech.kkpayments.data.model.MenuModel;
import com.fintech.kkpayments.data.network.responses.AuthResponse;
import com.fintech.kkpayments.databinding.FragmentHomeMenuFragmentsBinding;
import com.fintech.kkpayments.fragments.sliders.SliderFragment;
import com.fintech.kkpayments.listeners.NumberPayListener;
import com.fintech.kkpayments.listeners.RecyclerViewClickListener;
import com.fintech.kkpayments.log.MahaDashActReturnResp;
import com.fintech.kkpayments.util.Accessable;
import com.fintech.kkpayments.util.DisplayMessageUtil;
import com.fintech.kkpayments.util.MyAlertUtils;
import com.fintech.kkpayments.util.UtilHolder;
import com.fintech.kkpayments.util.ViewUtils;
import com.fintech.kkpayments.viewmodel.HomeViewModel;
import com.fintech.kkpayments.viewmodel.MobileRechargeViewModel;
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
public class HomeMenuFragments extends Fragment implements RecyclerViewClickListener, NumberPayListener {

    private FragmentHomeMenuFragmentsBinding binding;
    HomeViewModel viewModel;
    ProgressDialog dialog;

    User user = null;

    public HomeMenuFragments() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // Inflate the layout for this fragment

        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_home_menu_fragments, container, false);
        binding.getRoot().setOverScrollMode(View.OVER_SCROLL_NEVER);
        viewModel = new ViewModelProvider(this).get(HomeViewModel.class);
        binding.setHomeViewModel(viewModel);
        dialog = new ProgressDialog(requireActivity());
        AppDatabase appDatabase = AppDatabase.getAppDatabase(requireActivity());

        appDatabase.getUserDao().getUser().observe(requireActivity(), currentUser -> user = currentUser);
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        if (savedInstanceState == null) {
            setFragment(new SliderFragment(), binding.HomeSliderFragment);
            setMenus();
        }
        viewModel.bringTheNews(binding.everynews, binding.newsSection);
        binding.everynews.setSelected(true);
        newsModal();
    }


    private void setMenus() {


        //Money Transfer
        binding.moneyTransfer.setLayoutManager(new GridLayoutManager(requireContext(), 4, GridLayoutManager.VERTICAL, false));
        List<MenuModel> moneyTransferList = new ArrayList<>();
        moneyTransferList.add(new MenuModel(R.drawable.ic_money_transfer, "Mobile Payments"));
        moneyTransferList.add(new MenuModel(R.drawable.ic_aeps, "AePS"));
        moneyTransferList.add(new MenuModel(R.drawable.ic_bank, "DMT"));
        moneyTransferList.add(new MenuModel(R.drawable.ic_m_atm, "Micro ATM"));
        binding.moneyTransfer.setAdapter(new MenuAdapter(moneyTransferList, this));
        binding.moneyTransfer.setOverScrollMode(View.OVER_SCROLL_NEVER);


        //Recharge
        binding.firstHomeMenu.setLayoutManager(new GridLayoutManager(requireContext(), 4, GridLayoutManager.VERTICAL, false));
        List<MenuModel> rechargeList = new ArrayList<>();
        rechargeList.add(new MenuModel(R.drawable.ic_mobile_recharge, "Mobile\nRecharge"));
        rechargeList.add(new MenuModel(R.drawable.ic_fastag_recharge, "FASTag\nRecharge"));
        rechargeList.add(new MenuModel(R.drawable.ic_dth, "DTH"));
        rechargeList.add(new MenuModel(R.drawable.ic_cable_tv, "Cable Tv", "Cable"));
        binding.firstHomeMenu.setAdapter(new MenuAdapter(rechargeList, this));
        binding.firstHomeMenu.setOverScrollMode(View.OVER_SCROLL_NEVER);


        //AEPS Services
        binding.aepsHomeMenu.setLayoutManager(new GridLayoutManager(requireActivity(), 4, GridLayoutManager.VERTICAL, false));
        List<MenuModel> aePSTransferList = new ArrayList<>();
        aePSTransferList.add(new MenuModel(R.drawable.ic_balance_enquiry_new, "Balance\nEnquiry"));
        aePSTransferList.add(new MenuModel(R.drawable.ic_mini_statment_new, "Mini\nStatement"));
        aePSTransferList.add(new MenuModel(R.drawable.ic_withdrawal_new, "Cash\nWithdrawal"));
        aePSTransferList.add(new MenuModel(R.drawable.ic_aadharpay_new, "Aadhaar\nPay"));
        binding.aepsHomeMenu.setAdapter(new MenuAdapter(aePSTransferList, this));
        binding.aepsHomeMenu.setOverScrollMode(View.OVER_SCROLL_NEVER);


        //Utilities
        binding.utilitiesHomeMenu.setLayoutManager(new GridLayoutManager(requireContext(), 4, GridLayoutManager.VERTICAL, false));
        List<MenuModel> utilitiesList = new ArrayList<>();
        utilitiesList.add(new MenuModel(R.drawable.bbps_menu, "BBPS", "BBPS"));
        utilitiesList.add(new MenuModel(R.drawable.ic_cylinder, "Book A\nCylinder", "Gas"));
        utilitiesList.add(new MenuModel(R.drawable.ic_water, "Water"));
        utilitiesList.add(new MenuModel(R.drawable.ic_electricity, "Electricity"));
        utilitiesList.add(new MenuModel(R.drawable.ic_postpaid, "Postpaid"));
        utilitiesList.add(new MenuModel(R.drawable.ic_boardband, "Broadband"));
        utilitiesList.add(new MenuModel(R.drawable.ic_cylinder, "LPG"));


        utilitiesList.add(new MenuModel(R.drawable.ic_data_card_prepaid, "Data Card\nPrepaid", "Datacard Prepaid"));
        utilitiesList.add(new MenuModel(R.drawable.ic_landline, "Landline"));
        utilitiesList.add(new MenuModel(R.drawable.ic_data_card_postpaid, "Data Card\nPostpaid", "BBPS"));

        binding.utilitiesHomeMenu.setAdapter(new MenuAdapter(utilitiesList, this));
        binding.utilitiesHomeMenu.setOverScrollMode(View.OVER_SCROLL_NEVER);


        //Finances and Taxes
        binding.taxesHomeMenu.setLayoutManager(new GridLayoutManager(requireContext(), 2, GridLayoutManager.VERTICAL, false));
        List<MenuModel> financesList = new ArrayList<>();
        financesList.add(new MenuModel(R.drawable.ic_credit_card_bill, "Credit Card"));
        financesList.add(new MenuModel(R.drawable.ic_lic_bill, "LIC", "LIC"));
        binding.taxesHomeMenu.setAdapter(new MenuAdapter(financesList, this));
        binding.taxesHomeMenu.setOverScrollMode(View.OVER_SCROLL_NEVER);


        binding.clickPayout.setOnClickListener(v -> {
            viewModel.checkPaysprintServiceExistence(requireActivity(), onBoard -> startPaysprintOnboard(onBoard.getPaysprintApiCred()), start -> startPayout());
        });

    }


    public TextView getMainBalance() {
        return binding.mainBalText;
    }

    public TextView getAepsBalance() {
        return binding.aepsBalText;
    }


    //Should be not use this but the one which has been commented
    @SuppressLint("UseCompatLoadingForDrawables")
    @Override
    public void onRecyclerViewClickItem(View view, MenuModel model) {
        switch (model.getTitle()) {

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
            case "LIC": {
                Intent intent = new Intent(requireActivity(), LicFetchBill.class);
                startActivity(intent);
                break;
            }
            case "Credit Card":
                viewModel.checkPaysprintServiceExistence(requireActivity(), onBoard -> startPaysprintOnboard(onBoard.getPaysprintApiCred()), start -> startCreditCard());
                break;
            case "AePS":
                viewModel.checkPaysprintServiceExistence(requireActivity(), onBoard -> startPaysprintOnboard(onBoard.getPaysprintApiCred()), start -> startAEPS());
                break;
            case "Micro ATM":
                viewModel.checkPaysprintServiceExistence(requireActivity(), onBoard -> startPaysprintOnboard(onBoard.getPaysprintApiCred()), start -> startMicroAtm(start.getPaysprintMerchantCred(), start.getPaysprintApiCred()));
                break;
            case "DMT": {
                Intent intent = new Intent(requireActivity(), QueryRemitter.class);
                startActivity(intent);
                break;
            }
            case "FASTag\nRecharge":
                startActivity(new Intent(requireActivity(), OperatorList.class));
                break;
            case "Mobile Payments": {
                proceedToSeekMobileNumber();
                break;
            }
            case "Postpaid":
            case "LPG":
            case "Data Card\nPostpaid":
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
                String panPage = "https://"+requireActivity().getString(R.string.base_url_data) + "/Dashboard/User/ApplyPanAgent";
                Intent browserIntent = new Intent(Intent.ACTION_VIEW, Uri.parse(panPage));
                startActivity(browserIntent);
                break;
            }

            case "Current Account":
                viewModel.checkPaysprintServiceExistence(requireActivity(), onBoard -> startPaysprintOnboard(onBoard.getPaysprintApiCred()), start -> startAePSBE());
                break;
            case "Saving Account":
                viewModel.checkPaysprintServiceExistence(requireActivity(), onBoard -> startPaysprintOnboard(onBoard.getPaysprintApiCred()), start -> startAePSBE());
                break;
            case "Balance\nEnquiry":
                viewModel.checkPaysprintServiceExistence(requireActivity(), onBoard -> startPaysprintOnboard(onBoard.getPaysprintApiCred()), start -> startAePSBE());
                break;
            case "Cash\nWithdrawal":
                viewModel.checkPaysprintServiceExistence(requireActivity(), onBoard -> startPaysprintOnboard(onBoard.getPaysprintApiCred()), start -> startAePSCW());
                break;
            case "Aadhaar\nPay":
                viewModel.checkPaysprintServiceExistence(requireActivity(), onBoard -> startPaysprintOnboard(onBoard.getPaysprintApiCred()), start -> startAePSM());
                break;
            case "Mini\nStatement":
                viewModel.checkPaysprintServiceExistence(requireActivity(), onBoard -> startPaysprintOnboard(onBoard.getPaysprintApiCred()), start -> startAePSMS());
                break;
            case "Payout":
                viewModel.checkPaysprintServiceExistence(requireActivity(), onBoard -> startPaysprintOnboard(onBoard.getPaysprintApiCred()), start -> startPayout());
                break;

            default:
                break;
        }


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
        ViewUtils.showToast(requireActivity(), "1");
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
                viewModel.checkIfAccountExists(selectedPhone, HomeMenuFragments.this);
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

    @Override
    public void isNumberValid(AuthResponse authResponse) {

        if (authResponse.isStatus()) {
            MyAlertUtils.dismissAlertDialog();
            Intent intent = new Intent(requireActivity(), SendMoney.class);
            intent.putExtra("receiver_id", authResponse.getUser().getId());
            intent.putExtra("receiver_name", authResponse.getUser().getName() + " " + authResponse.getUser().getLastname());
            intent.putExtra("receiver_mobile", authResponse.getUser().getMobile());
            startActivity(intent);
        } else {
            DisplayMessageUtil.anotherDialogFailure(requireActivity(), authResponse.getMessage());
        }
    }

    @Override
    public void showMessage(String message) {
        MyAlertUtils.showServerAlertDialog(requireActivity(), message);
    }

    @Override
    public void showProgress(String message) {
        MyAlertUtils.showProgressAlertDialog(requireActivity());
    }


    public void beginMahagramAtm(MahagramApiCred response, MahagramMerchant merchant) {
        if (merchant == null) {
            startActivity(new Intent(requireActivity(), BcRegistration.class));
            return;
        } else {
            merchant.getBCID();
        }
        if (appInstalledOrNot("org.egram.microatm")) {
            Intent intent = new Intent();
            intent.setComponent(new
                    ComponentName("org.egram.microatm", "org.egram.microatm.BluetoothMacSearchActivity"));
            intent.putExtra("saltkey", response.getSaltKey());
            intent.putExtra("secretkey", response.getSecretKey());
            intent.putExtra("bcid", merchant.getBCID());
            intent.putExtra("userid", user.getId());
            intent.putExtra("bcemailid", merchant.getEmail());
            intent.putExtra("phone1", merchant.getNum());
            intent.putExtra("clientrefid", createMultipleTransactionID());
            intent.putExtra("vendorid", "");
            intent.putExtra("udf1", "");
            intent.putExtra("udf2", "");
            intent.putExtra("udf3", "");
            intent.putExtra("udf4", "");
            startActivityForResult(intent, 12322);
        } else {
            try {
                AlertDialog.Builder dialog = new AlertDialog.Builder(requireActivity());
                dialog.setTitle("Permission to ATM Installation");
                dialog.setMessage("You need to install external ATM App from Google's Play store.");
                dialog.create();
                dialog.setPositiveButton("Proceed", (dialog12, which) -> {
                    Intent viewIntent = new Intent("android.intent.action.VIEW",
                            Uri.parse("https://play.google.com/store/apps/details?id=org.egram.microatm"));
                    startActivity(viewIntent);
                });

                dialog.setNegativeButton("Cancel", (dialog1, which) -> {

                });
                dialog.show();
            } catch (Exception e) {
                AlertDialog.Builder dialog = new AlertDialog.Builder(requireActivity());
                dialog.create();
                dialog.setPositiveButton("Proceed", (dialog12, which) -> {
                    Intent viewIntent = new Intent("android.intent.action.VIEW",
                            Uri.parse("https://play.google.com/store/apps/details?id=org.egram.microatm"));
                    startActivity(viewIntent);
                });

                dialog.setNegativeButton("Cancel", (dialog1, which) -> {

                });
                dialog.show();
                ViewUtils.showToast(requireActivity(), "Unable to Connect Try Again...");
                e.printStackTrace();
            }
        }
    }


    private boolean appInstalledOrNot(String uri) {
        PackageManager pm = requireActivity().getPackageManager();
        try {
            pm.getPackageInfo(uri, PackageManager.GET_ACTIVITIES);
            return true;
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
        }
        return false;
    }

    public final String createMultipleTransactionID() {
        String AgentTranID = "";
        try {
            @SuppressLint("SimpleDateFormat")
            SimpleDateFormat sdf = new SimpleDateFormat("yyMMddHHmmssSS");
            Date date = new Date();
            String tranID = sdf.format(date);
            int n = 6;
            Random randGen = new Random();
            int startNum = (int) Math.pow(10, n - 1);
            int range = (int) (Math.pow(10, n) - startNum);
            int randomNum = randGen.nextInt(range) + startNum;
            String ran = String.valueOf(randomNum);
            AgentTranID = tranID + ran;
        } catch (Throwable e) {
            e.printStackTrace();
        }
        return AgentTranID;
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


    private void startAEPS() {
        startActivity(new Intent(requireActivity(), AepsHome.class));
    }

    private void startAePSBE() {
        startActivity(new Intent(requireActivity(), BalanceEnquiry.class));
    }

    private void startAePSMS() {
        startActivity(new Intent(requireActivity(), MiniStatement.class));
    }

    private void startAePSCW() {
        Intent intent = new Intent(requireActivity(), CashWithdrawal.class);
        intent.putExtra("aepsType", "CW");
        startActivity(intent);
    }

    private void startAePSM() {
        Intent intent = new Intent(requireActivity(), CashWithdrawal.class);
        intent.putExtra("aepsType", "M");
        startActivity(intent);
    }

    private void startPayout() {
        Intent intent = new Intent(requireActivity(), PaysprintPayout.class);
        startActivity(intent);
    }

    public void startCreditCard() {
        startActivity(new Intent(requireActivity(), CCFetchBillK.class));
    }


    public void onAddFundClick() {
        if (Accessable.isAccessable()) {
            startActivity(new Intent(requireActivity(), AddFundList.class));
        }
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