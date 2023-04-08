package com.fintech.kkpayments.viewmodel;

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

import com.fintech.kkpayments.R;
import com.fintech.kkpayments.activities.addfunds.FundExchange;
import com.fintech.kkpayments.activities.addfunds.PayActivity;
//import com.fintech.paytoindia.activities.addfunds.PayActivityWithPayTm;
import com.fintech.kkpayments.activities.addfunds.PayActivityWebView;
import com.fintech.kkpayments.activities.addfunds.RequestOffline;
import com.fintech.kkpayments.data.db.entities.User;
import com.fintech.kkpayments.data.network.responses.RegularResponse;
import com.fintech.kkpayments.data.repositories.FundRepository;
import com.fintech.kkpayments.data_model.request.RequestedHistoryModel;
import com.fintech.kkpayments.deer_listener.Receiver;
import com.fintech.kkpayments.listeners.ChangerListener;
import com.fintech.kkpayments.listeners.ResetListener;
import com.fintech.kkpayments.listeners.WebViewPayment;
import com.fintech.kkpayments.util.Accessable;
import com.fintech.kkpayments.util.DisplayMessageUtil;
import com.fintech.kkpayments.util.MyAlertUtils;
import com.fintech.kkpayments.util.NetworkUtil;

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
public class FundViewModel extends ViewModel {

    private long mLastClickTime = 0;
    public FundRepository fundRepository;
    public String receiptImageEncoded = "";
    public String amount = "";
    public String payment_mode = "";
    public String transaction_id = "";
    public String remarks = "";
    public ChangerListener listener = null;
    public ResetListener resetListener = null;

    @Inject
    public FundViewModel(FundRepository fundRepository){
        this.fundRepository = fundRepository;
    }

    public void onRazorPayAddFund(View view){
        view.getContext().startActivity(new Intent(view.getContext(), PayActivity.class));
    }


    public void onOfflineAddFund(View view){
        view.getContext().startActivity(new Intent(view.getContext(), RequestOffline.class));
    }

    public void onUPIPay(View view){
        view.getContext().startActivity(new Intent(view.getContext(), PayActivityWebView.class));
    }

    public void onPayTmAddFund(View view){
       // view.getContext().startActivity(new Intent(view.getContext(), PayActivityWithPayTm.class));
    }

    public void onWalletExchange(View view){
        view.getContext().startActivity(new Intent(view.getContext(), FundExchange.class));
    }


    public void onSpinnerClick(View view){

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
        ArrayAdapter<String> adapter = new ArrayAdapter<>(view.getContext(), android.R.layout.simple_list_item_1,list);
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

    public void onRequestOffline(View view){
        //make the payment done..
        if (SystemClock.elapsedRealtime() - mLastClickTime < 1000){
            return;
        }
        else {

        if(amount.isEmpty()){
            MyAlertUtils.showServerAlertDialog(view.getContext(), "Enter valid amount");
        }
        else if(transaction_id.isEmpty()){
            MyAlertUtils.showServerAlertDialog(view.getContext(), "Enter valid Transaction Id");
        }
        else if(payment_mode.isEmpty()){
            MyAlertUtils.showServerAlertDialog(view.getContext(), "Select valid Payment Mode");
        }
        else if(receiptImageEncoded.isEmpty()){
            MyAlertUtils.showServerAlertDialog(view.getContext(), "Select a Receipt");
        }
        else{
            User user = fundRepository.appDatabase.getUserDao().getRegularUser();
            if(user!=null){
                MyAlertUtils.showProgressAlertDialog(view.getContext());
                fundRepository.apiServices.offlineWalletDemand(user.getMobile(), user.getPassword(), user.getToken(), amount, payment_mode, transaction_id, remarks, receiptImageEncoded)
                        .subscribeOn(Schedulers.io())
                        .observeOn(AndroidSchedulers.mainThread())
                        .subscribe(new Observer<RegularResponse>() {
                            @Override
                            public void onSubscribe(@NonNull Disposable d) {

                            }

                            @Override
                            public void onNext(@NonNull RegularResponse regularResponse) {
                                if(regularResponse.isStatus()){
                                    MyAlertUtils.showAlertDialog(view.getContext(), "Result", regularResponse.getMessage(), R.drawable.success);
                                    listener.eraseAll();
                                    payment_mode = "";
                                    receiptImageEncoded = "";
                                    remarks = "";
                                    amount = "";
                                    transaction_id = "";
                                }
                                else{
                                    MyAlertUtils.showServerAlertDialog(view.getContext(), regularResponse.getMessage());
                                }
                            }

                            @Override
                            public void onError(@NonNull Throwable e) {
                                MyAlertUtils.showServerAlertDialog(view.getContext(), e.getMessage());
                            }

                            @Override
                            public void onComplete() {

                            }
                        });
            }
         }

        }
        mLastClickTime = SystemClock.elapsedRealtime();
    }

    public void onFundExchangeClick(View view){
        User user = fundRepository.appDatabase.getUserDao().getRegularUser();
        if(user.getAepsbalance()!=null){
            if(user.getAepsbalance().isEmpty()){
                MyAlertUtils.showServerAlertDialog(view.getContext(), "Not Enough AePS Balance");
            }
            else if(amount.isEmpty()){
                MyAlertUtils.showServerAlertDialog(view.getContext(), "Provide valid amount");
            }
            else{

                if (SystemClock.elapsedRealtime() - mLastClickTime < 1000){
                    return;
                }
                else {
                    double leftBal = Double.parseDouble(user.getAepsbalance());
                    double currAmount = Double.parseDouble(amount);
                    if(leftBal<currAmount){
                        MyAlertUtils.showServerAlertDialog(view.getContext(), "Not Enough AePS Balance");
                    }
                    else{
                        fundRepository.exchangeWallet(view.getContext(), amount, resetListener);
                    }
                }
                mLastClickTime = SystemClock.elapsedRealtime();
            }
        }
    }


    public void getRequestedHistory(String transaction_id, Receiver<List<RequestedHistoryModel>> receiver){
        NetworkUtil.getNetworkResult(fundRepository.apiServices.getRequestHistory(transaction_id, "requestedHistory"), null, data -> {
            receiver.getData(data.getReceivableData());
        });
    }


    public void madeValidPayment(String amount, Context context, WebViewPayment viewPayment) {
        if (Accessable.isAccessable()){
            DisplayMessageUtil.loading(context);
            NetworkUtil.getNetworkResult(fundRepository.apiServices.payFundAmount(amount), context, result->{
                DisplayMessageUtil.dismissDialog();
                if(result.status){
                    viewPayment.webViewPage(result.message);
                }else{
                    DisplayMessageUtil.error(context, result.message);
                }
            });
        }
    }


}
