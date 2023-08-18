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
import android.widget.Toast;

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
import com.fintech.superadmin.activities.aeps.BrandedAePSHome;
import com.fintech.superadmin.activities.aeps.brandedComp.AllString;
import com.fintech.superadmin.activities.aeps.brandedComp.DeviceDataModel;
import com.fintech.superadmin.activities.aeps.brandedComp.util;
import com.fintech.superadmin.activities.aeps.brandedComp.utilDevices;
import com.fintech.superadmin.activities.bbps.BbpsEnter;
import com.fintech.superadmin.activities.creditcard.CCFetchBillK;
import com.fintech.superadmin.activities.fastag.OperatorList;
import com.fintech.superadmin.activities.lic.LicFetchBill;
import com.fintech.superadmin.activities.mahagrm_bc.BcRegistration;
import com.fintech.superadmin.activities.microatm.MicroAtmHome;
import com.fintech.superadmin.activities.pan.NSDLPanActivity;
import com.fintech.superadmin.activities.payoutpaysprint.PaysprintPayout;
import com.fintech.superadmin.activities.rechargeactivities.RechargeMyPlan;
import com.fintech.superadmin.activities.rechargeactivities.SelectOperator;
import com.fintech.superadmin.adapters.MenuAdapter;
import com.fintech.superadmin.clean.presentation.payout.PayoutActivity;
import com.fintech.superadmin.data.apiResponse.merchant.MerchantCred;
import com.fintech.superadmin.data.db.AppDatabase;
import com.fintech.superadmin.data.db.entities.User;
import com.fintech.superadmin.data.dto.MahagramApiCred;
import com.fintech.superadmin.data.dto.MahagramMerchant;
import com.fintech.superadmin.data.dto.PaysprintApiCred;
import com.fintech.superadmin.data.dto.PaysprintMerchantCred;
import com.fintech.superadmin.data.model.MenuModel;
import com.fintech.superadmin.databinding.AepsDailyAuthDialogBinding;
import com.fintech.superadmin.databinding.AepsRegAuthDialogBinding;
import com.fintech.superadmin.databinding.FragmentHomeMenuFragmentsBinding;
import com.fintech.superadmin.deer_listener.Receiver;
import com.fintech.superadmin.flight.presentation.home.FlightHomeActivity;
import com.fintech.superadmin.fragments.sliders.SliderFragment;
import com.fintech.superadmin.helper.SimpleCustomChromeTabsHelper;
import com.fintech.superadmin.listeners.RecyclerViewClickListener;
import com.fintech.superadmin.log.MahaDashActReturnResp;
import com.fintech.superadmin.util.Constant;
import com.fintech.superadmin.util.DisplayMessageUtil;
import com.fintech.superadmin.util.MyAlertUtils;
import com.fintech.superadmin.util.NetworkUtil;
import com.fintech.superadmin.util.UtilHolder;
import com.fintech.superadmin.util.ViewUtils;
import com.fintech.superadmin.viewmodel.HomeViewModel;
import com.fintech.superadmin.viewmodel.MobileRechargeViewModel;
import com.google.android.material.bottomsheet.BottomSheetDialog;
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
public class HomeMenuFragments extends Fragment implements RecyclerViewClickListener {

    String bank = "1";
    private String oType = "";
    private FragmentHomeMenuFragmentsBinding binding;
    HomeViewModel viewModel;
    ProgressDialog dialog;
    User user = null;
    private DeviceDataModel morphoDeviceData;

    private final String IciciPidData = "<?xml version=\"1.0\"?><PidOptions ver=\"1.0\"><Opts env=\"P\" fCount=\"1\" fType=\"2\" iCount=\"0\" format=\"0\" pidVer=\"2.0\" timeout=\"10000\" posh=\"UNKNOWN\" /></PidOptions>";
    private BottomSheetDialog bottomAePsSheetDialog;
    private AepsDailyAuthDialogBinding aepsDailyAuthDialogBinding;
    private AepsRegAuthDialogBinding aepsRegAuthDialogBinding;
    private String regAadhaar = "";

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
        if (getString(R.string.magicWallet).trim().equalsIgnoreCase("yes")) {
            binding.magicContainer.setVisibility(View.VISIBLE);
        } else {
            binding.magicContainer.setVisibility(View.GONE);
        }
        return binding.getRoot();
    }


    @SuppressLint("SetTextI18n")
    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        if (savedInstanceState == null) {
            setFragment(new SliderFragment(), binding.HomeSliderFragment);
        }
        AppDatabase appDatabase = AppDatabase.getAppDatabase(requireActivity());
        appDatabase.getUserDao().getUser().observe(requireActivity(), currentUser -> {
            user = currentUser;
            try {
                if (!currentUser.getUserstatus().trim().equals("5")) {
                    setBToBMenus();
                    binding.b2baepsBalText.setText("AePS: " + user.getAepsbalance());
                    binding.b2bmainBalText.setText("Main: " + user.getMainbalance());
                } else {
                    binding.b2cmainBalText.setText("Main: " + user.getMainbalance());
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        });
        viewModel.bringTheNews(binding.everynews, binding.newsSection);
        binding.everynews.setSelected(true);
        newsModal();
        observeMagicWallet();
    }

    @Override
    public void onResume() {
        super.onResume();
        if (getString(R.string.magicWallet).trim().equalsIgnoreCase("yes")) {
            NetworkUtil.getNetworkResult(viewModel.apiServices.magicWalletStatus("magicWalletStatus"), null, result -> {
                viewModel._360_Wallet.setValue(result.getReceivableData());
                if (result.getStatus()) {
                    viewModel._360_error.setValue("");
                } else {
                    viewModel._360_error.setValue(result.getMessage());
                }
            });
        }
    }

    @SuppressLint("SetTextI18n")
    private void observeMagicWallet() {
        if (getString(R.string.magicWallet).trim().equalsIgnoreCase("yes")) {
            viewModel._360_error.observe(requireActivity(), result -> {
                if (result != null && !result.trim().isEmpty()) {
                    binding.magicContainer.setVisibility(View.VISIBLE);
                    binding.magicWalletText.setText("Magic W: Activate now");
                }
            });
            viewModel._360_Wallet.observe(requireActivity(), result -> {
                if (result != null && !result.trim().isEmpty()) {
                    binding.magicContainer.setVisibility(View.VISIBLE);
                    binding.magicWalletText.setText("Magic W: " + getString(R.string.rupee_sign) + result);
                }
            });
            binding.magicContainer.setOnClickListener(v -> {
                viewModel._360Operate(requireActivity());
            });
        } else {
            binding.magicContainer.setVisibility(View.GONE);
        }
    }


    private void setBToBMenus() {
        //Money Transfer
        if (viewModel.isAlreadySet) {
            return;
        }
        binding.moneyTransfer.setLayoutManager(new GridLayoutManager(requireContext(), 5, GridLayoutManager.VERTICAL, false));
        List<MenuModel> moneyTransferList = new ArrayList<>();
        moneyTransferList.add(new MenuModel(R.drawable.contact, "Mobile Payments"));
        moneyTransferList.add(new MenuModel(R.drawable.fingerprint, "AePS"));
//        moneyTransferList.add(new MenuModel(R.drawable.ic_aeps, "Fing AePS"));
        moneyTransferList.add(new MenuModel(R.drawable.dmt, "DMT"));
        moneyTransferList.add(new MenuModel(R.drawable.dmt, "X-Payout"));
        moneyTransferList.add(new MenuModel(R.drawable.ic_m_atm, "Micro ATM"));
        binding.moneyTransfer.setAdapter(new MenuAdapter(moneyTransferList, this));
        binding.moneyTransfer.setOverScrollMode(View.OVER_SCROLL_NEVER);


        //AEPS Services
        binding.aepsHomeMenu.setLayoutManager(new GridLayoutManager(requireActivity(), 3, GridLayoutManager.VERTICAL, false));
        List<MenuModel> aePSTransferList = new ArrayList<>();
        aePSTransferList.add(new MenuModel(R.drawable.aeps, "Fino AePs"));
        //aePSTransferList.add(new MenuModel(R.drawable.aeps, "ICICI AePS"));
        aePSTransferList.add(new MenuModel(R.drawable.aeps, "NSDL AePs"));
        aePSTransferList.add(new MenuModel(R.drawable.cashwithdraw, "Move To Bank"));
        binding.aepsHomeMenu.setAdapter(new MenuAdapter(aePSTransferList, this));
        binding.aepsHomeMenu.setOverScrollMode(View.OVER_SCROLL_NEVER);

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
        utilitiesList.add(new MenuModel(R.drawable.landline, "Landline"));
        utilitiesList.add(new MenuModel(R.drawable.ic_cms, "CMS"));
        utilitiesList.add(new MenuModel(R.drawable.utipancard, "UTI Pan"));
        utilitiesList.add(new MenuModel(R.drawable.utipancard, "NSDL Pan"));
        utilitiesList.add(new MenuModel(R.drawable.school_bus, "Bus"));
//        utilitiesList.add(new MenuModel(R.drawable.ic_data_card_postpaid, "Data Card\nPostpaid", "BBPS"));
        binding.utilitiesHomeMenu.setAdapter(new MenuAdapter(utilitiesList, this));
        binding.utilitiesHomeMenu.setOverScrollMode(View.OVER_SCROLL_NEVER);


        //Finances and Taxes
        binding.taxesHomeMenu.setLayoutManager(new GridLayoutManager(requireContext(), 4, GridLayoutManager.VERTICAL, false));
        List<MenuModel> financesList = new ArrayList<>();
        financesList.add(new MenuModel(R.drawable.emi, "EMI"));
        financesList.add(new MenuModel(R.drawable.insurance, "Insurance", "Insurance"));
        financesList.add(new MenuModel(R.drawable.lic_insurance, "LIC", "LIC"));
        financesList.add(new MenuModel(R.drawable.muncipaltax, "Municipal", "MUNICIPALITY"));
        binding.taxesHomeMenu.setAdapter(new MenuAdapter(financesList, this));
        binding.taxesHomeMenu.setOverScrollMode(View.OVER_SCROLL_NEVER);


        //Travels & Holiday
        binding.travelMenus.setLayoutManager(new GridLayoutManager(requireContext(), 4, GridLayoutManager.VERTICAL, false));
        List<MenuModel> BookingList = new ArrayList<>();
        BookingList.add(new MenuModel(R.drawable.flightsbooking, "Flights"));
        BookingList.add(new MenuModel(R.drawable.trainbooking, "Train"));


        binding.b2bclickPayout.setOnClickListener(v -> viewModel.checkPaysprintServiceExistence(requireActivity(), onBoard -> startPaysprintOnboard(onBoard.getPaysprintApiCredentials()), start -> startPayout()));

        binding.accountOpeningContainer.setVisibility(View.VISIBLE);
        binding.b2BContainer.setVisibility(View.VISIBLE);
        binding.b2cContainer.setVisibility(View.GONE);
        viewModel.isAlreadySet = true;


        String url = getString(R.string.base_url_data);
        if (url.contains("easytravelhub")) {

            binding.accountOpeningContainer.setVisibility(View.GONE);
            binding.travelMenus.setAdapter(new MenuAdapter(BookingList, this));
            binding.travelMenus.setOverScrollMode(View.OVER_SCROLL_NEVER);


        } else {

            //Account Opening
            binding.accountOpeningContainer.setVisibility(View.VISIBLE);
            binding.accountOpening.setLayoutManager(new GridLayoutManager(requireContext(), 3, GridLayoutManager.VERTICAL, false));
            List<MenuModel> accountOpening = new ArrayList<>();
            accountOpening.add(new MenuModel(R.drawable.ic_axis_bank_01, "Saving Account"));
            accountOpening.add(new MenuModel(R.drawable.ic_axis_bank_01, "Current Account"));
            accountOpening.add(new MenuModel(R.drawable.ic_axis_bank_01, "Current Properitor"));
            binding.accountOpening.setAdapter(new MenuAdapter(accountOpening, this));
            binding.accountOpening.setOverScrollMode(View.OVER_SCROLL_NEVER);


            BookingList.add(new MenuModel(R.drawable.busbooking, "Bus"));
            BookingList.add(new MenuModel(R.drawable.hotelbooking, "Hotels"));
            binding.travelMenus.setAdapter(new MenuAdapter(BookingList, this));
            binding.travelMenus.setOverScrollMode(View.OVER_SCROLL_NEVER);
        }
    }


    //Should be not use this but the one which has been commented
    @SuppressLint("UseCompatLoadingForDrawables")
    @Override
    public void onRecyclerViewClickItem(View view, MenuModel model) {
        bank = "2";
        switch (model.getTitle()) {
            case "Flights": {
                startActivity(new Intent(requireActivity(), FlightHomeActivity.class));
                break;
            }
            case "CMS": {
                startCMS();
                break;
            }
            case "Bus": {
                startBusRedirect();
                break;
            }
            case "Train": {
                startRailway();
                break;
            }
            case "UTI Pan": {
                viewModel.startRakeshUTIPan(requireActivity());
                break;
            }
            case "NSDL Pan": {
                startActivity(new Intent(requireActivity(), NSDLPanActivity.class));
                break;
            }
            case "X-Payout": {
                startActivity(new Intent(requireActivity(), PayoutActivity.class));
                break;
            }
            case "Move To Bank": {
                viewModel.checkPaysprintServiceExistence(requireActivity(), onBoard -> startPaysprintOnboard(onBoard.getPaysprintApiCredentials()), start -> startPayout());
                break;
            }
            case "Fino AePs": {
                bank = "2";
                viewModel.checkPaysprintServiceExistence(requireActivity(), onBoard -> startPaysprintOnboard(onBoard.getPaysprintApiCredentials()), start -> startAEPS());
                break;
            }
            case "ICICI AePS": {
                bank = "1";
                viewModel.checkPaysprintServiceExistence(requireActivity(), onBoard -> startPaysprintOnboard(onBoard.getPaysprintApiCredentials()), start -> startAEPS());
                break;
            }
            case "NSDL AePs": {
                bank = "3";
                viewModel.checkPaysprintServiceExistence(requireActivity(), onBoard -> startPaysprintOnboard(onBoard.getPaysprintApiCredentials()), start -> startAEPS());
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
                Intent intent;
                if (getString(R.string.dmtName).trim().toLowerCase().equals("eko")) {
                    intent = new Intent(requireActivity(), com.fintech.superadmin.activities.eko_tobank.QueryRemitter.class);
                } else {
                    intent = new Intent(requireActivity(), com.fintech.superadmin.activities.tobank.QueryRemitter.class);
                }
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
            case "Landline":
            case "Cable Tv":
            case "Municipal":
            case "Book A\nCylinder":
            case "Water":
            case "EMI":
            case "Insurance":
            case "Data Card\nPrepaid":
            case "Piped Gas":
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
        switch (requestCode) {
            case 1:
                if (resultCode == -1) {
                    this.morphoDeviceData = (new utilDevices()).morphoDeviceData(binding.getRoot(), data.getStringExtra("DEVICE_INFO"));
                    if (this.morphoDeviceData.getErrCode().equalsIgnoreCase("919")) {
                        MorphoFinger();
                    }
                }
                break;

            case 2:
                if (resultCode == -1) {
                    DeviceDataModel dataModel = (new utilDevices()).morphoFingerData(binding.getRoot(), data.getStringExtra("PID_DATA"), this.morphoDeviceData);
                    if (dataModel.getErrCode().equalsIgnoreCase("0")) {
                        if (this.oType.equalsIgnoreCase("DA")) {
                            dailyAuthentication(data.getStringExtra("PID_DATA"));
                        } else if (this.oType.equalsIgnoreCase("OR")) {
                            onceRegistration(data.getStringExtra("PID_DATA"));
                        }
                    } else {
                        Toast.makeText(requireActivity(), dataModel.getErrCode() + " :  " + dataModel.getErrMsg(), Toast.LENGTH_SHORT).show();
                    }
                }
                break;

            case 3:
                if (resultCode == -1) {
                    DeviceDataModel dataModel = (new utilDevices()).mantraData(binding.getRoot(), data.getStringExtra("PID_DATA"));
                    if (dataModel.getErrCode().equalsIgnoreCase("0")) {
                        if (this.oType.equalsIgnoreCase("DA")) {
                            dailyAuthentication(data.getStringExtra("PID_DATA"));
                        } else if (this.oType.equalsIgnoreCase("OR")) {
                            onceRegistration(data.getStringExtra("PID_DATA"));
                        }
                    } else {
                        Toast.makeText(requireActivity(), dataModel.getErrCode() + " :  " + dataModel.getErrMsg(), Toast.LENGTH_SHORT).show();
                    }
                }
                break;
            case 4:
                if (resultCode == -1) {
                    DeviceDataModel dataModel = (new utilDevices()).secugenData(binding.getRoot(), data.getStringExtra("PID_DATA"));
                    if (dataModel.getErrCode().equalsIgnoreCase("0")) {
                        if (this.oType.equalsIgnoreCase("DA")) {
                            dailyAuthentication(data.getStringExtra("PID_DATA"));
                        } else if (this.oType.equalsIgnoreCase("OR")) {
                            onceRegistration(data.getStringExtra("PID_DATA"));
                        }
                    } else {
                        Toast.makeText(requireActivity(), dataModel.getErrCode() + " :  " + dataModel.getErrMsg(), Toast.LENGTH_SHORT).show();
                    }

                }
                break;
            case 5:
                if (resultCode == -1) {
                    DeviceDataModel dataModel = (new utilDevices()).tatvikData(binding.getRoot(), data.getStringExtra("PID_DATA"));
                    if (dataModel.getErrCode().equalsIgnoreCase("0")) {
                        if (this.oType.equalsIgnoreCase("DA")) {
                            dailyAuthentication(data.getStringExtra("PID_DATA"));
                        } else if (this.oType.equalsIgnoreCase("OR")) {
                            onceRegistration(data.getStringExtra("PID_DATA"));
                        }
                    } else {
                        Toast.makeText(requireActivity(), dataModel.getErrCode() + " :  " + dataModel.getErrMsg(), Toast.LENGTH_SHORT).show();
                    }
                }
                break;
            case 6:
                if (resultCode == -1) {
                    DeviceDataModel dataModel = (new utilDevices()).starTekData(binding.getRoot(), data.getStringExtra("PID_DATA"));
                    if (dataModel.getErrCode().equalsIgnoreCase("0")) {
                        if (this.oType.equalsIgnoreCase("DA")) {
                            dailyAuthentication(data.getStringExtra("PID_DATA"));
                        } else if (this.oType.equalsIgnoreCase("OR")) {
                            onceRegistration(data.getStringExtra("PID_DATA"));
                        }
                    } else {
                        Toast.makeText(requireActivity(), dataModel.getErrCode() + " :  " + dataModel.getErrMsg(), Toast.LENGTH_SHORT).show();
                    }
                }
                break;
            case 7:
                if (resultCode == -1) {
                    DeviceDataModel dataModel = (new utilDevices()).EvoluteData(binding.getRoot(), data.getStringExtra("PID_DATA"));
                    if (dataModel.getErrCode().equalsIgnoreCase("0")) {
                        if (this.oType.equalsIgnoreCase("DA")) {
                            dailyAuthentication(data.getStringExtra("PID_DATA"));
                        } else if (this.oType.equalsIgnoreCase("OR")) {
                            onceRegistration(data.getStringExtra("PID_DATA"));
                        }
                    } else {
                        Toast.makeText(requireActivity(), dataModel.getErrCode() + " :  " + dataModel.getErrMsg(), Toast.LENGTH_SHORT).show();
                    }
                }

                break;

            case 8:
                if (resultCode == -1) {
                    DeviceDataModel dataModel = (new utilDevices()).NextBIoData(binding.getRoot(), data.getStringExtra("PID_DATA"));
                    if (dataModel.getErrCode().equalsIgnoreCase("0")) {
                        if (this.oType.equalsIgnoreCase("DA")) {
                            dailyAuthentication(data.getStringExtra("PID_DATA"));
                        } else if (this.oType.equalsIgnoreCase("OR")) {
                            onceRegistration(data.getStringExtra("PID_DATA"));
                        }
                    } else {
                        Toast.makeText(requireActivity(), dataModel.getErrCode() + " :  " + dataModel.getErrMsg(), Toast.LENGTH_SHORT).show();
                    }
                }
                break;
        }


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


    private void startFingAEPS() {
        Intent intent = new Intent(requireActivity(), BrandedAePSHome.class);
        intent.putExtra("apiName", "fingPay");
        intent.putExtra("bank", bank);
        startActivity(intent);
    }

    private void startAEPS() {

        paySprintOnceReg(onceRegResult -> {
            dailyAuth(dailyAuthData -> {
                Intent intent = new Intent(requireActivity(), BrandedAePSHome.class);
                intent.putExtra("apiName", "paysprint");
                intent.putExtra("bank", bank);
                startActivity(intent);
            });
        });
    }

    private void startAePSBE() {

        paySprintOnceReg(onceRegResult -> {
            dailyAuth(dailyAuthData -> {
                Intent intent = new Intent(requireActivity(), BrandedAePSHome.class);
                intent.putExtra("aepsType", "BE");
                intent.putExtra("apiName", "paysprint");
                intent.putExtra("bank", bank);
                startActivity(intent);
            });
        });
    }

    private void startAePSMS() {
        paySprintOnceReg(onceRegResult -> {
            dailyAuth(dailyAuthData -> {

                Intent intent = new Intent(requireActivity(), BrandedAePSHome.class);
                intent.putExtra("aepsType", "MS");
                intent.putExtra("apiName", "paysprint");
                intent.putExtra("bank", bank);
                startActivity(intent);

            });
        });
    }

    private void startAePSCW() {

        paySprintOnceReg(onceRegResult -> {
            dailyAuth(dailyAuthData -> {
                Intent intent = new Intent(requireActivity(), BrandedAePSHome.class);
                intent.putExtra("aepsType", "CW");
                intent.putExtra("apiName", "paysprint");
                intent.putExtra("bank", bank);
                startActivity(intent);
            });

        });
    }

    private void startAePSM() {

        paySprintOnceReg(onceRegResult->{
            dailyAuth(dailyAuthData->{
                Intent intent = new Intent(requireActivity(), BrandedAePSHome.class);
                intent.putExtra("aepsType", "M");
                intent.putExtra("apiName", "paysprint");
                intent.putExtra("bank", bank);
                startActivity(intent);
            });
        });
    }

    private void startPayout() {
        Intent intent = new Intent(requireActivity(), PaysprintPayout.class);
        startActivity(intent);
    }

    public void startCreditCard() {
        startActivity(new Intent(requireActivity(), CCFetchBillK.class));
    }

    private void startCMS() {
        viewModel.startCMS(requireActivity(), "cms", data -> {
            SimpleCustomChromeTabsHelper simple = new SimpleCustomChromeTabsHelper(requireActivity());
            simple.openUrlForResult(data, Constant.CHROME_CUSTOM_TAB_REQUEST_CODE);
        });
    }

    private void startRailway() {
        viewModel.startRailway(requireActivity(), data -> {
            SimpleCustomChromeTabsHelper simple = new SimpleCustomChromeTabsHelper(requireActivity());
            simple.openUrlForResult(data, Constant.CHROME_CUSTOM_TAB_REQUEST_CODE);
        });
    }

    private void startBusRedirect() {
        viewModel.busRedirect(requireActivity());
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


    private void MorphoDevice() {
        PackageManager packageManager = this.requireActivity().getPackageManager();
        if (util.isPackageInstalled("com.scl.rdservice", packageManager)) {
            Intent intent = new Intent("in.gov.uidai.rdservice.fp.INFO");
            intent.setPackage("com.scl.rdservice");
            this.startActivityForResult(intent, 1);
        } else {
            android.app.AlertDialog.Builder alertDialog = new android.app.AlertDialog.Builder(requireActivity(), R.style.alertDialog);
            alertDialog.setTitle("Get Service");
            alertDialog.setMessage("Morpho RD Services Not Found.Click OK to Download Now.");
            alertDialog.setPositiveButton("OK", (dialog, which) -> {
                try {
                    this.startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse("https://play.google.com/store/apps/details?id=com.scl.rdservice")));
                } catch (Exception var4) {
                    (new util()).snackBar(binding.b2bwalletBalLayout, "Something went wrong.Please try again later.", AllString.SnackBarBackGroundColor);
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
        PackageManager packageManager = requireActivity().getPackageManager();
        if (util.isPackageInstalled("com.mantra.rdservice", packageManager)) {
            Intent intent2 = new Intent();
            intent2.setComponent(new ComponentName("com.mantra.rdservice", "com.mantra.rdservice.RDServiceActivity"));
            intent2.setAction("in.gov.uidai.rdservice.fp.CAPTURE");
            intent2.putExtra("PID_OPTIONS", this.IciciPidData);
            this.startActivityForResult(intent2, 3);
        } else {
            android.app.AlertDialog.Builder alertDialog = new android.app.AlertDialog.Builder(requireActivity(), R.style.alertDialog);
            alertDialog.setTitle("Get Service");
            alertDialog.setMessage("Mantra RD Services Not Found.Click OK to Download Now.");
            alertDialog.setPositiveButton("OK", (dialog, which) -> {
                try {
                    this.startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse("https://play.google.com/store/apps/details?id=com.mantra.rdservice")));
                } catch (Exception var4) {
                    (new util()).snackBar(binding.getRoot(), "Something went wrong.Please try again later.", AllString.SnackBarBackGroundColor);
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
        PackageManager packageManager = requireActivity().getPackageManager();
        if (util.isPackageInstalled("com.secugen.rdservice", packageManager)) {
            Intent intent2 = new Intent();
            intent2.setComponent(new ComponentName("com.secugen.rdservice", "com.secugen.rdservice.Capture"));
            intent2.setAction("in.gov.uidai.rdservice.fp.CAPTURE");
            intent2.putExtra("PID_OPTIONS", this.IciciPidData);
            this.startActivityForResult(intent2, 4);
        } else {
            android.app.AlertDialog.Builder alertDialog = new android.app.AlertDialog.Builder(requireActivity(), R.style.alertDialog);
            alertDialog.setTitle("Get Service");
            alertDialog.setMessage("SecuGen RD Services Not Found.Click OK to Download Now.");
            alertDialog.setPositiveButton("OK", (dialog, which) -> {
                try {
                    this.startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse("https://play.google.com/store/apps/details?id=com.secugen.rdservice")));
                } catch (Exception var4) {
                    (new util()).snackBar(binding.getRoot(), "Something went wrong.Please try again later.", AllString.SnackBarBackGroundColor);
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
        PackageManager packageManager = requireActivity().getPackageManager();
        if (util.isPackageInstalled("com.tatvik.bio.tmf20", packageManager)) {
            Intent intent2 = new Intent();
            intent2.setComponent(new ComponentName("com.tatvik.bio.tmf20", "com.tatvik.bio.tmf20.RDMainActivity"));
            intent2.setAction("in.gov.uidai.rdservice.fp.CAPTURE");
            intent2.putExtra("PID_OPTIONS", this.IciciPidData);
            this.startActivityForResult(intent2, 5);
        } else {
            android.app.AlertDialog.Builder alertDialog = new android.app.AlertDialog.Builder(requireActivity(), R.style.alertDialog);
            alertDialog.setTitle("Get Service");
            alertDialog.setMessage("Tatvik RD Services Not Found.Click OK to Download Now.");
            alertDialog.setPositiveButton("OK", (dialog, which) -> {
                try {
                    this.startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse("https://play.google.com/store/apps/details?id=com.tatvik.bio.tmf20")));
                } catch (Exception var4) {
                    (new util()).snackBar(binding.getRoot(), "Something went wrong.Please try again later.", AllString.SnackBarBackGroundColor);
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
        PackageManager packageManager = requireActivity().getPackageManager();
        if (util.isPackageInstalled("com.acpl.registersdk", packageManager)) {
            Intent intent = new Intent();
            intent.setAction("in.gov.uidai.rdservice.fp.CAPTURE");
            intent.setComponent(new ComponentName("com.acpl.registersdk", "com.acpl.registersdk.MainActivity"));
            intent.putExtra("PID_OPTIONS", this.IciciPidData);
            this.startActivityForResult(intent, 6);
        } else {
            android.app.AlertDialog.Builder alertDialog = new android.app.AlertDialog.Builder(requireActivity(), R.style.alertDialog);
            alertDialog.setTitle("Get Service");
            alertDialog.setMessage("Startek RD Service not found. Click OK to download now.");
            alertDialog.setPositiveButton("OK", (dialogInterface, i) -> {
                try {
                    this.startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse("https://play.google.com/store/apps/details?id=com.acpl.registersdk")));
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
        PackageManager packageManager = requireActivity().getPackageManager();
        if (util.isPackageInstalled("com.evolute.rdservice", packageManager)) {
            Intent intent2 = new Intent();
            intent2.setAction("in.gov.uidai.rdservice.fp.CAPTURE");
            intent2.setComponent(new ComponentName("com.evolute.rdservice", "com.evolute.rdservice.RDserviceActivity"));
            intent2.putExtra("PID_OPTIONS", this.IciciPidData);
            this.startActivityForResult(intent2, 7);
        } else {
            android.app.AlertDialog.Builder alertDialog = new android.app.AlertDialog.Builder(requireActivity(), R.style.alertDialog);
            alertDialog.setTitle("Get Service");
            alertDialog.setMessage("Evolute RD Services Not Found.Click OK to Download Now.");
            alertDialog.setPositiveButton("OK", (dialog, which) -> {
                try {
                    this.startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse("https://play.google.com/store/apps/details?id=com.evolute.rdservice")));
                } catch (Exception var4) {
                    (new util()).snackBar(binding.getRoot(), "Something went wrong.Please try again later.", AllString.SnackBarBackGroundColor);
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
        PackageManager packageManager = requireActivity().getPackageManager();
        if (util.isPackageInstalled("com.nextbiometrics.onetouchrdservice", packageManager)) {
            Intent intent2 = new Intent();
            intent2.setAction("in.gov.uidai.rdservice.fp.CAPTURE");
            intent2.putExtra("PID_OPTIONS", this.IciciPidData);
            intent2.setPackage("com.nextbiometrics.onetouchrdservice");
            this.startActivityForResult(intent2, 8);
        } else {
            android.app.AlertDialog.Builder alertDialog = new android.app.AlertDialog.Builder(requireActivity(), R.style.alertDialog_sdk);
            alertDialog.setTitle("Get Service");
            alertDialog.setMessage("NEXT Biometrics L0 Is Not Found.Click OK to Download Now.");
            alertDialog.setPositiveButton("OK", (dialog, which) -> {
                try {

                    this.startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse("https://play.google.com/store/apps/details?id=com.nextbiometrics.onetouchrdservice&hl=en_IN&gl=US")));
                } catch (Exception var4) {
                    (new util()).snackBar(binding.getRoot(), "Something went wrong.Please try again later.", AllString.SnackBarBackGroundColor);
                    var4.printStackTrace();
                }

            });
            alertDialog.setNegativeButton("Cancel", (dialog, which) -> {
                dialog.dismiss();
            });
            alertDialog.show();
        }

    }


    private void dailyAuthentication(String fingerData) {
        NetworkUtil.getNetworkResult(viewModel.apiServices.AePSDailyAuthenticate("APP", "" + regAadhaar, fingerData, UtilHolder.getLongitude(), UtilHolder.getLatitude()), requireActivity(), result -> {
            if (result.status || result.response_code == 1) {
                oType = "";
                if (bottomAePsSheetDialog != null) {
                    bottomAePsSheetDialog.dismiss();
                }
            } else {
                DisplayMessageUtil.error(requireActivity(), "" + result.message);
            }
        });
    }

    private void onceRegistration(String fingerData) {
        String aadhaarNumber = aepsRegAuthDialogBinding.aadhaarNumber.getText().toString().trim();
        NetworkUtil.getNetworkResult(viewModel.apiServices.AePSRegistrationAuthenticate("APP", "" + aadhaarNumber, fingerData, UtilHolder.getLongitude(), UtilHolder.getLatitude()), requireActivity(), result -> {
            if (result.status || result.response_code == 1) {
                oType = "";
                if (bottomAePsSheetDialog != null) {
                    bottomAePsSheetDialog.dismiss();
                }
            } else {
                DisplayMessageUtil.error(requireActivity(), "" + result.message);
            }
        });
    }

    @SuppressLint("SetTextI18n")
    private void aePSRegistrationAuthDialog() {
        oType = "OR";
        bottomAePsSheetDialog = new BottomSheetDialog(requireActivity(), R.style.BottomSheetDialogTheme);
        aepsRegAuthDialogBinding = AepsRegAuthDialogBinding.inflate(getLayoutInflater());
        aepsRegAuthDialogBinding.textChangeDeviceName.setOnClickListener((v) -> this.registrationSelection());

        String selectedDevice = AllString.getValue(requireActivity(), AllString.SELECTED_DEVICEONE);
        if (selectedDevice != null && selectedDevice.length() > 0) {
            String device1 = "" + selectedDevice;
            aepsRegAuthDialogBinding.textDeviceName.setText(device1);
            if (selectedDevice.equalsIgnoreCase("Mantra")) {
                aepsRegAuthDialogBinding.imgDevice.setImageResource(R.drawable.w_mantrimages);
            } else if (selectedDevice.equalsIgnoreCase("Morpho")) {
                aepsRegAuthDialogBinding.imgDevice.setImageResource(R.drawable.w_moprhoimages);
            } else if (selectedDevice.equalsIgnoreCase("Tatvik")) {
                aepsRegAuthDialogBinding.imgDevice.setImageResource(R.drawable.w_tatvikimagesl);
            } else if (selectedDevice.equalsIgnoreCase("Startek")) {
                aepsRegAuthDialogBinding.imgDevice.setImageResource(R.drawable.w_startekimage);
            } else if (selectedDevice.equalsIgnoreCase("Secugen")) {
                aepsRegAuthDialogBinding.imgDevice.setImageResource(R.drawable.w_secugenimages);
            } else if (selectedDevice.equalsIgnoreCase("Evolute")) {
                aepsRegAuthDialogBinding.imgDevice.setImageResource(R.drawable.w_evoluteimages);
            } else if (selectedDevice.equalsIgnoreCase("NextBio")) {
                aepsRegAuthDialogBinding.imgDevice.setImageResource(R.drawable.w_nextbioimages);
            }
        } else {
            this.registrationSelection();
        }


        aepsRegAuthDialogBinding.submit.setOnClickListener(v -> {
            if (aepsRegAuthDialogBinding.aadhaarNumber.getText().toString().trim().length() != 12) {
                ViewUtils.showToast(requireActivity(), "Please Enter your valid Aadhaar Number");
            } else if (!aepsRegAuthDialogBinding.checkBox.isChecked()) {
                ViewUtils.showToast(requireActivity(), "Please confirm if it's your Aadhaar");
            } else {
                this.scanDevice();
            }
        });
        bottomAePsSheetDialog.setContentView(aepsRegAuthDialogBinding.getRoot());
        bottomAePsSheetDialog.show();
    }

    @SuppressLint({"UseCompatLoadingForDrawables", "SetTextI18n"})
    private void dailySelection() {
        String[] deviceType = new String[]{"Mantra", "Morpho", "Tatvik", "Startek", "Secugen", "Evolute", "NextBio"};
        androidx.appcompat.app.AlertDialog.Builder alert = new androidx.appcompat.app.AlertDialog.Builder(requireActivity());
        alert.setTitle("Please select one option");
        alert.setSingleChoiceItems(deviceType, -1, (dialog, which) -> {
            AllString.setValue(requireActivity(), AllString.SELECTED_DEVICEONE, deviceType[which]);
            AllString.setValue(requireActivity(), AllString.SELECTED_DEVICE_INDEX, String.valueOf(which));
            aepsDailyAuthDialogBinding.textDeviceName.setText(deviceType[which]);
            if (deviceType[which].equalsIgnoreCase("Mantra")) {
                aepsDailyAuthDialogBinding.imgDevice.setImageResource(R.drawable.w_mantrimages);
            } else if (deviceType[which].equalsIgnoreCase("Morpho")) {
                aepsDailyAuthDialogBinding.imgDevice.setImageResource(R.drawable.w_moprhoimages);
            } else if (deviceType[which].equalsIgnoreCase("Tatvik")) {
                aepsDailyAuthDialogBinding.imgDevice.setImageResource(R.drawable.w_tatvikimagesl);
            } else if (deviceType[which].equalsIgnoreCase("Startek")) {
                aepsDailyAuthDialogBinding.imgDevice.setImageResource(R.drawable.w_startekimage);
            } else if (deviceType[which].equalsIgnoreCase("Secugen")) {
                aepsDailyAuthDialogBinding.imgDevice.setImageResource(R.drawable.w_secugenimages);
            } else if (deviceType[which].equalsIgnoreCase("Evolute")) {
                aepsDailyAuthDialogBinding.imgDevice.setImageResource(R.drawable.w_evoluteimages);
            } else if (deviceType[which].equalsIgnoreCase("NextBio")) {
                aepsDailyAuthDialogBinding.imgDevice.setImageResource(R.drawable.w_nextbioimages);
            }

        });
        alert.setPositiveButton("OK", (dialog, which) -> {
            dialog.dismiss();
        });
        alert.setCancelable(false);
        alert.show();
    }

    @SuppressLint({"UseCompatLoadingForDrawables", "SetTextI18n"})
    private void registrationSelection() {
        String[] deviceType = new String[]{"Mantra", "Morpho", "Tatvik", "Startek", "Secugen", "Evolute", "NextBio"};
        androidx.appcompat.app.AlertDialog.Builder alert = new androidx.appcompat.app.AlertDialog.Builder(requireActivity());
        alert.setTitle("Please select one option");
        alert.setSingleChoiceItems(deviceType, -1, (dialog, which) -> {
            AllString.setValue(requireActivity(), AllString.SELECTED_DEVICEONE, deviceType[which]);
            AllString.setValue(requireActivity(), AllString.SELECTED_DEVICE_INDEX, String.valueOf(which));
            aepsRegAuthDialogBinding.textDeviceName.setText(deviceType[which]);
            if (deviceType[which].equalsIgnoreCase("Mantra")) {
                aepsRegAuthDialogBinding.imgDevice.setImageResource(R.drawable.w_mantrimages);
            } else if (deviceType[which].equalsIgnoreCase("Morpho")) {
                aepsRegAuthDialogBinding.imgDevice.setImageResource(R.drawable.w_moprhoimages);
            } else if (deviceType[which].equalsIgnoreCase("Tatvik")) {
                aepsRegAuthDialogBinding.imgDevice.setImageResource(R.drawable.w_tatvikimagesl);
            } else if (deviceType[which].equalsIgnoreCase("Startek")) {
                aepsRegAuthDialogBinding.imgDevice.setImageResource(R.drawable.w_startekimage);
            } else if (deviceType[which].equalsIgnoreCase("Secugen")) {
                aepsRegAuthDialogBinding.imgDevice.setImageResource(R.drawable.w_secugenimages);
            } else if (deviceType[which].equalsIgnoreCase("Evolute")) {
                aepsRegAuthDialogBinding.imgDevice.setImageResource(R.drawable.w_evoluteimages);
            } else if (deviceType[which].equalsIgnoreCase("NextBio")) {
                aepsRegAuthDialogBinding.imgDevice.setImageResource(R.drawable.w_nextbioimages);
            }

        });
        alert.setPositiveButton("OK", (dialog, which) -> {
            dialog.dismiss();
        });
        alert.setCancelable(false);
        alert.show();
    }

    @SuppressLint("SetTextI18n")
    private void aePSDailyAuthDialog() {
        oType = "DA";
        bottomAePsSheetDialog = new BottomSheetDialog(requireActivity(), R.style.BottomSheetDialogTheme);
        aepsDailyAuthDialogBinding = AepsDailyAuthDialogBinding.inflate(getLayoutInflater());
        aepsDailyAuthDialogBinding.textChangeDeviceName.setOnClickListener((v) -> this.dailySelection());

        String message = aepsDailyAuthDialogBinding.aadhaarNumber.getText().toString();
        aepsDailyAuthDialogBinding.aadhaarNumber.setText(message + regAadhaar);

        String selectedDevice = AllString.getValue(requireActivity(), AllString.SELECTED_DEVICEONE);
        if (selectedDevice != null && selectedDevice.length() > 0) {
            String device1 = "" + selectedDevice;
            aepsDailyAuthDialogBinding.textDeviceName.setText(device1);
            if (selectedDevice.equalsIgnoreCase("Mantra")) {
                aepsDailyAuthDialogBinding.imgDevice.setImageResource(R.drawable.w_mantrimages);
            } else if (selectedDevice.equalsIgnoreCase("Morpho")) {
                aepsDailyAuthDialogBinding.imgDevice.setImageResource(R.drawable.w_moprhoimages);
            } else if (selectedDevice.equalsIgnoreCase("Tatvik")) {
                aepsDailyAuthDialogBinding.imgDevice.setImageResource(R.drawable.w_tatvikimagesl);
            } else if (selectedDevice.equalsIgnoreCase("Startek")) {
                aepsDailyAuthDialogBinding.imgDevice.setImageResource(R.drawable.w_startekimage);
            } else if (selectedDevice.equalsIgnoreCase("Secugen")) {
                aepsDailyAuthDialogBinding.imgDevice.setImageResource(R.drawable.w_secugenimages);
            } else if (selectedDevice.equalsIgnoreCase("Evolute")) {
                aepsDailyAuthDialogBinding.imgDevice.setImageResource(R.drawable.w_evoluteimages);
            } else if (selectedDevice.equalsIgnoreCase("NextBio")) {
                aepsDailyAuthDialogBinding.imgDevice.setImageResource(R.drawable.w_nextbioimages);
            }
        } else {
            this.dailySelection();
        }

        aepsDailyAuthDialogBinding.submit.setOnClickListener(v -> {
            if (!aepsDailyAuthDialogBinding.checkBox.isChecked()) {
                ViewUtils.showToast(requireActivity(), "Please confirm if it's your Aadhaar");
            } else {
                this.scanDevice();
            }
        });
        bottomAePsSheetDialog.setContentView(aepsDailyAuthDialogBinding.getRoot());
        bottomAePsSheetDialog.show();
    }

    private void scanDevice() {
        try {
            int indexCount = 50;
            String deviceValue = AllString.getValue(requireActivity(), AllString.SELECTED_DEVICE_INDEX);
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
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void dailyAuth(Receiver<Boolean> receiver) {
        NetworkUtil.getNetworkResult(viewModel.apiServices.aEPSDailyAuthCheck("aePSDailyAuth"), requireActivity(), result -> {
            regAadhaar = "" + result.message;
            if (result.status) {
                receiver.getData(true);
            } else {
                aePSDailyAuthDialog();
            }
        });
    }

    private void paySprintOnceReg(Receiver<Boolean> receiver) {
        NetworkUtil.getNetworkResult(viewModel.apiServices.aEPSRegistrationAuthCheck("aePSReg"), requireActivity(), result -> {
            if (result.status || result.response_code == 1) {
                receiver.getData(true);
            } else {
                aePSRegistrationAuthDialog();
            }
        });
    }
}