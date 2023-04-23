package com.fintech.superadmin.util;

import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.AsyncTask;
import android.util.Log;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.databinding.BindingAdapter;

import com.bumptech.glide.Glide;
import com.github.twocoffeesoneteam.glidetovectoryou.GlideToVectorYou;
import com.github.twocoffeesoneteam.glidetovectoryou.GlideToVectorYouListener;
import com.fintech.superadmin.R;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.concurrent.ExecutionException;

public class BindingUtils {


    @BindingAdapter("loadLogoImage")
    public static void loadLogoImage(ImageView imageView, String url){
        try {
            Log.d("URL_INFO", "INITIAL == "+url);
            DownloadImageTask task = new DownloadImageTask(imageView);
            task.execute(url);
        }
        catch (NullPointerException e){
            e.fillInStackTrace();
        }
    }


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





    private static class DownloadImageTask extends AsyncTask<String, Void, String> {
        private ImageView imageView;

        public DownloadImageTask(ImageView imageView) {
            this.imageView = imageView;
        }

        @Override
        protected String doInBackground(String... urls) {
            String finalUrl = null;
            try {
                URL url = new URL(urls[0]);
                HttpURLConnection connection = (HttpURLConnection) url.openConnection();
                connection.setInstanceFollowRedirects(false);
                connection.setRequestMethod("GET");

                if (connection.getResponseCode() >= 300 && connection.getResponseCode() <= 399) {
                    // If the response code is a redirect, get the final URL from the "Location" header
                    String redirectUrl = connection.getHeaderField("Location");
                    finalUrl = new URL(redirectUrl).toString();
                } else {
                    // Otherwise, the final URL is the original URL
                    finalUrl = url.toString();
                    //Constants.LogoUrl = finalUrl;
                }
                Log.d("URL_INFO", "FINAL == "+url);
                // Download the image and save it to internal storage
                Bitmap bitmap = Glide.with(imageView).asBitmap().load(finalUrl).submit().get();
                saveImage(bitmap, "logo.png", imageView.getContext());
            } catch (IOException | ExecutionException | InterruptedException e) {
                e.printStackTrace();
            }

            return finalUrl;
        }

        @Override
        protected void onPostExecute(String finalUrl) {
            super.onPostExecute(finalUrl);
            // Load the image from internal storage
            try {
                Bitmap bitmap = loadImage("logo.png", imageView.getContext());
                imageView.setImageBitmap(bitmap);
            }catch (Exception e){
                e.fillInStackTrace();
            }
        }
    }

    private static void saveImage(Bitmap bitmap, String filename, Context context) {
        try {
            FileOutputStream outputStream = context.openFileOutput(filename, Context.MODE_PRIVATE);
            bitmap.compress(Bitmap.CompressFormat.PNG, 100, outputStream);
            outputStream.flush();
            outputStream.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private static Bitmap loadImage(String filename, Context context) {
        try {
            FileInputStream inputStream = context.openFileInput(filename);
            Bitmap bitmap = BitmapFactory.decodeStream(inputStream);
            inputStream.close();
            return bitmap;
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
    }


}
