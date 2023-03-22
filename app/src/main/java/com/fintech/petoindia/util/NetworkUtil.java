package com.fintech.petoindia.util;

import android.content.Context;
import android.util.Log;

import com.fintech.petoindia.deer_listener.Receiver;

import java.io.File;
import java.net.URLConnection;

import javax.annotation.Nullable;

import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers;
import io.reactivex.rxjava3.core.Observable;
import io.reactivex.rxjava3.schedulers.Schedulers;
import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;

public class NetworkUtil{

    public static<T> void getNetworkResult(Observable<T> apiCall, @Nullable Context context, Receiver<T> receiver){
        if(context!=null) {
            DisplayMessageUtil.loading(context);
        }
        apiCall.subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(res->{
                    DisplayMessageUtil.dismissDialog();
                    receiver.getData(res);
                }, err->{
                    if(context!=null){
                        DisplayMessageUtil.error(context, err.getMessage());
                    }
                });
    }


    public static MultipartBody.Part prepareFilePart(String partName, String fileUri) {
        File file = new File(fileUri);
        String mimeType= URLConnection.guessContentTypeFromName(file.getName());
        Log.e("mimeType",mimeType);
        //create RequestBody instance from file
        RequestBody requestFile = RequestBody.create(file, MediaType.parse("image/*"));
        //RequestBody requestFile = RequestBody.create(MediaType.parse(mimeType), file);
        //MultipartBody.Part is used to send also the actual file name
        return MultipartBody.Part.createFormData(partName, file.getName(), requestFile);
    }

    private void getMultiPartData(){
        String fileName = "your file name with extension";
//        RequestBody requestBody = RequestBody.create(MediaType.parse("multipart/form-data"), file);
//        MultipartBody.Part.createFormData("file", fileName, requestBody);
    }


}
