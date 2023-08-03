package com.fintech.superadmin.viewmodel;

import android.annotation.SuppressLint;
import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.view.View;

import androidx.lifecycle.ViewModel;

import com.fintech.superadmin.activities.aeps.AepsReceiptActivity;
import com.fintech.superadmin.data.model.AEPSBanksModel;
import com.fintech.superadmin.data.network.AePSDto;
import com.fintech.superadmin.data.network.responses.AePSBalanceEnquiryResponse;
import com.fintech.superadmin.data.network.responses.MiniStatementResponse;
import com.fintech.superadmin.data.repositories.AepsRepository;
import com.fintech.superadmin.deer_listener.Receiver;
import com.fintech.superadmin.listeners.AepsBankListener;
import com.fintech.superadmin.listeners.BankNameListener;
import com.fintech.superadmin.listeners.ResetListener;
import com.fintech.superadmin.util.DisplayMessageUtil;
import com.fintech.superadmin.util.NetworkUtil;
import com.fintech.superadmin.util.ViewUtils;

import java.util.List;

import javax.inject.Inject;

import dagger.hilt.android.lifecycle.HiltViewModel;

@HiltViewModel
public class AepsViewModel extends ViewModel implements AepsBankListener {

    public String aadhaar = "";
    public String mobile = "";
    public String amount = "";
    public String transactionType = "";
    public String selectedBank = null;
    public String bank = "1";

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


    public void selectBankList(View view, String apiType, Receiver<AEPSBanksModel> receiver) {

        if (aepsBanksList == null) {
            if (apiType.trim().equalsIgnoreCase("paysprint")) {
                NetworkUtil.getNetworkResult(aepsRepository.apiServices.getPaysprintBankList("123"), view.getContext(), aepsBanksModels -> {
                    aepsBanksList = aepsBanksModels;
                    ViewUtils.onSpinnerViewBring("Select Bank", view.getContext(), aepsBanksList, receiver::getData);
                });
            } else if (apiType.trim().equalsIgnoreCase("fingpay")) {
                NetworkUtil.getNetworkResult(aepsRepository.apiServices.getFingPayBankList("getMappedBanks"), view.getContext(), aepsBanksModels -> {
                    aepsBanksList = aepsBanksModels;
                    ViewUtils.onSpinnerViewBring("Select Bank", view.getContext(), aepsBanksList, receiver::getData);
                });
            }

        } else {
            ViewUtils.onSpinnerViewBring("Select Bank", view.getContext(), aepsBanksList, receiver::getData);
        }

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


    @SuppressLint("CheckResult")
    public void startAEPSServices(Context context, String apiType, String aadhar, String fingerData, String mobile, String transType, String longitude, String latitude, String amount, ResetListener listener) {

        if (apiType.trim().equalsIgnoreCase("paysprint")) {
            NetworkUtil.getNetworkResult(aepsRepository.apiServices.AEPSResponse(bank, "APP", aadhar, fingerData.trim(), mobile, transType, selectedAepsBankModel.getIinno(), longitude, latitude, amount), context, result -> {
                DisplayMessageUtil.dismissDialog();
                Intent intent = new Intent(context, AepsReceiptActivity.class);
                intent.putExtra("response", result);
                intent.putExtra("transactionType", transType.trim());
                intent.putExtra("bankName", selectedAepsBankModel.getBankname());
                listener.resetRequiredData(true);
                context.startActivity(intent);
            });
        } else if (apiType.trim().equalsIgnoreCase("fingpay")) {
            NetworkUtil.getNetworkResult(aepsRepository.apiServices.AEPSFingPayResponse("APP", aadhar, fingerData.trim(), mobile, transType, selectedAepsBankModel.getIinno(), longitude, latitude, amount), context, result -> {
                DisplayMessageUtil.dismissDialog();
                Intent intent = new Intent(context, AepsReceiptActivity.class);
                AePSDto aePSDto = result.toAePsDto(result);
                intent.putExtra("response", aePSDto);
                intent.putExtra("transactionType", transType.trim());
                intent.putExtra("bankName", selectedAepsBankModel.getBankname());
                listener.resetRequiredData(true);
                context.startActivity(intent);
            });
        }
    }


}
