package com.fintech.payware.util;

import android.os.AsyncTask;

import com.fintech.payware.deer_listener.DownloadListener;

import java.io.InputStream;
import java.net.URL;

public class Downloader extends AsyncTask<Void, Void, InputStream> {
        String url;
        DownloadListener listener;

        public Downloader(String url, DownloadListener listener){
            this.url = url;
            this.listener = listener;
        }
        @Override
        protected InputStream doInBackground(Void... voids) {
            URL newurl;
            InputStream inputStream = null;
            try {
                newurl = new URL(url);
                inputStream =  newurl.openConnection().getInputStream();
            } catch (Exception e) {
                e.printStackTrace();
            }
            return inputStream;
        }

    @Override
    protected void onPostExecute(InputStream inputStream) {
        super.onPostExecute(inputStream);
        if(inputStream==null){
            listener.error("Failed do Download");
        }
        else{
            listener.success(inputStream);
        }
    }
}
