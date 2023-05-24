package com.fintech.superadmin.viewmodel;


import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.view.View;
import android.widget.TextView;

import androidx.cardview.widget.CardView;
import androidx.lifecycle.ViewModel;

import com.fintech.superadmin.R;
import com.fintech.superadmin.activities.addfunds.AddFundList;
import com.fintech.superadmin.activities.addfunds.FundExchange;
import com.fintech.superadmin.activities.fingBoard.FingBoardHome;
import com.fintech.superadmin.data.apiResponse.merchant.FingPayBoardCred;
import com.fintech.superadmin.data.db.entities.User;
import com.fintech.superadmin.data.dto.MahagramResponse;
import com.fintech.superadmin.data.dto.PaysprintResponse;
import com.fintech.superadmin.data.model.MenuModel;
import com.fintech.superadmin.data.network.APIServices;
import com.fintech.superadmin.data.network.responses.AuthResponse;
import com.fintech.superadmin.data.repositories.HomeRepository;
import com.fintech.superadmin.deer_listener.Receiver;
import com.fintech.superadmin.listeners.BringHistoryListener;
import com.fintech.superadmin.listeners.NumberPayListener;
import com.fintech.superadmin.util.Accessable;
import com.fintech.superadmin.util.DisplayMessageUtil;
import com.fintech.superadmin.util.DisplayQrUtilKt;
import com.fintech.superadmin.util.NetworkUtil;

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

    @Inject
    public HomeViewModel(HomeRepository homeRepository, APIServices apiServices) {
        this.homeRepository = homeRepository;
        this.apiServices = apiServices;
        initLessList();
        initMoreList();
        switchLess();
    }


    private void initLessList() {
        lessList.add(new MenuModel(R.drawable.c_mobile, "Mobile\nRecharge"));
        lessList.add(new MenuModel(R.drawable.c_fastag, "FASTag\nRecharge"));
        lessList.add(new MenuModel(R.drawable.c_dth, "DTH"));
        lessList.add(new MenuModel(R.drawable.c_cable, "Cable Tv", "Cable"));

        lessList.add(new MenuModel(R.drawable.c_bbps, "BBPS", "BBPS"));
        lessList.add(new MenuModel(R.drawable.c_lpg, "Book A\nCylinder", "Gas"));
        lessList.add(new MenuModel(R.drawable.ic_water, "Water"));
        lessList.add(new MenuModel(R.drawable.c_more, "See All"));

    }

    private void initMoreList() {

        moreList.add(new MenuModel(R.drawable.c_mobile, "Mobile\nRecharge"));
        moreList.add(new MenuModel(R.drawable.c_fastag, "FASTag\nRecharge"));
        moreList.add(new MenuModel(R.drawable.c_dth, "DTH"));
        moreList.add(new MenuModel(R.drawable.c_cable, "Cable Tv", "Cable"));
        moreList.add(new MenuModel(R.drawable.c_bbps, "BBPS", "BBPS"));
        moreList.add(new MenuModel(R.drawable.c_lpg, "Book A\nCylinder", "Gas"));
        moreList.add(new MenuModel(R.drawable.ic_water, "Water"));
        moreList.add(new MenuModel(R.drawable.c_electricity, "Electricity"));
        moreList.add(new MenuModel(R.drawable.c_mobile, "Postpaid"));
        moreList.add(new MenuModel(R.drawable.c_data_card, "Broadband"));
        moreList.add(new MenuModel(R.drawable.c_lpg, "LPG"));
        moreList.add(new MenuModel(R.drawable.c_datacard_postpaid, "Data Card\nPrepaid", "Datacard Prepaid"));
        moreList.add(new MenuModel(R.drawable.c_landline, "Landline"));
        moreList.add(new MenuModel(R.drawable.c_datacard_postpaid, "Data Card\nPostpaid", "BBPS"));
        moreList.add(new MenuModel(R.drawable.c_insurance, "Insurance"));
        moreList.add(new MenuModel(R.drawable.c_credit_card, "Credit Card"));
        moreList.add(new MenuModel(R.drawable.c_lic, "LIC"));

        moreList.add(new MenuModel(R.drawable.c_home, "abc"));
        moreList.add(new MenuModel(R.drawable.c_more, "See Less"));

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
