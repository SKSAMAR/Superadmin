package com.fintech.petoindia.viewmodel;

import android.app.Dialog;
import android.content.Context;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.SearchView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;
import androidx.recyclerview.widget.GridLayoutManager;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ObjectWriter;
import com.fintech.petoindia.R;
import com.fintech.petoindia.adapters.DthInfoAdapter;
import com.fintech.petoindia.adapters.OperatorAdapter;
import com.fintech.petoindia.data.bbpsresponse.BBPSOPData;
import com.fintech.petoindia.data.browseplan.BrowsePlanResponse;
import com.fintech.petoindia.data.dthinfo.DthInfoResponse;
import com.fintech.petoindia.data.fastag.FastagBillFetch;
import com.fintech.petoindia.data.hlr.HLRResponse;
import com.fintech.petoindia.data.model.CircleModel;
import com.fintech.petoindia.data.model.MenuModel;
import com.fintech.petoindia.data.model.OperatorModel;
import com.fintech.petoindia.data.network.responses.FetchBillInfo;
import com.fintech.petoindia.data.network.responses.LicFetchResponse;
import com.fintech.petoindia.data.network.responses.OperatorResponse;
import com.fintech.petoindia.data.repositories.MobileRechargesRepository;
import com.fintech.petoindia.databinding.ActivitySelectOperatorBinding;
import com.fintech.petoindia.databinding.BbpsCategoryDesignBinding;
import com.fintech.petoindia.databinding.DthInformationDesignBinding;
import com.fintech.petoindia.databinding.FetchBillFastagBinding;
import com.fintech.petoindia.databinding.FetchBillLicBinding;
import com.fintech.petoindia.databinding.OtpScreenLayoutBinding;
import com.fintech.petoindia.deer_listener.Receiver;
import com.fintech.petoindia.listeners.CircleListener;
import com.fintech.petoindia.listeners.FastAgListener;
import com.fintech.petoindia.listeners.OperatorListener;
import com.fintech.petoindia.listeners.PaymentListener;
import com.fintech.petoindia.listeners.RegularClick;
import com.fintech.petoindia.listeners.ResetListener;
import com.fintech.petoindia.listeners.VisibilityListener;
import com.fintech.petoindia.pagestate.BBPSState;
import com.fintech.petoindia.util.Accessable;
import com.fintech.petoindia.util.ConfirmationUtil;
import com.fintech.petoindia.util.DisplayMessageUtil;
import com.fintech.petoindia.util.MyAlertUtils;
import com.fintech.petoindia.util.NetworkUtil;
import com.fintech.petoindia.util.PopupUtil;
import com.fintech.petoindia.util.UtilHolder;
import com.fintech.petoindia.util.ViewUtils;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;

import javax.inject.Inject;

import dagger.hilt.android.lifecycle.HiltViewModel;
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers;
import io.reactivex.rxjava3.annotations.NonNull;
import io.reactivex.rxjava3.core.Observer;
import io.reactivex.rxjava3.disposables.Disposable;
import io.reactivex.rxjava3.schedulers.Schedulers;

@HiltViewModel
public class MobileRechargeViewModel extends ViewModel implements CircleListener {

    public String mode = "";
    public String name = null;
    public String plan = null;
    public OperatorModel operatorModel = null;
    public MenuModel menuModel;
    public String mPin = "";
    public static String service = null;
    public String enteredNumber = null;
    public PaymentListener listener = null;

    public MutableLiveData<String> mobileNumber = new MutableLiveData<>("");
    public HLRResponse hlrResponse = new HLRResponse();

    //Dth Bill essential ends
    public ResetListener resetListener;
    boolean access = false;
    public BBPSOPData selectedBBPS;

    //LIC
    public String lic_number = "";
    public String lic_email = "";
    public String LICMODE = "";
    //LIC

    //FASTAG
    public OperatorResponse.OperatorData fastagOpperatorModel;
    //FASTAG

    //credit card variables
    public String cc_mobile = "";
    public String cc_number = "";
    public String cc_amount = "";
    public String cc_name = "";
    public String cc_otp = "";
    public String cc_reference = "";
    public String selectedcardType = "";
    //credit card


    public VisibilityListener visibilityListener;
    public MobileRechargesRepository mobileRechargesRepository;
    public FetchBillInfo myFetchedBill;
    MutableLiveData<BBPSState> _bBBPs_state = new MutableLiveData<>(BBPSState.SELECTION_OF_BILLER);
    public LiveData<BBPSState> bBBPsState = _bBBPs_state;
    public ArrayList<BBPSOPData> opData;
    public String consumer_number;

    public String ad1 = "";
    public String ad2 = "";
    public String ad3 = "";
    public String op_name = "", op_category = "";
    HashSet<String> categoryList;

    public MutableLiveData<HLR_STATE> hlr_state = new MutableLiveData<>(HLR_STATE.FETCH);

    @Inject
    public MobileRechargeViewModel(MobileRechargesRepository mobileRechargesRepository) {
        this.mobileRechargesRepository = mobileRechargesRepository;
    }

    public void onRechargeButtonClick(View view) {
        if (Accessable.isAccessable()) {
            if (PopupUtil.access) {
                if (mobileNumber.getValue() == null || mobileNumber.getValue().isEmpty()) {
                    MyAlertUtils.showAlertDialog(view.getContext(), "Warning", "Provide a valid number", R.drawable.warning);
                } else if (plan == null || plan.isEmpty()) {
                    MyAlertUtils.showAlertDialog(view.getContext(), "Warning", "Provide a valid plan", R.drawable.warning);
                } else if (mPin == null || mPin.trim().isEmpty()) {
                    MyAlertUtils.showAlertDialog(view.getContext(), "Warning", "Provide a valid M-pin", R.drawable.warning);
                } else {
                    String message = mobileNumber.getValue().replaceAll("[^\\w]", "");
                    StringBuilder builder = new StringBuilder(message);
                    if (service.equals("prepaid")) {
                        if (builder.charAt(0) == '9' && builder.charAt(1) == '1') {
                            builder.deleteCharAt(0);
                            builder.deleteCharAt(0);
                        }
                    }
                    MyAlertUtils.showProgressAlertDialog(view.getContext());
                    mobileRechargesRepository.accessThePayment(view.getContext(), String.valueOf(builder), plan, operatorModel.getOperatorcode(), mPin, resetListener);
                    //PopupUtil.access = false;
                }
            } else {
                PopupUtil.tPinSystem(view.getContext(), mobileRechargesRepository.apiServices);
            }
        }
    }

    public LiveData<List<OperatorModel>> getOperators(String type, Context context) {
        return mobileRechargesRepository.getOperatorsList(type, context);
    }

    @Override
    public void MyCircleListener(View view, CircleModel model) {
        ViewUtils.showToast(view.getContext(), model.getCircle());
    }


    public LiveData<BrowsePlanResponse> getBrowsePlan(Context context) {
        DisplayMessageUtil.loading(context);
        MutableLiveData<BrowsePlanResponse> data = new MutableLiveData<>();
        mobileRechargesRepository.apiServices
                .browsePlan(operatorModel.getName(), "check_plan")
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(resp -> {
                    DisplayMessageUtil.dismissDialog();
                    if (resp.status && resp.responseCode.equals(1)) {
                        data.setValue(resp);
                    } else {
                        DisplayMessageUtil.error(context, resp.message);
                    }
                    data.setValue(resp);
                }, err -> DisplayMessageUtil.error(context, err.getLocalizedMessage()));
        return data;
    }


    public void findCustomerInfoDth(Context context) {
        DisplayMessageUtil.loading(context);
        mobileRechargesRepository.apiServices
                .dth_info(mobileNumber.getValue(), operatorModel.getName(), "dth_info")
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(res -> {
                    DisplayMessageUtil.dismissDialog();
                    if (res.getStatus() && res.getResponse_code().equals(1)) {
                        dthInfoDesign(context, res.getInfo());
                    } else {
                        DisplayMessageUtil.error(context, res.getMessage());
                    }
                }, err -> DisplayMessageUtil.error(context, err.getLocalizedMessage()));
    }

    private void dthInfoDesign(Context context, List<? extends DthInfoResponse.Info> list) {
        Dialog dialog = new Dialog(context, R.style.MyTransparentBottomSheetDialogTheme);
        DthInformationDesignBinding binding = DthInformationDesignBinding.inflate(LayoutInflater.from(context));
        dialog.setContentView(binding.getRoot());
        binding.myRecycler.setLayoutManager(new GridLayoutManager(context, 1, GridLayoutManager.VERTICAL, false));
        binding.myRecycler.setAdapter(new DthInfoAdapter(list));
        binding.cancel.setOnClickListener(v -> {
            dialog.dismiss();
        });
        dialog.show();
    }


    public void onFetchTheBillLic(View view) {
        if (Accessable.isAccessable()) {
            if (lic_number.isEmpty()) {
                MyAlertUtils.showWarningAlertDialog(view.getContext(), "Provide Lic Number");
            } else if (lic_email.isEmpty()) {
                MyAlertUtils.showWarningAlertDialog(view.getContext(), "Provide Lic Email");
            } else {
                DisplayMessageUtil.loading(view.getContext());
                mobileRechargesRepository.apiServices.fetchLicBill(lic_number, lic_email, "fetch_lic")
                        .subscribeOn(Schedulers.io())
                        .observeOn(AndroidSchedulers.mainThread())
                        .subscribe(res -> {
                            DisplayMessageUtil.dismissDialog();
                            if (res.isStatus()) {
                                showBillDataLic(view.getContext(), res);
                            } else {
                                DisplayMessageUtil.error(view.getContext(), res.getMessage());
                            }
                        }, err -> DisplayMessageUtil.error(view.getContext(), err.getMessage()));
            }
        }
    }

    private void showBillDataLic(Context context, LicFetchResponse response) {
        AlertDialog.Builder alert = new AlertDialog.Builder(context, R.style.CustomDialog);
        FetchBillLicBinding binding = FetchBillLicBinding.inflate(LayoutInflater.from(context));
        alert.setView(binding.getRoot());
        AlertDialog alertDialog = alert.create();
        alertDialog.setCanceledOnTouchOutside(false);
        alertDialog.show();
        binding.setBillData(response);
        binding.consumerId.setText(lic_number);
        binding.cancelButton.setOnClickListener(v -> alertDialog.dismiss());
        binding.confirmButton.setOnClickListener(v -> {

            ObjectWriter ow = new ObjectMapper().writer().withDefaultPrettyPrinter();
            String json = "";
            try {
                json = ow.writeValueAsString(response);
            } catch (JsonProcessingException e) {
                e.printStackTrace();
            }
            MyAlertUtils.showProgressAlertDialog(context);
            NetworkUtil.getNetworkResult(mobileRechargesRepository.apiServices.payLicBill(lic_number, lic_email, json, UtilHolder.getLongitude(), UtilHolder.getLatitude(), "pay_lic", LICMODE), context,
                    data -> {
                        if (data.isStatus()) {
                            resetListener.resetRequiredData(true);
                            alertDialog.dismiss();
                            DisplayMessageUtil.success(context, data.getMessage());
                        } else {
                            DisplayMessageUtil.error(context, data.getMessage());
                        }
                    });
        });
    }


    public void fetchBbpsCategory(View view) {
        EditText categoryName = view.getRootView().findViewById(R.id.category_bbps);
        if (categoryList == null) {
            categoryList = new HashSet<>();
            fetchBbpsOperatorsCategory(view.getContext(), data -> {
                if (data.equals(1)) {
                    popInfoDesignCategory(view.getContext(), (viewData, data1) -> {
                        categoryName.setText(data1);
                        op_category = data1;
                    });
                }
            });
        } else {
            popInfoDesignCategory(view.getContext(), (viewData, data) -> {
                categoryName.setText(data);
                op_category = data;
            });
        }
    }

    public void fetchBbpsOperatorsCategory(Context context, Receiver<Integer> receiver) {
        if (Accessable.isAccessable()) {
            DisplayMessageUtil.loading(context);
            mobileRechargesRepository.apiServices
                    .fetchBBpsOperators("fetch_operators")
                    .subscribeOn(Schedulers.io())
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribe(res -> {
                        DisplayMessageUtil.dismissDialog();
                        if (res.getStatus() && res.getResponse_code().equals(1)) {
                            opData = res.getData();
                            for (BBPSOPData e : res.getData()) {
                                try {
                                    categoryList.add(e.getCategory().toUpperCase().trim());
                                } catch (NullPointerException exception) {
                                    exception.printStackTrace();
                                }
                            }
                            receiver.getData(1);
                        } else {
                            DisplayMessageUtil.success(context, res.getMessage());
                        }
                    }, err -> DisplayMessageUtil.error(context, err.getLocalizedMessage()));
        }
    }


    public void fetchBbpsOperators(View view) {
        EditText category_bbps = view.getRootView().findViewById(R.id.category_bbps);
        EditText operatorName = view.getRootView().findViewById(R.id.operator_bbps);
        if (category_bbps.getText().toString().trim().isEmpty()) {
            DisplayMessageUtil.error(view.getContext(), "Select a valid category");
            return;
        } else if (opData != null) {
            ArrayList<BBPSOPData> arr_filtered = new ArrayList<>();
            if (op_category.equals("Gas/LPG")) {
                for (BBPSOPData e : opData) {
                    if (e.getCategory().equalsIgnoreCase("GAS") || e.getCategory().equalsIgnoreCase("LPG")) {
                        arr_filtered.add(e);
                    }
                }
            } else {
                for (BBPSOPData e : opData) {
                    if (e.getCategory().equalsIgnoreCase(category_bbps.getText().toString())) {
                        arr_filtered.add(e);
                    }
                }
            }


            popInfoDesign(view.getContext(), arr_filtered, (view1, data) -> {
                operatorName.setText(data);
                op_name = data;
            });
            return;
        }
        if (Accessable.isAccessable()) {
            ArrayList<BBPSOPData> arr_filtered = new ArrayList<>();
            DisplayMessageUtil.loading(view.getContext());
            mobileRechargesRepository.apiServices
                    .fetchBBpsOperators("fetch_operators")
                    .subscribeOn(Schedulers.io())
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribe(res -> {
                        DisplayMessageUtil.dismissDialog();
                        if (res.getStatus() && res.getResponse_code().equals(1)) {
                            opData = res.getData();
                            for (BBPSOPData e : res.getData()) {
                                try {
                                    if (e.getCategory().equalsIgnoreCase("Gas/LPG")) {
                                        if (e.getCategory().equalsIgnoreCase("GAS") || e.getCategory().equalsIgnoreCase("LPG")) {
                                            arr_filtered.add(e);
                                        }
                                    } else {
                                        if (e.getCategory().equalsIgnoreCase(op_category)) {
                                            arr_filtered.add(e);
                                        }
                                    }
                                } catch (NullPointerException exception) {
                                    exception.printStackTrace();
                                }
                            }
                            popInfoDesign(view.getContext(), arr_filtered, (view1, data) -> operatorName.setText(data));
                        } else {
                            DisplayMessageUtil.success(view.getContext(), res.getMessage());
                        }
                    }, err -> DisplayMessageUtil.error(view.getContext(), err.getLocalizedMessage()));
        }
    }


    public void proceedToFetchBillBBPS(View view) {
        if (selectedBBPS == null) {
            DisplayMessageUtil.error(view.getContext(), "Select a valid " + menuModel.getTitle() + " operator.");
        } else {
            _bBBPs_state.setValue(BBPSState.BILL_FETCH);
        }
    }

    public void onFetchBBPSBill(View view) {
        if (Accessable.isAccessable()) {

            /**
             if(selectedBBPS.getAd1_d_name() != null && !selectedBBPS.getAd1_d_name().trim().isEmpty() && selectedBBPS.getAd1_regex()!=null){
             boolean isValid = ViewUtils.isRegexValid(getAd1(), selectedBBPS.getAd1_regex());
             if(!isValid){
             DisplayMessageUtil.error(view.getContext(), "Enter valid "+selectedBBPS.getAd1_d_name());
             return;
             }
             }
             if(selectedBBPS.getAd2_d_name() != null && !selectedBBPS.getAd2_d_name().trim().isEmpty() && selectedBBPS.getAd2_regex()!=null){
             boolean isValid = ViewUtils.isRegexValid( getAd2(), selectedBBPS.getAd2_regex());
             if(!isValid){
             DisplayMessageUtil.error(view.getContext(), "Enter valid "+selectedBBPS.getAd2_d_name());
             return;
             }
             }
             if(selectedBBPS.getAd3_d_name() != null && !selectedBBPS.getAd3_d_name().trim().isEmpty()&& selectedBBPS.getAd3_regex()!=null){
             boolean isValid = ViewUtils.isRegexValid( getAd3(), selectedBBPS.getAd3_regex());
             if(!isValid){
             DisplayMessageUtil.error(view.getContext(), "Enter valid "+selectedBBPS.getAd3_d_name());
             return;
             }
             }
             **/

            if (consumer_number == null || consumer_number.trim().isEmpty() || consumer_number.trim().length() < 5) {
                DisplayMessageUtil.error(view.getContext(), "Enter a valid " + selectedBBPS.getDisplayname());
                return;
            }
            ObjectWriter ow = new ObjectMapper().writer().withDefaultPrettyPrinter();
            String json = "";
            try {
                json = ow.writeValueAsString(selectedBBPS);
            } catch (JsonProcessingException e) {
                e.printStackTrace();
            }

            DisplayMessageUtil.loading(view.getContext());
            mobileRechargesRepository.apiServices
                    .fetchBBpsBill(consumer_number, selectedBBPS.getId(), "", getAd1(), getAd2(), getAd3(), json)
                    .subscribeOn(Schedulers.io())
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribe(res -> {
                        DisplayMessageUtil.dismissDialog();
                        if (res.isStatus() && res.getResponse_code().equals(1)) {
                            if (res.getBill_fetch() != null && res.getBill_fetch().getBillnetamount() != null) {
                                myFetchedBill = res;
                                _bBBPs_state.setValue(BBPSState.BILL_PAYEMENT);
                            } else {
                                DisplayMessageUtil.error(view.getContext(), "Enter a valid detail\n" + res);
                            }
                        } else {
                            DisplayMessageUtil.error(view.getContext(), res.getMessage());
                        }
                    }, err -> DisplayMessageUtil.error(view.getContext(), err.getLocalizedMessage()));
        }
    }

    public void proceedToPayBillBBPS(View view) {
        if (Accessable.isAccessable()) {
            if (myFetchedBill == null || op_name == null || op_category == null) {
                ViewUtils.showToast(view.getContext(), "Select all information");
                _bBBPs_state.setValue(BBPSState.SELECTION_OF_BILLER);
            }
//            else if(mPin==null || mPin.trim().isEmpty()){
//                ViewUtils.showSnackBar(view, "Enter a valid M-PIN");
//            }
            else {

                ObjectWriter ow = new ObjectMapper().writer().withDefaultPrettyPrinter();
                String json = "";
                try {
                    json = ow.writeValueAsString(myFetchedBill);
                } catch (JsonProcessingException e) {
                    e.printStackTrace();
                }
                DisplayMessageUtil.loading(view.getContext());
                String type = "ONLINE";
                mobileRechargesRepository.apiServices
                        .payBBpsBill(myFetchedBill.bill_fetch.billnetamount, consumer_number, selectedBBPS.getId(), json, op_name, op_category, mPin, UtilHolder.getLongitude(), UtilHolder.getLatitude(), type)
                        .subscribeOn(Schedulers.io())
                        .observeOn(AndroidSchedulers.mainThread())
                        .subscribe(res -> {
                            DisplayMessageUtil.dismissDialog();
                            if (res.getResponse_code().equals(1)) {
                                DisplayMessageUtil.success(view.getContext(), res.getMessage());
                                consumer_number = "";
                                myFetchedBill = null;
                                _bBBPs_state.setValue(BBPSState.SELECTION_OF_BILLER);
                            } else {
                                DisplayMessageUtil.error(view.getContext(), res.getMessage());
                            }
                        }, err -> DisplayMessageUtil.error(view.getContext(), err.getLocalizedMessage()));
            }
        }
    }


    private <T> void popInfoDesign(Context context, ArrayList<T> list, @Nullable RegularClick regularClick) {
        Dialog dialog = new Dialog(context, R.style.MyTransparentBottomSheetDialogTheme);
        BbpsCategoryDesignBinding binding = BbpsCategoryDesignBinding.inflate(LayoutInflater.from(context));
        dialog.setContentView(binding.getRoot());
        int search_plateId = context.getResources().getIdentifier("android:id/search_plate", null, null);
        View mSearchPlate = binding.searchBar.findViewById(search_plateId);
        mSearchPlate.setBackgroundColor(Color.TRANSPARENT);
        ArrayAdapter<T> adapter = new ArrayAdapter<>(context, android.R.layout.simple_list_item_1, list);
        binding.searchBar.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                adapter.getFilter().filter(query);
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                adapter.getFilter().filter(newText);
                return false;
            }
        });
        binding.myRecycler.setAdapter(adapter);
        binding.myRecycler.setOnItemClickListener((parent, view, position, id) -> {
            selectedBBPS = (BBPSOPData) adapter.getItem(position);
            if (regularClick != null) {
                regularClick.onClickItem(view, selectedBBPS.getName());
            }
            dialog.dismiss();
        });

        binding.cancel.setOnClickListener(v -> dialog.dismiss());
        dialog.show();
    }


    private void popInfoDesignCategory(Context context, @Nullable RegularClick regularClick) {
        ArrayList<String> list = new ArrayList<>(categoryList);
        Dialog dialog = new Dialog(context, R.style.MyTransparentBottomSheetDialogTheme);
        BbpsCategoryDesignBinding binding = BbpsCategoryDesignBinding.inflate(LayoutInflater.from(context));
        dialog.setContentView(binding.getRoot());
        int search_plateId = context.getResources().getIdentifier("android:id/search_plate", null, null);
        View mSearchPlate = binding.searchBar.findViewById(search_plateId);
        mSearchPlate.setBackgroundColor(Color.TRANSPARENT);
        ArrayAdapter<String> adapter = new ArrayAdapter<>(context, android.R.layout.simple_list_item_1, list);
        binding.searchBar.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                adapter.getFilter().filter(query);

                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                adapter.getFilter().filter(newText);

                return false;
            }
        });
        binding.myRecycler.setAdapter(adapter);
        binding.myRecycler.setOnItemClickListener((parent, view, position, id) -> {
            if (regularClick != null) {
                regularClick.onClickItem(view, adapter.getItem(position).toString());
            }
            dialog.dismiss();
        });

        binding.cancel.setOnClickListener(v -> dialog.dismiss());
        dialog.show();
    }


    public void setAd1(String ad1) {
        this.ad1 = ad1;
    }

    public void setAd2(String ad2) {
        this.ad2 = ad2;
    }

    public void setAd3(String ad3) {
        this.ad3 = ad3;
    }

    public String getAd1() {
        if (ad1 == null) {
            return "";
        }
        return ad1;
    }

    public String getAd2() {
        if (ad2 == null) {
            return "";
        }
        return ad2;
    }

    public String getAd3() {
        if (ad3 == null) {
            return "";
        }
        return ad3;
    }


    //FASTAG

    public void onFetchTheBillFastag(View view) {
        if (Accessable.isAccessable()) {
            if (consumer_number != null && consumer_number.trim().length() < 3) {
                MyAlertUtils.showWarningAlertDialog(view.getContext(), "Provide Provide valid" + fastagOpperatorModel.getDisplayname());
            } else {
                fetchFasTagBillOnline(view.getContext());
            }
        }
    }

    //Fastag

    public void bring_fastAgList(Context context, FastAgListener listener) {
        MyAlertUtils.showProgressAlertDialog(context);
        mobileRechargesRepository.apiServices.getFast_agOperator("fastag_list")
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<OperatorResponse>() {
                    @Override
                    public void onSubscribe(@NonNull Disposable d) {

                    }

                    @Override
                    public void onNext(@NonNull OperatorResponse s) {
                        MyAlertUtils.dismissAlertDialog();
                        if (s.isStatus() && s.getResponse_code() == 1) {
                            listener.setRecycler(s.getData());
                        } else {
                            MyAlertUtils.showWarningAlertDialog(context, s.getMessage());
                        }
                    }

                    @Override
                    public void onError(@NonNull Throwable e) {
                        MyAlertUtils.showServerAlertDialog(context, "Failed due to\n" + e.getMessage());
                    }

                    @Override
                    public void onComplete() {

                    }
                });
    }


    public void fetchFasTagBillOnline(Context context) {
        DisplayMessageUtil.loading(context);
        mobileRechargesRepository.apiServices.fetchFasTagBill(consumer_number, fastagOpperatorModel.getId(), "fetch_fast")
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(res -> {
                    DisplayMessageUtil.dismissDialog();
                    if (res.getResponse_code().equals(1)) {
                        showBillDataFastAg(context, res);
                    } else {
                        DisplayMessageUtil.error(context, res.getMessage());
                    }
                }, err -> {
                    DisplayMessageUtil.error(context, err.getMessage());
                });
    }


    private void showBillDataFastAg(Context context, FastagBillFetch fastagBillFetch) {
        AlertDialog.Builder alert = new AlertDialog.Builder(context, R.style.CustomDialog);
        FetchBillFastagBinding binding = FetchBillFastagBinding.inflate(LayoutInflater.from(context));
        alert.setView(binding.getRoot());
        AlertDialog alertDialog = alert.create();
        alertDialog.setCanceledOnTouchOutside(false);
        alertDialog.show();
        binding.setBillData(fastagBillFetch);
        binding.consumerId.setText(consumer_number);
        if (fastagBillFetch.getDuedate() == null) {
            binding.dueDateLayout.setVisibility(View.GONE);
        }


        binding.cancelButton.setOnClickListener(v -> alertDialog.dismiss());
        binding.confirmButton.setOnClickListener(v -> {
            try {
                float checkBalance = Float.parseFloat(fastagBillFetch.getAmount());
                if (checkBalance < 99 || checkBalance >= 1000) {
                    DisplayMessageUtil.error(context, "Please make transaction between 100 Rs & 1000");
                    return;
                }
            } catch (NumberFormatException exception) {
                DisplayMessageUtil.error(context, "Provide a valid amount");
                return;
            }

            ObjectWriter ow = new ObjectMapper().writer().withDefaultPrettyPrinter();
            String json = "";
            try {
                json = ow.writeValueAsString(fastagBillFetch);
            } catch (JsonProcessingException e) {
                e.printStackTrace();
            }
            alertDialog.dismiss();
            DisplayMessageUtil.loading(context);
            mobileRechargesRepository.apiServices.payFastAgBill(consumer_number, fastagOpperatorModel.getId(), json, UtilHolder.getLongitude(), UtilHolder.getLatitude(), "pay_fastag")
                    .subscribeOn(Schedulers.io())
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribe(res -> {
                        if (res.getResponse_code().equals(1)) {
                            DisplayMessageUtil.success(context, res.getMessage());
                            resetListener.resetRequiredData(true);
                        } else {
                            DisplayMessageUtil.error(context, res.getMessage());
                        }
                    }, err -> DisplayMessageUtil.error(context, err.getMessage()));
        });
    }

    public void getHLRData(Context context) {
        NetworkUtil.getNetworkResult(mobileRechargesRepository.apiServices.getHLRInformation(mode, mobileNumber.getValue(), "hlr_check"),
                context, data -> {
                    if (data.getResponse_code().equals(1)) {
                        operatorModel = data.getOperator();
                        hlrResponse = data;
                        hlr_state.setValue(HLR_STATE.FOUND);
                    } else {
                        //DisplayMessageUtil.error(context, data.getMessage());
                        getOperators("Prepaid", context).observeForever(operatorModels -> {
                            if (operatorModels != null) {
                                setOperators(operatorModels, context);
                            }
                        });
                    }
                });
    }


    public void setOperators(List<OperatorModel> list, Context context) {
        ActivitySelectOperatorBinding binding = ActivitySelectOperatorBinding.inflate(LayoutInflater.from(context));
        AlertDialog alertDialog = new AlertDialog.Builder(context).create();
        alertDialog.setView(binding.getRoot());
        alertDialog.setCanceledOnTouchOutside(false);
        alertDialog.show();
        if (list == null || list.isEmpty()) {
            binding.noOperators.setVisibility(View.GONE);
        }
        binding.parentScroll.setBackgroundColor(Color.TRANSPARENT);
        binding.parentScroll.setBackgroundResource(android.R.color.transparent);
        binding.selectOperatorSliders.setVisibility(View.GONE);
        binding.operatorsRecycler.setLayoutManager(new GridLayoutManager(context, 1, GridLayoutManager.VERTICAL, false));
        OperatorAdapter operatorAdapter = new OperatorAdapter(list, new OperatorListener() {
            @Override
            public void myOperatorClicks(View view, OperatorModel model) {
                operatorModel = model;
                hlrResponse = new HLRResponse(0, "manual", operatorModel, false, new HLRResponse.Info("IN", operatorModel.getName()));
                hlr_state.setValue(HLR_STATE.FOUND);
                alertDialog.dismiss();
            }

            @Override
            public void myOperatorClicks(View view, OperatorResponse.OperatorData data) {

            }

            @Override
            public void noOperatorFound(boolean result) {
                if (result) {
                    binding.noOperators.setVisibility(View.VISIBLE);
                } else {
                    binding.noOperators.setVisibility(View.GONE);
                }
            }
        });
        binding.operatorsRecycler.setAdapter(operatorAdapter);
        binding.operatorsRecycler.setOverScrollMode(View.OVER_SCROLL_NEVER);
        binding.OperatorListCard.setVisibility(View.VISIBLE);
        binding.operatorSearchList.setOnQueryTextListener(new androidx.appcompat.widget.SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                operatorAdapter.getFilter().filter(query);
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                operatorAdapter.getFilter().filter(newText);
                return false;
            }
        });
    }

    public void selectCardType(View view) {
        if (Accessable.isAccessable()) {
            EditText cardType = view.findViewById(R.id.cardType);
            List<String> list = new ArrayList<>();
            list.add("VISA");
            list.add("MASTERCARD");
            ViewUtils.onSpinnerViewBring("Select Card Type", view.getContext(), list, selection -> {
                cardType.setText(selection);
                selectedcardType = selection;
            });
        }
    }

    public void startCreditCardPayment(View view) {
        if (Accessable.isAccessable()) {
            if (cc_name.isEmpty()) {
                MyAlertUtils.showWarningAlertDialog(view.getContext(), "Provide Name");
            } else if (cc_number.isEmpty()) {
                MyAlertUtils.showWarningAlertDialog(view.getContext(), "Provide Card Number");
            } else if (cc_amount.isEmpty()) {
                MyAlertUtils.showWarningAlertDialog(view.getContext(), "Provide Amount");
            } else if (cc_mobile.isEmpty()) {
                MyAlertUtils.showWarningAlertDialog(view.getContext(), "Provide Mobile");
            } else if (cc_otp.isEmpty()) {
                MyAlertUtils.showWarningAlertDialog(view.getContext(), "Provide OTP");
            } else if (selectedcardType.isEmpty()) {
                MyAlertUtils.showWarningAlertDialog(view.getContext(), "Select a valid card Type");
            } else {
                ConfirmationUtil.showConfirmation(view.getContext(), () ->
                        NetworkUtil.getNetworkResult(mobileRechargesRepository.apiServices.payCreditCardBill(
                                selectedcardType, cc_name, cc_amount, cc_number, cc_mobile, cc_reference, cc_otp
                        ), view.getContext(), result -> {
                            if (result.status) {
                                DisplayMessageUtil.success(view.getContext(), result.message);
                                resetListener.resetRequiredData(true);
                            } else {
                                DisplayMessageUtil.error(view.getContext(), result.message);
                            }
                        }));
            }
        }
    }


    public void sendOtpCrediCardBill(View view) {
        if (Accessable.isAccessable()) {
            if (cc_name.isEmpty()) {
                MyAlertUtils.showWarningAlertDialog(view.getContext(), "Provide Name");
            } else if (cc_number.isEmpty()) {
                MyAlertUtils.showWarningAlertDialog(view.getContext(), "Provide Card Number");
            } else if (cc_amount.isEmpty()) {
                MyAlertUtils.showWarningAlertDialog(view.getContext(), "Provide Amount");
            } else if (cc_mobile.isEmpty() || cc_mobile.length() < 10) {
                MyAlertUtils.showWarningAlertDialog(view.getContext(), "Provide valid Mobile");
            } else if (selectedcardType.isEmpty()) {
                MyAlertUtils.showWarningAlertDialog(view.getContext(), "Select a valid card Type");
            } else {
                NetworkUtil.getNetworkResult(mobileRechargesRepository.apiServices.fetchCreditCardBill(
                        selectedcardType, cc_name.trim(), cc_amount, cc_number, cc_mobile, "fetcBill"
                ), view.getContext(), result -> {
                    if (result.status) {
                        cc_reference = result.ref_id;
                        visibilityListener.startVisibility();
                    } else {
                        DisplayMessageUtil.error(view.getContext(), result.message);
                    }
                });
            }
        }
    }


    public enum HLR_STATE {
        FETCH,
        FOUND
    }

}
