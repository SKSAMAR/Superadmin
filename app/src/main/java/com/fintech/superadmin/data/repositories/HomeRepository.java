package com.fintech.superadmin.data.repositories;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Point;
import android.util.Log;
import android.view.Display;
import android.view.LayoutInflater;
import android.view.WindowManager;

import androidx.appcompat.app.AlertDialog;
import androidx.lifecycle.LiveData;

import com.fintech.superadmin.activities.aeps.AepsReceiptActivity;
import com.fintech.superadmin.activities.transactionDetails.TransactionDetailsReceiptActivity;
import com.fintech.superadmin.data.network.AePSDto;
import com.fintech.superadmin.util.NetworkUtil;
import com.google.gson.Gson;
import com.google.zxing.WriterException;
import com.fintech.superadmin.R;
import com.fintech.superadmin.activities.addfunds.FundDetailedAnalytic;
import com.fintech.superadmin.activities.aeps.AepsDetailedAnalytic;
import com.fintech.superadmin.activities.aeps.MiniStatementAnalytic;
import com.fintech.superadmin.activities.bbps.BBPSDetailedAnalytic;
import com.fintech.superadmin.activities.fastag.FASTagDetailedAnalytic;
import com.fintech.superadmin.activities.lic.LICDetailedAnalytic;
import com.fintech.superadmin.activities.microatm.MicroAtmDetailedAnalytic;
import com.fintech.superadmin.activities.payoutpaysprint.PayoutDetailedAnalytic;
import com.fintech.superadmin.activities.rechargeactivities.RechargeDetailedAnalytic;
import com.fintech.superadmin.activities.tobank.DMTDetailedAnalytic;
import com.fintech.superadmin.data.db.AppDatabase;
import com.fintech.superadmin.data.db.entities.User;
import com.fintech.superadmin.data.db.entities.UserProfile;
import com.fintech.superadmin.data.model.GraphModel;
import com.fintech.superadmin.data.model.HistoryModel;
import com.fintech.superadmin.data.network.APIServices;
import com.fintech.superadmin.data.network.responses.AnalCardReport;
import com.fintech.superadmin.data.network.responses.AnalyticsResponseModel;
import com.fintech.superadmin.data.network.responses.DetailedHistoryResponse;
import com.fintech.superadmin.data.network.responses.QRResponse;
import com.fintech.superadmin.data.network.responses.RegularResponse;
import com.fintech.superadmin.data.network.responses.SystemResponse;
import com.fintech.superadmin.databinding.QrScreenDesignBinding;
import com.fintech.superadmin.listeners.AnalyticOperationListener;
import com.fintech.superadmin.listeners.BaseAnalyticListener;
import com.fintech.superadmin.listeners.BringHistoryListener;
import com.fintech.superadmin.listeners.OnBoardingListeners;
import com.fintech.superadmin.listeners.ResetListener;
import com.fintech.superadmin.util.Accessable;
import com.fintech.superadmin.util.DisplayMessageUtil;
import com.fintech.superadmin.util.MyAlertUtils;
import com.fintech.superadmin.util.ViewUtils;

import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import androidmads.library.qrgenearator.QRGContents;
import androidmads.library.qrgenearator.QRGEncoder;
import dagger.hilt.android.qualifiers.ApplicationContext;
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers;
import io.reactivex.rxjava3.annotations.NonNull;
import io.reactivex.rxjava3.core.Observer;
import io.reactivex.rxjava3.disposables.Disposable;
import io.reactivex.rxjava3.schedulers.Schedulers;

public class HomeRepository {

    APIServices apiServices;
    public AppDatabase appDatabase;

    @Inject
    public HomeRepository(@ApplicationContext Context context, APIServices apiServices) {
        appDatabase = AppDatabase.getAppDatabase(context);
        this.apiServices = apiServices;
    }


    public void checkOnBoardStatus(Context context, String merchant_code) {
        DisplayMessageUtil.loading(context);
        apiServices.onBoardingStatus(merchant_code, "onboard_status")
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(res -> {
                    if (res.isStatus() && res.getResponse_code().equals(1)) {
                        DisplayMessageUtil.success(context, "OnBoarding Status\n" + res.getMessage());
                    } else {
                        DisplayMessageUtil.error(context, "OnBoarding Status\n" + res.getMessage());
                    }
                }, err -> DisplayMessageUtil.error(context, err.getLocalizedMessage()));
    }


    public void getMyHistories(BringHistoryListener listener, String indexing, String fromDate, String toDate, String transType, String result, String id) {
        User user = appDatabase.getUserDao().getRegularUser();
        apiServices.getHistory(user.getId(), user.getUserstatus(), indexing, fromDate, toDate, transType, result, id)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<List<HistoryModel>>() {
                    @Override
                    public void onSubscribe(@NonNull Disposable d) {
                        listener.onStart("Please wait, While Loading...");
                    }

                    @Override
                    public void onNext(@NonNull List<HistoryModel> historyModels) {
                        listener.onHistoryBrought(historyModels);
                    }

                    @Override
                    public void onError(@NonNull Throwable e) {
                        listener.onFailure(e.toString());
                    }

                    @Override
                    public void onComplete() {
                        listener.onComplete("Completed..");
                    }
                });
    }

    public void getOnBoardingStore(OnBoardingListeners listeners, String mobile, String owner, String owner_id, String partner_id, String merchant_code, String code) {

        apiServices.onSuccessOnBoarding(mobile, owner, owner_id, partner_id, merchant_code, code)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<String>() {
                    @Override
                    public void onSubscribe(@NonNull Disposable d) {
                        listeners.onBegin();
                    }

                    @Override
                    public void onNext(@NonNull String result) {
                        listeners.onBoardingResponse(result, "insertion");
                    }

                    @Override
                    public void onError(@NonNull Throwable e) {
                        listeners.onFailure(e.getMessage());
                    }

                    @Override
                    public void onComplete() {
                        listeners.onComplete();
                    }
                });
    }

    public void getAnalyticsData(BringHistoryListener listener, String indexing, String fromDate, String toDate, String transType, String result, String id) {
        User user = appDatabase.getUserDao().getRegularUser();
        if (user != null) {
            apiServices.getAllAnalytics(user.getId(), user.getUserstatus(), indexing, fromDate, toDate, transType, result, id)
                    .subscribeOn(Schedulers.io())
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribe(new Observer<List<AnalyticsResponseModel>>() {
                        @Override
                        public void onSubscribe(@NonNull Disposable d) {
                            listener.onStart("Please wait.. loading data");
                        }

                        @Override
                        public void onNext(@NonNull List<AnalyticsResponseModel> analyticsResponseModels) {
                            listener.onAnalyticsBrought(analyticsResponseModels);
                        }

                        @Override
                        public void onError(@NonNull Throwable e) {
                            listener.onFailure(e.getMessage());
                        }

                        @Override
                        public void onComplete() {
                            listener.onComplete("Finished..");
                        }
                    });
        }
    }


    public void fullDetailsOfAnalytics(Context context, String report_id, AnalyticsResponseModel model) {
        String user_id = appDatabase.getUserDao().getRegularUser().getId();

        NetworkUtil.getNetworkResult(apiServices.getDetailedReport(report_id, user_id), context, result -> {
            DisplayMessageUtil.dismissDialog();
            String type = result.getReceivableData().getReportTitle().trim().toLowerCase();
            if (type.equalsIgnoreCase("aeps") || type.equalsIgnoreCase("cw") || type.equalsIgnoreCase("be") || type.equalsIgnoreCase("ms") || type.equalsIgnoreCase("m")) {
                try {
                    Object res = result.getReceivableData().getApiResponse();
                    Intent intent = new Intent(context, AepsReceiptActivity.class);
                    intent.putExtra("transactionType", type.trim());
                    intent.putExtra("response", new Gson().fromJson(res.toString(), AePSDto.class));
                    context.startActivity(intent);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
            else{
                try {
                    Intent intent = new Intent(context, TransactionDetailsReceiptActivity.class);
                    intent.putExtra("transactionType", type.trim());
                    intent.putExtra("response", result.getReceivableData());
                    intent.putExtra("regular", model);
                    context.startActivity(intent);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }

        });

//        if (report_id != null && user_id != null) {
//
//            MyAlertUtils.showProgressAlertDialog(context);
//            apiServices.getMyAnalyticDetailed(report_id, user_id)
//                    .subscribeOn(Schedulers.io())
//                    .observeOn(AndroidSchedulers.mainThread())
//                    .subscribe(new Observer<DetailedHistoryResponse>() {
//                        @Override
//                        public void onSubscribe(@NonNull Disposable d) {
//
//                        }
//
//                        @Override
//                        public void onNext(@NonNull DetailedHistoryResponse detailedHistoryResponse) {
//                            MyAlertUtils.dismissAlertDialog();
//                            if (detailedHistoryResponse.isStatus() && !detailedHistoryResponse.getResponse_code().equals(999)) {
//                                if (detailedHistoryResponse.getTrans_type() != null) {
//                                    if (detailedHistoryResponse.getTrans_type().equals("recharge")) {
//                                        Intent intent = new Intent(context, RechargeDetailedAnalytic.class);
//                                        intent.putExtra("detailed", detailedHistoryResponse);
//                                        intent.putExtra("regular", model);
//                                        context.startActivity(intent);
//                                    } else if (detailedHistoryResponse.getTrans_type().equals("bbps")) {
//                                        Intent intent = new Intent(context, BBPSDetailedAnalytic.class);
//                                        intent.putExtra("detailed", detailedHistoryResponse);
//                                        intent.putExtra("regular", model);
//                                        context.startActivity(intent);
//                                    } else if (detailedHistoryResponse.getTrans_type().equals("atm")) {
//                                        Intent intent = new Intent(context, MicroAtmDetailedAnalytic.class);
//                                        intent.putExtra("detailed", detailedHistoryResponse);
//                                        intent.putExtra("regular", model);
//                                        context.startActivity(intent);
//                                    } else if (detailedHistoryResponse.getTrans_type().equals("dmt")) {
//                                        Intent intent = new Intent(context, DMTDetailedAnalytic.class);
//                                        intent.putExtra("detailed", detailedHistoryResponse);
//                                        intent.putExtra("regular", model);
//                                        context.startActivity(intent);
//                                    } else if (detailedHistoryResponse.getTrans_type().equals("payout")) {
//                                        Intent intent = new Intent(context, PayoutDetailedAnalytic.class);
//                                        intent.putExtra("detailed", detailedHistoryResponse);
//                                        intent.putExtra("regular", model);
//                                        context.startActivity(intent);
//                                    } else if (detailedHistoryResponse.getTrans_type().equals("fund")) {
//                                        Intent intent = new Intent(context, FundDetailedAnalytic.class);
//                                        intent.putExtra("detailed", detailedHistoryResponse);
//                                        intent.putExtra("regular", model);
//                                        context.startActivity(intent);
//                                    } else if (detailedHistoryResponse.getTrans_type().equals("lic")) {
//                                        Intent intent = new Intent(context, LICDetailedAnalytic.class);
//                                        intent.putExtra("detailed", detailedHistoryResponse);
//                                        intent.putExtra("regular", model);
//                                        context.startActivity(intent);
//                                    } else if (detailedHistoryResponse.getTrans_type().equals("2")) { //2 is for FASTag
//                                        Intent intent = new Intent(context, FASTagDetailedAnalytic.class);
//                                        intent.putExtra("detailed", detailedHistoryResponse);
//                                        intent.putExtra("regular", model);
//                                        context.startActivity(intent);
//                                    } else if (detailedHistoryResponse.getTrans_type().equals("aeps")) {
//                                        if (detailedHistoryResponse.getType_response() != null) {
//                                            String res = detailedHistoryResponse.getData_response();
//                                            try {
//
//                                                Intent intent = new Intent(context, AepsReceiptActivity.class);
//                                                intent.putExtra("transactionType", detailedHistoryResponse.getType_response().trim());
//                                                intent.putExtra("response", new Gson().fromJson(res, AePSDto.class));
//                                                context.startActivity(intent);
//                                            } catch (Exception e) {
//                                                e.printStackTrace();
//                                            }
//
//                                            /**
//                                             Intent intent;
//                                             if (detailedHistoryResponse.getType_response().equals("ms")) {
//                                             intent = new Intent(context, MiniStatementAnalytic.class);
//                                             } else {
//                                             intent = new Intent(context, AepsDetailedAnalytic.class);
//                                             }
//                                             intent.putExtra("detailed", detailedHistoryResponse);
//                                             intent.putExtra("regular", model);
//                                             context.startActivity(intent);
//                                             **/
//                                        }
//                                    }
//                                }
//
//                            } else {
//                                MyAlertUtils.showServerAlertDialog(context, detailedHistoryResponse.getMessage());
//                            }
//
//                        }
//
//                        @Override
//                        public void onError(@NonNull Throwable e) {
//                            MyAlertUtils.showServerAlertDialog(context, "Failed due to\n" + e.getMessage());
//                        }
//
//                        @Override
//                        public void onComplete() {
//
//                        }
//                    });
//
//        }
    }

    public void updatePayDeerStatus(AnalyticsResponseModel model, Context context, AnalyticOperationListener listener) {
        try {

            if (model.getOp_id().equals("Recharge")) {
                rechargePrepaidStatus(result -> listener.resetAllData(), context, model.getTxn_id());
            } else if (model.getOperator_name().equals("DMT")) {
                checkStatusCFPDHistory(result -> listener.resetAllData(), context, model.getTxn_id());
            } else if (model.getOperator_name().equals("Payout")) {
                cfPayoutCheckStatus(result -> listener.resetAllData(), context, model.getTxn_id());
            } else if (model.getOperator_name().equals("AEPS")) {
                aEPSCheckStatus(result -> listener.resetAllData(), context, model.getTxn_id());
            } else {
                updatePendingStatus(model.getTxn_id(), model.getPayment_type(), context, listener);
            }

        } catch (NullPointerException e) {
            e.printStackTrace();
        }

    }


    public void rechargePrepaidStatus(ResetListener listener, Context context, String reference) {
        DisplayMessageUtil.loading(context);
        apiServices.prepaidRecharge_Status(reference, "check_status")
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(resp -> {
                    DisplayMessageUtil.dismissDialog();
                    if (resp.getResponse_code().equals(1)) {
                        DisplayMessageUtil.anotherDialogSuccess(context, resp.getMessage());
                        listener.resetRequiredData(true);
                    } else {
                        DisplayMessageUtil.error(context, resp.getMessage());
                    }
                }, error -> DisplayMessageUtil.error(context, error.getLocalizedMessage()));
    }


    public void aEPSCheckStatus(ResetListener listener, Context context, String reference) {
        DisplayMessageUtil.loading(context);
        apiServices.aEPS_Status(reference, "check_aeps_status")
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(resp -> {
                    DisplayMessageUtil.dismissDialog();
                    if (resp.isStatus() && resp.getResponse_code().equals(1)) {
                        DisplayMessageUtil.anotherDialogSuccess(context, resp.getMessage());
                        listener.resetRequiredData(true);
                    } else {
                        DisplayMessageUtil.error(context, resp.getMessage());
                    }
                }, error -> DisplayMessageUtil.error(context, error.getLocalizedMessage()));
    }


    public void cfPayoutCheckStatus(ResetListener listener, Context context, String reference) {
        DisplayMessageUtil.loading(context);
        apiServices.cfPayoutStatus(reference, "check_status")
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(resp -> {
                    DisplayMessageUtil.dismissDialog();
                    if (resp.getStatus() && resp.getResponse_code().equals(1)) {
                        DisplayMessageUtil.anotherDialogSuccess(context, resp.getMessage());
                        listener.resetRequiredData(true);
                    } else {
                        DisplayMessageUtil.error(context, resp.getMessage());
                    }
                }, error -> DisplayMessageUtil.error(context, error.getLocalizedMessage()));
    }


    public void checkStatusCFPDHistory(ResetListener my_reset, Context context, String reference_id) {
        if (Accessable.isAccessable()) {
            DisplayMessageUtil.loading(context);
            apiServices.cfpfmtCheckStatus(reference_id, "check_dmt_status")
                    .subscribeOn(Schedulers.io())
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribe(resp -> {
                        DisplayMessageUtil.dismissDialog();
                        if (resp.getResponse_code().equals(1)) {
                            my_reset.resetRequiredData(true);
                            DisplayMessageUtil.anotherDialogSuccess(context, resp.getMessage());
                        } else {
                            DisplayMessageUtil.error(context, resp.getMessage());
                        }
                    }, error -> DisplayMessageUtil.error(context, error.getLocalizedMessage()));
        }
    }


    public void updatePendingStatus(String reference_id, String type, Context context, AnalyticOperationListener listener) {
        apiServices.getUpdatesOnTransaction(type, reference_id)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<RegularResponse>() {
                    @Override
                    public void onSubscribe(@NonNull Disposable d) {
                        MyAlertUtils.showProgressAlertDialog(context);
                    }

                    @Override
                    public void onNext(@NonNull RegularResponse response) {
                        DisplayMessageUtil.dismissDialog();
                        if (response.isStatus() && response.getResponse_code().equals(1)) {
                            DisplayMessageUtil.anotherDialogSuccess(context, response.getMessage());
                            listener.resetAllData();
                        } else {
                            DisplayMessageUtil.error(context, response.getMessage());
                        }
                    }

                    @Override
                    public void onError(@NonNull Throwable e) {
                        MyAlertUtils.showServerAlertDialog(context, "Failed due to " + e.getMessage());
                    }

                    @Override
                    public void onComplete() {

                    }
                });
    }

    public LiveData<UserProfile> getUserProfileLiveData() {
        return appDatabase.getUserProfileDao().getUserProfile();
    }


    public void bringCardInfo(Context context, String type, String onDay, String fromDate, String toDate, BaseAnalyticListener listener) {
        User user = appDatabase.getUserDao().getRegularUser();
        apiServices.getAnalyticCardData(user.getId(), user.getUserstatus(), user.getToken(), type, onDay)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<AnalCardReport>() {
                    @Override
                    public void onSubscribe(@NonNull Disposable d) {

                        MyAlertUtils.showProgressAlertDialog(context);
                    }

                    @Override
                    public void onNext(@NonNull AnalCardReport report) {
                        MyAlertUtils.dismissAlertDialog();
                        if (report.isStatus() && report.getResponse_code() == 1) {
                            listener.getCardReport(report);
                            bringGraphInfo(context, type, onDay, fromDate, toDate, listener);
                        } else {
                            MyAlertUtils.showServerAlertDialog(context, report.getMessage());
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

    public void bringGraphInfo(Context context, String type, String onDay, String fromDate, String toDate, BaseAnalyticListener listener) {
        User user = appDatabase.getUserDao().getRegularUser();
        apiServices.getAnalyticGraphData(user.getId(), user.getUserstatus(), user.getToken(), type, onDay, fromDate, toDate)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<ArrayList<GraphModel>>() {
                    @Override
                    public void onSubscribe(@NonNull Disposable d) {

                        MyAlertUtils.showProgressAlertDialog(context);
                    }

                    @Override
                    public void onNext(@NonNull ArrayList<GraphModel> list) {
                        MyAlertUtils.dismissAlertDialog();
                        listener.getGraphReport(list);
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

    public void getQRCodeResponse(Context context) {
        apiServices.getQrCode("showqr")
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<SystemResponse<QRResponse>>() {
                    @Override
                    public void onSubscribe(@NonNull Disposable d) {
                        MyAlertUtils.showProgressAlertDialog(context);
                    }

                    @Override
                    public void onNext(@NonNull SystemResponse<QRResponse> response) {
                        if (response.getResponse_code().equals(1)) {
                            MyAlertUtils.dismissAlertDialog();
                            QrScreenDesignBinding qrScreenDesignBinding = QrScreenDesignBinding.inflate(LayoutInflater.from(context));
                            AlertDialog alert = new AlertDialog.Builder(context).create();
                            alert.setView(qrScreenDesignBinding.getRoot());
                            qrScreenDesignBinding.closeImage.setOnClickListener(
                                    view -> alert.dismiss()
                            );
                            alert.setCanceledOnTouchOutside(false);
                            WindowManager manager = (WindowManager) alert.getWindow().getWindowManager();
                            Display display = manager.getDefaultDisplay();
                            Point point = new Point();
                            display.getSize(point);
                            int width = point.x;
                            int height = point.y;
                            int dimen = width < height ? width : height;
                            dimen = dimen * 3 / 4;
                            Bitmap bitmap;
                            QRGEncoder qrgEncoder = new QRGEncoder(response.getReceivableData().qr_image, null, QRGContents.Type.TEXT, dimen);
                            try {
                                bitmap = qrgEncoder.encodeAsBitmap();
                                qrScreenDesignBinding.qrImage.setImageBitmap(bitmap);
                            } catch (WriterException e) {
                                ViewUtils.showToast(context, e.toString());
                            }
                            alert.show();
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
