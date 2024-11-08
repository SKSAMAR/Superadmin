package com.fintech.superadmin.viewmodel;

import android.annotation.SuppressLint;
import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.SystemClock;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.view.WindowManager;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ListView;

import androidx.lifecycle.ViewModel;

import com.fintech.superadmin.R;
import com.fintech.superadmin.activities.addfunds.CashFreeAddWalletActivity;
import com.fintech.superadmin.activities.addfunds.FundExchange;
import com.fintech.superadmin.activities.addfunds.PayActivity;
//import com.fintech.superadmin.activities.addfunds.PayActivityWithPayTm;
import com.fintech.superadmin.activities.addfunds.PayActivityWebView;
import com.fintech.superadmin.activities.addfunds.PayUMoney;
import com.fintech.superadmin.activities.addfunds.RequestOffline;
import com.fintech.superadmin.data.db.entities.User;
import com.fintech.superadmin.data.repositories.FundRepository;
import com.fintech.superadmin.data_model.offline.OfflineBank;
import com.fintech.superadmin.data_model.request.RequestedHistoryModel;
import com.fintech.superadmin.deer_listener.Receiver;
import com.fintech.superadmin.listeners.ChangerListener;
import com.fintech.superadmin.listeners.ResetListener;
import com.fintech.superadmin.listeners.WebViewPayment;
import com.fintech.superadmin.model.PayuResponse;
import com.fintech.superadmin.util.Accessable;
import com.fintech.superadmin.util.DisplayMessageUtil;
import com.fintech.superadmin.util.MyAlertUtils;
import com.fintech.superadmin.util.NetworkUtil;
import com.google.android.material.textfield.TextInputEditText;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import dagger.hilt.android.lifecycle.HiltViewModel;
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers;
import io.reactivex.rxjava3.schedulers.Schedulers;
import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;

@HiltViewModel
public class FundViewModel extends ViewModel {

    private long mLastClickTime = 0;
    public FundRepository fundRepository;
    public MultipartBody.Part receipt;
    public String amount = "";
    public String payment_mode = "";
    public String transaction_id = "";
    public String remarks = "";
    public ChangerListener listener = null;
    public ResetListener resetListener = null;
    public List<OfflineBank> offlineBanks = new ArrayList<>();
    public OfflineBank selectedOfflineBank;


    @Inject
    public FundViewModel(FundRepository fundRepository) {
        this.fundRepository = fundRepository;
    }

    public void onRazorPayAddFund(View view) {
        view.getContext().startActivity(new Intent(view.getContext(), PayActivity.class));
    }


    public void onOfflineAddFund(View view) {
        view.getContext().startActivity(new Intent(view.getContext(), RequestOffline.class));
    }

    public void onUPIPay(View view) {
        view.getContext().startActivity(new Intent(view.getContext(), PayActivityWebView.class));
    }

    public void onPayU(View view) {
        view.getContext().startActivity(new Intent(view.getContext(), PayUMoney.class));
    }

    public void onWalletCashFree(View view) {
        view.getContext().startActivity(new Intent(view.getContext(), CashFreeAddWalletActivity.class));
    }

    public void onPayTmAddFund(View view) {
        // view.getContext().startActivity(new Intent(view.getContext(), PayActivityWithPayTm.class));
    }

    public void onWalletExchange(View view) {
        view.getContext().startActivity(new Intent(view.getContext(), FundExchange.class));
    }


    public String getSelectedBankName() {
        if (selectedOfflineBank != null && selectedOfflineBank.BANK_NAME != null) {
            return selectedOfflineBank.BANK_NAME;
        }
        return "";
    }

    public String getSelectedBankID() {
        if (selectedOfflineBank != null && selectedOfflineBank.ID != null) {
            return selectedOfflineBank.ID;
        }
        return "";
    }
    public void getOfflineBanks(Context context, Receiver<Boolean> receiver) {
        if (offlineBanks!=null && !offlineBanks.isEmpty()){
            receiver.getData(true);
            return;
        }
        NetworkUtil.getNetworkResult(fundRepository.apiServices.getOfflineBank("offline_banks"), context, result -> {
            if (result.getStatus()) {
                offlineBanks = result.getReceivableData();
                receiver.getData(true);
            } else {
                DisplayMessageUtil.error(context, result.getMessage());
            }
        });
    }


    public void onSpinnerClick(View view) {

        Dialog dialog = new Dialog(view.getContext());
        dialog.setContentView(R.layout.my_spinner_row);
        dialog.getWindow().setLayout(WindowManager.LayoutParams.MATCH_PARENT, WindowManager.LayoutParams.WRAP_CONTENT);
        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        dialog.show();
        EditText searchOperator = dialog.findViewById(R.id.SearchOperator);
        ListView myOperatorListView = dialog.findViewById(R.id.MyOperatorListView);
        List<String> list = new ArrayList<>();
        list.add("IMPS");
        list.add("NEFT");
        list.add("Offline");
        ArrayAdapter<String> adapter = new ArrayAdapter<>(view.getContext(), android.R.layout.simple_list_item_1, list);
        myOperatorListView.setAdapter(adapter);
        searchOperator.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                adapter.getFilter().filter(s);
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });

        myOperatorListView.setOnItemClickListener((parent, view1, position, id) -> {
            listener.changeData(adapter.getItem(position));
            payment_mode = adapter.getItem(position);
            dialog.dismiss();
        });
    }

    public void onBanksSpinnerClick(View view) {
        getOfflineBanks(view.getContext(), result->{

            Dialog dialog = new Dialog(view.getContext());
            dialog.setContentView(R.layout.my_spinner_row);
            dialog.getWindow().setLayout(WindowManager.LayoutParams.MATCH_PARENT, WindowManager.LayoutParams.WRAP_CONTENT);
            dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
            dialog.show();
            EditText searchOperator = dialog.findViewById(R.id.SearchOperator);
            ListView myOperatorListView = dialog.findViewById(R.id.MyOperatorListView);
            ArrayAdapter<OfflineBank> adapter = new ArrayAdapter<>(view.getContext(), android.R.layout.simple_list_item_1, offlineBanks);
            myOperatorListView.setAdapter(adapter);
            searchOperator.addTextChangedListener(new TextWatcher() {
                @Override
                public void beforeTextChanged(CharSequence s, int start, int count, int after) {

                }

                @Override
                public void onTextChanged(CharSequence s, int start, int before, int count) {
                    adapter.getFilter().filter(s);
                }

                @Override
                public void afterTextChanged(Editable s) {

                }
            });

            myOperatorListView.setOnItemClickListener((parent, view1, position, id) -> {
                selectedOfflineBank = adapter.getItem(position);
                TextInputEditText editText = view.getRootView().findViewById(R.id.bankName);
                editText.setText(getSelectedBankName());
                dialog.dismiss();
            });
        });
    }

    @SuppressLint("CheckResult")
    public void onRequestOffline(View view) {
        //make the payment done..
        if (SystemClock.elapsedRealtime() - mLastClickTime < 1000) {
            return;
        } else {

            if (amount.isEmpty()) {
                MyAlertUtils.showServerAlertDialog(view.getContext(), "Enter valid amount");
            } else if (transaction_id.isEmpty()) {
                MyAlertUtils.showServerAlertDialog(view.getContext(), "Enter valid Transaction Id");
            } else if (payment_mode.isEmpty()) {
                MyAlertUtils.showServerAlertDialog(view.getContext(), "Select valid Payment Mode");
            } else {
                MyAlertUtils.showProgressAlertDialog(view.getContext());
                fundRepository.apiServices.offlineWalletDemand(receipt, getTextPlain(getSelectedBankID()), getTextPlain(amount), getTextPlain(payment_mode), getTextPlain(transaction_id), getTextPlain(remarks), getTextPlain("1"))
                        .subscribeOn(Schedulers.io())
                        .observeOn(AndroidSchedulers.mainThread())
                        .subscribe(regularResponse -> {
                            if (regularResponse.isStatus()) {
                                MyAlertUtils.showAlertDialog(view.getContext(), "Result", regularResponse.getMessage(), R.drawable.success);
                                listener.eraseAll();
                                payment_mode = "";
                                receipt = null;
                                remarks = "";
                                amount = "";
                                transaction_id = "";
                            } else {
                                MyAlertUtils.showServerAlertDialog(view.getContext(), regularResponse.getMessage());
                            }
                        }, e -> {
                            MyAlertUtils.showServerAlertDialog(view.getContext(), e.getMessage());
                        });
            }

        }
        mLastClickTime = SystemClock.elapsedRealtime();
    }

    public void onFundExchangeClick(View view) {
        User user = fundRepository.appDatabase.getUserDao().getRegularUser();
        if (user.getAepsbalance() != null) {
            if (user.getAepsbalance().isEmpty()) {
                MyAlertUtils.showServerAlertDialog(view.getContext(), "Not Enough AePS Balance");
            } else if (amount.isEmpty()) {
                MyAlertUtils.showServerAlertDialog(view.getContext(), "Provide valid amount");
            } else {

                if (SystemClock.elapsedRealtime() - mLastClickTime < 1000) {
                    return;
                } else {
                    try {
                        double leftBal = Double.parseDouble(user.getAepsbalance());
                        double currAmount = Double.parseDouble(amount);
                        if (leftBal < currAmount) {
                            MyAlertUtils.showServerAlertDialog(view.getContext(), "Not Enough AePS Balance");
                        } else {
                            fundRepository.exchangeWallet(view.getContext(), amount, resetListener);
                        }
                    } catch (Exception e) {
                        e.printStackTrace();
                        MyAlertUtils.showServerAlertDialog(view.getContext(), e.getLocalizedMessage());
                    }
                }
                mLastClickTime = SystemClock.elapsedRealtime();
            }
        }
    }


    public void getPayuCredential(Context context, Receiver<PayuResponse> responsiveListener, String txn_id, String amount) {
        DisplayMessageUtil.loading(context);
        fundRepository.apiServices.getPayuCall(txn_id, amount)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(res -> {
                    DisplayMessageUtil.dismissDialog();
                    if (res.getStatus() && res.getResponse_code().equals(1)) {
                        responsiveListener.getData(res);
                    } else {
                        DisplayMessageUtil.error(context, res.getMessage());
                    }
                }, err -> DisplayMessageUtil.error(context, err.getLocalizedMessage()));
    }

    public void getPayuHashify(Context context, Receiver<String> responsiveListener, String hashData) {
        DisplayMessageUtil.loading(context);
        fundRepository.apiServices.getHashCallHashify(hashData)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(res -> {
                    DisplayMessageUtil.dismissDialog();
                    if (res.getStatus() && res.getResponse_code().equals(1)) {
                        responsiveListener.getData(res.getReceivableData());
                    } else {
                        DisplayMessageUtil.error(context, res.getMessage());
                    }
                }, err -> DisplayMessageUtil.error(context, err.getLocalizedMessage()));
    }

    public void getRequestedHistory(Context context, String transaction_id, Receiver<List<RequestedHistoryModel>> receiver) {
        fundRepository.apiServices.getRequestHistory(transaction_id, "requestedHistory")
                .subscribeOn(AndroidSchedulers.mainThread())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(result -> {
                    receiver.getData(result.getReceivableData());
                }, error -> {
                    DisplayMessageUtil.error(context, error.getLocalizedMessage());
                });
    }


    public void madeValidPayment(String amount, Context context, WebViewPayment viewPayment) {
        if (Accessable.isAccessable()) {
            DisplayMessageUtil.loading(context);
            NetworkUtil.getNetworkResult(fundRepository.apiServices.payFundAmount(amount, "gatewayType"), context, result -> {
                DisplayMessageUtil.dismissDialog();
                if (result.status) {
                    viewPayment.webViewPage(result.message);
                } else {
                    DisplayMessageUtil.error(context, result.message);
                }
            });
        }
    }


    public void setProfileImage(File file, Context context) {
        RequestBody requestFile = RequestBody.create(MediaType.parse("multipart/form-data"), file);
        receipt = MultipartBody.Part.createFormData("receipt", file.getName(), requestFile);
    }

    private RequestBody getTextPlain(String value) {
        return RequestBody.create(MediaType.parse("text/plain"), value);
    }
}