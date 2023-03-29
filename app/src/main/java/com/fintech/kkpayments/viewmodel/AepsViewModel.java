package com.fintech.kkpayments.viewmodel;

import android.annotation.SuppressLint;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.WindowManager;
import android.widget.SearchView;

import androidx.lifecycle.ViewModel;
import androidx.recyclerview.widget.GridLayoutManager;

import com.fintech.kkpayments.R;
import com.fintech.kkpayments.activities.facilityresponses.AePSSuccessMiniStatementResponse;
import com.fintech.kkpayments.activities.facilityresponses.AePSSuccessResponseScreen;
import com.fintech.kkpayments.activities.facilityresponses.AepsFailureReponseScreen;
import com.fintech.kkpayments.adapters.AEPSBankAdapter;
import com.fintech.kkpayments.data.model.AEPSBanksModel;
import com.fintech.kkpayments.data.network.responses.AePSBalanceEnquiryResponse;
import com.fintech.kkpayments.data.network.responses.MiniStatementResponse;
import com.fintech.kkpayments.data.repositories.AepsRepository;
import com.fintech.kkpayments.databinding.MyRecyclerSpinnerBinding;
import com.fintech.kkpayments.listeners.AepsBankListener;
import com.fintech.kkpayments.listeners.BankNameListener;
import com.fintech.kkpayments.listeners.ResetListener;
import com.fintech.kkpayments.util.MyAlertUtils;

import java.util.List;

import javax.inject.Inject;

import dagger.hilt.android.lifecycle.HiltViewModel;
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers;
import io.reactivex.rxjava3.annotations.NonNull;
import io.reactivex.rxjava3.core.Observer;
import io.reactivex.rxjava3.disposables.Disposable;
import io.reactivex.rxjava3.schedulers.Schedulers;

@HiltViewModel
public class AepsViewModel extends ViewModel implements AepsBankListener {

    public String aadhaar = "";
    public String mobile = "";
    public String amount = "";
    public String transactionType = "";
    public String selectedBank = null;

    public List<AEPSBanksModel> aepsBanksList = null;

    Dialog dialog;
    public AepsRepository aepsRepository;
    public BankNameListener bankNameListener;
    public static AePSBalanceEnquiryResponse globalAePSBalanceEnquiryResponse;
    public static MiniStatementResponse globalMiniStatementResponse;

    public static AEPSBanksModel selectedAepsBankModel = null;


    @Inject
    public AepsViewModel(AepsRepository aepsRepository) {
        this.aepsRepository = aepsRepository;
    }


    public void selectBankList(View view) {

        if (aepsBanksList == null) {
            ProgressDialog dialog = new ProgressDialog(view.getContext());
            dialog.setTitle("Loading");
            dialog.setMessage("Getting banks.");
            aepsRepository.apiServices.getBankList("123").subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread()).subscribe(new Observer<List<AEPSBanksModel>>() {
                @Override
                public void onSubscribe(@NonNull Disposable d) {
                    dialog.show();
                }

                @Override
                public void onNext(@NonNull List<AEPSBanksModel> aepsBanksModels) {
                    aepsBanksList = aepsBanksModels;
                }

                @Override
                public void onError(@NonNull Throwable e) {
                    dialog.setTitle("Error");
                    dialog.setMessage("Failed due to: " + e.getMessage());
                }

                @Override
                public void onComplete() {
                    dialog.dismiss();
                    setAepsBanksOnScreen(view);
                }
            });
        } else {
            setAepsBanksOnScreen(view);
        }

    }

    @SuppressLint("SetTextI18n")
    public void setAepsBanksOnScreen(View view) {
        MyRecyclerSpinnerBinding binding = MyRecyclerSpinnerBinding.inflate(LayoutInflater.from(view.getContext()));
        dialog = new Dialog(view.getContext(), R.style.BottomSheetDialogTheme);
        dialog.setContentView(binding.getRoot());
        dialog.getWindow().setLayout(WindowManager.LayoutParams.MATCH_PARENT, WindowManager.LayoutParams.WRAP_CONTENT);
        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        dialog.setCanceledOnTouchOutside(false);
        dialog.show();
        binding.headTitleSection.setText("Select Bank");
        AEPSBankAdapter aepsBankAdapter = new AEPSBankAdapter(aepsBanksList, this);
        binding.MyOperatorListView.setLayoutManager(new GridLayoutManager(view.getContext(), 1, GridLayoutManager.VERTICAL, false));
        binding.MyOperatorListView.setAdapter(aepsBankAdapter);
        binding.SearchOperator.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                aepsBankAdapter.getFilter().filter(query);
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                aepsBankAdapter.getFilter().filter(newText);
                return false;
            }
        });

    }

    @Override
    public void selectedAepsBanks(View view, AEPSBanksModel model) {
        selectedBank = model.getBankname();
        bankNameListener.setBankName(selectedBank);
        selectedAepsBankModel = model;
        if (dialog != null) {
            dialog.dismiss();
        }
    }


    public void startAEPSServices(Context context, String aadhar, String fingerData, String mobile, String transType, String longitude, String latitude, String amount, ResetListener listener) {
        MyAlertUtils.showProgressAlertDialog(context);
        aepsRepository.apiServices.AEPSResponse("APP", aadhar, fingerData.trim(), mobile, transType, selectedAepsBankModel.getIinno(), longitude, latitude, amount).subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread()).subscribe(new Observer<AePSBalanceEnquiryResponse>() {
            @Override
            public void onSubscribe(@NonNull Disposable d) {

            }

            @Override
            public void onNext(@NonNull AePSBalanceEnquiryResponse aePSBalanceEnquiryResponse) {

                globalAePSBalanceEnquiryResponse = aePSBalanceEnquiryResponse;
                MyAlertUtils.dismissAlertDialog();
                if (aePSBalanceEnquiryResponse.response_code.equals(1)) {
                    listener.resetRequiredData(true);
                    Intent intent = new Intent(context, AePSSuccessResponseScreen.class);
                    context.startActivity(intent);
                } else {
                    Intent intent = new Intent(context, AepsFailureReponseScreen.class);
                    context.startActivity(intent);
                }
            }

            @Override
            public void onError(@NonNull Throwable e) {
                MyAlertUtils.showServerAlertDialog(context, "Failed due to: " + e.getMessage());
            }

            @Override
            public void onComplete() {
//
            }
        });
    }


    public void startAepsResponseStatement(Context context, String aadhar, String fingerData, String mobile, String transType, String longitude, String latitude, String amount, ResetListener listener) {
        MyAlertUtils.showProgressAlertDialog(context);
        aepsRepository.apiServices.startAepsResponseMiniStatement("APP", aadhar, fingerData.trim(), mobile, transType, selectedAepsBankModel.getIinno(), longitude, latitude, amount).subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread()).subscribe(new Observer<MiniStatementResponse>() {
            @Override
            public void onSubscribe(@NonNull Disposable d) {

            }

            @Override
            public void onNext(@NonNull MiniStatementResponse miniStatementResponse) {
                globalMiniStatementResponse = miniStatementResponse;
                MyAlertUtils.dismissAlertDialog();
                if (miniStatementResponse.isStatus()) {
                    listener.resetRequiredData(true);
                    Intent intent = new Intent(context, AePSSuccessMiniStatementResponse.class);
                    context.startActivity(intent);
                } else {
                    MyAlertUtils.showServerAlertDialog(context, miniStatementResponse.getMessage());
                }

            }

            @Override
            public void onError(@NonNull Throwable e) {
                MyAlertUtils.showServerAlertDialog(context, "Failed due to: " + e.getMessage());
            }

            @Override
            public void onComplete() {
//
            }
        });
    }


}
