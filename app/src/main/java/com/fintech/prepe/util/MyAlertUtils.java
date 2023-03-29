package com.fintech.prepe.util;

import android.annotation.SuppressLint;
import android.app.ProgressDialog;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;

import androidx.appcompat.app.AlertDialog;

import com.fintech.prepe.R;
import com.fintech.prepe.databinding.CustomAlertDialogBinding;
import com.fintech.prepe.deer_listener.Receiver;

public class MyAlertUtils {

    public static AlertDialog alertDialog;
    public static ProgressDialog progressDialog;


    public static void showAlertDialog(Context context, String title, String message, int gifIcon){
        if(alertDialog!=null){
            alertDialog.dismiss();
        }
        if(progressDialog!=null){
            progressDialog.dismiss();
        }

        AlertDialog.Builder alert = new AlertDialog.Builder(context, R.style.CustomDialog);
        CustomAlertDialogBinding binding = CustomAlertDialogBinding.inflate(LayoutInflater.from(context));
        alert.setView(binding.getRoot());
        alertDialog = alert.create();
        alertDialog.setCanceledOnTouchOutside(false);
        alertDialog.show();
        binding.alertCancel.setOnClickListener(v -> alertDialog.dismiss());
        binding.alertOkay.setOnClickListener(v -> alertDialog.dismiss());
        binding.alertTitle.setText(title);
        binding.alertMessage.setText(message);
        binding.centerLogo.setImageResource(gifIcon);
    }


    public static void showDMTDialog(Context context, String message, int gifIcon){
        if(alertDialog!=null){
            alertDialog.dismiss();
        }
        if(progressDialog!=null){
            progressDialog.dismiss();
        }
        AlertDialog.Builder alert = new AlertDialog.Builder(context, R.style.CustomDialog);
        CustomAlertDialogBinding binding = CustomAlertDialogBinding.inflate(LayoutInflater.from(context));
        alert.setView(binding.getRoot());
        alertDialog = alert.create();
        alertDialog.setCanceledOnTouchOutside(false);
        alertDialog.show();
        binding.alertMessage.setText(message);
        binding.centerLogo.setImageResource(gifIcon);
        binding.centerLogo.setVisibility(View.GONE);
        binding.alertCancel.setOnClickListener(v -> alertDialog.dismiss());
        binding.alertOkay.setOnClickListener(v -> alertDialog.dismiss());
    }




    @SuppressLint("SetTextI18n")
    public static void showProgressAlertDialog(Context context){
        if(alertDialog!=null){
            alertDialog.dismiss();
        }
        if(progressDialog!=null){
            progressDialog.dismiss();
        }
        progressDialog = new ProgressDialog(context);
        progressDialog.setMessage("Please wait");
        progressDialog.setCanceledOnTouchOutside(false);
        progressDialog.show();
    }


    @SuppressLint("SetTextI18n")
    public static void showServerAlertDialog(Context context, String message){
        if(alertDialog!=null){
            alertDialog.dismiss();
        }
        if(progressDialog!=null){
            progressDialog.dismiss();
        }
        AlertDialog.Builder alert = new AlertDialog.Builder(context, R.style.CustomDialog);
        CustomAlertDialogBinding binding = CustomAlertDialogBinding.inflate(LayoutInflater.from(context));
        alert.setView(binding.getRoot());
        alertDialog = alert.create();
        alertDialog.setCanceledOnTouchOutside(false);
        alertDialog.show();
        binding.alertCancel.setVisibility(View.INVISIBLE);
        binding.alertOkay.setOnClickListener(v -> alertDialog.dismiss());
        binding.alertTitle.setText("Failed");
        binding.alertMessage.setText(message);
        binding.centerLogo.setImageResource(R.drawable.warning);
    }

    public static void dismissAlertDialog(){
        if(alertDialog!=null){
            alertDialog.dismiss();
        }
        if(progressDialog!=null){
            progressDialog.dismiss();
        }
    }

    public static void anotherAlertDialog(Context context, String message, String title, Integer gifIcon){
        AlertDialog alertDialogAnother;
        AlertDialog.Builder alert = new AlertDialog.Builder(context, R.style.CustomDialog);
        CustomAlertDialogBinding binding = CustomAlertDialogBinding.inflate(LayoutInflater.from(context));
        alert.setView(binding.getRoot());
        alertDialogAnother = alert.create();
        alertDialogAnother.setCanceledOnTouchOutside(false);
        alertDialogAnother.show();
        binding.alertCancel.setOnClickListener(v -> alertDialogAnother.dismiss());
        binding.alertOkay.setOnClickListener(v -> alertDialogAnother.dismiss());
        binding.alertTitle.setText(title);
        binding.alertMessage.setText(message);
        binding.centerLogo.setImageResource(gifIcon);
    }

    public static void showAnotherDialog(Context context, String title, String message, int gifIcon){
        AlertDialog.Builder alert = new AlertDialog.Builder(context, R.style.CustomDialog);
        CustomAlertDialogBinding binding = CustomAlertDialogBinding.inflate(LayoutInflater.from(context));
        alert.setView(binding.getRoot());
        AlertDialog alertDialog2 = alert.create();
        alertDialog2.setCanceledOnTouchOutside(false);
        alertDialog2.show();
        binding.alertCancel.setOnClickListener(v -> alertDialog2.dismiss());
        binding.alertOkay.setOnClickListener(v -> alertDialog2.dismiss());
        binding.alertTitle.setText(title);
        binding.alertMessage.setText(message);
        binding.centerLogo.setImageResource(gifIcon);
    }


    @SuppressLint("SetTextI18n")
    public static void showWarningAlertDialog(Context context, String message){
        if(alertDialog!=null){
            alertDialog.dismiss();
        }
        if(progressDialog!=null){
            progressDialog.dismiss();
        }
        AlertDialog.Builder alert = new AlertDialog.Builder(context, R.style.CustomDialog);
        CustomAlertDialogBinding binding = CustomAlertDialogBinding.inflate(LayoutInflater.from(context));
        alert.setView(binding.getRoot());
        alertDialog = alert.create();
        alertDialog.setCanceledOnTouchOutside(false);
        alertDialog.show();
        binding.alertCancel.setVisibility(View.INVISIBLE);
        binding.alertOkay.setOnClickListener(v -> alertDialog.dismiss());
        binding.alertTitle.setText("Warning");
        binding.alertMessage.setText(message);
        binding.centerLogo.setImageResource(R.drawable.warning);
    }


    public static void showActionAlertDialog(Context context, String title, String message, int gifIcon, Receiver<Boolean> receiver){
        if(alertDialog!=null){
            alertDialog.dismiss();
        }
        if(progressDialog!=null){
            progressDialog.dismiss();
        }

        AlertDialog.Builder alert = new AlertDialog.Builder(context, R.style.CustomDialog);
        CustomAlertDialogBinding binding = CustomAlertDialogBinding.inflate(LayoutInflater.from(context));
        alert.setView(binding.getRoot());
        alertDialog = alert.create();
        alertDialog.setCanceledOnTouchOutside(false);
        alertDialog.show();
        binding.alertCancel.setOnClickListener(v -> alertDialog.dismiss());
        binding.alertOkay.setOnClickListener(v -> alertDialog.dismiss());
        binding.alertTitle.setText(title);
        binding.alertMessage.setOnClickListener(vb-> receiver.getData(true));
        binding.alertMessage.setText(message);
        binding.centerLogo.setImageResource(gifIcon);
    }
}
