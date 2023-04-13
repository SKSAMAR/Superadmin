package com.fintech.paytcash.viewmodel;

import android.view.View;

import androidx.lifecycle.ViewModel;

import com.fintech.paytcash.R;
import com.fintech.paytcash.data.network.APIServices;
import com.fintech.paytcash.data.network.responses.NumberPayResponse;
import com.fintech.paytcash.data.repositories.MobileNumberPayRepository;
import com.fintech.paytcash.listeners.NumberPayListener;
import com.fintech.paytcash.listeners.ResetListener;
import com.fintech.paytcash.util.MyAlertUtils;

import javax.inject.Inject;

import dagger.hilt.android.lifecycle.HiltViewModel;
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers;
import io.reactivex.rxjava3.annotations.NonNull;
import io.reactivex.rxjava3.core.Observer;
import io.reactivex.rxjava3.disposables.Disposable;
import io.reactivex.rxjava3.schedulers.Schedulers;

@HiltViewModel
public class MobileNumberPayViewModel extends ViewModel {

    public String amount = null;
    public String receiver_id = null;
    public String receiver_mobile = null;
    public String receiver_status = null;
    public APIServices  apiServices;
    MobileNumberPayRepository repository;
    public NumberPayListener listener;

    public ResetListener resetListener;

    @Inject
    public MobileNumberPayViewModel(APIServices apiServices, MobileNumberPayRepository repository){
        this.apiServices = apiServices;
        this.repository = repository;
    }



    public void checkIfAccountExists(String mobile){
        repository.isNumberValidCheck(mobile, listener);
    }

    public void sendMoneyToContact(View view){
        if(amount==null || amount.isEmpty()){
            MyAlertUtils.showAlertDialog(view.getContext(), "Warning", "Enter a valid amount", R.drawable.warning);
        }else {
            apiServices.numberAuthenticatePay(receiver_mobile, amount)
                    .subscribeOn(Schedulers.io())
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribe(new Observer<NumberPayResponse>() {
                        @Override
                        public void onSubscribe(@NonNull Disposable d) {
                            MyAlertUtils.showProgressAlertDialog(view.getContext());
                        }

                        @Override
                        public void onNext(@NonNull NumberPayResponse numberPayResponse) {
                            if(numberPayResponse.isStatus()) {
                                MyAlertUtils.showAlertDialog(view.getContext(), "Success", numberPayResponse.getMessage(), R.drawable.success);
                                resetListener.resetRequiredData(true);
                            }
                            else{
                                MyAlertUtils.showAlertDialog(view.getContext(), "Failed", numberPayResponse.getMessage(), R.drawable.failed);
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

    public void requestMoneyToContact(View view){
        MyAlertUtils.showAlertDialog(view.getContext(), "Warning", "Coming soon, Currently in work..", R.drawable.warning);
    }

}
