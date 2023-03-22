package com.fintech.petoindia.util;

import android.app.AlertDialog;
import android.content.Context;
import android.view.LayoutInflater;

import com.fintech.petoindia.databinding.ConfirmationDialogDesignBinding;
import com.fintech.petoindia.listeners.TaskPerformer;

public class ConfirmationUtil {

    public static AlertDialog dialog;

    public static void showConfirmation(Context context, TaskPerformer listener){
        ConfirmationDialogDesignBinding binding = ConfirmationDialogDesignBinding.inflate(LayoutInflater.from(context));
        dialog = new AlertDialog.Builder(context).create();
        dialog.setView(binding.getRoot());
        binding.no.setOnClickListener(view-> dialog.dismiss());
        binding.yes.setOnClickListener(view2 -> {
            listener.yes();
            dialog.dismiss();
        });
        dialog.show();
    }

    public static void dismiss(){
        if(dialog!=null){
            dialog.dismiss();
        }
    }
}
