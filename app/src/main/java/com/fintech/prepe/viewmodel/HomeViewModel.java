package com.fintech.prepe.viewmodel;


import android.content.Context;
import android.content.Intent;
import android.view.View;
import android.widget.TextView;

import androidx.cardview.widget.CardView;
import androidx.lifecycle.ViewModel;

import com.fintech.prepe.activities.addfunds.AddFundList;
import com.fintech.prepe.activities.addfunds.FundExchange;
import com.fintech.prepe.data.dto.MahagramResponse;
import com.fintech.prepe.data.dto.PaysprintResponse;
import com.fintech.prepe.data.network.APIServices;
import com.fintech.prepe.data.network.responses.AuthResponse;
import com.fintech.prepe.data.repositories.HomeRepository;
import com.fintech.prepe.deer_listener.Receiver;
import com.fintech.prepe.listeners.BringHistoryListener;
import com.fintech.prepe.listeners.NumberPayListener;
import com.fintech.prepe.util.Accessable;
import com.fintech.prepe.util.DisplayMessageUtil;
import com.fintech.prepe.util.DisplayQrUtilKt;
import com.fintech.prepe.util.NetworkUtil;
import com.fintech.prepe.util.ViewUtils;

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

    @Inject
    public HomeViewModel(HomeRepository homeRepository, APIServices apiServices) {
        this.homeRepository = homeRepository;
        this.apiServices = apiServices;
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


    public void checkIfAccountExists(String mobile, NumberPayListener listener) {
        apiServices.numberAuthenticate(mobile)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<AuthResponse>() {
                    @Override
                    public void onSubscribe(@NonNull Disposable d) {
                        listener.showProgress("Please Wait, Loading..");
                    }

                    @Override
                    public void onNext(@NonNull AuthResponse authResponse) {
                        listener.isNumberValid(authResponse);
                    }

                    @Override
                    public void onError(@NonNull Throwable e) {
                        listener.showMessage("Failed due to\n" + e.getMessage());
                    }

                    @Override
                    public void onComplete() {

                    }
                });
    }

    public void bringTheNews(TextView textView, CardView cardView) {
        apiServices.getMeLatestNews("")
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<String>() {
                    @Override
                    public void onSubscribe(@NonNull Disposable d) {

                    }

                    @Override
                    public void onNext(@NonNull String s) {
                        news = s;
                        textView.setText(news);
                        textView.setSelected(true);
                        if (!s.isEmpty()) {
                            cardView.setVisibility(View.VISIBLE);
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

    public void openAnAccount(Context context, String type, Receiver<String> stringReceiver) {
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

    public void clickedOnQR(View view) {
//        String[] permission = {Manifest.permission.CAMERA};
        if (Accessable.isAccessable()) {
            ViewUtils.showToast(view.getContext(), "This service is unavailable, contact admin");
            return;
//            PermissionUtil.givePermissions(view.getContext(), permission, result -> {
//                if (result == 1) {
//                    view.getContext().startActivity(new Intent(view.getContext(), QrActivity.class));
//                }
//            });
        }
    }
}
