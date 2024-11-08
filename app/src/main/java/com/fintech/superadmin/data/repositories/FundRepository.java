package com.fintech.superadmin.data.repositories;

import android.content.Context;
import android.os.AsyncTask;

import com.fintech.superadmin.Provider;
import com.fintech.superadmin.R;
import com.fintech.superadmin.data.db.AppDatabase;
import com.fintech.superadmin.data.db.dao.UserDao;
import com.fintech.superadmin.data.db.entities.RazorpayData;
import com.fintech.superadmin.data.db.entities.User;
import com.fintech.superadmin.data.network.APIServices;
import com.fintech.superadmin.data.network.responses.GatewayResponse;
import com.fintech.superadmin.data.network.responses.OnlineFundResponse;
import com.fintech.superadmin.data.network.responses.PaytmTokenInformation;
import com.fintech.superadmin.deer_listener.Receiver;
import com.fintech.superadmin.listeners.ResetListener;
import com.fintech.superadmin.listeners.ResponseTypeListener;
import com.fintech.superadmin.util.DisplayMessageUtil;
import com.fintech.superadmin.util.MyAlertUtils;

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
    public UserRepository userRepository;

    @Inject
    public FundRepository(@ApplicationContext Context context, APIServices apiServices, UserRepository userRepository) {
        appDatabase = AppDatabase.getAppDatabase(context);
        this.apiServices = apiServices;
        this.userRepository = userRepository;
    }

    public void addFundServices(Context context, String fund_type, String order_id, String amount, String status) {
        User user = appDatabase.getUserDao().getRegularUser();
        if (user != null) {
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
                            if (onlineFundResponse.isStatus()) {
                                MyAlertUtils.showAlertDialog(context, "Success", onlineFundResponse.getMessage(), R.drawable.success);
                            } else {
                                MyAlertUtils.showAlertDialog(context, "Failed", onlineFundResponse.getMessage(), R.drawable.failed);
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
    }

    public void bringTheRazorPayData(Context context, Receiver<RazorpayData> callBackListener, String amount) {
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
                        if (gatewayResponse.isStatus() && gatewayResponse.getRazor_pay() != null) {
                            MyAlertUtils.dismissAlertDialog();
                            callBackListener.getData(gatewayResponse.getRazor_pay());
                        } else {
                            MyAlertUtils.showServerAlertDialog(context, "Inform Admin to Provide Razorpay credentials to server");
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

    public void bringPaytmToken(ResponseTypeListener listener, String code, String oid, String amount) {

        apiServices.generateTokenCall(code, oid, amount)
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
                        listener.onError("Failed due to\n" + e.getMessage());
                    }

                    @Override
                    public void onComplete() {

                    }
                });
    }


    public void exchangeWallet(Context context, String amount, ResetListener resetListener) {
        MyAlertUtils.showProgressAlertDialog(context);
        apiServices.exchangeWallet("walletExchange", amount)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(result -> {
                    if (result.status) {
                        DisplayMessageUtil.anotherDialogSuccess(context, "" + result.message);
                        resetListener.resetRequiredData(true);
                        userRepository.refreshUserData(context);
                    }else{
                        DisplayMessageUtil.error(context, "" + result.message);
                    }
                }, error -> DisplayMessageUtil.error(context, error.getMessage()));
    }


    public void saveUser(User user) {
        UserDao userDao = appDatabase.getUserDao();
        new FundRepository.InsertUserAsyncTask(userDao).execute(user);
    }

    public static class InsertUserAsyncTask extends AsyncTask<User, Void, Void> {
        private UserDao userDao;

        private InsertUserAsyncTask(UserDao userDao) {
            this.userDao = userDao;
        }

        @Override
        protected Void doInBackground(User... users) {
            userDao.insert(users[0]);
            return null;
        }
    }

}
