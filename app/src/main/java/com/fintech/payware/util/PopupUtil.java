package com.fintech.payware.util;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;

import androidx.appcompat.app.AlertDialog;

import com.fintech.payware.R;
import com.fintech.payware.data.db.AppDatabase;
import com.fintech.payware.data.db.entities.User;
import com.fintech.payware.data.network.APIServices;
import com.fintech.payware.data.network.responses.RegularResponse;
import com.fintech.payware.databinding.TPinLayoutBinding;
import com.fintech.payware.pins.TPinRegister;

import java.util.Objects;

import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers;
import io.reactivex.rxjava3.annotations.NonNull;
import io.reactivex.rxjava3.core.Observer;
import io.reactivex.rxjava3.disposables.Disposable;
import io.reactivex.rxjava3.schedulers.Schedulers;

public class PopupUtil {

    static AlertDialog alertDialog;
    public static boolean access = true;

    public static void displayTPinDialog(Context context, String pin){
        if(alertDialog!=null){
            alertDialog.dismiss();
        }
        AlertDialog.Builder alert = new AlertDialog.Builder(context, R.style.CustomDialog);
        TPinLayoutBinding binding = TPinLayoutBinding.inflate(LayoutInflater.from(context));
        alert.setView(binding.getRoot());
        alertDialog = alert.create();
        alertDialog.setCanceledOnTouchOutside(false);
        alertDialog.show();
        binding.cancelButton.setOnClickListener(v -> alertDialog.dismiss());
        binding.confirmButton.setOnClickListener(v -> alertDialog.dismiss());
    }

    public static void tPinSystem(Context context, APIServices  apiServices){
        AppDatabase appDatabase = AppDatabase.getAppDatabase(context);
        User user = appDatabase.getUserDao().getRegularUser();
        MyAlertUtils.showProgressAlertDialog(context);
        apiServices.TpinSecuritySystem(user.getId(), user.getPassword() ,user.getToken())
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<RegularResponse>() {
                    @Override
                    public void onSubscribe(@NonNull Disposable d) {

                    }

                    @Override
                    public void onNext(@NonNull RegularResponse response) {
                        MyAlertUtils.dismissAlertDialog();
                        if(response.isStatus() && response.getResponse_code().equals(1)){
                            AlertDialog.Builder alert = new AlertDialog.Builder(context, R.style.CustomDialog);
                            TPinLayoutBinding binding = TPinLayoutBinding.inflate(LayoutInflater.from(context));
                            alert.setView(binding.getRoot());
                            AlertDialog alertDialog = alert.create();
                            alertDialog.setCanceledOnTouchOutside(false);
                            alertDialog.show();
                            binding.cancelButton.setOnClickListener(v -> alertDialog.dismiss());
                            binding.confirmButton.setOnClickListener(v -> {
                                if(!Objects.requireNonNull(binding.tPinEdit.getText()).toString().equals(response.getMessage())){
                                    MyAlertUtils.showAlertDialog(context, "Warning", "Provide your valid T-PIN", R.drawable.warning);
                                }else{
                                    access = true;
                                    alertDialog.dismiss();
                                }
                            });
                        }
                        else if(response.getResponse_code().equals(2)){
                            context.startActivity(new Intent(context, TPinRegister.class));
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
}
