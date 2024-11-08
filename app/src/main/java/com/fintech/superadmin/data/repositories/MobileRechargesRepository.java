package com.fintech.superadmin.data.repositories;

import android.annotation.SuppressLint;
import android.content.Context;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.fintech.superadmin.Provider;
import com.fintech.superadmin.R;
import com.fintech.superadmin.data.db.AppDatabase;
import com.fintech.superadmin.data.db.entities.User;
import com.fintech.superadmin.data.deer_response.RechargeResponse;
import com.fintech.superadmin.data.model.OperatorModel;
import com.fintech.superadmin.data.network.APIServices;
import com.fintech.superadmin.data.network.responses.CustomerInfoResponse;
import com.fintech.superadmin.data.network.responses.MyOfferResponse;
import com.fintech.superadmin.data.network.responses.RegularHistoryResponse;
import com.fintech.superadmin.data.network.responses.RegularResponse;
import com.fintech.superadmin.databinding.OtpScreenLayoutBinding;
import com.fintech.superadmin.listeners.DynamicROfferListener;
import com.fintech.superadmin.listeners.ROfferListener;
import com.fintech.superadmin.listeners.RegularHistoryListener;
import com.fintech.superadmin.listeners.ResetListener;
import com.fintech.superadmin.master_util.MessageUtil;
import com.fintech.superadmin.util.DisplayMessageUtil;
import com.fintech.superadmin.util.MyAlertUtils;

import java.util.List;
import java.util.Objects;

import javax.inject.Inject;

import dagger.hilt.android.qualifiers.ApplicationContext;
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers;
import io.reactivex.rxjava3.annotations.NonNull;
import io.reactivex.rxjava3.core.Observer;
import io.reactivex.rxjava3.disposables.Disposable;
import io.reactivex.rxjava3.schedulers.Schedulers;

public class MobileRechargesRepository {

    public APIServices  apiServices;
    AppDatabase appDatabase;
    public RegularHistoryListener regularHistoryListener;
    public MessageUtil messageUtil;

    @Inject
    public MobileRechargesRepository(@ApplicationContext Context context, APIServices  apiServices){
        appDatabase = AppDatabase.getAppDatabase(context);
        this.apiServices = apiServices;
        this.messageUtil = new MessageUtil(context);
    }




    public void accessThePayment(String service_type, Context context, String recharge_mobile, String recharge_amount, String recharge_operator, String tpin, ResetListener listener){
        apiServices.makeThePayment(service_type, recharge_mobile, recharge_amount, recharge_operator, tpin)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<RechargeResponse>() {
                    @Override
                    public void onSubscribe(@NonNull Disposable d) {
                        DisplayMessageUtil.loading(context);
                    }

                    @Override
                    public void onNext(@NonNull RechargeResponse rechargeResponse) {
                        if(rechargeResponse.response_code.equals(1)){
                            String status = rechargeResponse.message.toLowerCase();
                            if(status.equals("pending")){
                                DisplayMessageUtil.error(context, rechargeResponse.message);
                            }else{
                                DisplayMessageUtil.success(context, rechargeResponse.message);
                                listener.resetRequiredData(true);
                            }
                        }
                        else{
                            DisplayMessageUtil.error(context, rechargeResponse.message);
                        }
                    }
                    @Override
                    public void onError(@NonNull Throwable e) {
                        DisplayMessageUtil.error(context, e.getLocalizedMessage());
                    }

                    @Override
                    public void onComplete() {

                    }
                });
    }



    public LiveData<List<OperatorModel>> getOperatorsList(String operator, Context context){

        MyAlertUtils.showProgressAlertDialog(context);
        MutableLiveData<List<OperatorModel>> list = new MutableLiveData<>();
        apiServices.getOperatorsList(operator)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<List<OperatorModel>>() {
                    @Override
                    public void onSubscribe(@NonNull Disposable d) {
                    }

                    @Override
                    public void onNext(@NonNull List<OperatorModel> operatorModelList) {
                        list.setValue(operatorModelList);
                        MyAlertUtils.dismissAlertDialog();
                    }

                    @Override
                    public void onError(@NonNull Throwable e) {
                        MyAlertUtils.showServerAlertDialog(context, e.getMessage());
                    }

                    @Override
                    public void onComplete() {

                    }
                });

            return list;
        }

    @SuppressLint("CheckResult")
    public void getMeMyROffer(String sertvice_type, Context context, String op, String num, DynamicROfferListener listener){
        listener.onStartLooking();
        apiServices.getMeROffers(sertvice_type ,op, num,"r_offer")
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(response->{
                    if (Objects.equals(response.getResponse_code(), 1)){
                        listener.getMeROffer(response.getReceivableData());
                    }else{
                        DisplayMessageUtil.error(context, ""+response.getMessage());
                    }
                }, Throwable::printStackTrace);
    }

    public void getMeMyDthROffer(Context context, String op, String num, ROfferListener listener, String type){
        User user = appDatabase.getUserDao().getRegularUser();
        
        apiServices.getMeDthROffers(op, num,user.getId(), user.getPassword(),type)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<MyOfferResponse>() {
                    @Override
                    public void onSubscribe(@NonNull Disposable d) {
                        listener.onStartLooking();
                    }

                    @Override
                    public void onNext(@NonNull MyOfferResponse response) {
                        listener.getMeROffer(response);
                    }

                    @Override
                    public void onError(@NonNull Throwable e) {
                        MyAlertUtils.showServerAlertDialog(context,e.getMessage());
                    }

                    @Override
                    public void onComplete() {

                    }
                });
    }

    public void getMeDthCustomer(Context context, String op, String num, String type, ROfferListener listener){
        User user = appDatabase.getUserDao().getRegularUser();
        apiServices.getDthCustomerInfo(op, num,user.getId(), user.getPassword(),type)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<CustomerInfoResponse>() {
                    @Override
                    public void onSubscribe(@NonNull Disposable d) {
                        listener.onStartLooking();
                    }

                    @Override
                    public void onNext(@NonNull CustomerInfoResponse response) {
                        listener.onCustomerInfo(response);
                    }

                    @Override
                    public void onError(@NonNull Throwable e) {
                        MyAlertUtils.showServerAlertDialog(context,e.getMessage());
                    }

                    @Override
                    public void onComplete() {

                    }
                });
    }

    public void bringUsualHistory(RegularHistoryListener listener, Context context, String type){
        User user = appDatabase.getUserDao().getRegularUser();
        MyAlertUtils.showProgressAlertDialog(context);
        apiServices.getRegularHistoryResp(user.getId(), user.getToken(), type)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<RegularHistoryResponse>() {
                    @Override
                    public void onSubscribe(@NonNull Disposable d) {

                    }

                    @Override
                    public void onNext(@NonNull RegularHistoryResponse regularHistoryResponse) {
                        MyAlertUtils.dismissAlertDialog();
                        if(regularHistoryResponse.isStatus() && regularHistoryResponse.getResponse_code()==1){
                            //gotcha..
                            listener.bringTheHistory(regularHistoryResponse.getData());
                        }
                        else if(regularHistoryResponse.isStatus() && regularHistoryResponse.getResponse_code()==2){
                            //no history lis
                            listener.thereWasNoData();
                        }
                        else{
                            MyAlertUtils.showServerAlertDialog(context, regularHistoryResponse.getMessage());
                        }
                    }

                    @Override
                    public void onError(@NonNull Throwable e) {
                        MyAlertUtils.showServerAlertDialog(context, "Failed due to\n"+e.getMessage());
                    }

                    @Override
                    public void onComplete() {

                    }
                });


    }

    public void checkHistoryStatusRegular(Context context, String historystatus, String reference){
        User user = appDatabase.getUserDao().getRegularUser();
        
        MyAlertUtils.showProgressAlertDialog(context);
        apiServices.getRegularHistoryStatus(user.getId(), user.getToken(),historystatus,reference,Provider.getDeviceModel(), Provider.getLocalIpAddress())
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<RegularResponse>() {
                    @Override
                    public void onSubscribe(@NonNull Disposable d) {

                    }

                    @Override
                    public void onNext(@NonNull RegularResponse response) {
                        MyAlertUtils.dismissAlertDialog();
                        if(response.isStatus() && response.getResponse_code().equals(1)){
                            //gotcha..
                            MyAlertUtils.showAnotherDialog(context, "Result", response.getMessage(), R.drawable.success);
                            regularHistoryListener.bringTheHistoryAgain();
                        }
                        else{
                            MyAlertUtils.showServerAlertDialog(context, response.getMessage());
                        }
                    }

                    @Override
                    public void onError(@NonNull Throwable e) {
                        MyAlertUtils.showServerAlertDialog(context, "Failed due to\n"+e.getMessage());
                    }

                    @Override
                    public void onComplete() {

                    }
                });
    }


    public void cfPayoutCheckStatus(Context context, String reference){
        apiServices.cfPayoutStatus(reference, "check_status")
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(resp->{
                    if(resp.getStatus() && resp.getResponse_code().equals(1)){
                        DisplayMessageUtil.anotherDialogSuccess(context, resp.getMessage());
                        regularHistoryListener.bringTheHistoryAgain();
                    }
                    else{
                        DisplayMessageUtil.anotherDialogFailure(context, resp.getMessage());
                        regularHistoryListener.bringTheHistoryAgain();
                    }
                }, error-> DisplayMessageUtil.error(context,error.getLocalizedMessage()));
    }



}
