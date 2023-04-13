package com.fintech.paytcash.util;

import android.content.Context;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;

import androidx.appcompat.app.AlertDialog;

import com.fintech.paytcash.R;
import com.fintech.paytcash.data.network.APIServices;
import com.fintech.paytcash.data.network.responses.RegularResponse;
import com.fintech.paytcash.databinding.MpinScreenDialogBinding;
import com.fintech.paytcash.deer_listener.TaskListener;

import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers;
import io.reactivex.rxjava3.schedulers.Schedulers;

public class MPINUtil {

    static AlertDialog alertDialog;


    public static void showMPINDialog(Context context, TaskListener<RegularResponse> taskListener, APIServices apiServices) {
        if (alertDialog != null) {
            alertDialog.dismiss();
        }
        AlertDialog.Builder alert = new AlertDialog.Builder(context, R.style.AlertDialogThemeFull2);
        MpinScreenDialogBinding binding = MpinScreenDialogBinding.inflate(LayoutInflater.from(context));
        alert.setView(binding.getRoot());
        alertDialog = alert.create();
        alertDialog.setCanceledOnTouchOutside(false);
        alertDialog.setCancelable(false);
        alert.setCancelable(false);
        alertDialog.show();
        binding.loginAgain.setOnClickListener(v -> {
            taskListener.onResponse(new RegularResponse(false ,3 ,3, "Login Again", 3));
            dismissMPinDialog();
        });
        setClick(binding);
        binding.mPin1.requestFocus();
        binding.continueButton.setOnClickListener(view->{

            if(binding.mPin1.getText().toString().trim().isEmpty()){
                binding.mPin1.requestFocus();
            }
            else if(binding.mPin2.getText().toString().trim().isEmpty()){
                binding.mPin2.requestFocus();
            }
            else if(binding.mPin3.getText().toString().trim().isEmpty()){
                binding.mPin3.requestFocus();
            }
            else if(binding.mPin4.getText().toString().trim().isEmpty()){
                binding.mPin4.requestFocus();
            }
            else{
                String m_pin = binding.mPin1.getText().toString().trim()+binding.mPin2.getText().toString().trim()+binding.mPin3.getText().toString().trim()+binding.mPin4.getText().toString().trim();
                checkMPinAuthorisation(context, taskListener, apiServices, m_pin);
            }
        });

    }


    public static void dismissMPinDialog(){
        if(alertDialog!=null){
            alertDialog.dismiss();
        }
    }


    private static void setClick(MpinScreenDialogBinding binding){

        binding.mPin1.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if(!s.toString().trim().isEmpty()){
                    binding.mPin2.requestFocus();
                }
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
        binding.mPin2.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if(!s.toString().trim().isEmpty()){
                    binding.mPin3.requestFocus();
                }
            }

            @Override
            public void afterTextChanged(Editable s) {
                if(s.toString().trim().isEmpty()){
                    binding.mPin1.requestFocus();
                }
            }
        });
        binding.mPin3.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if(!s.toString().trim().isEmpty()){
                    binding.mPin4.requestFocus();
                }
            }

            @Override
            public void afterTextChanged(Editable s) {
                if(s.toString().trim().isEmpty()){
                    binding.mPin2.requestFocus();
                }
            }
        });
        binding.mPin4.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                if(s.toString().trim().isEmpty()){
                    binding.mPin3.requestFocus();
                }
            }
        });
    }


    private static void checkMPinAuthorisation(Context context,TaskListener<RegularResponse> listener, APIServices apiServices, String m_pin){
        if(Accessable.isAccessable()){
            DisplayMessageUtil.loading(context);
            apiServices.verifyMPINStatus(m_pin, "verify_mpin")
                    .subscribeOn(Schedulers.io())
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribe(res->{
                        DisplayMessageUtil.dismissDialog();
                        listener.onResponse(new RegularResponse(res.getStatus(), res.getResponse_code(), res.getResponse_code(), res.getMessage(), res.getResponse_code()));
                    }, err-> DisplayMessageUtil.error(context, err.getLocalizedMessage()));
        }
    }


}
