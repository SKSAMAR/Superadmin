package com.fintech.scnpay.data.repositories;

import android.annotation.SuppressLint;
import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.view.LayoutInflater;
import android.view.WindowManager;

import com.fintech.scnpay.activities.tobank.RegisterRemitter;
import com.fintech.scnpay.activities.tobank.ToAccount;
import com.fintech.scnpay.data.db.AppDatabase;
import com.fintech.scnpay.data.db.entities.User;
import com.fintech.scnpay.data.model.BankModel;
import com.fintech.scnpay.data.network.APIServices;
import com.fintech.scnpay.data.network.responses.AddBeneficiaryResponse;
import com.fintech.scnpay.data.network.responses.BeneficiaryBank;
import com.fintech.scnpay.data.network.responses.DmtUserData;
import com.fintech.scnpay.data.network.responses.QueryRemitterResponse;
import com.fintech.scnpay.data.network.responses.RegisterRemitterResponse;
import com.fintech.scnpay.databinding.OtpScreenLayoutBinding;
import com.fintech.scnpay.listeners.DMTHomeListeners;
import com.fintech.scnpay.listeners.ToBankListener;
import com.fintech.scnpay.util.DisplayMessageUtil;
import com.fintech.scnpay.util.MyAlertUtils;
import com.fintech.scnpay.viewmodel.ToBankViewModel;

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

public class ToBankRepository {

    public APIServices  apiServices;
    AppDatabase appDatabase;


    @Inject
    public ToBankRepository(@ApplicationContext Context context, APIServices  apiServices){
        appDatabase = AppDatabase.getAppDatabase(context);
        this.apiServices = apiServices;
    }


    public User getUserData(){
        return appDatabase.getUserDao().getRegularUser();
    }

    public void getAllBanksFromApi(ToBankListener listener){

        apiServices.getAllBanks("getBanks")
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<ArrayList<BankModel>>() {
                    @Override
                    public void onSubscribe(@NonNull Disposable d) {
                        listener.onStarted("Please Wait..");
                    }

                    @Override
                    public void onNext(@NonNull ArrayList<BankModel> bankModelList) {
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


    public void query_remitter_checking(String mobile, Context context){

        MyAlertUtils.showProgressAlertDialog(context);
        apiServices.queryRemitter( mobile,"proceeding")
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<QueryRemitterResponse>() {
                    @Override
                    public void onSubscribe(@NonNull Disposable d) {

                    }

                    @Override
                    public void onNext(@NonNull QueryRemitterResponse queryRemitterResponse) {
                        ToBankViewModel.MyQueryRemitterResponse = queryRemitterResponse;
                        if(queryRemitterResponse.isStatus() && (queryRemitterResponse.getResponse_code().equals(1) || queryRemitterResponse.getResponse_code().equals(4))){
                            Intent intent = new Intent(context, ToAccount.class);
                            intent.putExtra("number", mobile);
                            intent.putExtra("status", queryRemitterResponse.isStatus());
                            intent.putExtra("message",queryRemitterResponse.getMessage());
                            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                            intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                            context.startActivity(intent);
                        }
                        else if (queryRemitterResponse.getResponse_code().equals(111)){
                            Intent intent = new Intent(context, RegisterRemitter.class);
                            intent.putExtra("toUpdate", true);
                            context.startActivity(intent);
                        }
                        else if(!queryRemitterResponse.isStatus() && queryRemitterResponse.getResponse_code().equals(0)){

                            MyAlertUtils.dismissAlertDialog();
                            remitterOTPScreen(context);
                        }
                        else{
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

    public void query_remitter(String mobile, Context context){
        MyAlertUtils.showProgressAlertDialog(context);
        apiServices.queryRemitter(mobile,"proceeding")
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<QueryRemitterResponse>() {
                    @Override
                    public void onSubscribe(@NonNull Disposable d) {

                    }

                    @Override
                    public void onNext(@NonNull QueryRemitterResponse queryRemitterResponse) {
                        ToBankViewModel.MyQueryRemitterResponse = queryRemitterResponse;
                        if(queryRemitterResponse.isStatus() && queryRemitterResponse.getResponse_code().equals(1)){
//                            ToBankViewModel.remitter_mobile ="";
                            Intent intent = new Intent(context, ToAccount.class);
                            intent.putExtra("number", mobile);
                            intent.putExtra("status", queryRemitterResponse.isStatus());
                            intent.putExtra("message",queryRemitterResponse.getMessage());
                            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                            intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                            context.startActivity(intent);


//                            Intent intent = new Intent(context, DMTHome.class);
//                            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
//                            intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
//                            String message = null;
//                            intent.putExtra("message", message);
//                            context.startActivity(intent);
                        }
                        else if(!queryRemitterResponse.isStatus() && queryRemitterResponse.getResponse_code().equals(0)){
                            MyAlertUtils.dismissAlertDialog();
                            context.startActivity(new Intent(context, RegisterRemitter.class));
                        }
                        else{
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

    public void register_remitter(String otp ,Context context, String remitter_first_name, String remitter_last_name, String remitter_mobile, String address, String pincode, String dob, String str){
        MyAlertUtils.showProgressAlertDialog(context);
        apiServices.registerRemitter(otp,str, remitter_first_name,remitter_last_name,remitter_mobile, address, pincode, dob)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<RegisterRemitterResponse>() {
                    @Override
                    public void onSubscribe(@NonNull Disposable d) {

                    }
                    @Override
                    public void onNext(@NonNull RegisterRemitterResponse registerRemitterResponse) {
                        if(registerRemitterResponse.isStatus() && (registerRemitterResponse.response_code.equals(1))){
                            Intent intent = new Intent(context, ToAccount.class);
                            intent.putExtra("number", remitter_mobile);
                            intent.putExtra("status", true);
                            intent.putExtra("message",registerRemitterResponse.getMessage());
                            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                            intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                            context.startActivity(intent);
                            //query_remitter(remitter_mobile,context);

                        }
                        else{
                            DisplayMessageUtil.error(context,registerRemitterResponse.getMessage());
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

    public void addMyBeneficiary(String bank_id, String selectedMobile, BeneficiaryBank selectedBankModel, Context context, String holder_name, String ifsc, String account_number, String dmt_mobile){
        MyAlertUtils.showProgressAlertDialog(context);
        apiServices.registerBeneficiary(holder_name,account_number,ifsc,bank_id,dmt_mobile)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<AddBeneficiaryResponse>() {
                    @Override
                    public void onSubscribe(@NonNull Disposable d) {

                    }

                    @Override
                    public void onNext(@NonNull AddBeneficiaryResponse response) {

                        if(response.isStatus() && response.response_code.equals(1)){
                            Intent intent = new Intent(context, ToAccount.class);
                            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                            intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                            intent.putExtra("number", selectedMobile);
                            intent.putExtra("verificationProcess", true);
                            intent.putExtra("wholeInfoBeneficiary", response);
                            intent.putExtra("selectedBankModel", selectedBankModel);
                            context.startActivity(intent);
                        }
                        else{
                            DisplayMessageUtil.error(context, response.getMessage());
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
    public void getAllMyDMTReports(DMTHomeListeners listeners){
        User user = appDatabase.getUserDao().getRegularUser();
        
        apiServices.getAllDMTUsers(user.getId(), user.getUserstatus())
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
                        listeners.setErrorInFetch("Failed due to\n"+e.getMessage());
                    }

                    @Override
                    public void onComplete() {

                    }
                });

    }

    @SuppressLint("SetTextI18n")
    private void remitterOTPScreen(Context context){
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
            if(Objects.requireNonNull(binding.enterOtpDmt.getText()).toString().isEmpty()){
                DisplayMessageUtil.error(context,"Provide OTP");
            }else {
                Intent intent = new Intent(context, RegisterRemitter.class);
                intent.putExtra("enteredOTP", binding.enterOtpDmt.getText().toString());
                context.startActivity(intent);
            }
        });
    }


    public void getAllMyDMTAccounts(DMTHomeListeners listeners, String person){
        apiServices.getAllDMTUsers(person, "dmt_acc")
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
                        listeners.setErrorInFetch("Failed due to\n"+e.getMessage());
                    }

                    @Override
                    public void onComplete() {

                    }
                });
    }

}
