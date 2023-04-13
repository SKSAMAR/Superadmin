package com.fintech.paytcash.util;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.util.Base64;

import java.io.ByteArrayOutputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;

public class Base64Encoder {

    public static String encodeImage(Context context, Uri uri) {
        final InputStream imageStream;
        try {
            imageStream = context.getContentResolver().openInputStream(uri);
            final Bitmap selectedImage = BitmapFactory.decodeStream(imageStream);
            return Base64Encoder.getEncode(selectedImage);

        } catch (FileNotFoundException e) {
            e.printStackTrace();
            return e.getLocalizedMessage();
        }
    }

    private static String getEncode(Bitmap bm){
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        bm.compress(Bitmap.CompressFormat.PNG,100, baos);
        byte[] b = baos.toByteArray();
        return Base64.encodeToString(b, Base64.DEFAULT);
    }

}