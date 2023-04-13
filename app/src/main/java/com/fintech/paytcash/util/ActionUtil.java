package com.fintech.paytcash.util;

import android.content.Context;

import androidx.appcompat.app.AlertDialog;

import com.fintech.paytcash.deer_listener.ClickListener;

public class ActionUtil {

    public static void cameraPopUtil(Context context, ClickListener<Integer> listener){
            final CharSequence[] options = {"Take Photo", "Choose From Gallery","Cancel"};
            AlertDialog.Builder builder = new AlertDialog.Builder(context);
            builder.setTitle("Select Option");
            builder.setItems(options, (dialog, item) -> {
                if (options[item].equals("Take Photo")) {
                    dialog.dismiss();
                    listener.clickOn(Constant.CAMERA_IMAGE);

                } else if (options[item].equals("Choose From Gallery")) {
                    dialog.dismiss();
                    listener.clickOn(Constant.GALLERY_IMAGE);

                } else if (options[item].equals("Cancel")) {
                    dialog.dismiss();
                }
            });
            builder.show();
    }

}
