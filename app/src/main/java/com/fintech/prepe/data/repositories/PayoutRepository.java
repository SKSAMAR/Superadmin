package com.fintech.prepe.data.repositories;

import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;

import com.fintech.prepe.Provider;
import com.fintech.prepe.R;
import com.fintech.prepe.activities.payoutpaysprint.PaysprintPayout;
import com.fintech.prepe.activities.payoutpaysprint.VerifyAccounts;
import com.fintech.prepe.data.db.AppDatabase;
import com.fintech.prepe.data.db.dao.PaySprintDao;
import com.fintech.prepe.data.db.dao.UserDao;
import com.fintech.prepe.data.db.entities.User;
import com.fintech.prepe.data.network.APIServices;
import com.fintech.prepe.data.network.responses.AuthResponse;
import com.fintech.prepe.data.network.responses.PayoutAddResponse;
import com.fintech.prepe.data.network.responses.PayoutListResponse;
import com.fintech.prepe.data.network.responses.PayoutResponse;
import com.fintech.prepe.data.network.responses.RegularResponse;
import com.fintech.prepe.deer_listener.Receiver;
import com.fintech.prepe.listeners.PayoutHomeListener;
import com.fintech.prepe.util.DisplayMessageUtil;
import com.fintech.prepe.util.MyAlertUtils;
import com.fintech.prepe.util.ViewUtils;

import javax.inject.Inject;

import dagger.hilt.android.qualifiers.ApplicationContext;
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers;
import io.reactivex.rxjava3.annotations.NonNull;
import io.reactivex.rxjava3.core.Observer;
import io.reactivex.rxjava3.disposables.Disposable;
import io.reactivex.rxjava3.schedulers.Schedulers;

public class PayoutRepository {

    Context context;
    AppDatabase appDatabase;
    public APIServices apiServices;

    @Inject
    public PayoutRepository(@ApplicationContext Context context, APIServices apiServices) {
        appDatabase = AppDatabase.getAppDatabase(context);
        this.apiServices = apiServices;
    }


    public void checkVerificationStatus(Context context, String bene_id, String acc, Receiver<Boolean> receiver) {
        apiServices.checkVerificationStatus(bene_id, "checkVerifyStatus")
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<RegularResponse>() {
                    @Override
                    public void onSubscribe(@NonNull Disposable d) {
                        DisplayMessageUtil.loading(context);
                    }

                    @Override
                    public void onNext(@NonNull RegularResponse result) {
                        DisplayMessageUtil.dismissDialog();
                        if (result.isStatus() && result.getResponse_code() == 1) {
                            DisplayMessageUtil.anotherDialogSuccess(context, result.getMessage());
                            receiver.getData(true);

                        } else if (result.isStatus() && result.getResponse_code() == 2) {
                            Intent intent = new Intent(context, VerifyAccounts.class);
                            intent.putExtra("bene_id", bene_id);
                            intent.putExtra("account_no", acc);
                            context.startActivity(intent);

                        } else {
                            DisplayMessageUtil.error(context, result.getMessage());
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


    public void reAuthenticateTheUser(Context context, String amount, String bank, String ifsc, String trans_type) {
        this.context = context;
        MyAlertUtils.showProgressAlertDialog(context);
        User user = appDatabase.getUserDao().getRegularUser();

        apiServices.reAuthenticateUser(user.getMobile(), user.getPassword(), user.getToken())
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<AuthResponse>() {
                    @Override
                    public void onSubscribe(@NonNull Disposable d) {

                    }

                    @Override
                    public void onNext(@NonNull AuthResponse authResponse) {
                        if (authResponse.isStatus()) {
                            saveUser(authResponse.getUser());
                            payThroughPayouts(amount, bank, ifsc, trans_type);
                        } else {
                            MyAlertUtils.showServerAlertDialog(context, "Session Expired");
                            deleteUser();
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


    public void saveUser(User user) {
        UserDao userDao = appDatabase.getUserDao();
        new PayoutRepository.InsertUserAsyncTask(userDao).execute(user);
    }

    public void deleteUser() {
        UserDao userDao = appDatabase.getUserDao();
        PaySprintDao paySprintDao = appDatabase.getPaySprint();
        new PayoutRepository.DeleteUserAsyncTask(userDao, paySprintDao).execute();
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

    public static class DeleteUserAsyncTask extends AsyncTask<Void, Void, Void> {
        private UserDao userDao;
        private PaySprintDao paySprintDao;

        private DeleteUserAsyncTask(UserDao userDao, PaySprintDao paySprintDao) {
            this.userDao = userDao;
            this.paySprintDao = paySprintDao;
        }

        @Override
        protected Void doInBackground(Void... voids) {
            userDao.loggedOutUser();
            paySprintDao.loggedOut();
            return null;
        }
    }


    public void payThroughPayouts(String amount, String bank, String ifsc, String trans_type) {
        String myDeviceModel = android.os.Build.MODEL;
        String ip = Provider.getLocalIpAddress();
        User user = appDatabase.getUserDao().getRegularUser();

        apiServices.payThroughPayouts(amount, bank, ifsc, trans_type, user.getId(), user.getUserstatus(), ip, myDeviceModel)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<PayoutResponse>() {
                    @Override
                    public void onSubscribe(@NonNull Disposable d) {

                    }

                    @Override
                    public void onNext(@NonNull PayoutResponse payoutResponse) {
                        if (payoutResponse.getStatusCode().equals("DE_002")) {
                            MyAlertUtils.showAlertDialog(context, payoutResponse.getStatus(), payoutResponse.getStatusMessage(), R.drawable.success);
                        } else {
                            MyAlertUtils.showAlertDialog(context, payoutResponse.getStatus(), payoutResponse.getStatusMessage(), R.drawable.failed);
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


    public void paySprintPayoutList(PayoutHomeListener listener) {
        apiServices.paySprintPayoutList("list")
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<PayoutListResponse>() {
                    @Override
                    public void onSubscribe(@NonNull Disposable d) {
                        listener.initiateStart();
                    }

                    @Override
                    public void onNext(@NonNull PayoutListResponse response) {
                        if (response.isStatus() && response.getResponse_code() == 1) {
                            listener.setWholeRecycler(response.getData());
                        } else {
                            listener.setErrorInFetch(response.getMessage());
                        }


                    }

                    @Override
                    public void onError(@NonNull Throwable e) {
                        listener.setErrorInFetch("Failed due to\n" + e.getLocalizedMessage());
                    }

                    @Override
                    public void onComplete() {

                    }
                });


    }


    public void addPayoutAccount(Context context, String bank_id, String acc, String ifsc, String name) {
        MyAlertUtils.showProgressAlertDialog(context);
        apiServices.paySprintPayoutAddAccount(bank_id, acc, ifsc, name)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<PayoutAddResponse>() {
                    @Override
                    public void onSubscribe(@NonNull Disposable d) {

                    }

                    @Override
                    public void onNext(@NonNull PayoutAddResponse response) {
                        MyAlertUtils.dismissAlertDialog();
                        if (response.isStatus()) {
                            ViewUtils.showToast(context, response.getMessage());
                            Intent intent = new Intent(context, PaysprintPayout.class);
                            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                            intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                            context.startActivity(intent);

                        } else {
                            MyAlertUtils.showWarningAlertDialog(context, response.getMessage());
                        }
                    }

                    @Override
                    public void onError(@NonNull Throwable e) {
                        MyAlertUtils.showServerAlertDialog(context, "failed due to\n" + e.getLocalizedMessage());
                    }

                    @Override
                    public void onComplete() {

                    }
                });
    }

    /**
     * public void verifyAccount(Context context, String pan, String faadhar, String baadhar, String passbook, String doc_type, String row_id, String bene_id){
     * MyAlertUtils.showProgressAlertDialog(context);
     * apiServices.verifyPayoutAccount(pan, faadhar, baadhar, passbook, doc_type,row_id,bene_id,"verification", "APP")
     * .subscribeOn(Schedulers.io())
     * .observeOn(AndroidSchedulers.mainThread())
     * .subscribe(new Observer<RegularResponse>() {
     *
     * @Override public void onSubscribe(@NonNull Disposable d) {
     * <p>
     * }
     * @Override public void onNext(@NonNull RegularResponse response) {
     * MyAlertUtils.dismissAlertDialog();
     * if(response.isStatus() && response.getResponse_code()==1){
     * ViewUtils.showToast(context, response.getMessage());
     * Intent intent = new Intent(context, PaysprintPayout.class);
     * intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
     * intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
     * context.startActivity(intent);
     * <p>
     * }else{
     * MyAlertUtils.showWarningAlertDialog(context, response.getMessage());
     * }
     * }
     * @Override public void onError(@NonNull Throwable e) {
     * MyAlertUtils.showServerAlertDialog(context, "failed due to\n"+ e.getLocalizedMessage());
     * }
     * @Override public void onComplete() {
     * <p>
     * }
     * });
     * }
     **/


    public void sendMoneyPayouts(Context context, Dialog dialog, String bene_id, String mode, String amount, String acc, String ifsc) {
        MyAlertUtils.showProgressAlertDialog(context);
        apiServices.sendMoneyOnPayAmount(bene_id, amount, mode, acc, ifsc)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<RegularResponse>() {
                    @Override
                    public void onSubscribe(@NonNull Disposable d) {

                    }

                    @Override
                    public void onNext(@NonNull RegularResponse response) {
                        MyAlertUtils.dismissAlertDialog();
                        if (response.isStatus() && response.getResponse_code() == 1) {
                            dialog.dismiss();
                            MyAlertUtils.showAlertDialog(context, "Success", response.getMessage(), R.drawable.success);
                        } else {
                            MyAlertUtils.showWarningAlertDialog(context, response.getMessage());
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

}
