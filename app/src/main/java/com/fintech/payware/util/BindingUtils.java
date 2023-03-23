package com.fintech.payware.util;

import android.annotation.SuppressLint;
import android.graphics.Bitmap;
import android.net.Uri;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.databinding.BindingAdapter;

import com.bumptech.glide.Glide;
import com.github.twocoffeesoneteam.glidetovectoryou.GlideToVectorYou;
import com.github.twocoffeesoneteam.glidetovectoryou.GlideToVectorYouListener;
import com.fintech.payware.R;

public class BindingUtils {


    @BindingAdapter("urlImage")
    public static void loadUrlImage(ImageView imageView, String url){
        try {
            if(!url.isEmpty()){

                if(url.contains(".svg")){
                    loadSvgUrl(imageView, url);
                }
                else{
                    Glide.with(imageView).load(url.trim()).error(R.drawable.no_images).placeholder(R.drawable.loading).into(imageView);                }
            }
        }
        catch (NullPointerException e){
            e.fillInStackTrace();
        }
    }

    @BindingAdapter("urIImage")
    public static void loadUrIImage(ImageView imageView, Uri imageUri){
        try {
            imageView.setImageURI(imageUri);
        }
        catch (NullPointerException e){
            e.fillInStackTrace();
        }
    }


    @BindingAdapter("ProfileUrlImage")
    public static void loadProfileUrlImage(ImageView imageView, String url){

        try {

            if(!url.isEmpty()){
                Glide.with(imageView).load(url).error(R.drawable.ic_baseline_account_circle_24).placeholder(R.drawable.ic_baseline_account_circle_24).into(imageView);
            }
        }
        catch (NullPointerException e){
            e.fillInStackTrace();
        }
    }

    @BindingAdapter("resourceImage")
    public static void loadResourceImage(ImageView imageView, Integer image){
        try {

            imageView.setImageResource(image);
        }
        catch (NullPointerException e){
            e.fillInStackTrace();
        }
    }

    @SuppressLint("SetTextI18n")
    @BindingAdapter("booleanTextResult")
    public static void booleanTextData(TextView textView, boolean result){
        try {
            if(result){
                textView.setText("YES");
            }
            else{
                textView.setText("No");
            }
        }
        catch (NullPointerException e){
            e.fillInStackTrace();
        }
    }


    @BindingAdapter("bitmapResourceImage")
    public static void loadBitmapResourceImage(ImageView imageView, Bitmap bitmap){
        try {
            imageView.setImageBitmap(bitmap);
        }
        catch (NullPointerException e){
            e.fillInStackTrace();
        }
    }

    @BindingAdapter("dataOfText")
    public static void setIntegerToText(TextView textView, Long data){

        textView.setText(String.valueOf(data));
    }

    @BindingAdapter("loadSvgImage")
    public static void loadSvgUrl(ImageView imageView, String url){
        try {

            if(!url.contains(".svg")){
                loadUrlImage(imageView, url);
                return;
            }

            GlideToVectorYou
                    .init()
                    .setPlaceHolder(R.drawable.loading,R.drawable.loading)
                    .with(imageView.getContext())
                    .withListener(new GlideToVectorYouListener() {
                        @Override
                        public void onLoadFailed() {
                            imageView.setImageResource(R.drawable.loading);
                        }

                        @Override
                        public void onResourceReady() {
                        }
                    })
                    .load(Uri.parse(url), imageView);
        }
        catch (NullPointerException e){
            e.fillInStackTrace();
        }
    }

}
