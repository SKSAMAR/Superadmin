package com.fintech.superadmin.viewmodel;


import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.WindowManager;
import android.widget.TextView;

import androidx.cardview.widget.CardView;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.fintech.superadmin.R;
import com.fintech.superadmin.activities.addfunds.AddFundList;
import com.fintech.superadmin.activities.addfunds.FundExchange;
import com.fintech.superadmin.activities.fingBoard.FingBoardHome;
import com.fintech.superadmin.activities.mobilenumber.SendMoney;
import com.fintech.superadmin.clean.presentation.magic.MagicWalletActivity;
import com.fintech.superadmin.clean.presentation.rakeshpan.RakeshPanUTIApply;
import com.fintech.superadmin.clean.presentation.rakeshpan.coupon.UTIPanCouponActivity;
import com.fintech.superadmin.clean.util.ViewUtils;
import com.fintech.superadmin.data.apiResponse.merchant.FingPayBoardCred;
import com.fintech.superadmin.data.db.entities.User;
import com.fintech.superadmin.data.dto.MahagramResponse;
import com.fintech.superadmin.data.dto.PaysprintResponse;
import com.fintech.superadmin.data.model.MenuModel;
import com.fintech.superadmin.data.network.APIServices;
import com.fintech.superadmin.data.network.responses.RegularResponse;
import com.fintech.superadmin.data.repositories.HomeRepository;
import com.fintech.superadmin.databinding.NotificationsDesignBinding;
import com.fintech.superadmin.deer_listener.Receiver;
import com.fintech.superadmin.listeners.BringHistoryListener;
import com.fintech.superadmin.util.Accessable;
import com.fintech.superadmin.util.DisplayMessageUtil;
import com.fintech.superadmin.util.DisplayQrUtilKt;
import com.fintech.superadmin.util.NetworkUtil;
import com.fintech.superadmin.util.UtilHolder;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import dagger.hilt.android.lifecycle.HiltViewModel;
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers;
import io.reactivex.rxjava3.annotations.NonNull;
import io.reactivex.rxjava3.core.Observer;
import io.reactivex.rxjava3.disposables.Disposable;
import io.reactivex.rxjava3.schedulers.Schedulers;

@HiltViewModel
public class HomeViewModel extends ViewModel {

    public BringHistoryListener historyListener;
    public static String aepsBalance = "";
    public static String mainBalance = "";
    public HomeRepository homeRepository;
    public APIServices apiServices;
    public String news = null;

    public List<MenuModel> currentList = new ArrayList<>();
    private final List<MenuModel> moreList = new ArrayList<>();
    private final List<MenuModel> lessList = new ArrayList<>();
    public boolean isAlreadySet = false;

    public MutableLiveData<String> _360_Wallet = new MutableLiveData<>();
    public MutableLiveData<String> _360_error = new MutableLiveData<>("");

    @Inject
    public HomeViewModel(HomeRepository homeRepository, APIServices apiServices) {
        this.homeRepository = homeRepository;
        this.apiServices = apiServices;
        switchLess();
    }


    public void switchMore() {
        currentList = moreList;
    }

    public void switchLess() {
        currentList = lessList;
    }


    public void onAddFundClick(View view) {
        view.getContext().startActivity(new Intent(view.getContext(), AddFundList.class));
    }

    public void onWalletExchange(View view) {
        view.getContext().startActivity(new Intent(view.getContext(), FundExchange.class));
    }

    public void getQrCode(Context context) {
        if (Accessable.isAccessable()) {
            NetworkUtil.getNetworkResult(apiServices.getQrCode("displayQR"),
                    context, data -> {
                        if (data.getStatus()) {
                            DisplayMessageUtil.dismissDialog();
                            DisplayQrUtilKt.displayQr(data.getReceivableData(), context);
                        } else {
                            DisplayMessageUtil.error(context, data.getMessage());
                        }
                    });
        }
    }


    public void displayMobilePay(Activity activity) {
        if (Accessable.isAccessable()) {
            User user = homeRepository.appDatabase.getUserDao().getRegularUser();
            DisplayQrUtilKt.displayMobilePayQr(user, activity);
        }
    }

    @SuppressLint("SetTextI18n")
    public void _360Operate(Context context) {
        if (Accessable.isAccessable()) {
            NetworkUtil.getNetworkResult(apiServices.magicWalletStatus("magicWalletStatus"), context, result -> {
                _360_Wallet.setValue(result.getReceivableData());
                if (result.getStatus()) {
                    context.startActivity(new Intent(context, MagicWalletActivity.class));
                    _360_error.setValue("");
                } else {

                    User user = homeRepository.appDatabase.getUserDao().getRegularUser();
                    _360_error.setValue(result.getMessage());
                    NotificationsDesignBinding binding = NotificationsDesignBinding.inflate(LayoutInflater.from(context));
                    Dialog dialog = new Dialog(context, R.style.MyTransparentBottomSheetDialogTheme);
                    dialog.setContentView(binding.getRoot());
                    dialog.setCanceledOnTouchOutside(true);
                    WindowManager.LayoutParams params = dialog.getWindow().getAttributes();
                    dialog.getWindow().setAttributes(params);
                    dialog.show();
                    binding.cancel.setOnClickListener(view -> dialog.dismiss());
                    binding.errorMessage.setText(result.getMessage());
                    binding.errorMessage2.setText("Main Wallet Balance: " + user.getMainbalance());
                    binding.activeNow.setOnClickListener(v -> {
                        dialog.dismiss();
                        NetworkUtil.getNetworkResult(apiServices.magicWalletActivate("activateWallet"), context, aResult -> {
                            if (aResult.getStatus()) {
                                DisplayMessageUtil.success(context, aResult.getMessage());
                                NetworkUtil.getNetworkResult(apiServices.magicWalletStatus("magicWalletStatus"), null, info -> _360_Wallet.setValue(info.getReceivableData()));
                            } else {
                                DisplayMessageUtil.error(context, aResult.getMessage());
                            }
                        });
                    });

                }
            });
        }
    }


    public void checkPaysprintServiceExistence(
            Context context,
            Receiver<PaysprintResponse> receiver,
            Receiver<PaysprintResponse> start
    ) {
        NetworkUtil.getNetworkResult(apiServices.paysprintTypeExistence("payprint"), context, response -> {
            DisplayMessageUtil.dismissDialog();
            if (response.getToBoard()) {
                receiver.getData(response);
            } else {
                start.getData(response);
            }
        });
    }

    public void checkFindPayServiceExistence(
            Context context,
            Receiver<FingPayBoardCred> start
    ) {
        NetworkUtil.getNetworkResult(apiServices.fingpayExistence("fingpay"), context, response -> {
            DisplayMessageUtil.dismissDialog();
            if (response.getToBoard()) {
                DisplayMessageUtil.dismissDialog();
                context.startActivity(new Intent(context, FingBoardHome.class));
            } else {
                start.getData(response);
            }
        });
    }

    public void checkMahagramServiceExistence(
            Context context,
            Receiver<MahagramResponse> receiver,
            Receiver<MahagramResponse> start
    ) {
        NetworkUtil.getNetworkResult(apiServices.mahagramTypeExistence("mahgram"), context, response -> {
            DisplayMessageUtil.dismissDialog();
            if (response.getToBoard()) {
                receiver.getData(response);
            } else {
                start.getData(response);
            }
        });
    }

    @SuppressLint("CheckResult")
    public void startCMS(Context context, String type, Receiver<String> stringReceiver) {
        if (Accessable.isAccessable()) {
            DisplayMessageUtil.loading(context);
            apiServices.startCMS(type, UtilHolder.getLatitude(), UtilHolder.getLongitude())
                    .subscribeOn(Schedulers.io())
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribe(res -> {
                        DisplayMessageUtil.dismissDialog();
                        if (res.getStatus()) {
                            stringReceiver.getData(res.getRedirectionUrl());
                        } else {
                            DisplayMessageUtil.error(context, res.getMessage());
                        }
                    }, err -> DisplayMessageUtil.error(context, err.getMessage()));
        }
    }

    @SuppressLint("CheckResult")
    public void startRailway(Context context, Receiver<String> stringReceiver) {
        if (Accessable.isAccessable()) {
            DisplayMessageUtil.loading(context);
            apiServices.startRailways("railway")
                    .subscribeOn(Schedulers.io())
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribe(res -> {
                        DisplayMessageUtil.dismissDialog();
                        if (res.status || res.response_code == 1) {
                            stringReceiver.getData(res.message);
                        } else {
                            DisplayMessageUtil.error(context, res.getMessage());
                        }
                    }, err -> DisplayMessageUtil.error(context, err.getMessage()));
        }
    }


    @SuppressLint("CheckResult")
    public void busRedirect(Context context) {
        if (Accessable.isAccessable()) {
            DisplayMessageUtil.loading(context);
            apiServices.busRedirect("busRedirect")
                    .subscribeOn(Schedulers.io())
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribe(res -> {
                        DisplayMessageUtil.dismissDialog();
                        if (res.getResponseCode() != null && res.getResponseCode().equals(1)) {
                            ViewUtils.INSTANCE.openInCustomBrowser(context, res.getData().getUrl(), res.getData().getEncdata());
                        } else {
                            DisplayMessageUtil.error(context, res.getMessage());
                        }
                    }, err -> DisplayMessageUtil.error(context, err.getMessage()));
        }
    }

    @SuppressLint("CheckResult")
    public void startUTIPan(Context context) {
        if (Accessable.isAccessable()) {
            DisplayMessageUtil.loading(context);
            apiServices.utiPanCardRedirect("utiPanCard")
                    .subscribeOn(Schedulers.io())
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribe(res -> {
                        DisplayMessageUtil.dismissDialog();
                        if (res.getStatus() != null && res.getStatus()) {
                            ViewUtils.INSTANCE.openInCustomBrowser(context, res.getData().getUrl(), res.getData().getEncdata());
                        } else {
                            DisplayMessageUtil.error(context, res.getMessage());
                        }
                    }, err -> DisplayMessageUtil.error(context, err.getMessage()));
        }
    }

    @SuppressLint("CheckResult")
    public void startRakeshUTIPan(Context context) {
        if (Accessable.isAccessable()) {
            DisplayMessageUtil.loading(context);
            apiServices.rakeshUTI("rakeshUTI")
                    .subscribeOn(Schedulers.io())
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribe(res -> {
                        DisplayMessageUtil.dismissDialog();
                        if (res.status && res.response_code == 1) {
                            context.startActivity(new Intent(context, UTIPanCouponActivity.class));
                        } else if (res.response_code == 2) {
                            DisplayMessageUtil.error(context, res.getMessage());
                        } else {
                            context.startActivity(new Intent(context, RakeshPanUTIApply.class));
                        }
                    }, err -> DisplayMessageUtil.error(context, err.getMessage()));
        }
    }


    public void checkIfAccountExists(Context context, String mobile) {
        apiServices.numberAuthenticate(mobile, "sendPayAvailable")
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<RegularResponse>() {
                    @Override
                    public void onSubscribe(@NonNull Disposable d) {
                        DisplayMessageUtil.loading(context);
                    }

                    @Override
                    public void onNext(@NonNull RegularResponse authResponse) {
                        DisplayMessageUtil.dismissDialog();
                        if (authResponse.isStatus()) {
                            Intent intent = new Intent(context, SendMoney.class);
                            intent.putExtra("receiver_id", "");
                            intent.putExtra("receiver_name", "");
                            intent.putExtra("receiver_mobile", mobile);
                            context.startActivity(intent);
                        } else {
                            DisplayMessageUtil.anotherDialogFailure(context, authResponse.getMessage());
                        }
                    }

                    @Override
                    public void onError(@NonNull Throwable e) {
                        DisplayMessageUtil.anotherDialogFailure(context, e.getLocalizedMessage());
                    }

                    @Override
                    public void onComplete() {

                    }
                });
    }

    public void bringTheNews(TextView textView, CardView cardView) {
        apiServices.getMeLatestNews("newsAlert")
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<RegularResponse>() {
                    @Override
                    public void onSubscribe(@NonNull Disposable d) {

                    }

                    @Override
                    public void onNext(@NonNull RegularResponse response) {
                        if (response.isStatus()) {
                            news = response.getMessage();
                            textView.setText(news);
                            textView.setSelected(true);
                            if (!response.getMessage().isEmpty()) {
                                cardView.setVisibility(View.VISIBLE);
                            }
                        }
                    }

                    @Override
                    public void onError(@NonNull Throwable e) {
                        news = e.getLocalizedMessage();
                        textView.setText(news);
                    }

                    @Override
                    public void onComplete() {

                    }
                });
    }

    public void openAnAccount(Context context, int type, Receiver<String> stringReceiver) {
        if (Accessable.isAccessable()) {
            DisplayMessageUtil.loading(context);
            apiServices.openAccount(type)
                    .subscribeOn(Schedulers.io())
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribe(res -> {
                        DisplayMessageUtil.dismissDialog();
                        if (res.getStatus()) {
                            stringReceiver.getData(res.getData());
                        } else {
                            DisplayMessageUtil.error(context, res.getMessage());
                        }
                    }, err -> DisplayMessageUtil.error(context, err.getMessage()));
        }
    }

}
