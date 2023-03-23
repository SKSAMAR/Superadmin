package com.fintech.payware.data.repositories;

import android.content.Context;
import android.os.AsyncTask;

import com.fintech.payware.Provider;
import com.fintech.payware.R;
import com.fintech.payware.data.db.AppDatabase;
import com.fintech.payware.data.db.dao.UserDao;
import com.fintech.payware.data.db.entities.RazorpayData;
import com.fintech.payware.data.db.entities.User;
import com.fintech.payware.data.network.APIServices;
import com.fintech.payware.data.network.responses.AuthResponse;
import com.fintech.payware.data.network.responses.GatewayResponse;
import com.fintech.payware.data.network.responses.OnlineFundResponse;
import com.fintech.payware.data.network.responses.PaytmTokenInformation;
import com.fintech.payware.deer_listener.Receiver;
import com.fintech.payware.listeners.ResetListener;
import com.fintech.payware.listeners.ResponseTypeListener;
import com.fintech.payware.util.MyAlertUtils;

import javax.inject.Inject;

import dagger.hilt.android.qualifiers.ApplicationContext;
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers;
import io.reactivex.rxjava3.annotations.NonNull;
import io.reactivex.rxjava3.core.Observer;
import io.reactivex.rxjava3.disposables.Disposable;
import io.reactivex.rxjava3.schedulers.Schedulers;

public class FundRepository {
    public AppDatabase appDatabase;
    public APIServices apiServices;

    @Inject
    public FundRepository(@ApplicationContext Context context, APIServices  apiServices){
        appDatabase = AppDatabase.getAppDatabase(context);
        this.apiServices = apiServices;
    }

    public void addFundServices(Context context,String fund_type,String order_id,String amount,String status){
        User user = appDatabase.getUserDao().getRegularUser();
        if(user!=null) {
            String ip_address = Provider.getLocalIpAddress();
            String device = Provider.getDeviceModel();
            MyAlertUtils.showProgressAlertDialog(context);
            apiServices.onlineAddFundProcess(fund_type, order_id, user.getMobile(), user.getPassword(), amount, status, device, ip_address)
                    .subscribeOn(Schedulers.io())
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribe(new Observer<OnlineFundResponse>() {
                        @Override
                        public void onSubscribe(@NonNull Disposable d) {

                        }

                        @Override
                        public void onNext(@NonNull OnlineFundResponse onlineFundResponse) {
                            if(onlineFundResponse.isStatus()){
                                MyAlertUtils.showAlertDialog(context, "Success",onlineFundResponse.getMessage(),R.drawable.success);
                            }
                            else{
                                MyAlertUtils.showAlertDialog(context, "Failed",onlineFundResponse.getMessage(),R.drawable.failed);
                            }
                        }

                        @Override
                        public void onError(@NonNull Throwable e) {
                            MyAlertUtils.showServerAlertDialog(context, "Failed due to\n"+ e.getMessage());
                        }

                        @Override
                        public void onComplete() {

                        }
                    });
        }
    }

    public void bringTheRazorPayData(Context context, Receiver<RazorpayData> callBackListener, String amount){
        MyAlertUtils.showProgressAlertDialog(context);
        apiServices.getPaymentCredentials("razor_pay", amount)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<GatewayResponse>() {
                    @Override
                    public void onSubscribe(@NonNull Disposable d) {

                    }

                    @Override
                    public void onNext(@NonNull GatewayResponse gatewayResponse) {
                        if(gatewayResponse.isStatus() && gatewayResponse.getRazor_pay()!=null){
                            MyAlertUtils.dismissAlertDialog();
                            callBackListener.getData(gatewayResponse.getRazor_pay());
                        }
                        else{
                            MyAlertUtils.showServerAlertDialog(context,"Inform Admin to Provide Razorpay credentials to server");
                        }
                    }

                    @Override
                    public void onError(@NonNull Throwable e) {
                        MyAlertUtils.showServerAlertDialog(context,"Failed due to\n"+e.getMessage());
                    }

                    @Override
                    public void onComplete() {

                    }
                });
    }

    public void bringPaytmToken(ResponseTypeListener listener, String code, String  oid, String amount){

        apiServices.generateTokenCall(code,oid,amount)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<PaytmTokenInformation>() {
                    @Override
                    public void onSubscribe(@NonNull Disposable d) {
                        listener.onResponseStart();
                    }

                    @Override
                    public void onNext(@NonNull PaytmTokenInformation my_token_res) {
                        listener.onResponse(my_token_res);
                    }

                    @Override
                    public void onError(@NonNull Throwable e) {
                        listener.onError("Failed due to\n"+e.getMessage());
                    }

                    @Override
                    public void onComplete() {

                    }
                });
    }


    public void exchangeWallet(Context context, String amount, ResetListener resetListener){
        User user = appDatabase.getUserDao().getRegularUser();
        MyAlertUtils.showProgressAlertDialog(context);
        apiServices.exchangeWallet(user.getMobile(), user.getPassword(), user.getToken(), amount)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<AuthResponse>() {
                    @Override
                    public void onSubscribe(@NonNull Disposable d) {

                    }

                    @Override
                    public void onNext(@NonNull AuthResponse authResponse) {
                        if(!authResponse.isStatus()){
                            MyAlertUtils.showServerAlertDialog(context, authResponse.getMessage());
                        }
                        else{
                            resetListener.resetRequiredData(true);
                            MyAlertUtils.showAlertDialog(context,"Result", authResponse.getMessage(), R.drawable.success);
                            if(authResponse.user!=null){
                                saveUser(authResponse.user);
                            }
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



    public void saveUser(User user){
        UserDao userDao = appDatabase.getUserDao();
        new FundRepository.InsertUserAsyncTask(userDao).execute(user);
    }

    public static class InsertUserAsyncTask extends AsyncTask<User, Void, Void> {
        private UserDao userDao;
        private InsertUserAsyncTask(UserDao userDao){
            this.userDao = userDao;
        }
        @Override
        protected Void doInBackground(User... users) {
            userDao.insert(users[0]);
            return null;
        }
    }

}
