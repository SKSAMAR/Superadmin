package com.fintech.petoindia.util;

import android.Manifest;
import android.content.Context;
import android.os.Build;

import com.karumi.dexter.Dexter;
import com.karumi.dexter.MultiplePermissionsReport;
import com.karumi.dexter.PermissionToken;
import com.karumi.dexter.listener.PermissionRequest;
import com.karumi.dexter.listener.multi.MultiplePermissionsListener;
import com.fintech.petoindia.deer_listener.Receiver;

import java.util.List;

public class PermissionUtil {

    public static void givePermission(Context context, Receiver<Integer> receiver){
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.R) {
            Dexter.withContext(context)
                    .withPermissions(Manifest.permission.READ_EXTERNAL_STORAGE,
                            Manifest.permission.WRITE_EXTERNAL_STORAGE)
                    .withListener(new MultiplePermissionsListener() {
                        @Override
                        public void onPermissionsChecked(MultiplePermissionsReport multiplePermissionsReport) {
                            receiver.getData(1);
                        }

                        @Override
                        public void onPermissionRationaleShouldBeShown(List<PermissionRequest> list, PermissionToken permissionToken) {
                            receiver.getData(0);
                        }
                    })
                    .check();
        }
    }

        public static void givePermissions(Context context, String[] permissions, Receiver<Integer> receiver){
            Dexter.withContext(context)
                    .withPermissions(permissions)
                    .withListener(new MultiplePermissionsListener() {
                        @Override
                        public void onPermissionsChecked(MultiplePermissionsReport multiplePermissionsReport) {
                            receiver.getData(1);
                        }

                        @Override
                        public void onPermissionRationaleShouldBeShown(List<PermissionRequest> list, PermissionToken permissionToken) {
                            receiver.getData(1);
                        }
                    })
                    .check();
        }

    }
