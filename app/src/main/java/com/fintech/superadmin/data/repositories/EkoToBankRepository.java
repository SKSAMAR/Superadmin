package com.fintech.superadmin.data.repositories;

import android.annotation.SuppressLint;
import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.view.LayoutInflater;
import android.view.WindowManager;

import com.fintech.superadmin.activities.eko_tobank.RegisterRemitter;
import com.fintech.superadmin.activities.eko_tobank.ToAccount;
import com.fintech.superadmin.data.db.AppDatabase;
import com.fintech.superadmin.data.db.entities.User;
import com.fintech.superadmin.data.eko.EkoAddBeneResponse;
import com.fintech.superadmin.data.eko.EkoDmtVerificationResponse;
import com.fintech.superadmin.data.eko.EkoSendOtpResponse;
import com.fintech.superadmin.data.eko.QueryRemitterResponse;
import com.fintech.superadmin.data.eko.RecipientListItem;
import com.fintech.superadmin.data.model.BankModel;
import com.fintech.superadmin.data.model.EkoBankModel;
import com.fintech.superadmin.data.network.APIServices;
import com.fintech.superadmin.data.network.responses.AddBeneficiaryResponse;
import com.fintech.superadmin.data.network.responses.BeneficiaryBank;
import com.fintech.superadmin.data.network.responses.DmtUserData;
import com.fintech.superadmin.data.network.responses.RegisterRemitterResponse;
import com.fintech.superadmin.databinding.OtpScreenLayoutBinding;
import com.fintech.superadmin.listeners.DMTHomeListeners;
import com.fintech.superadmin.listeners.ToBankListener;
import com.fintech.superadmin.listeners.ToEkoBankListener;
import com.fintech.superadmin.util.DisplayMessageUtil;
import com.fintech.superadmin.util.MyAlertUtils;
import com.fintech.superadmin.viewmodel.EkoToBankViewModel;
import com.fintech.superadmin.viewmodel.ToBankViewModel;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import javax.inject.Inject;

import dagger.hilt.android.qualifiers.ApplicationContext;
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers;
import io.reactivex.rxjava3.annotations.NonNull;
import io.reactivex.rxjava3.core.Observer;
import io.reactivex.rxjava3.disposables.Disposable;
import io.reactivex.rxjava3.schedulers.Schedulers;

public class EkoToBankRepository {

    public APIServices apiServices;
    AppDatabase appDatabase;


    @Inject
    public EkoToBankRepository(@ApplicationContext Context context, APIServices apiServices) {
        appDatabase = AppDatabase.getAppDatabase(context);
        this.apiServices = apiServices;
    }


    public void getAllBanksFromApi(ToEkoBankListener listener) {

        apiServices.getAllEkoBanks("getEkoBanks")
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<ArrayList<EkoBankModel>>() {
                    @Override
                    public void onSubscribe(@NonNull Disposable d) {
                        listener.onStarted("Please Wait..");
                    }

                    @Override
                    public void onNext(@NonNull ArrayList<EkoBankModel> bankModelList) {
                        listener.setAllBanks(bankModelList);
                    }

                    @Override
                    public void onError(@NonNull Throwable e) {
                        listener.onCompleted(e.getMessage());
                    }

                    @Override
                    public void onComplete() {
                        listener.onCompleted("Done..");
                    }
                });
    }


    public void query_remitter_checking(String mobile, Context context) {

        MyAlertUtils.showProgressAlertDialog(context);
        apiServices.ekoqueryRemitter(mobile, "getInfoBtn")
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<QueryRemitterResponse>() {
                    @Override
                    public void onSubscribe(@NonNull Disposable d) {

                    }


                    @Override
                    public void onNext(@NonNull QueryRemitterResponse queryRemitterResponse) {
                        EkoToBankViewModel.MyQueryRemitterResponse = queryRemitterResponse;
                        if (queryRemitterResponse.getResponseStatusId() != null && queryRemitterResponse.getResponseStatusId().equals(0) && queryRemitterResponse.getStatus() != null && queryRemitterResponse.getStatus().equals(0)) {
                            Intent intent = new Intent(context, ToAccount.class);
                            intent.putExtra("number", mobile);
                            intent.putExtra("status", true);
                            intent.putExtra("message", queryRemitterResponse.getMessage());
                            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                            intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                            context.startActivity(intent);
                        } else if (queryRemitterResponse.getStatus() != null && queryRemitterResponse.getStatus().equals(463)) {

                            MyAlertUtils.dismissAlertDialog();
                            Intent intent = new Intent(context, RegisterRemitter.class);
                            context.startActivity(intent);
                        } else if (queryRemitterResponse.getResponseStatusId() != null && queryRemitterResponse.getResponseStatusId().equals(-1) && queryRemitterResponse.getStatus() != null && queryRemitterResponse.getStatus().equals(0)) {

                            MyAlertUtils.dismissAlertDialog();
                            Intent intent = new Intent(context, RegisterRemitter.class);
                            context.startActivity(intent);
                        } else {
                            DisplayMessageUtil.error(context, queryRemitterResponse.getMessage());
                        }
                    }

                    @Override
                    public void onError(@NonNull Throwable e) {
                        DisplayMessageUtil.error(context, e.getMessage());
                    }

                    @Override
                    public void onComplete() {

                    }
                });
    }

    public void register_remitter(Context context, String remitter_first_name, String remitter_last_name, String remitter_mobile, String address, String pincode, String dob) {
        MyAlertUtils.showProgressAlertDialog(context);
        apiServices.ekoregisterRemitter("send_otp", remitter_first_name, remitter_last_name, remitter_mobile, address, pincode, dob)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<EkoSendOtpResponse>() {
                    @Override
                    public void onSubscribe(@NonNull Disposable d) {

                    }

                    @Override
                    public void onNext(@NonNull EkoSendOtpResponse registerRemitterResponse) {
                        try {
                            if (registerRemitterResponse.getStatus().equals(0) && (registerRemitterResponse.getResponseStatusId().equals(0))) {
                                remitterOTPScreen(context, registerRemitterResponse.getData().getOtpRefId(), remitter_first_name, remitter_last_name, remitter_mobile, address, pincode, dob);
                            } else {
                                DisplayMessageUtil.error(context, registerRemitterResponse.getMessage());
                            }

                        } catch (Exception e) {
                            e.printStackTrace();
                            DisplayMessageUtil.error(context, "" + registerRemitterResponse.getMessage());
                        }
                    }

                    @Override
                    public void onError(@NonNull Throwable e) {
                        MyAlertUtils.showServerAlertDialog(context, e.getMessage());
                    }

                    @Override
                    public void onComplete() {

                    }
                });
    }


    public void verify_remitter(Context context, String otp, String str_res, String remitter_first_name, String remitter_last_name, String remitter_mobile, String address, String pincode, String dob) {
        MyAlertUtils.showProgressAlertDialog(context);
        apiServices.ekoVerifyRemitter(str_res, otp, remitter_first_name, remitter_last_name, remitter_mobile, address, pincode, dob)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<EkoDmtVerificationResponse>() {
                    @Override
                    public void onSubscribe(@NonNull Disposable d) {

                    }

                    @Override
                    public void onNext(@NonNull EkoDmtVerificationResponse registerRemitterResponse) {
                        try {
                            if (registerRemitterResponse.getStatus().equals(0) && (registerRemitterResponse.getResponseStatusId().equals(0))) {
                                Intent intent = new Intent(context, ToAccount.class);
                                intent.putExtra("number", remitter_mobile);
                                intent.putExtra("status", true);
                                intent.putExtra("message", registerRemitterResponse.getMessage());
                                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                                intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                                context.startActivity(intent);
                            } else {
                                DisplayMessageUtil.error(context, registerRemitterResponse.getMessage());
                            }

                        } catch (Exception e) {
                            e.printStackTrace();
                            DisplayMessageUtil.error(context, "" + registerRemitterResponse.getMessage());
                        }
                    }

                    @Override
                    public void onError(@NonNull Throwable e) {
                        MyAlertUtils.showServerAlertDialog(context, e.getMessage());
                    }

                    @Override
                    public void onComplete() {

                    }
                });
    }


    public void addMyBeneficiary(String bank_id, String selectedMobile, RecipientListItem selectedBankModel, Context context, String holder_name, String ifsc, String account_number, String dmt_mobile) {
        MyAlertUtils.showProgressAlertDialog(context);
        apiServices.ekoregisterBeneficiary(holder_name, account_number, ifsc, bank_id, dmt_mobile)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<EkoAddBeneResponse>() {
                    @Override
                    public void onSubscribe(@NonNull Disposable d) {

                    }

                    @Override
                    public void onNext(@NonNull EkoAddBeneResponse response) {
                        try {
                            if (response.getResponseStatusId().equals(0) && response.getStatus().equals(0)) {
                                Intent intent = new Intent(context, ToAccount.class);
                                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                                intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                                intent.putExtra("number", selectedMobile);
                                intent.putExtra("verificationProcess", true);
                                intent.putExtra("wholeInfoBeneficiary", response);
                                intent.putExtra("selectedBankModel", selectedBankModel);
                                context.startActivity(intent);
                            } else {
                                DisplayMessageUtil.error(context, response.getMessage());
                            }
                        }catch (Exception e){
                            e.printStackTrace();
                            DisplayMessageUtil.error(context, ""+response.getMessage());
                        }
                    }

                    @Override
                    public void onError(@NonNull Throwable e) {
                        MyAlertUtils.showServerAlertDialog(context, e.getMessage());
                    }

                    @Override
                    public void onComplete() {

                    }
                });

    }

    public void getAllMyDMTReports(DMTHomeListeners listeners) {
        User user = appDatabase.getUserDao().getRegularUser();

        apiServices.ekogetAllDMTUsers(user.getId(), user.getUserstatus())
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<List<DmtUserData>>() {
                    @Override
                    public void onSubscribe(@NonNull Disposable d) {
                        listeners.initiateStart();
                    }

                    @Override
                    public void onNext(@NonNull List<DmtUserData> list) {
                        listeners.setWholeRecycler(list);
                    }

                    @Override
                    public void onError(@NonNull Throwable e) {
                        listeners.setErrorInFetch("Failed due to\n" + e.getMessage());
                    }

                    @Override
                    public void onComplete() {

                    }
                });

    }

    @SuppressLint("SetTextI18n")
    private void remitterOTPScreen(Context context, String str_res, String remitter_first_name, String remitter_last_name, String remitter_mobile, String address, String pincode, String dob) {
        Dialog dialog = new Dialog(context);
        OtpScreenLayoutBinding binding = OtpScreenLayoutBinding.inflate(LayoutInflater.from(context));
        dialog.setContentView(binding.getRoot());
        dialog.getWindow().setLayout(WindowManager.LayoutParams.MATCH_PARENT, WindowManager.LayoutParams.WRAP_CONTENT);
        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        dialog.show();
        dialog.setCanceledOnTouchOutside(false);
        binding.cancelLayout.setOnClickListener(v -> dialog.dismiss());
        binding.verifyOtp.setText("Proceed");
        binding.verifyOtp.setOnClickListener(v -> {
            if (Objects.requireNonNull(binding.enterOtpDmt.getText()).toString().isEmpty()) {
                DisplayMessageUtil.error(context, "Provide OTP");
            } else {
                String otp = binding.enterOtpDmt.getText().toString();
                verify_remitter(context, otp, str_res, remitter_first_name, remitter_last_name, remitter_mobile, address, pincode, dob);
            }
        });
    }


    public void getAllMyDMTAccounts(DMTHomeListeners listeners, String person) {
        apiServices.ekogetAllDMTUsers(person, "dmt_acc")
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<List<DmtUserData>>() {
                    @Override
                    public void onSubscribe(@NonNull Disposable d) {

                    }

                    @Override
                    public void onNext(@NonNull List<DmtUserData> list) {
                        listeners.setWholeRecycler(list);
                    }

                    @Override
                    public void onError(@NonNull Throwable e) {
                        listeners.setErrorInFetch("Failed due to\n" + e.getMessage());
                    }

                    @Override
                    public void onComplete() {

                    }
                });
    }

}
